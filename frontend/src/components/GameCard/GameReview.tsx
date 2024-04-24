import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import StarRating from "../StarRating/Star";
import { Link } from "react-router-dom";
import { createReview, getThisReview, updateReview } from "../../services/ReviewService"; // Import the createReview function
import { Review } from "../../services/Models";

function GameReview() {
  const { id } = useParams<{ id?: string }>(); // Define id as optional
  const navigate = useNavigate();

  const [rating, setRating] = useState(0);
  const [reviewText, setReviewText] = useState("");
  const [updating, setUpdating] = useState(false);

  useEffect(() => {
    const fetchReviewDetails = async () => {
      if (reviewText === "" && id !== undefined && !updating) {
        try {
          const myReviews = await getThisReview(id);
          if (myReviews && (myReviews.stars !== undefined || myReviews.description !== undefined)) {
            setRating(myReviews.stars ?? 0);
            setReviewText(myReviews.description ?? "");
            setUpdating(true);
          }
        } catch (error) {
          console.error("Error fetching game details:", error);
        }
      }
    };
    fetchReviewDetails();
  }, [id, reviewText, updating]);

  const handleSubmit = async (e: { preventDefault: () => void; }) => {
    e.preventDefault();

    try {
      // Call the createReview function from services
      const review : Review = { stars: rating, description: reviewText, gameId: parseInt(id || "0")};
      if (updating) {  
        const response : Response = await updateReview(review);
        if (response.status == 200) {
          navigate(`/game/${id}`); // Redirect to game page if review is submitted successfully
        } else {
          console.error("Error submitting review:", await response.text());
        }
      } else {
        const response : Response = await createReview(review);
        if (response.status == 201) {
          navigate(`/game/${id}`); // Redirect to game page if review is submitted successfully
        } else {
          console.error("Error submitting review:", await response.text());
        }
      }

      
    } catch (error) {
      console.error("Error submitting review:", error);
    }
  };

  return (
    <>
      <Link className="return_link" to={`/game/${id}`}>
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
        <h2>Tilbake</h2>
      </Link>

      <form className="create_game_form" onSubmit={handleSubmit}>
        <h1>Vurder Spill {id}</h1>
        <label htmlFor="rating">Rating:</label>
        <StarRating rating={rating} onRate={setRating} />

        <label htmlFor="review">Omtale:</label>
        <textarea
          id="review"
          name="review"
          placeholder="Enter your review"
          value={reviewText}
          onChange={(e) => setReviewText(e.target.value)}
        />
        <br></br>
        <input type="submit" value="Send inn" />
      </form>
    </>
  );
}

export default GameReview;
