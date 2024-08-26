import axios from "axios";

function create(baseURL, options) {
    const instance = axios.create(Object.assign({ baseURL }, options));
    return instance;
}

export const boardApi = create(`${import.meta.env.VITE_API_BASE_URL}/board`)