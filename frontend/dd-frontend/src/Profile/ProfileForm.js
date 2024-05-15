import React, { useState, useEffect, useRef } from 'react';
import Nav from '../Navigation/Nav';
import './ProfileForm.css';
import { AiFillStar } from 'react-icons/ai';


const defaultAvatarSrc = 'https://i.stack.imgur.com/l60Hf.png';
const adminAvatarSrc = 'https://cdn2.iconfinder.com/data/icons/shopping-colorline/64/admin-512.png';

const ProfileForm = () => {
  const [userData, setUserData] = useState(null);
  const [editing, setEditing] = useState(false);
  const [editedUserData, setEditedUserData] = useState({
    email: '',
    phoneNumber: '',
    balance: 0
  });
  const [guestNotification, setGuestNotification] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [showProducts, setShowProducts] = useState(false);
  const [products, setProducts] = useState([]);
  const [query, setQuery] = useState('');
  const [editingProduct, setEditingProduct] = useState(null);

  const [showUsers, setShowUsers] = useState(false);
  const [users, setUsers] = useState([]);

  const [editingUser, setEditingUser] = useState(null);
  const usernameRef = useRef({});
  const emailRef = useRef({});
  const phoneRef = useRef({});
  const balanceRef = useRef({});
  const roleRef = useRef({});

  const handleEditUser = (userId) => {
    setEditingUser(userId);
  };

  const handleUpdateUser = async (userId) => {
    const username = usernameRef.current[userId].value;
    const email = emailRef.current[userId].value;
    const phoneNumber = phoneRef.current[userId].value;
    const balance = balanceRef.current[userId].value;
    const role = roleRef.current[userId].value;

    try {
      const response = await fetch(`http://localhost:8090/api/profile/update/${userId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, email, phoneNumber, balance, role })
      });

      if (response.ok) {
        console.log('User data updated successfully');
        setEditingUser(null);
        // Optionally, you can update the user list after editing
        // Refetch the users data or update the state accordingly
      } else {
        console.error('Failed to update user data');
      }
    } catch (error) {
      console.error('Error updating user data:', error);
    }
  };




  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      const { role } = JSON.parse(storedUser);
      setIsAdmin(role === 'admin');
    }

    const fetchUserData = async () => {
      try {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
          const { username } = JSON.parse(storedUser);
          const response = await fetch(`http://localhost:8090/api/profile/${username}`);
          const data = await response.json();
          setUserData(data);
          setEditedUserData(data);
        } else {
          console.error('Username not found in local storage');
          setGuestNotification(true);
        }
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchUserData();

    if (isAdmin) {
      const fetchProducts = async () => {
        try {
          const response = await fetch('http://localhost:8090/api/products/all');
          const data = await response.json();
          setProducts(data);
        } catch (error) {
          console.error('Error fetching products:', error);
        }
      };

      fetchProducts();
    }
  }, [isAdmin]);

  const handleEdit = () => {
    setEditing(true);
    setEditedUserData(userData);
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setEditedUserData(prevData => ({
      ...prevData,
      [name]: value
    }));
  };


  const fetchAllUsers = async () => {
    try {
      const response = await fetch('http://localhost:8090/api/profile/all');
      const data = await response.json();
      setUsers(data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  const handleShowUsers = () => {
    setShowUsers(true);
    fetchAllUsers();
  };

  const renderUsers = () => {
    return users.map((user) => (
      <div key={user.userId} className="user-card">
        <div className="user-image">
          <img src={user.avatar ? user.avatar : defaultAvatarSrc} alt="User Avatar" />
        </div>
        <div className="user-info">
          <p>Username: {user.username}</p>
          <p>Email: {user.email}</p>
          <p>Phone Number: {user.phoneNumber}</p>
          <p>Balance: {user.balance}</p>
        </div>
      </div>
    ));
  };


  const [editedProductData, setEditedProductData] = useState({
    name: '',
    description: '',
    price: 0,
    manufacturer: '',
    category: '',
    image: '',
    rating: 0,
    sale: 0
  });

  const [addingProduct, setAddingProduct] = useState(false);

const handleAddProduct = () => {
  setAddingProduct(true);
};

const renderAddProductForm = () => {
  return (
    <div className="add-product-form">
      <div className="input-group">
        <label htmlFor="name" className="label">Name:</label>
        <input type="text" id="name" className="input-field" />
      </div>
      <div className="input-group">
        <label htmlFor="description" className="label">Description:</label>
        <input type="text" id="description" className="input-field" />
      </div>
      <div className="input-group">
        <label htmlFor="price" className="label">Price:</label>
        <input type="number" id="price" className="input-field" />
      </div>
      <div className="input-group">
        <label htmlFor="amount" className="label">Amount:</label>
        <input type="number" id="amount" className="input-field" />
      </div>
      <div className="input-group">
        <label htmlFor="manufacturer" className="label">Manufacturer:</label>
        <input type="text" id="manufacturer" className="input-field" />
      </div>
      <div className="input-group">
        <label htmlFor="category" className="label">Category:</label>
        <input type="text" id="category" className="input-field" />
      </div>
      <div className="input-group">
        <label htmlFor="image" className="label">Image URL:</label>
        <input type="text" id="image" className="input-field" />
      </div>
      <div className="input-group">
        <label htmlFor="rating" className="label">Rating:</label>
        <input type="number" id="rating" className="input-field" />
      </div>
      <div className="input-group">
        <label htmlFor="sale" className="label">Sale:</label>
        <input type="number" id="sale" className="input-field" />
      </div>
      <button onClick={handleSaveProduct}>Save</button>
      <button onClick={() => setAddingProduct(false)}>Cancel</button>
    </div>
  );
};

const handleSaveProduct = () => {
  // Logic for saving the product
  setAddingProduct(false); // Reset addingProduct state after saving
};


  const handleSubmit = async () => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      const { id } = JSON.parse(storedUser);

      try {
        // Collect all the data from the text fields
        const editedDataToSend = { ...editedUserData };

        // Send the request with all the collected data
        const response = await fetch(`http://localhost:8090/api/profile/update/${id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(editedDataToSend)
        });

        if (response.ok) {
          console.log('User data updated successfully');
        } else {
          console.error('Failed to update user data');
        }
      } catch (error) {
        console.error('Error updating user data:', error);
      }
    } else {
      console.error('User ID not found in local storage');
    }

    setEditing(false);
  };


  const handleBack = () => {
    window.location.href = 'http://localhost:3000/home';
  };

  const handleProductEdit = async (productId) => {
    try {
      // Collect all the data from the text fields
      const updatedProductData = { ...editedProductData };
      const productFields = ['name', 'description', 'price', 'manufacturer', 'category', 'image', 'rating', 'sale'];

      // Filter out null or undefined values from the edited product data
      const validProductData = Object.fromEntries(
        Object.entries(updatedProductData).filter(([key, value]) => productFields.includes(key) && value !== null && value !== undefined)
      );

      // Send the request with all the collected data
      const response = await fetch(`http://localhost:8090/api/products/edit/${productId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(validProductData)
      });

      if (response.ok) {
        console.log('Product data updated successfully');
        // Optionally, you can update the product list after editing
        // Refetch the products data or update the state accordingly
      } else {
        console.error('Failed to update product data');
      }
    } catch (error) {
      console.error('Error updating product data:', error);
    }
  };



  const handleProductEditFieldChange = (event, fieldName) => {
    const { value } = event.target;
    // Fetch the current values from all text fields
    const updatedProductData = {
      ...editedProductData,
      name: document.getElementById('name').value || '',
      description: document.getElementById('description').value || '',
      price: document.getElementById('price').value || '',
      manufacturer: document.getElementById('manufacturer').value || '',
      category: document.getElementById('category').value || '',
      image: document.getElementById('image').value || '',
      rating: document.getElementById('rating').value || '',
      sale: document.getElementById('sale').value || ''
    };
    // Update the state with the current values
    setEditedProductData(updatedProductData);
  };

  const handleProductDelete = async (productId) => {
    try {
      const response = await fetch(`http://localhost:8090/api/products/delete/${productId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        }
      });

      if (response.ok) {
        console.log('Product deleted successfully');
        // Optionally, you can update the product list after deletion
        // Refetch the products data or update the state accordingly
      } else {
        console.error('Failed to delete product');
      }
    } catch (error) {
      console.error('Error deleting product:', error);
    }
  };



  const renderEditFields = (productId) => {
    const product = products.find((product) => product.productId === productId);

    return (
      <div>
        <div className="input-group">
          <label htmlFor="name" className="label">Name:</label>
          <input
            type="text"
            id="name"
            value={editedProductData.name || (product ? product.name : '')}
            onChange={(event) => handleProductEditFieldChange(event, 'name')}
            className="input-field"
          />
        </div>
        <div className="input-group">
          <label htmlFor="description" className="label">Description:</label>
          <input
            type="text"
            id="description"
            value={editedProductData.description || (product ? product.description : '')}
            onChange={(event) => handleProductEditFieldChange(event, 'description')}
            className="input-field"
          />
        </div>
        <div className="input-group">
          <label htmlFor="price" className="label">Price:</label>
          <input
            type="text"
            id="price"
            value={editedProductData.price || (product ? product.price : '')}
            onChange={(event) => handleProductEditFieldChange(event, 'price')}
            className="input-field"
          />
        </div>
        <div className="input-group">
          <label htmlFor="manufacturer" className="label">Manufacturer:</label>
          <input
            type="text"
            id="manufacturer"
            value={editedProductData.manufacturer || (product ? product.manufacturer : '')}
            onChange={(event) => handleProductEditFieldChange(event, 'manufacturer')}
            className="input-field"
          />
        </div>
        <div className="input-group">
          <label htmlFor="category" className="label">Category:</label>
          <input
            type="text"
            id="category"
            value={editedProductData.category || (product ? product.category : '')}
            onChange={(event) => handleProductEditFieldChange(event, 'category')}
            className="input-field"
          />
        </div>
        <div className="input-group">
          <label htmlFor="image" className="label">Image:</label>
          <input
            type="text"
            id="image"
            value={editedProductData.image || (product ? product.image : '')}
            onChange={(event) => handleProductEditFieldChange(event, 'image')}
            className="input-field"
          />
        </div>
        <div className="input-group">
          <label htmlFor="rating" className="label">Rating:</label>
          <input
            type="text"
            id="rating"
            value={editedProductData.rating || (product ? product.rating : '')}
            onChange={(event) => handleProductEditFieldChange(event, 'rating')}
            className="input-field"
          />
        </div>
        <div className="input-group">
          <label htmlFor="sale" className="label">Sale:</label>
          <input
            type="text"
            id="sale"
            value={editedProductData.sale || (product ? product.sale : '')}
            onChange={(event) => handleProductEditFieldChange(event, 'sale')}
            className="input-field"
          />
        </div>
        <button onClick={() => handleProductEdit(productId)}>Save</button>
        <button onClick={() => handleProductDelete(productId)} className="delete-button">Delete</button>
      </div>
    );
  };


  const handleProductEditButtonClick = (productId) => {
    setEditingProduct(productId);
  };

  const isProductEditing = (productId) => {
    return editingProduct === productId;
  };

  const filteredData = () => {
    return products.map(({ productId, name, description, price, manufacturer, category, image, rating, sale }) => {
      const discountedPrice = price * (100 - sale) / 100;
      const newPrice = `$${discountedPrice.toFixed(2)}`;

      const starIcons = Array.from({ length: parseInt(rating, 10) }, (_, i) => (
        <AiFillStar className="rating-star" key={i} />
      ));

      return (
        <div key={productId} className="product-card">
          <div className="product-image">
            <img src={image} alt={name} />
          </div>
          <div className="product-info">
            <h3>{name}</h3>
            <p>Description: {description}</p>
            <p>Manufacturer: {manufacturer}</p>
            <p>Category: {category}</p>
            <p>Price: {newPrice}</p>
            <p>Rating: {starIcons}</p>
            {isAdmin && (
              <div>
                <button className="edit-button" onClick={() => handleProductEditButtonClick(productId)}>
                  Edit
                </button>
                {isProductEditing(productId) && renderEditFields(productId)}
              </div>
            )}
          </div>
        </div>
      );
    });
  };

  return (
    <div>
      <Nav />
      <div className="profile-form">
        <h2>User Profile</h2>
        {guestNotification && (
          <div className="guest-notification">Please log in to view your profile.</div>
        )}
        {!guestNotification && userData && (
          <div>
            <div className="avatar-container">
              <img src={userData.avatar ? userData.avatar : defaultAvatarSrc} alt="Avatar" className="avatar" />
            </div>
            <div className="user-info">
              <p>Username: {userData.username}</p>
              <p>Role: {userData.role}</p>
              <p>Email: {userData.email}</p>
              <p>Phone Number: {userData.phoneNumber}</p>
              <p>Balance: ${userData.balance}</p>
              <div>
                <button onClick={handleEdit}>Edit</button>
                <button onClick={handleBack} className="back-button">Back</button>
              </div>
              {editing && (
                <div>
                  <div className="input-group">
                    <label htmlFor="username" className="label">Username:</label>
                    <input type="text" name="username" value={editedUserData.username || ''} onChange={handleInputChange} className="input-field" />
                  </div>
                  <div className="input-group">
                    <label htmlFor="email" className="label">Email:</label>
                    <input type="text" name="email" value={editedUserData.email || ''} onChange={handleInputChange} className="input-field" />
                  </div>
                  <div className="input-group">
                    <label htmlFor="phoneNumber" className="label">Phone Number:</label>
                    <input type="text" name="phoneNumber" value={editedUserData.phoneNumber || ''} onChange={handleInputChange} className="input-field" />
                  </div>
                  <div className="input-group">
                    <label htmlFor="balance" className="label">Balance:</label>
                    <input type="text" name="balance" value={editedUserData.balance || ''} onChange={handleInputChange} className="input-field" />
                  </div>
                  <div className="input-group">
                    <label htmlFor="balance" className="label">Image URL:</label>
                    <input type="text" name="image" value={editedUserData.image || ''} onChange={handleInputChange} className="input-field" />
                  </div>
                  <button onClick={handleSubmit}>Save</button>
                </div>
              )}
  
              {isAdmin && showProducts && (
                <div className="products-container">
                  <input
                    type="text"
                    placeholder="Search products..."
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                  />
                  <div className="product-list">
                    {filteredData()}
                  </div>
                  <button onClick={handleAddProduct} className="add-product-button">Add Product</button>
                  {addingProduct && renderAddProductForm()}
                </div>
              )}
              {isAdmin && !showProducts && (
                <div>
                  <button onClick={() => setShowProducts(true)}>Show Products</button>
                  <button onClick={handleShowUsers}>Show Users</button> {/* Added Show Users button */}
                  {/* Render users if showUsers is true */}
                  {showUsers && (
                    <div className="users-container">
                      {users.map((user) => (
                        <div key={user.userId} className="user-card">
                          <div className="user-image">
                            <img src={user.avatar ? user.avatar : defaultAvatarSrc} alt="User Avatar" />
                          </div>
                          <div className="user-info">
                            <div className="user-info-item">
                              <p><strong>Username:</strong> {user.username}</p>
                            </div>
                            <div className="user-info-item">
                              <p><strong>Email:</strong> {user.email}</p>
                            </div>
                            <div className="user-info-item">
                              <p><strong>Phone Number:</strong> {user.phoneNumber}</p>
                            </div>
                            <div className="user-info-item">
                              <p><strong>Balance:</strong> ${user.balance}</p>
  
                            </div>
                            <div className="user-info-item">
                              <p><strong>Role:</strong> {user.role}</p>
                            </div>
                            <div className="user-info-item">
                              <button onClick={() => handleEditUser(user.userId)}>Edit</button>
  
                            </div>
                            {editingUser === user.userId && (
                              <div className="edit-user-form">
                                <div className="input-group">
                                  <label htmlFor="edit-username">Username:</label>
                                  <input type="text" id="edit-username" defaultValue={user.username} ref={(input) => { usernameRef.current[user.userId] = input; }} />
                                </div>
                                <div className="input-group">
                                  <label htmlFor="edit-email">Email:</label>
                                  <input type="text" id="edit-email" defaultValue={user.email} ref={(input) => { emailRef.current[user.userId] = input; }} />
                                </div>
                                <div className="input-group">
                                  <label htmlFor="edit-phone">Phone Number:</label>
                                  <input type="text" id="edit-phone" defaultValue={user.phoneNumber} ref={(input) => { phoneRef.current[user.userId] = input; }} />
                                </div>
                                <div className="input-group">
                                  <label htmlFor="edit-balance">Balance:</label>
                                  <input type="text" id="edit-balance" defaultValue={user.balance} ref={(input) => { balanceRef.current[user.userId] = input; }} />
                                </div>
                                <div className="input-group">
                                  <label htmlFor="edit-role">Role:</label>
                                  <input type="text" id="edit-role" defaultValue={user.role} ref={(input) => { roleRef.current[user.userId] = input; }} />
                                </div>
                                <button onClick={() => handleUpdateUser(user.userId)}>Save</button>
                                <button onClick={() => setEditingUser(null)}>Cancel</button>
                              </div>
                            )}
                          </div>
                        </div>
  
                      ))}
                    </div>
                  )}
  
  
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
  

};

export default ProfileForm;
