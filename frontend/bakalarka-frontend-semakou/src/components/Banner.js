import React from 'react';
import { Link } from 'react-router-dom';
import './Banner.css'; // Import styles for the banner
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';

const Banner = () => {
  return (
    <div className="banner">
      <h1 className="banner-title">Digital Dynasty</h1>
      <div className="banner-search">
        <input type="text" placeholder="Search products" />
        <button>Search</button>
      </div>
      <div className="banner-controls">
        <select>
          <option>Euro</option>
          <option>Dollar</option>
          <option>Czech Crown</option>
        </select>
        <Link to="/profile">
          <AccountBoxIcon /> {/* Render the AccountBoxIcon component */}
        </Link>
        <Link to="/cart">
          <ShoppingCartIcon /> {/* Render the ShoppingCartIcon component */}
        </Link>
      </div>
    </div>
  );
};

export default Banner;
