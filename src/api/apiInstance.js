import axios from "axios";

const apiCall = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
    },
});

function create(baseURL, options) {
    const instance = axios.create(Object.assign({ baseURL }, options));
    return instance;
}

export const boardApi = create(`${import.meta.env.VITE_API_BASE_URL}/board`)