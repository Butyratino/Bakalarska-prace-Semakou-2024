import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './LoginForm.css'; // Проверьте, что файл стилей правильно подключен
import axios from 'axios';
import { useUser } from './UserContext';

const LoginForm = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
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
        body: JSON.stringify({
          username,
          password,
        }),
      });
  
      if (response.ok) {
        const userData = await response.json();
        const token = response.headers.get('Authorization');
      
        localStorage.setItem('userid', userData.id);
        localStorage.setItem('username', userData.username);
        localStorage.setItem('role', userData.role);
      
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        alert('Login successful!');
        loginUser(userData);
        console.log('Navigating to /sidebar');
        navigate('/sidebar');
      } else {
        alert('Login failed. Please try again.');
      }
    } catch (error) {
      console.error('Error during login:', error);
    }
  };

  return (
    <div className="LoginForm"> {/* Проверьте, что этот класс соответствует вашим стилям */}
      <h1 className="LoginForm-title">Login</h1>
      <form onSubmit={handleLogin}>
        <div className="LoginForm-input-container">
          <label htmlFor="username">Username:</label>
          <input type="text" id="username" name="username" value={username} onChange={handleUsernameChange} required />
        </div>

        <div className="LoginForm-input-container">
          <label htmlFor="password">Password:</label>
          <input type="password" id="password" name="password" value={password} onChange={handlePasswordChange} required />
        </div>

        <button type="submit" className="LoginForm-button">Login</button>

        <Link to="/" className="LoginForm-button-back">Back</Link>
      </form>
    </div>
  );
};

export default LoginForm;
