import React, { createContext, useContext, useState } from 'react';

// Create a context for the user data
const UserContext = createContext();

// Create a provider component to wrap the app and provide the user context
export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  // Expose both the user data and the setUser function
  const userContextValue = { user, setUser, loginUser };

  function loginUser(userData) {
    setUser(userData);
  }

  return <UserContext.Provider value={userContextValue}>{children}</UserContext.Provider>;
};

// Create a hook to access the user context
export const useUser = () => {
  const context = useContext(UserContext);

  if (!context) {
    throw new Error('useUser must be used within a UserProvider');
  }

  return context;
};
