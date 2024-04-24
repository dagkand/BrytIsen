import { useNavigate } from "react-router-dom";
import "./FavoriteCard.css";

export interface FavoriteCardProps {
  emojilist: string[];
  listname: string;
  id: number;
  key: number;
}

const ListItemWrapper: React.FC<{ emoji: string }> = ({ emoji }) => {
  return <span className="listemoji">{emoji}</span>;
};

const FavoriteCard: React.FC<FavoriteCardProps> = ({
  emojilist,
  listname,
  id,
}) => {
  emojilist = emojilist.slice(0, 4);
  const navigate = useNavigate();
  const navToList = () => {
    console.log("Navigating to list");
    navigate(`/lists/${id}`);
  };
  return (
    <div className="favorite-card" onClick={navToList}>
      <div className="favorite-card_thumbnail">
        {emojilist.map((emoji: string, index: number) => (
          <ListItemWrapper key={index} emoji={emoji} />
        ))}
      </div>
      <a className="listname">{listname}</a>
    </div>
  );
};

export default FavoriteCard;
