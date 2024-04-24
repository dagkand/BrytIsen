import { useState } from "react";
import "./Star.css";

function StarRating({ rating, onRate }: { rating: number, onRate: (newRating: number) => void }) {
  const [hoverRating, setHoverRating] = useState(0);

  const handleMouseEnter = (index: number) => {
    setHoverRating(index + 1);
  };

  const handleMouseLeave = () => {
    setHoverRating(rating); // Use the actual rating on mouse leave
  };

  const handleClick = (newRating: number) => {
    onRate(newRating);
  };

  return (
    <div className="star-rating">
      {[...Array(5)].map((_, index) => (
        <span
          key={index}
          className={`star ${
            hoverRating > index || rating > index ? "filled" : ""
          }`}
          onMouseEnter={() => handleMouseEnter(index)}
          onMouseLeave={handleMouseLeave}
          onClick={() => handleClick(index + 1)}
        >
          ★
        </span>
      ))}
    </div>
  );
}

export default StarRating;