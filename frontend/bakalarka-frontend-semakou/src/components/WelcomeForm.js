// src/components/WelcomeForm.js
import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './WelcomeForm.css';

const WelcomeForm = () => {
  const navigate = useNavigate();

  useEffect(() => {
    // Clear local storage when the component mounts (on visiting the root route)
    localStorage.clear();
  }, []);

  const handleContinueWithoutLogin = () => {
    // Redirect to the main route
    navigate('/home');
  };

  return (
    <div className="WelcomeForm">
      <h1 className="WelcomeForm-title">Welcome!</h1>
      <p>Choose an option:</p>
      <Link to="/login" className="WelcomeForm-link">Login</Link>
      <Link to="/registration" className="WelcomeForm-link">Register</Link>
      
      {/* Continue without login button */}
      <button onClick={handleContinueWithoutLogin} className="WelcomeForm-link">Continue as guest</button>
    </div>
  );
};

export default WelcomeForm;
