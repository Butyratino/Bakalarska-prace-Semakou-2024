import React from "react";
import "./Category.css";
import Input from "../../components/Input";

function Category({ handleChange }) {
  return (
    <div>
      <h2 className="sidebar-title">Category</h2>

      <div>
        {/* Radio button for All */}
        <label className="sidebar-label-container">
          <input onChange={handleChange} type="radio" value="" name="category" />
          <span className="checkmark"></span>All
        </label>

        {/* Radio buttons for each category */}
        <Input
          handleChange={handleChange}
          value="Laptop"
          title="Laptops"
          name="category"
        />
        <Input
          handleChange={handleChange}
          value="Smartphone"
          title="Smartphones"
          name="category"
        />
        <Input
          handleChange={handleChange}
          value="Headphones"
          title="Headphones"
          name="category"
        />
        <Input
          handleChange={handleChange}
          value="Smartwatch"
          title="Smartwatches"
          name="category"
        />
        <Input
          handleChange={handleChange}
          value="Wireless Mouse"
          title="Wireless Mouses"
          name="category"
        />
      </div>
    </div>
  );
}

export default Category;