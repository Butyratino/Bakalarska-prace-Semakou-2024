// Rating.js

import React from "react";
import "./Rating.css";
import Input from "../../components/Input";

const Rating = ({ handleChange }) => {
  return (
    <>
      <div>
        <h2 className="sidebar-title rating-title">Rating</h2>
        <label className="sidebar-label-container">
          <input onChange={handleChange} type="radio" value="" name="rating" />
          <span className="checkmark all"></span>
          All
        </label>

        <Input
          handleChange={handleChange}
          value="1"
          title="1 Star"
          name="rating"          
        />

        <Input
          handleChange={handleChange}
          value="2"
          title="2 Stars"
          name="rating"          
        />

        <Input
          handleChange={handleChange}
          value="3"
          title="3 Stars"
          name="rating"          
        />

        <Input
          handleChange={handleChange}
          value="4"
          title="4 Stars"
          name="rating"          
        />

        <Input
          handleChange={handleChange}
          value="5"
          title="5 Stars"
          name="rating"          
        />
      </div>
    </>
  );
};

export default Rating;
