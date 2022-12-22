import axios from "axios";

/**
 * Usage:
 * 1. import { axiosHttpInterceptor } from "../../config/interceptor/HttpInterceptor";
 * 2. API 호출
 *    let url = "/signin"
 *    axiosHttpInterceptor
 *      .post(url, {....
 */

export const axiosHttpInterceptor = axios.create({
  baseURL: "http://localhost:9090/",
  withCredentials: false,
  headers: {
    "Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json",
  },
});


axiosHttpInterceptor.interceptors.request.use(
  (req) => {
    req.headers.authorization = `Bearer ` + localStorage.getItem('token');
    return req;
  },
  (err) => {
    return Promise.reject(err);
  }
);

axiosHttpInterceptor.interceptors.response.use(
  (res) => {
    return res;
  },
  (err) => {
    return Promise.reject(err);
  }
);
