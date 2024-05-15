import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Orders.css';

const Orders = () => {
  const [orders, setOrders] = useState([]);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    // Fetch user ID from local storage
    const user = JSON.parse(localStorage.getItem('user'));
    if (user) {
      setUserId(user.id);
    }
  }, []);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        if (!userId) return;

        const response = await axios.get(`http://localhost:8090/api/order/user/${userId}`);
        setOrders(response.data);
      } catch (error) {
        console.error('Error fetching orders:', error);
      }
    };

    fetchOrders();
  }, [userId]);

  return (
    <div className="orders-container">
      <h2>Your Orders</h2>
      <div className="order-list">
        {orders.map(order => (
          <div key={order.orderId} className="order-item">
            <p>Order Date: {order.orderDate}</p>
            <p>Total Price: ${order.totalPrice}</p>
            <p>Product Name: {order.name}</p>
            <p>Product Description: {order.description}</p>
            <p>Price: ${order.price}</p>
            <p>Manufacturer: {order.manufacturer}</p>
            <p>Category: {order.category}</p>
            {/* Add more details as needed */}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Orders;
