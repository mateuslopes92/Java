import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080/api",
});

// eslint-disable-next-line react-refresh/only-export-components
export default API;
