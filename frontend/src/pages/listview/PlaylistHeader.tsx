import { Link, useParams } from 'react-router-dom';
import './PlaylistHeader.css';
import { Game } from '../../services/Models';
import { useNavigate } from 'react-router-dom';
import copy from 'copy-to-clipboard'; // Import the copy function from the copy-to-clipboard package
import { deleteList } from '../../services/Listservice';


const PlaylistHeader = ({ name, games, emojis, viewing }: { name: string, games: Array<Game>, emojis: Array<string>, viewing: boolean }) => {

    const navigate = useNavigate();
    const { id } = useParams<{ id?: string }>();
    

    const playLeave = () => {
        if (games.length > 0) {
            const id = games[0].id;
            navigate(`/game/${id}`); // Navigate to the game details page with the game ID
        }
    };

    const shuffleLeave = () => {
        if (games.length > 0) {
            const randomIndex = Math.floor(Math.random() * games.length); // Generate a random index
            const id = games[randomIndex].id;
            navigate(`/game/${id}`); // Navigate to the game details page with the game ID
        }
    };

    const shareList = () => {
        const currentURL = window.location.href + "/view"; // Get the current URL
        copy(currentURL); 
        alert('Link kopiert til utklippstavle.'); // Show a popup message
    };

    const handleDelete = async () => {
        if (window.confirm("Er du sikker på at du vil slette spillelisten?")) {
            try {
                if (id) {
                    await deleteList(parseInt(id));
                    navigate(`/favorites`);
                    window.alert("Spilleliste slettet.");
                    console.log('Playlist deleted successfully');
                }
            }
            catch (error) {
                console.log((error as Error).message)
                if ((error as Error).message === "Cant delete this list") {
                    window.alert("Du kan ikke slette denne spillelisten.");
                    return;
                }
                console.error('Error deleting playlist:', error);
            }
        }
    };

    return (
        <>
            <Link className="return-text" to="/favorites">
                <svg xmlns="http://www.w3.org/2000/svg" className="icon icon-tabler icon-tabler-arrow-back" width="34" height="34" viewBox="0 0 24 24" strokeWidth="2" stroke="#ffffff" fill="none" strokeLinecap="round" strokeLinejoin="round">
                    <path stroke="none" d="M0 0h24v24H0z" fill="none" />
                    <path d="M9 11l-4 4l4 4m-4 -4h11a4 4 0 0 0 0 -8h-1" />
                </svg>
                Gå tilbake  
            </Link>
            <div className="playlist-header">
                <div className="playlist-icon">
                    {emojis.map((emoji: string, index: number) => (
                        <div className="playlist-icon-emoji" key={index}>{emoji}</div>
                    ))}
                </div>
                <div className="playlist-info">
                    <div className="playlist-title">{name}</div>
                    <div className="playlist-buttons">
                        <div className="playlist-button play" onClick={playLeave}></div>
                        <div className="playlist-button shuffle" onClick={shuffleLeave}></div>
                        <div className="playlist-button share" onClick={shareList}></div>
                        {!viewing && <div className="playlist-button delete" onClick={handleDelete}></div> }
                    </div>
                </div>
            </div>
        </>
    );
}

export default PlaylistHeader;