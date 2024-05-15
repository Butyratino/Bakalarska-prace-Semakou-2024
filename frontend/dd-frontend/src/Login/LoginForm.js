// LoginForm.js
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useUser } from './UserContext';
import './LoginForm.css';

const LoginForm = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null); // State to store login error message
  const navigate = useNavigate();
  const { loginUser } = useUser();

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8090/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        const errorMessage = await response.text(); // Get error message from response
        throw new Error(errorMessage || 'Login failed');
      }

      const userData = await response.json();
      loginUser(userData); // Save user data to context and local storage
      localStorage.setItem('user', JSON.stringify(userData)); // Save user data to local storage
      navigate('/home');
    } catch (error) {
      setError(error.message); // Set login error message
      console.error('Login failed:', error);
    }
  };

  return (
    <div className="LoginForm">
      <h1 className="LoginForm-title">Login</h1>
      <form onSubmit={handleLogin}>
      <div className="LoginForm-input-container">
  <label htmlFor="username">Username:</label>
  <input 
    type="text" 
    id="username" 
    name="username" 
    value={username} 
    onChange={handleUsernameChange} 
    required 
    className="LoginForm-input" // Add className here
  />
</div>

<div className="LoginForm-input-container">
  <label htmlFor="password">Password:</label>
  <input 
    type="password" 
    id="password" 
    name="password" 
    value={password} 
    onChange={handlePasswordChange} 
    required 
    className="LoginForm-input" // Add className here
  />
</div>


        {error && <div className="LoginForm-error">{error}</div>} {/* Display login error message if there is one */}

        <button type="submit" className="LoginForm-button">Login</button>

        <Link to="/welcome" className="LoginForm-button-back">Back</Link>
      </form>
    </div>
  );
};

export default LoginForm;
