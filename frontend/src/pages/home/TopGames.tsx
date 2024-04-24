import { useEffect, useState } from "react";
import GameCard from "../../components/GameCard/GameCard";
import { Game } from "../../services/Models";
import { getGames } from "../../services/GameService";
import "./TopGames.css";

const TopGames: React.FC = () => {
  
  const [, setGames] = useState<Array<Game>>([]);
  const [filteredGames, setFilteredGames] = useState<Array<Game>>([]);


  useEffect(() => {
    const fetchGames = async () => {
      try {
        const list = await getGames();
        setGames(list);
        const topGames = list
          .slice()
          .sort((a, b) => (a.rating ?? 0) < (b.rating ?? 0) ? 1 : -1)
          .slice(0, 10);

        setFilteredGames(topGames);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchGames();
  }, []);

  return (
    <div>
    <h1 className="top-games-header">Topp ti spill</h1>
    <div className="game-grid">
      {filteredGames.map((game) => (
        <GameCard
          emoji={game.emoji ?? "🧪"}
          name={game.name ?? "Default"}
          key={game.id ?? 0}
          id={game.id}
          players={1} 
          rating={game.rating ?? 0}        />
      ))}
    </div>
    </div>
  );
}

export default TopGames;


