const { VITE_BACKEND_URL } = import.meta.env;
const API_URL = VITE_BACKEND_URL || 'http://localhost:8081';

export { API_URL };
