import axios from 'axios'

// Axios 인스턴스
//
// 공식 문서: https://axios-http.com/kr/docs/intro

export const defaultAxios = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
})
