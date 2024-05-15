// Price.js

import React from "react";
import "./Price.css";
import Input from "../../components/Input";

const Price = ({ handleChange }) => {
  return (
    <>
      <div className="ml">
        <h2 className="sidebar-title price-title">Price</h2>

        <label className="sidebar-label-container">
          <input onChange={handleChange} type="radio" value="" name="priceRange" />
          <span className="checkmark"></span>All
        </label>

        <Input
          handleChange={handleChange}
          value="0-150"
          title="$0 - $150"
          name="priceRange"
        />

        <Input
          handleChange={handleChange}
          value="150-500"
          title="$150 - $500"
          name="priceRange"
        />

        <Input
          handleChange={handleChange}
          value="500-1000"
          title="$500 - $1000"
          name="priceRange"
        />

        <Input
          handleChange={handleChange}
          value="1000-"
          title="Over $1000"
          name="priceRange"
        />
      </div>
    </>
  );
};

export default Price;
