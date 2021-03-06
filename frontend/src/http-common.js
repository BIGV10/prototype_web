import axios from 'axios';
import router from '@/router';

const api = axios.create({
    // 'baseURL': process.env.VUE_APP_BASE_URL + "/api",
    baseURL: "http://localhost:9000/api",
    // baseURL: "http://bigv.ddns.net:9000/api",
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
    }
});

api.interceptors.response.use(null, error => {
    let path = '#';
    switch (error.response.status) {
        case 401:
            path = '/login';
            break;
        case 403:
            path = '/403';
            break;
        // case 404:
        //     path = '/404';
        //     break;
    }
    router.push(path);
    return Promise.reject(error);
});

export default api;