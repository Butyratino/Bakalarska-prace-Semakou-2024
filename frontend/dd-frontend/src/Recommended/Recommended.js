// Recommended.js

import React from "react";
import Button from "../components/Button";
import "./Recommended.css";

const Recommended = ({ handleClick }) => {
  return (
    <>
      <div>
        <h2 className="recommended-title">Recommended</h2>
        <div className="recommended-flex">
          <Button onClickHandler={handleClick} value="" title="All Products" />
          <Button onClickHandler={handleClick} value="Apple" title="Apple" />
          <Button onClickHandler={handleClick} value="Samsung" title="Samsung" />
          <Button onClickHandler={handleClick} value="Xiaomi" title="Xiaomi" />
          <Button onClickHandler={handleClick} value="Sony" title="Sony" />
          <Button onClickHandler={handleClick} value="Lenovo" title="Lenovo" />
        </div>
      </div>
    </>
  );
};

export default Recommended;
