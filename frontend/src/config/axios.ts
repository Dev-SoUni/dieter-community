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

// Axios 인터셉터
//
// 공식 문서: https://axios-http.com/kr/docs/interceptors

defaultAxios.interceptors.response.use(
  (response) => response,
  // Axios 에러 핸들링
  //
  // 공식 문서: https://axios-http.com/kr/docs/handling_errors
  (error) => {
    if (error.response) {
      console.log('요청에 대한 응답이 2XX 외의 상태 코드입니다.', {
        status: error.response.status,
        headers: error.response.headers,
        data: error.response.data,
      })

      // 액세스 토큰 만료에 대한 임시 처리 (강제 로그아웃)
      // 추후 변경이 필요함!! Refresh Token 도입 예정
      if (
        error.response.data.status === 401 &&
        error.response.data.message === '토큰이 만료되었습니다.'
      ) {
        // TODO: Refresh Access Token
        window.localStorage.removeItem('accessToken')
        window.location.reload()
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
