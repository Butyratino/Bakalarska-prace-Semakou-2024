import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { UserProvider } from './Login/UserContext'; 
import Home from './Home';
import WelcomeForm from './Welcome/WelcomeForm';
import Login from './Login/LoginForm';
import Registration from './Registration/RegistrationForm';
import Profile from './Profile/ProfileForm';
import Product from "./Products/Products";
import ProductDetail from "./Products/ProductDetail"; 
import Cart from "./Cart/Cart";
import Order from "./Orders/Orders";

function App() {
  const currency = "USD"; // Define the currency here

  return (
    <Router>
      <UserProvider>
        <Routes>
          <Route path="/" element={<Navigate to="/home" replace />} />
          <Route path="/home" element={<Home currency={currency} />} /> {/* Pass currency prop to Home component */}
          <Route path="/welcome" element={<WelcomeForm />} />
          <Route path="/login" element={<Login />} />
          <Route path="/registration" element={<Registration />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/product" element={<Product />} />
          <Route path="/cart" element={<Cart />} />          
          <Route path="/product/:productId" element={<ProductDetail />} />
          <Route path="/orders" element={<Order />} />  
        </Routes>
      </UserProvider>
    </Router>
  );
}

export default App;
