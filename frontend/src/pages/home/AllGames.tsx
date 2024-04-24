import React, { useState, useEffect } from 'react';
import "./AllGames.css";
import { getGames } from "../../services/GameService";
import { Category, Game } from "../../services/Models";
import GameCard from "../../components/GameCard/GameCard";
import Search from "../../components/Search/Search";
import Filter from "../../components/Filter/Filter";

const AllGames: React.FC = () => {
  const [games, setGames] = useState<Array<Game>>([]);
  const [filteredGames, setFilteredGames] = useState<Array<Game>>([]);

  useEffect(() => {
    const fetchGames = async () => {
      try {
        const list = await getGames();
        setGames(list);
        setFilteredGames(list);
        console.log("Games fetched:", list);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchGames();
  }, []);

  const handleSearch = (searchTerm: string) => {
    const results = games.filter((game) =>
      (game.name ?? "").toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredGames(results);
  };

  const handleFilter = (numPlayers: number, minDuration: number, maxDuration: number, categories: Array<Category>) => {
    const results = games.filter(game => {
      const gameMinPlayers = game.players_min ?? 0;
      const gameMaxPlayers = game.players_max ?? Number.MAX_SAFE_INTEGER;
      const gameMinDuration = game.duration_min ?? 0;
      const gameMaxDuration = game.duration_max ?? Number.MAX_SAFE_INTEGER;

      const meetsPlayerCriteria =
        numPlayers === 0 ||
        (gameMinPlayers <= numPlayers && gameMaxPlayers >= numPlayers);

      const meetsDurationCriteria =
        (minDuration === 0 && maxDuration === 24) ||
        (gameMaxDuration >= minDuration && gameMinDuration <= maxDuration);

      const hasCategory = categories.length === 0 || categories.every(category => 
        game.categories?.some(gameCategory => gameCategory.name === category.name)
      );
  
      
      return meetsPlayerCriteria && meetsDurationCriteria && hasCategory;
    });
    setFilteredGames(results);
  };

  useEffect(() => {
    const mobileFilter = document.querySelector(".mobile-filter");
    const hamburgerMenu = document.querySelector(".hamburger-menu");
    const closeHamburger = document.querySelector(".close-hamburger");
    const applyFilter = document.querySelector(".apply-filter-button");

    const toggleMobileFilter = () => {
      mobileFilter?.classList.toggle("is-active");
    };

    hamburgerMenu?.addEventListener("click", toggleMobileFilter);
    closeHamburger?.addEventListener("click", toggleMobileFilter);
    applyFilter?.addEventListener("click", toggleMobileFilter);

    // Clean up function to remove event listeners when component unmounts
    return () => {
      hamburgerMenu?.removeEventListener("click", toggleMobileFilter);
      closeHamburger?.removeEventListener("click", toggleMobileFilter);
    };
  }, []);

  return (
    <div id="parent">
      <div id="narrow" className="search-and-filter">
        <Search onSearch={handleSearch} />
        <Filter onFilterApplied={handleFilter} />
      </div>
      <div className="hamburger-menu">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="44"
          height="44"
          viewBox="0 0 24 24"
          strokeWidth="2"
          stroke="#ffffff"
          fill="none"
          strokeLinecap="round"
          strokeLinejoin="round"
        >
          <path stroke="none" d="M0 0h24v24H0z" fill="none" />
          <path d="M4 6l16 0" />
          <path d="M4 12l16 0" />
          <path d="M4 18l16 0" />
        </svg>
        Filtrer
      </div>
      <div className="mobile-search">
        <Search onSearch={handleSearch} />
      </div>
      <div className="mobile-filter">
        <div className="close-hamburger">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="44"
            height="44"
            viewBox="0 0 24 24"
            strokeWidth="2"
            stroke="#ffffff"
            fill="none"
            strokeLinecap="round"
            strokeLinejoin="round"
          >
            <path stroke="none" d="M0 0h24v24H0z" fill="none" />
            <path d="M18 6l-12 12" />
            <path d="M6 6l12 12" />
          </svg>
          Lukk meny
        </div>
        <Filter onFilterApplied={handleFilter} />
      </div>
      <div id="wide" className="game-grid">
        {filteredGames.map((game) => (
          <GameCard
            emoji={game.emoji ?? "🧪"}
            name={game.name ?? "Default"}
            key={game.id ?? 0}
            id={game.id}
            players={1} 
            rating={game.rating ?? 0}          />
        ))}
      </div>
    </div>
  );
};

export default AllGames;
