import { useNavigate } from 'react-router-dom'

import { useAppDispatch } from '../app/hook.ts'
import { setAccessToken, setMember } from '../features/auth/authSlice.ts'
import { logout } from '../api/auth.ts'

export const useLogout = () => {
  // LocalStorage 'accesssToken' 키 값 삭제
  // Redux Store에서 'accessToken' 값을 null로 변경
  // Redux Store에서 'member' 값을 null로 변경

  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  return async (callbackURL: string | undefined) => {
    window.localStorage.removeItem('accessToken')
    dispatch(setAccessToken(null))
    dispatch(setMember(null))
    await logout()

    if (callbackURL) {
      navigate(callbackURL)
    }
  }
}
