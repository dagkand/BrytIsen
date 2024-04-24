import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Logout = () => {
    const navigate = useNavigate();

    useEffect(() => {
        sessionStorage.removeItem('token');

        
        navigate('/all');
    }, [navigate]);

    return null;
};

export default Logout;