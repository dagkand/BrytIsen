import React from 'react';
import { useNavigate } from 'react-router-dom';
import './GameCard.css';

export interface GameCardProps {
  key: number;
  emoji: string;
  name: string;
  id?: number; // Add id prop
  players: number;
  rating: number;
}

const GameCard: React.FC<GameCardProps> = ({ emoji, name, id, rating }) => {
  const navigate = useNavigate();

  const leave = () => {
    navigate(`/game/${id}`); // Navigate to the game details page with the game ID
  };

  return (
    <div className="games-card" onClick={leave}>
      <h1 className="game-card-emoji">{emoji}</h1>
      <h3>{name}</h3>
        <div className="star-rating">
        <div className="star-icon">
          <span role="img" aria-label="star">⭐</span>
        </div>
        <div className="rating-value">{rating.toFixed(1)}</div>
      </div>
    </div>
  );
}

export default GameCard;