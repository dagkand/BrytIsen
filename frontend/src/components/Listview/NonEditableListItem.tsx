import { useNavigate } from 'react-router-dom';
import { Game } from '../../services/Models';
import "./Draggable.css";

interface NonEditableItemInListProps {
    game: Game;
}

const NonEditableItemInList = (props: NonEditableItemInListProps) => {
    const navigate = useNavigate();
    const leave = () => {
        navigate(`/game/${props.game.id}`);
    };

    return (
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
        </div>
    );
}

export default NonEditableItemInList;