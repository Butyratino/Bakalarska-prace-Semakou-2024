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
    // Clear local storage
    localStorage.clear();
    // Redirect to the Recommended route when continue without login button is clicked
    navigate('/home');
  };

  return (
    <div className="WelcomeForm" style={{ textAlign: "center" }}>
      <h1 className="WelcomeForm-title">Welcome to Dygital Dynasty!</h1>
      <p>Choose an option:</p>
      <Link to="/login" className="WelcomeForm-link">Login</Link>
      <Link to="/registration" className="WelcomeForm-link">Register</Link>
      
      {/* Continue without login button */}
      <button onClick={handleContinueWithoutLogin} className="WelcomeForm-link Continue">Continue without login</button>
    </div>
  );
};

export default WelcomeForm;
