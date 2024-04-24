import React from "react";
import { useEffect } from "react";
import { getMyReviews } from "../../services/ReviewService";
import { Review } from "../../services/Models";
import EditableReviewPrompt from "../../components/Review/EditableReviewPrompt";
import { Link } from "react-router-dom";
import "./MyReviews.css";

function MyReviews() {
  const [reviews, setReviews] = React.useState<Array<Review>>([]);

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        console.log("Fetching reviews");
        const list = await getMyReviews();
        list.reverse();
        setReviews(list);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchReviews();
  }, []);

  return (
    <div>
      {reviews.map((review) => (
        <div key={review.game?.id}>
          <Link className="gamelink" to={"/game/" + review.game?.id}>
            {review.game?.emoji} {review.game?.name}
          </Link>
          <EditableReviewPrompt
            stars={review.stars ?? 5}
            creator={review.user?.userName ?? "Navn Naveson"}
            text={review.description ?? "Sykt bra!"}
            id={review.game?.id ?? 0}
          />
        </div>
      ))}
    </div>
  );
}

export default MyReviews;
