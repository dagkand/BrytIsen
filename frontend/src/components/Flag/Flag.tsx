import { useEffect, useState } from 'react';
import './Flag.css';
import { hasReportedGame, reportGame, unReportGame } from '../../services/GameService';
import useAuthCheck from '../../services/AuthService';

interface ReportFlagProps {
  id: number;
  onUpdate: () => void;
}

const ReportFlag: React.FC<ReportFlagProps> = ({id, onUpdate}) => {
  const [isFilled, setIsFilled] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useAuthCheck({ setLoggedIn: setIsLoggedIn, shouldRedirect: false });

  useEffect(() => {
    if (!isLoggedIn) return;
    async function checkIfReported() {
      const reported : boolean = await hasReportedGame(id);
      setIsFilled(Boolean(reported));
    }
    checkIfReported();
  }, [id, isLoggedIn]);

  const report = async () => {
    if (!isLoggedIn) return alert('You need to be logged in to report a game');
    if (isFilled) {
      setIsFilled(false);
      await unReportGame(id);
    } else {
      setIsFilled(true);
      await reportGame(id);
    }
    onUpdate();
  }

  return (
    <svg onClick={report} className="flag-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
      <path d="M4 15s1-1 4-1 5 2 8 2 4-1 4-1V3s-1 1-4 1-5-2-8-2-4 1-4 1zM4 22v-7" fill={isFilled ? "#FF4500" : "none"} stroke={isFilled ? "#FF4500" : "currentColor"} />
    </svg>
  );
};

export default ReportFlag;
