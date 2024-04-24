import { useState } from "react";
import "./Review.css";

/*
 * Review component
 * @param stars: number - rating of the review
 * @param creator: string - name of the creator of the review
 * @param text: string - text of the review
 */

const ReviewPrompt = ({
  stars,
  creator,
  text,
}: {
  stars: number;
  creator: string;
  text: string;
}) => {
  const [showFullDesc, setShowFullDesc] = useState(false);

  const toggleShowFullDesc = () => {
    setShowFullDesc(!showFullDesc);
  };

  const maxlength = 80;

  return (
    <div className="reviewDiv">
      <h1 className="stars">
        {[...Array(5)].map((_, index) => (
          <span key={index}>
            {index < stars ? (
              <svg
                className="svgStar"
                width="32"
                height="30"
                viewBox="0 0 42 40"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M14.112 12.4565L2.41532 14.1524L2.20815 14.1945C1.89454 14.2778 1.60864 14.4428 1.37965 14.6727C1.15066 14.9025 0.986788 15.1891 0.904762 15.503C0.822736 15.817 0.825498 16.147 0.912766 16.4596C1.00003 16.7721 1.16868 17.0558 1.40148 17.2819L9.87515 25.53L7.87681 37.1809L7.85298 37.3825C7.83378 37.7069 7.90114 38.0305 8.04814 38.3203C8.19514 38.6101 8.41652 38.8555 8.6896 39.0316C8.96268 39.2077 9.27764 39.3081 9.60225 39.3224C9.92686 39.3367 10.2494 39.2645 10.537 39.1132L20.998 33.6132L31.4351 39.1132L31.6185 39.1975C31.9211 39.3167 32.25 39.3533 32.5714 39.3034C32.8928 39.2536 33.1951 39.1191 33.4474 38.9138C33.6997 38.7086 33.8928 38.4399 34.007 38.1354C34.1212 37.8308 34.1523 37.5014 34.0971 37.1809L32.097 25.53L40.5743 17.28L40.7173 17.1242C40.9216 16.8726 41.0555 16.5714 41.1055 16.2511C41.1554 15.9309 41.1196 15.6032 41.0016 15.3014C40.8837 14.9995 40.6878 14.7343 40.434 14.5328C40.1802 14.3313 39.8775 14.2006 39.5568 14.1542L27.8601 12.4565L22.6315 1.85986C22.4802 1.55284 22.246 1.2943 21.9553 1.11352C21.6647 0.93273 21.3293 0.836914 20.987 0.836914C20.6447 0.836914 20.3093 0.93273 20.0186 1.11352C19.728 1.2943 19.4938 1.55284 19.3425 1.85986L14.112 12.4565Z"
                  fill="white"
                />
              </svg>
            ) : (
              <svg
                className="svgStar"
                width="32"
                height="30"
                viewBox="0 0 42 40"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M14.112 12.4565L2.41532 14.1524L2.20815 14.1945C1.89454 14.2778 1.60864 14.4428 1.37965 14.6727C1.15066 14.9025 0.986788 15.1891 0.904762 15.503C0.822736 15.817 0.825498 16.147 0.912766 16.4596C1.00003 16.7721 1.16868 17.0558 1.40148 17.2819L9.87515 25.53L7.87681 37.1809L7.85298 37.3825C7.83378 37.7069 7.90114 38.0305 8.04814 38.3203C8.19514 38.6101 8.41652 38.8555 8.6896 39.0316C8.96268 39.2077 9.27764 39.3081 9.60225 39.3224C9.92686 39.3367 10.2494 39.2645 10.537 39.1132L20.998 33.6132L31.4351 39.1132L31.6185 39.1975C31.9211 39.3167 32.25 39.3533 32.5714 39.3034C32.8928 39.2536 33.1951 39.1191 33.4474 38.9138C33.6997 38.7086 33.8928 38.4399 34.007 38.1354C34.1212 37.8308 34.1523 37.5014 34.0971 37.1809L32.097 25.53L40.5743 17.28L40.7173 17.1242C40.9216 16.8726 41.0555 16.5714 41.1055 16.2511C41.1554 15.9309 41.1196 15.6032 41.0016 15.3014C40.8837 14.9995 40.6878 14.7343 40.434 14.5328C40.1802 14.3313 39.8775 14.2006 39.5568 14.1542L27.8601 12.4565L22.6315 1.85986C22.4802 1.55284 22.246 1.2943 21.9553 1.11352C21.6647 0.93273 21.3293 0.836914 20.987 0.836914C20.6447 0.836914 20.3093 0.93273 20.0186 1.11352C19.728 1.2943 19.4938 1.55284 19.3425 1.85986L14.112 12.4565Z"
                  fill="gray"
                />
              </svg>
            )}
          </span>
        ))}
      </h1>
      <h2>{creator} said:</h2>
      {showFullDesc ? (
        <div>
          {text}
          <button onClick={toggleShowFullDesc}>
            Vis mindre
            <svg
              width="20"
              height="10"
              viewBox="0 0 31 19"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M2 2.88965L15.5 16.5428L29 2.88965"
                stroke="white"
                strokeWidth="4"
                strokeLinecap="round"
                strokeLinejoin="round"
                transform="scale(1, -1) translate(0, -19)"
              />
            </svg>
          </button>
        </div>
      ) : (
        <div>
          {text && (
            <>
              {text.length > maxlength
                ? `${text.slice(0, maxlength)}...`
                : text}
              {text.length > maxlength && (
                <button className="toggleShow" onClick={toggleShowFullDesc}>
                  Vis mer
                  <svg
                    width="20"
                    height="10"
                    viewBox="0 0 31 19"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      d="M2 2.88965L15.5 16.5428L29 2.88965"
                      stroke="white"
                      strokeWidth="4"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                    />
                  </svg>
                </button>
              )}
            </>
          )}
        </div>
      )}
    </div>
  );
};

export default ReviewPrompt;
