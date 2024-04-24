import './PlaylistView.css'
import PlaylistHeader from './PlaylistHeader';
import { useEffect, useState } from 'react';
import { Game, List } from "../../services/Models"
import { getGamesFromList } from '../../services/GameService';
import { useParams } from 'react-router-dom';
import { getList, removeGameFromList, updateListOrder, viewList } from '../../services/Listservice';
import { DndContext, useSensors, PointerSensor, closestCenter, DragEndEvent } from '@dnd-kit/core';
import { SortableContext, arrayMove, verticalListSortingStrategy } from '@dnd-kit/sortable';
import SortableGameInList from '../../components/Listview/Draggable';
import { restrictToParentElement, restrictToVerticalAxis } from '@dnd-kit/modifiers';
import NonEditableItemInList from '../../components/Listview/NonEditableListItem';

function PlaylistView() {
    
    const { id } = useParams<{ id?: string }>();
    const { view } = useParams<{ view?: string }>();
    const isView = view === 'view';

    const [list, setList] = useState<List>();
    const [games, setGames] = useState<Array<Game>>([]);
    const [items, setItems] = useState<Array<number>>([]);
    
    const handleRemoveGame = async (gameId: number) => {
        if (window.confirm("Er du sikker på at du vil fjerne spillet fra spillelisten?")) {
            if (list) {
                
                await removeGameFromList(list.id, gameId);
                window.location.reload();
                const updatedGames = games.filter(game => game.id !== gameId);
                setGames(updatedGames);
            }
        }
    };
    useEffect (() => {
        const fetchList = async () => {
            if (id == undefined) {
                return
            }
            if (isView) {
                const list = await viewList(parseInt(id));
                setList(list);
            } else {
                const list = await getList(parseInt(id));
                setList(list);
            }
            const games = await getGamesFromList(parseInt(id))
            setGames(games)
            const arrayOfNumbers = [...Array(games.length).keys()].map(i => games[i].id as number);
            setItems(arrayOfNumbers);
        }
        fetchList();
    }, [id, isView]);

    const sensors = useSensors({
        sensor: PointerSensor,
        options: {
            activationConstraint: {
                distance: 10
            }
        }
    });

    useEffect(() => {
        if (list && !isView) {
            updateListOrder(list.id, items);
        }
    }, [items, list, isView]);

    const handleEndDrag = (event: DragEndEvent) => {
        const {active, over} = event;
        if (active.id !== over?.id) {
            const oldIndex = items.findIndex(item => item === active.id);
            const newIndex = items.findIndex(item => item === over?.id);
            setItems(arrayMove(items, oldIndex, newIndex));
            setGames(arrayMove(games, oldIndex, newIndex));
        }
    }

    return (
        <DndContext sensors={sensors} collisionDetection={closestCenter} onDragEnd={handleEndDrag} modifiers={[restrictToVerticalAxis, restrictToParentElement]}>
        <div className="playlist-view">
            <div className='header'>
                <PlaylistHeader name={list?.name ?? "name not found"} games={games} emojis={[...Array(games.length).keys()].map(i => games[i].emoji as unknown as string).slice(0,4)} viewing={isView}/>
            </div>
            {isView ? 
            <>
            <div className="game-items-container">
                {
                    items.map((item) => {
                        const game = games.find(game => game.id === item);
                        return game ? <NonEditableItemInList key={item} game={game} /> : null;
                    })
                }
            </div>
            </> : <>
            <div className="game-items-container">
                <SortableContext items={items} strategy={verticalListSortingStrategy}>
                    {
                        items.map((item) => {
                            const game = games.find(game => game.id === item);
                            return game ? <SortableGameInList id={item} key={item} game={game} handleRemoveGame={handleRemoveGame} /> : null;
                        })
                    }
                </SortableContext>
            </div>
            </>}
        </div>
        </DndContext>
    )
}

export default PlaylistView;