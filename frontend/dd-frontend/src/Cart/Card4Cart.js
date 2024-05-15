import React from 'react';
import PropTypes from 'prop-types'; // Import PropTypes
import { BsFillBagFill } from "react-icons/bs";

const Card4Cart = ({ img, title, description, amount, manufacturer, category, price, sale, productId, cartId }) => {
  // Calculate the new price based on the sale
  const newPrice = ((price * (100 - sale)) / 100).toFixed(2);

  const handleBuyClick = async () => {
    try {
        if (!cartId) {
            console.error('Cart ID not initialized');
            return;
        }

        const response = await fetch(`http://localhost:8090/api/cart/buy/${productId}/${cartId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
        });

        if (!response.ok) {
            throw new Error('Failed to buy product');
        }

        // If successful, update UI accordingly
        // You might want to implement a callback function or some other mechanism to handle this.
    } catch (error) {
        console.error('Error:', error);
    }
};

  
  
  

  const handleDeleteClick = async () => {
    try {
      if (!cartId) {
        console.error('Cart ID not initialized');
        return;
      }

      // Send DELETE request to the endpoint
      const response = await fetch(`http://localhost:8090/api/cart/delete/${productId}/${cartId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
      });

      if (!response.ok) {
        throw new Error('Failed to delete product');
      }

      // If successful, update UI accordingly (remove the product from the list, etc.)
      // You might want to implement a callback function or some other mechanism to handle this.
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div className="small-card-container">
      <img src={img} alt={title} className="small-card-img" />
      <div className="small-card-details">
        <h3 className="small-card-title">{title}</h3>
        <p className="small-card-description">{description}</p>
        <p className="small-card-details">{`Manufacturer: ${manufacturer}`}</p>
        <p className="small-card-details">{`Category: ${category}`}</p>
        <p className="small-card-details">{`Amount: ${amount}`}</p>
        <section className="small-card-price">
          <div className="small-price">
            <del>${price.toFixed(2)}</del>
            <span className="small-new-price">${newPrice}</span>
          </div>
          <div className="small-bag">
            <BsFillBagFill className="small-bag-icon" />
          </div>
        </section>
        <button className="buy-button" onClick={handleBuyClick}>Buy</button>
        <button className="delete-button" onClick={handleDeleteClick}>Delete</button>
      </div>
    </div>
  );
};

Card4Cart.propTypes = {
  img: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  description: PropTypes.string.isRequired,
  amount: PropTypes.number.isRequired,
  manufacturer: PropTypes.string.isRequired,
  category: PropTypes.string.isRequired,
  price: PropTypes.number.isRequired,
  sale: PropTypes.number.isRequired,
  productId: PropTypes.number.isRequired,
  cartId: PropTypes.number.isRequired,
};


export default Card4Cart;
