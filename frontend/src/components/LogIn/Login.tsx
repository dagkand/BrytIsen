import { useEffect } from "react";
import Button from "../Button/Button";
import "./Login.css";
import { isLoggedIn } from "../../services/UserService";
import { useNavigate, useParams } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const redirected = useParams<{ redirected?: string }>();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const loggedIn = await isLoggedIn();
        if (loggedIn) {
          navigate("/welcome");
        }
      } catch (error) {
        console.error("Error fetching user:", error);
      }
    };
    fetchData();
  }, [navigate]);

  const handleLogin = () =>
    (window.location.href =
      "http://localhost:8080/oauth2/authorization/google");

  return (
    <div className="login_div">
      {redirected.redirected && (
        <p>Du må være logget inn for å bruke denne funksjonen</p>
      )}
      <h2>Klikk for å logge inn eller registrere bruker:</h2>
      <Button className="login_button" onClick={handleLogin}>
        <p className="login_button_text">Logg inn med Google</p>
      </Button>
    </div>
  );
};

export default Login;
