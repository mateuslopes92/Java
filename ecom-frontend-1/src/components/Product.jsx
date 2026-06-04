import { useContext, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

import AppContext from "../Context/Context";
import axios from "../axios";
import { useState } from "react";

const Product = () => {
  const { id } = useParams();
  const { addToCart, removeFromCart, refreshData } = useContext(AppContext);
  const [product, setProduct] = useState(null);
  const [imageUrl, setImageUrl] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/product/${id}`
        );
        setProduct(response.data);
        if (response.data.imageName) {
          fetchImage();
        }
      } catch (error) {
        console.error("Error fetching product:", error);
      }
    };

    const fetchImage = async () => {
      const response = await axios.get(
        `http://localhost:8080/api/product/${id}/image`,
        { responseType: "blob" }
      );
      setImageUrl(URL.createObjectURL(response.data));
    };

    fetchProduct();
  }, [id]);

  const deleteProduct = async () => {
    try {
      await axios.delete(`http://localhost:8080/api/product/${id}`);
      removeFromCart(id);
      console.log("Product deleted successfully");
      refreshData();
      navigate("/");
    } catch (error) {
      console.error("Error deleting product:", error);
    }
  };

  const handleEditClick = () => {
    navigate(`/product/update/${id}`);
  };

  const handlAddToCart = () => {
    addToCart(product);
    alert("Product added to cart");
  };
  if (!product) {
    return (
      <h2 className="text-center" style={{ padding: "10rem" }}>
        Loading...
      </h2>
    );
  }
  return (
    <div className="containers">
      <img
        className="left-column-img"
        src={imageUrl}
        alt={product.imageName}
      />
      <div className="right-column">
        <div className="product-description">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'baseline' }}>
            <span>{product.category}</span>
            <p className="release-date">
              <h6>Listed : <span> <i> {new Date(product.releaseDate).toLocaleDateString()}</i></span></h6>
            </p>
          </div>
          <h1>{product.name}</h1>
          <i>{product.brand}</i>
          <p style={{ fontWeight: 'bold', fontSize: '1rem', margin: '10px 0px 0px' }}>PRODUCT DESCRIPTION :</p>
          <p>{product.desc}</p>
        </div>
        <div className="product-price">
          <span>{"$" + product.price}</span>
          <button
            className={`cart-btn ${!product.available ? "disabled-btn" : ""}`}
            onClick={handlAddToCart}
            disabled={!product.available}
          >
            {product.available ? "Add to cart" : "Out of Stock"}
          </button>
          <h6>
            Stock Available :{" "}
            <i style={{ color: "var(--badge-bg)", fontWeight: "bold" }}>
              {product.quantity}
            </i>
          </h6>
        </div>
        <div className="update-button">
          <button className="btn btn-primary" type="button" onClick={handleEditClick}>
            Update
          </button>
          <button className="btn btn-danger" type="button" onClick={deleteProduct}>
            Delete
          </button>
        </div>
      </div>
    </div>
  );
};

export default Product;
