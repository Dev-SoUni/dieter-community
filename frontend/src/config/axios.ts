import axios from 'axios'
import { store } from '../app/store.ts'
import { setAccessToken, setMember } from '../features/auth/authSlice.ts'
import { logout, refresh } from '../api/auth.ts'

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

// Axios 인터셉터
//
// 공식 문서: https://axios-http.com/kr/docs/interceptors

defaultAxios.interceptors.response.use(
  (response) => response,
  // Axios 에러 핸들링
  //
  // 공식 문서: https://axios-http.com/kr/docs/handling_errors
  async (error) => {
    if (error.response) {
      console.log('요청에 대한 응답이 2XX 외의 상태 코드입니다.', {
        status: error.response.status,
        headers: error.response.headers,
        data: error.response.data,
      })

      if (
        error.response.data.status === 401 &&
        error.response.data.code === 'TOKEN_EXPIRED'
      ) {
        try {
          const { accessToken } = await refresh()

          window.localStorage.setItem('accessToken', accessToken)
          store.dispatch(setAccessToken(accessToken))

          error.config.headers.Authorization = `Bearer ${accessToken}`

          return defaultAxios(error.config)
        } catch (error) {
          window.localStorage.removeItem('accessToken')
          await logout()
          store.dispatch(setAccessToken(null))
          store.dispatch(setMember(null))
        }
      }
    } else if (error.request) {
      console.log('요청에 대한 응답을 받지 못했습니다.', {
        request: error.request,
      })
    } else {
      console.log('요청을 설정하는 중 문제가 발생했습니다.', {
        message: error.message,
      })
    }
  },
)
