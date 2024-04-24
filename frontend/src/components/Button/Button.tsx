import React from 'react';
import './Button.css'; 

interface ButtonProps {
  onClick: () => void;
  className?: string;
  children?: React.ReactNode;
}

const Button: React.FC<ButtonProps> = ({ onClick, className, children }) => {
  return (
    <button className={`button ${className || ''}`} onClick={onClick}>
      {children}
    </button>
  );
};

export default Button;