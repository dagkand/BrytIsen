import React, { useEffect, useState, useCallback } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import { getGame } from "../../services/GameService";
import { Game, List, Review } from "../../services/Models";
import "./GameDetails.css";
import Timer from "../Timer/Timer";
import { getGamesReviews } from "../../services/ReviewService";
import ReviewPrompt from "../Review/ReviewPrompt";
import ReportFlag from "../Flag/Flag.tsx";
import useAuthCheck from "../../services/AuthService.ts";
import { addGameToList, getMyLists } from "../../services/Listservice.ts";

const CreateReviewButton: React.FC<{ id: number }> = ({ id }) => {
  const navigate = useNavigate();


  return (
    <>
      <button onClick={() => navigate(`/review/${id}`)}>
        Skriv en anmeldelse
      </button>
    </>
  );
};

const GameDetails: React.FC = () => {
  const { id } = useParams<{ id?: string }>();
  const [game, setGame] = useState<Game | null>(null);
  const [reviews, setReviews] = useState<Review[]>([]);
  const [lists, setLists] = useState<Array<List>>([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);


  useAuthCheck({ setLoggedIn: setIsLoggedIn, shouldRedirect: false });

  const [isDropdownVisible, setDropdownVisible] = useState(false);
  function toggleDropdown() {
    setDropdownVisible(!isDropdownVisible);
  }

  const doAddGameToList = (listId: number, gameId: number) => {
    addGameToList(listId, gameId);
    toggleDropdown();
  };

  const fetchGameDetails = useCallback(async () => {
    if (id) {
      try {
        const gameData = await getGame(id);
        setGame(gameData);
        // Fetch reviews for the game
        const gameReviews = await getGamesReviews(Number(id));
        setReviews(gameReviews);
      } catch (error) {
        console.error("Error fetching game details:", error);
      }
    }
  }, [id]);

  useEffect(() => {
    const fetchLists = async () => {
      if (!isLoggedIn) return;
      try {
        const myLists = await getMyLists();
        setLists(myLists);
      } catch (error) {
        console.error("Error fetching lists:", error);
      }
    };
    fetchLists();
  }, [isLoggedIn]);

  function shareGame(): void {
    // Check if the Clipboard API is supported by the browser
    if (!navigator.clipboard) {
      console.error("Clipboard API not supported");
      return;
    }

    // Get the current URL
    const url: string = window.location.href;

    // Copy the URL to the clipboard
    navigator.clipboard
      .writeText(url)
      .then(() => {
        console.log("URL copied to clipboard");
        alert('Link kopiert til utklippstavle.'); // Show a popup message
      })
      .catch((error) => {
        console.error("Failed to copy URL to clipboard:", error);
      });
  }

  useEffect(() => {
    fetchGameDetails();
  }, [fetchGameDetails]);

  if (!game) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <>
      <Link className="return-text" to="/all">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="icon icon-tabler icon-tabler-arrow-back"
          width="34"
          height="34"
          viewBox="0 0 24 24"
          strokeWidth="2"
          stroke="#ffffff"
          fill="none"
          strokeLinecap="round"
          strokeLinejoin="round"
        >
          <path stroke="none" d="M0 0h24v24H0z" fill="none" />
          <path d="M9 11l-4 4l4 4m-4 -4h11a4 4 0 0 0 0 -8h-1" />
        </svg>
        Returner
      </Link>
      <div className="container">
        <div className="game-header">
          <div className="game-header-emoji">{game.emoji}</div>
          <div className="header-right">
            <h2>{game.name}</h2>
            <div className="players">
              <p>
                <span>Min Spillere: {game.players_min}</span>
                <span>Max Spillere: {game.players_max}</span>
              </p>
              <p>
                Varighet: {game.duration_min} til {game.duration_max} minutter
              </p>
            </div>
          </div>
        </div>
        <div className="subheader-row">
          <div className="timer">
            <span>Timer:</span> <Timer />{" "}
          </div>
          <div className="lt-spilleliste-kapp" onClick={() => toggleDropdown()}>
            Legg til i spilleliste
            {isDropdownVisible && (
              <div className="game-dropdown-menu">
                {lists.map((list) => (
                  <div
                    key={list.id}
                    className="game-dropdown-item"
                    onClick={() =>
                      doAddGameToList(Number(list.id), game?.id ?? 0)
                    }
                  >
                    <p>{list.name}</p>
                  </div>
                ))}
              </div>
              )}
          </div>
          <button onClick={shareGame}>Del Spillet</button>
        </div>
        <h2>Beskrivelse</h2>
        <p className="description">{game.description}</p>
        <h2>Regler</h2>
        <p className="description">{game.rules}</p>
        <h2>Rapporter</h2>
        <p>Rapporter spillet hvis du føler at innholdet er upassende.</p>
        <div className="reports">
          <p>
            <span className="label">
              Antall ganger rapportert: {game.reportCount}
            </span>
          </p>
          <p>
            <span className="label">Rapporter:</span>{" "}
            <span>
              <ReportFlag id={game.id ?? 0} onUpdate={fetchGameDetails} />
            </span>
          </p>
        </div>
        <h2>Vurderinger</h2>
        <div className="vurderinger">
          <p>
            <span>Antall Vurderinger: {game.reviewCount}</span>
            <span>Vurdering: {game.rating}</span>
          </p>
        </div>
        <div className="reviews">
          {isLoggedIn && <CreateReviewButton id={game.id ?? 0} /> }
          {reviews.map((review: Review, index) => (
            <ReviewPrompt
              key={index}
              stars={review.stars ?? 0}
              creator={review.user?.userName ?? ""}
              text={review.description ?? ""}
            />
          ))}
        </div>
      </div>
    </>
  );
};

export default GameDetails;
