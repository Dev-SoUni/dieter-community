import { useNavigate } from 'react-router-dom'

import { useAppDispatch } from '../app/hook.ts'
import { setAccessToken } from '../features/auth/authSlice.ts'

export const useLogout = () => {
  // LocalStorage 'accesssToken' 키 값 삭제
  // Redux Store에서 'accessToken' 값을 null로 변경

  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  return (callbackURL: string | undefined) => {
    window.localStorage.removeItem('accessToken')
    dispatch(setAccessToken(null))

    if (callbackURL) {
      navigate(callbackURL)
    }
  }
}
