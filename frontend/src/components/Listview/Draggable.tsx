import {useSortable} from '@dnd-kit/sortable';
import {CSS} from '@dnd-kit/utilities';
import { Game } from '../../services/Models';
import { useNavigate } from 'react-router-dom';
import "./Draggable.css";

interface SortableGameInListProps {
    game: Game;
    id: number;
    handleRemoveGame: (gameId: number) => Promise<void>
}

function SortableGameInList(props: SortableGameInListProps) {
  const {
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
  } = useSortable({id: props.id});
  
  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
  };

  const navigate = useNavigate();
  const leave = () => {
    navigate(`/game/${props.id}`); // Navigate to the game details page with the game ID
  };

  const remove = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
      e.stopPropagation(); // Prevent the click event from propagating to the parent div
      props.handleRemoveGame(props.id); // Call the handleRemove function with the game ID
  };
    
  return (
    <div ref={setNodeRef} style={style} {...attributes} {...listeners}>
        <div className="game-item" onClick={leave}>
            <div className="game-icon">
                <div className="game-icon-emoji">{props.game.emoji}</div>
            </div>
            <div className="game-info">
                <div className="game-title">{props.game.name}</div>
                <div className="star-rating">
                    <div className="star-icon">
                        <span role="img" aria-label="star">⭐</span>
                    </div>
                    <div className="rating-value">{props.game.rating?.toFixed(1)}</div>                
                </div>
            </div>
            <div className="game-text">
                <div className="game-duration">Varighet: {props.game.duration_min}-{props.game.duration_max}</div>
                <div className="game-players">Antall spillere: {props.game.players_min}-{props.game.players_max}</div>
            </div>
            <div className="remove-button" onClick={remove}>
                <div className="remove-cross"></div>
            </div>
        </div>
    </div>
  );
}

export default SortableGameInList;