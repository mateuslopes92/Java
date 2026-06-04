import { useContext, useEffect, useState } from "react";

import AppContext from "../Context/Context";
import { Link } from "react-router-dom";
import axios from "axios";
import unplugged from "../assets/unplugged.png"

// eslint-disable-next-line react/prop-types
const Home = ({ selectedCategory }) => {
  const { data, isError, addToCart, refreshData } = useContext(AppContext);
  const [products, setProducts] = useState([]);
  const [isDataFetched, setIsDataFetched] = useState(false);

  useEffect(() => {
    if (!isDataFetched) {
      refreshData();
      setIsDataFetched(true);
    }
  }, [refreshData, isDataFetched]);

  useEffect(() => {
    if (data && data.length > 0) {
      const fetchImagesAndUpdateProducts = async () => {
        const updatedProducts = await Promise.all(
          data.map(async (product) => {
            try {
              const response = await axios.get(
                `http://localhost:8080/api/product/${product.id}/image`,
                { responseType: "blob" }
              );
              const imageUrl = URL.createObjectURL(response.data);
              return { ...product, imageUrl };
            } catch (error) {
              console.error(
                "Error fetching image for product ID:",
                product.id,
                error
              );
              return { ...product, imageUrl: "placeholder-image-url" };
            }
          })
        );
        setProducts(updatedProducts);
      };

      fetchImagesAndUpdateProducts();
    }
  }, [data]);

  const filteredProducts = selectedCategory
    ? products.filter((product) => product.category === selectedCategory)
    : products;

  if (isError) {
    return (
      <h2 className="text-center" style={{ padding: "18rem" }}>
        <img src={unplugged} alt="Error" style={{ width: '100px', height: '100px' }} />
      </h2>
    );
  }
  return (
    <div className="grid">
      {filteredProducts.length === 0 ? (
        <h2 className="text-center">No Products Available</h2>
      ) : (
        filteredProducts.map((product) => {
          const { id, brand, name, price, available, imageUrl } = product;
          return (
            <div className={`card ${!available ? "unavailable" : ""}`} key={id}>
              <Link to={`/product/${id}`} style={{ textDecoration: "none", color: "inherit", display: "flex", flexDirection: "column", height: "100%" }}>
                <div className="card-img-wrap">
                  <img src={imageUrl} alt={name} />
                </div>
                <div className="card-body">
                  <h5 className="card-title">{name.toUpperCase()}</h5>
                  <i className="card-brand">{"~ " + brand}</i>
                  <div className="home-cart-price">
                    <h5 className="card-text"><i className="bi bi-currency-rupee"></i>{price}</h5>
                  </div>
                  <button
                    className="btn-hover"
                    onClick={(e) => {
                      e.preventDefault();
                      addToCart(product);
                    }}
                    disabled={!available}
                  >
                    {available ? "Add to Cart" : "Out of Stock"}
                  </button>
                </div>
              </Link>
            </div>
          );
        })
      )}
    </div>
  );
};

export default Home;
