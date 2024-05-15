import React, { useState, useEffect } from "react";
import { Link } from 'react-router-dom'; 
import Navigation from "./Navigation/Nav";
import Products from "./Products/Products";
import Recommended from "./Recommended/Recommended";
import Sidebar from "./Sidebar/Sidebar";
import Card from "./components/Card";
import "./index.css";
import { AiFillStar } from "react-icons/ai";
import { FiLogIn } from "react-icons/fi";

function LoginButton() { 
  return (
    <Link to="/welcome" className="login-button">
      <FiLogIn />
    </Link>
  );
}

function Home() { 
  const [data, setData] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedPriceRange, setSelectedPriceRange] = useState(null);
  const [selectedRating, setSelectedRating] = useState(null);
  const [selectedManufacturer, setSelectedManufacturer] = useState(null);
  const [query, setQuery] = useState("");
  const [loading, setLoading] = useState(true);
  const [currency, setCurrency] = useState("");

  useEffect(() => {
    fetchData();
    const storedCurrency = localStorage.getItem('currency');
    setCurrency(storedCurrency || 'USD');
  }, []);

  const fetchData = async () => {
    try {
      const response = await fetch("http://localhost:8090/api/products/all");
      const jsonData = await response.json();
      setData(jsonData);
      setLoading(false);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const handleInputChange = (event) => {
    setQuery(event.target.value);
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    if (name === "category") {
      setSelectedCategory(value);
    } else if (name === "priceRange") {
      setSelectedPriceRange(value);
    } else if (name === "rating") {
      setSelectedRating(value);
    } else if (name === "manufacturer") {
      setSelectedManufacturer(value); 
    }
  };

  const handleClick = (event) => {
    setSelectedManufacturer(event.target.value);
  };

  // Filter products based on selected filters
  function filteredData(products, selectedCategory, selectedPriceRange, selectedRating, selectedManufacturer, query) {
    return products
      .filter(product => {
        // Apply query filter
        if (query) {
          return product.name.toLowerCase().includes(query.toLowerCase());
        }
        return true;
      })
      .filter(product => {
        // Apply category filter
        if (selectedCategory) {
          return product.category === selectedCategory;
        }
        return true;
      })
      .filter(product => {
        // Apply price range filter
        if (selectedPriceRange) {
          const [minPrice, maxPrice] = selectedPriceRange.split('-').map(Number);
          const discountedPrice = product.price * (100 - product.sale) / 100;
          return minPrice <= discountedPrice && (!maxPrice || discountedPrice <= maxPrice);
        }
        return true;
      })
      .filter(product => {
        // Apply rating filter
        if (selectedRating) {
          return parseInt(product.rating).toString() === selectedRating;
        }
        return true;
      })
      .filter(product => {
        // Apply manufacturer filter
        if (selectedManufacturer) {
          return product.manufacturer === selectedManufacturer;
        }
        return true;
      })
      .map(({ productId, name, description, price, manufacturer, category, image, rating, sale }) => {
        let currencySymbol = '$'; // Default currency symbol
        let discountedPrice = price * (100 - sale) / 100;

        if (currency === 'EUR') {
          currencySymbol = '€';
          price *= 0.926;
          discountedPrice *= 0.926;
        } else if (currency === 'CZK') {
          currencySymbol = 'Kč';
          price *= 22.954;
          discountedPrice *= 22.954;
        }

        const prevPrice = `${currencySymbol}${price.toFixed(2)}`;
        const newPrice = `${currencySymbol}${discountedPrice.toFixed(2)}`;

        // Generate the appropriate number of stars based on the rating
        const starIcons = Array.from({ length: parseInt(rating, 10) }, (_, i) => (
          <AiFillStar className="rating-star" key={i} />
        ));

        return (
          <div className="home" key={productId}>
            <Link to={`/product/${productId}`}>
              <Card
                img={image}
                title={name}
                star={starIcons}
                reviews=""
                prevPrice={prevPrice}
                newPrice={newPrice}
                currency={currency} // Pass the currency prop to Card component
              />
            </Link>
          </div>
        );
      });
  }

  const result = filteredData(data, selectedCategory, selectedPriceRange, selectedRating, selectedManufacturer, query);

  const updateCurrency = (selectedCurrency) => {
    setCurrency(selectedCurrency);
    localStorage.setItem('currency', selectedCurrency);
  };

  return (
    <>
      <LoginButton />
      <Sidebar handleChange={handleChange} />
      <Navigation query={query} handleInputChange={handleInputChange} currency={currency} updateCurrency={updateCurrency} />
      <Recommended handleClick={handleClick} />
      {loading ? (
        <p>Loading...</p>
      ) : (
        <Products result={result} />
      )}
    </>
  );
}

export default Home;
