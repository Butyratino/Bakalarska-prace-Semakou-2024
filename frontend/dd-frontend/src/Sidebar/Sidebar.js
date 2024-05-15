import React from "react";
import Category from "./Category/Category";
import Price from "./Price/Price";
import Rating from "./Rating/Rating"; // Import Rating component instead of Colors
import "./Sidebar.css";


const Sidebar = ({ handleChange }) => {
  return (
    <>
      <section className="sidebar">
        <div className="logo-container">
          <h1>Digital Dynasty</h1>
        </div>
        <Category handleChange={handleChange} />
        <Price handleChange={handleChange} />
        <Rating handleChange={handleChange} /> {/* Use Rating component */}
      </section>
    </>
  );
};

export default Sidebar;
