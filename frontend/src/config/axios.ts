import axios from 'axios'

// Axios 인스턴스
//
// 공식 문서: https://axios-http.com/kr/docs/intro

export const defaultAxios = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
})

defaultAxios.interceptors.request.use(
  (config) => {
    const accessToken = window.localStorage.getItem('accessToken')

    if (accessToken) {
      config.headers['Authorization'] = `Bearer ${accessToken}`
    }

    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)

defaultAxios.interceptors.response.use(
  (response) => response,
  (error) => {
    console.log(error)
  },
)
