import { useState } from "react";
import Button from "../Button/Button";
import "./Header.css";
import { Link, useResolvedPath, useMatch, useNavigate } from "react-router-dom";
import { isLoggedIn } from "../../services/UserService";
import ColorSchemeToggle from "../ColorSchemeChanger/ColorSchemeToggle";

function Header() {
  const navigate = useNavigate();
  const [isDropdownVisible, setIsDropdownVisible] = useState(false); // State to control the visibility of the dropdown
  const [loggedIn, setLoggedIn] = useState(false); // State to control the visibility of the dropdown
    
  // Function to toggle dropdown visibility
  const toggleDropdown = () => {
    setIsDropdownVisible(!isDropdownVisible);
    checkLoggedIn();
  };

  const checkLoggedIn = async () => {
    const loggedIn = await isLoggedIn();
    setLoggedIn(loggedIn);
  };

  return (
    <>
      <div className="header-row-1">
        <div className="left">
          <Button onClick={() => navigate("/create")}>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="buttonIcon"
              width="22"
              height="22"
              viewBox="0 0 24 24"
              strokeWidth="1.5"
              stroke="#ffffff"
              fill="none"
              strokeLinecap="round"
              strokeLinejoin="round"
            >
              <path stroke="none" d="M0 0h24v24H0z" fill="none" />
              <path d="M4 20h4l10.5 -10.5a2.828 2.828 0 1 0 -4 -4l-10.5 10.5v4" />
              <path d="M13.5 6.5l4 4" />
            </svg>
            <p>Lag spill</p>
          </Button>
        </div>
        <h1 id="title">Bryt Isen</h1>
        <div className="dropdown-container">
          <svg
            onClick={toggleDropdown} // Toggle dropdown on click
            xmlns="http://www.w3.org/2000/svg"
            width="60"
            height="60"
            viewBox="0 0 24 24"
            strokeWidth="2"
            stroke="#ffffff"
            fill="none"
            strokeLinecap="round"
            strokeLinejoin="round"
            style={{ cursor: "pointer" }} // Change cursor to pointer on hover
          >
            <path d="M12 12m-9 0a9 9 0 1 0 18 0a9 9 0 1 0 -18 0" />
            <path d="M12 10m-3 0a3 3 0 1 0 6 0a3 3 0 1 0 -6 0" />
            <path d="M6.168 18.849a4 4 0 0 1 3.832 -2.849h4a4 4 0 0 1 3.834 2.855" />
          </svg>
          <div className={`dropdown-menu ${isDropdownVisible ? 'visible' : ''}`}>
            {isDropdownVisible && (
              <>
              {loggedIn ? (
                <>
                  <Link
                    to="/reviews"
                    className="dropdown-item"
                    onClick={toggleDropdown}
                  >
                  Mine Anmeldelser
                  </Link>
                  <Link
                    to="/logout"
                    className="dropdown-item"
                    onClick={toggleDropdown}
                  >
                    Logg Ut
                  </Link>
                </>
              ) : (
                <>
                  <Link
                    to="/login"
                    className="dropdown-item"
                    onClick={toggleDropdown}
                  >
                    Logg Inn
                  </Link>
                </>
              )}
              </>
            )}
            <div className={`moon ${isDropdownVisible ? 'visible' : ''}`}>
                  <ColorSchemeToggle />
            </div>
          </div>
          </div>
      </div>
      <div className="header-links">
        <CustomLink to="/">Topplister</CustomLink>
        <CustomLink to="/favorites">Favoritter</CustomLink>
        <CustomLink to="/all">Alle leker</CustomLink>
      </div>
    </>
  );
}

function CustomLink({
  to,
  children,
  ...props
}: {
  to: string;
  children: React.ReactNode;
}) {
  const resolvedPath = useResolvedPath(to);
  const isActive = useMatch({ path: resolvedPath.pathname, end: true });
  return (
    <Link to={to} {...props} className={isActive ? "active" : ""}>
      {children}
    </Link>
  );
}

export default Header;
