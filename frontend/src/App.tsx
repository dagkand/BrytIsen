import "./App.css";
import Header from "./components/header/Header";
import { Route, Routes } from "react-router-dom";
import TopGames from "./pages/home/TopGames";
import Favorites from "./pages/home/Favorites";
import AllGames from "./pages/home/AllGames";
import CreateGame from "./pages/creategame/CreateGame";
import GameDetails from "./components/GameCard/GameDetails";
import UserForm from "./components/UserForm/UserForm";
import { GoogleOAuthProvider } from "@react-oauth/google";
import Login from "./components/LogIn/Login";
import LoginSuccess from "./components/LogIn/LoginSuccess";
import Welcome from "./components/LogIn/Welcome";
import ListView from "./pages/listview/PlaylistView";
import GameReview from "./components/GameCard/GameReview";
import MyReviews from "./pages/myreviews/MyReviews";
import Logout from './components/LogOut/Logout'

// This way of handling the clientId is probaly not secure, but it works for now
const clientId =
  "721101879951-1h9gbapa71463dp1ubv3hiuel63td6mq.apps.googleusercontent.com";

function App() {
  return (
    <GoogleOAuthProvider clientId={clientId}>
      <Header />
      <main>
      <Routes>
        <Route path="/" element={<TopGames />} />
        <Route path="/favorites" element={<Favorites />} />
        <Route path="/all" element={<AllGames />} />
        <Route path="/create" element={<CreateGame />} />
        <Route path="/game/:id" element={<GameDetails />} />
        <Route path="/lists/:id" element={<ListView />} />
        <Route path="/lists/:id/:view" element={<ListView />} />
        <Route path="/register" element={<UserForm />} />
        <Route path="/login/:redirected" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/login/success" element={<LoginSuccess />} />
        <Route path="/welcome" element={<Welcome />} />
        <Route path="/review/:id" element={<GameReview />} />
        <Route path="reviews" element={<MyReviews />} />
        <Route path="/logout" element={<Logout />} />
      </Routes>
      </main>
      <footer>
        <hr />
        <p>Laget av Gruppe24</p>
      </footer>
    </GoogleOAuthProvider>
  );
}

export default App;
