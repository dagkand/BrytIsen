import React, { useState } from "react";
import Button from "../Button/Button";
import { registerUser } from "../../services/UserService";
import "./UserForm.css";
import { User } from "../../services/Models";
import { useNavigate } from "react-router-dom";

const UserForm: React.FC = () => {
  const [username, setUsername] = useState("");

  useState(() => {
    const hash = window.location.hash;
        const tokenFromUrl = new URLSearchParams(hash.substring(1)).get('token');

        if (tokenFromUrl) {
            console.log('tokenFromUrl', tokenFromUrl);
            sessionStorage.setItem('token', tokenFromUrl);
            window.history.replaceState({}, document.title, window.location.pathname);
            window.location.reload();
        }
  });
  
  const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value);
  };

  //   const handleSubmit = () => {
  //         // Sjekk om brukernavn er i eksisterende bukernavn.
  //         // Les av en liste med eksisterende bukernavn. names.json fil

  //     console.log(username);
  //   };

  const navigate = useNavigate();
  const leave = () => {
    navigate("/all");
  };

  const handleSubmit = async () => {
    console.log("ER DU HER?");
    console.log(username);

    try {
        const user : User = { userName: username }
        const response = await registerUser(user);
        if (response.status === 201) {
            leave();
        } else if (response.status === 409) {
            console.error("Brukernanvet allerede i bruk");
        } else {
            console.error("Feil ved registrering av bruker");
        }
    } catch (error) {
      console.error("Det oppstod en feil ved henting av brukernavn:", error);
      // Implementer feilhåndtering, f.eks. vise en feilmelding til brukeren
    }
    
    
  };

  return (
    <div className="user-form-container">
      <label htmlFor="username" className="username-label">
        Brukernavn
      </label>
      <input
        id="username"
        type="text"
        value={username}
        onChange={handleUsernameChange}
        className="username-input"
        autoComplete="off"
      />
      <Button onClick={handleSubmit} className="register-button">
        Registrer
      </Button>
    </div>
  );
};

export default UserForm;
