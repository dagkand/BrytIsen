import { useEffect } from 'react';
import { useNavigate } from "react-router-dom";

const LoginSuccess = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const hash = window.location.hash;
        const tokenFromUrl = new URLSearchParams(hash.substring(1)).get('token');

        if (tokenFromUrl) {
            console.log('tokenFromUrl', tokenFromUrl);
            sessionStorage.setItem('token', tokenFromUrl);
            window.history.replaceState({}, document.title, window.location.pathname);
            navigate("/welcome");
        }
    }, [navigate]);

    return "";
}

export default LoginSuccess;