// Nav.js
import React, { useState } from "react";
import { Link } from 'react-router-dom';
import { FiBookOpen } from "react-icons/fi";
import { AiOutlineShoppingCart, AiOutlineUserAdd } from "react-icons/ai";
import "./Nav.css";

const Nav = ({ handleInputChange, query, updateCurrency }) => {
  
  const [currency, setCurrency] = useState(localStorage.getItem("currency") || "USD"); 

  const userData = JSON.parse(localStorage.getItem('user'));
  const username = userData ? userData.username : null;

  const handleProfileClick = async () => {
    if (!userData) {
      alert('Please log in first.');
    } else {
      fetchUserData(username);
    }
  };

  const fetchUserData = async (username) => {
    try {
      const response = await fetch(`http://localhost:8090/api/profile/${username}`);
      const userData = await response.json();
      console.log('User data:', userData);
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  const handleCurrencyChange = (event) => {
    const selectedCurrency = event.target.value;
    setCurrency(selectedCurrency);
    localStorage.setItem("currency", selectedCurrency); // Store selected currency in localStorage
    updateCurrency(selectedCurrency);
  };

  return (
    <nav>
      <div className="nav-container">
        <input
          className="search-input"
          type="text"
          onChange={handleInputChange}
          value={query}
          placeholder="Enter your product."
        />
        <select value={currency} className="currency-selector" onChange={handleCurrencyChange}> {/* Set value to currency state */}
          <option value="USD">USD</option>
          <option value="EUR">EUR</option>
          <option value="CZK">CZK</option>
        </select>
      </div>
      <span className="username">{username}</span>
      <div className="profile-container">
        <a href="/orders">
          <FiBookOpen className="nav-icons" />
        </a>
        <a href="/cart">
          <AiOutlineShoppingCart className="nav-icons" />
        </a>
        
        <a href="/profile">
          <AiOutlineUserAdd className="nav-icons" />
        </a>
        
      </div>
    </nav>
  );
};

export default Nav;
