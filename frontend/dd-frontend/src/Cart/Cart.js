// Cart.js
import React, { useState, useEffect } from 'react';
import Card4Cart from './Card4Cart';
import './Cart.css';


function Cart() {
  const [products, setProducts] = useState([]);
  const [cartId, setCartId] = useState(null);

  useEffect(() => {
    const getCartId = () => {
      const user = localStorage.getItem('user');
      if (user) {
        const userData = JSON.parse(user);
        setCartId(userData.id);
      }
    };

    getCartId();
  }, []);

  useEffect(() => {
    const fetchCartData = async () => {
      try {
        if (!cartId) return;

        const response = await fetch(`http://localhost:8090/api/cart/${cartId}`);
        if (!response.ok) {
          throw new Error('Failed to fetch cart data');
        }
        const cartData = await response.json();
        setProducts(cartData);
      } catch (error) {
        console.error('Error fetching cart data:', error);
      }
    };

    if (cartId) {
      fetchCartData();
    }
  }, [cartId]);

  // Function to handle buying a product
  const handleBuyClick = async (productId) => {
    try {
      if (!cartId) {
        console.error('Cart ID not initialized');
        return;
      }

      // Handle the buy click logic here
    } catch (error) {
      console.error('Error:', error);
    }
  };

  // Function to handle deleting a product
  const handleDeleteClick = async (productId) => {
    try {
      if (!cartId) {
        console.error('Cart ID not initialized');
        return;
      }

      // Handle the delete click logic here
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    
    <div className="cart-container">
      <h2>Your Cart</h2>
      <div className="cart-items">
        {products.map(product => (
          <Card4Cart
            key={`${product.productId}-${product.name}`}
            img={product.image}
            title={product.name}
            description={product.description}
            amount={product.amount}
            manufacturer={product.manufacturer}
            category={product.category}
            price={product.price}
            sale={product.sale}
            productId={product.productId}
            cartId={cartId}
            onBuyClick={handleBuyClick}
            onDeleteClick={handleDeleteClick}
          />
        ))}
      </div>
    </div>
  );
}

export default Cart;
