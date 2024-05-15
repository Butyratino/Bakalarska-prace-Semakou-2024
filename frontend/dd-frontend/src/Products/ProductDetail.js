import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { AiFillStar } from 'react-icons/ai';
import Nav from '../Navigation/Nav';
import "./ProductDetail.css";

function ProductDetail() {
  const { productId } = useParams();
  const [product, setProduct] = useState(null);
  const [comments, setComments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [commentText, setCommentText] = useState('');
  const [rating, setRating] = useState(1);
  const [user, setUser] = useState(null);
  const [addToCartResponse, setAddToCartResponse] = useState('');

  useEffect(() => {
    async function fetchProductAndComments() {
      try {
        let userObject = null;
    
        // Fetch user data from local storage if it exists
        const userString = localStorage.getItem('user');
        if (userString) {
          userObject = JSON.parse(userString);
        }
    
        // Fetch product data
        const productResponse = await fetch(`http://localhost:8090/api/products/${productId}`);
        if (!productResponse.ok) {
          throw new Error('Failed to fetch product data');
        }
        const productData = await productResponse.json();
    
        // Fetch comments data
        const commentsResponse = await fetch(`http://localhost:8090/api/products/comments/${productId}`);
        if (!commentsResponse.ok) {
          throw new Error('Failed to fetch comments data');
        }
        const commentsData = await commentsResponse.json();
    
        setProduct(productData);
        setComments(commentsData);
        setUser(userObject);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching product, comments, or user data:', error);
      }
    }
    

    fetchProductAndComments();
  }, [productId]);

  const handleBuyClick = async () => {
    try {
      const response = await fetch(`http://localhost:8090/api/products/${productId}/addToCart`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          userId: user.id,
          productId: productId, // Ensure productId is stringified
        }),
      });
  
      if (!response.ok) {
        throw new Error('Failed to add product to cart');
      }
  
      const responseData = await response.json();
      setAddToCartResponse(responseData.message);
    } catch (error) {
      console.error('Error adding product to cart:', error);
      setAddToCartResponse('Error adding product to cart');
    }
  };
  

  const handleBackClick = () => {
    window.location.href = 'http://localhost:3000/home';
  };

  const handleRateProduct = async () => {
    try {
      const response = await fetch(`http://localhost:8090/api/products/comments/new`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          userId: user.id,
          productId,
          text: commentText,
          rating
        }),
      });

      if (!response.ok) {
        throw new Error('Failed to rate the product');
      }

      setCommentText('');
      setRating(1);

      const updatedCommentsResponse = await fetch(`http://localhost:8090/api/products/comments/${productId}`);
      if (!updatedCommentsResponse.ok) {
        throw new Error('Failed to fetch updated comments');
      }
      const updatedCommentsData = await updatedCommentsResponse.json();
      setComments(updatedCommentsData);
    } catch (error) {
      console.error('Error rating the product:', error);
    }
  };


  if (loading) {
    return <div>Loading...</div>;
  }

  if (!product) {
    return <div>Product not found</div>;
  }

  const newPrice = product.price * (100 - product.sale) / 100;

  const renderStars = () => {
    const stars = [];
    for (let i = 0; i < product.rating; i++) {
      stars.push(<AiFillStar key={i} className="rating-star" />);
    }
    return stars;
  };

  return (
    <div>
      <Nav />
      <div className="product-detail-container">
        <h2 className="product-title">{product.name}</h2>
        <img src={product.image} alt={product.name} className="product-image" />
        <p className="product-description">Description: {product.description}</p>
        <p className="product-label">Price: 
          <span className="original-price">${product.price.toFixed(2)}</span>
          <span className="price-gap">-</span>
          <span className="new-price">${newPrice.toFixed(2)}</span>
        </p>
        <p className="product-label">Manufacturer: <span className="product-value">{product.manufacturer}</span></p>
        <p className="product-label">Category: <span className="product-value">{product.category}</span></p>
        <p className="product-label">Rating: {renderStars()}</p>
        <p className="product-label">Sale: <span className="sale">{product.sale}%</span></p>

        <div className="buttons-row">
          <button className="add-to-cart-button" onClick={handleBuyClick}>Add to Cart</button>
          <button className="back-button" onClick={handleBackClick}>Back</button>
        </div>
  
        <div className="comments-section">
          <div className="comments-header">
            {user && (
              <div className="comment-inputs">
                <textarea 
                  value={commentText} 
                  onChange={(e) => setCommentText(e.target.value)} 
                  placeholder="Add your comment..." 
                  className="comment-textarea" 
                />
                <select 
                  value={rating} 
                  onChange={(e) => setRating(parseInt(e.target.value))} 
                  className="rating-select"
                >
                  {[1, 2, 3, 4, 5].map((value) => (
                    <option key={value} value={value}>{value}</option>
                  ))}
                </select>
                <button className="rate-button" onClick={handleRateProduct}>Rate</button>
              </div>
            )}
          </div>
          <div className="comments-label">
            <h3 className="comments-heading">Comments:</h3>
          </div>
          <ul>
            {comments.map(comment => (
              <li key={comment.commentId} className="comment-frame">
                <div className="comment-header">
                  <span className="comment-username">{comment.username}</span>
                  <span className="comment-role">{comment.role}</span>
                </div>
                <div className="comment-text">
                  <p>{comment.text}</p>
                </div>
                <div className="comment-rating">
                  {[...Array(comment.rating)].map((_, index) => (
                    <AiFillStar key={index} className="rating-star" />
                  ))}
                </div>
                <div className="comment-created-at">
                  Created At: {new Date(comment.createdAt).toLocaleString()}
                </div>
              </li>
            ))}
          </ul>
        </div>
        
        {addToCartResponse && <div>{addToCartResponse}</div>}
      </div>
    </div>
  );
}

export default ProductDetail;
