import { useEffect, useState } from 'react'
import { Navigate } from 'react-router-dom'
import Box from '@mui/material/Box'

import { useAppDispatch } from '../app/hook.ts'
import Loading from '../components/common/loading'
import { logout as logoutDispatch } from '../features/auth/authSlice.ts'
import { logout as logoutAPI } from '../api/auth.ts'

const LogoutPage = () => {
  const [isLoading, setIsLoading] = useState(true)

  const dispatch = useAppDispatch()

  useEffect(() => {
    const logout = async () => {
      try {
        setIsLoading(true)
        await logoutAPI()
        dispatch(logoutDispatch())
      } catch (error) {
        console.log(error)
        throw new Error('로그아웃 중 문제가 발생했습니다.')
      } finally {
        setIsLoading(false)
      }
    }

    logout()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  if (isLoading) {
    return (
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',

          mt: 24,
        }}
      >
        <Loading size="lg" />
      </Box>
    )
  }

  return <Navigate to="/" />
}

export default LogoutPage
