// src/components/RegistrationForm.js
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './RegistrationForm.css';

const RegistrationForm = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleRegistration = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8090/api/auth/registration', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username,
          password,
          role: 'user',
        }),
      });


      if (response.ok) {
        alert('Registration successful!');
      } else {
        const errorMessage = await response.text();
        console.error('Registration failed:', errorMessage);
        alert(`Registration failed. Error: ${errorMessage}`);
      }
    } catch (error) {
      console.error('Error during registration:', error);
      alert('Registration failed. Please try again.');
    }
  };

  return (
    <div className="RegistrationForm">
      <h1 className="RegistrationForm-title">Registration</h1>
      <form onSubmit={handleRegistration}>
        <div className="RegistrationForm-input-container">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            name="username"
            value={username}
            onChange={handleUsernameChange}
            required
            className="RegistrationForm-input"
          />
        </div>

        <div className="RegistrationForm-input-container">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={password}
            onChange={handlePasswordChange}
            required
            className="RegistrationForm-input"
          />
        </div>

        <button type="submit" className="RegistrationForm-button">
          Register
        </button>

        <Link to="/" className="RegistrationForm-button-back">
          Back
        </Link>
      </form>
    </div>
  );
};

export default RegistrationForm;
