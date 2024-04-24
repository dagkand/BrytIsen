import { useState, useEffect, useRef } from 'react';
import './Timer.css';

const Timer = () => {
  const [seconds, setSeconds] = useState(0);
  const [isActive, setIsActive] = useState(false);
  const intervalRef = useRef<number | undefined>(undefined);

  function toggle() {
    setIsActive(!isActive);
  }

  function reset() {
    setSeconds(0);
    setIsActive(false);
  }

  useEffect(() => {
    if (isActive) {
      intervalRef.current = window.setInterval(() => {
        setSeconds(seconds => seconds + 1);
      }, 1000);
    } else if (!isActive && seconds !== 0) {
      clearInterval(intervalRef.current);
    }
    return () => clearInterval(intervalRef.current);
  }, [isActive, seconds]);

  return (
    <>
    <div className="timer">
      <div className="time">{new Date(seconds * 1000).toISOString().substr(11, 8)}</div>
      <div className="buttons">
        <button onClick={toggle}>
          {isActive ? 'Pause' : 'Start'}
        </button>
        <button onClick={reset}>
          Tilbakestill
        </button>
      </div>
    </div>
    </>
  );
};

export default Timer;
