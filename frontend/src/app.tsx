import { useEffect } from 'react'
import { ThemeProvider } from '@mui/material'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import { HelmetProvider } from 'react-helmet-async'

import { useAppDispatch } from './app/hook.ts'
import { setAccessToken, setMember } from './features/auth/authSlice.ts'
import { defaultAxios } from './config/axios.ts'
import MainLayout from './layouts/MainLayout.tsx'
import MainPage from './pages/MainPage.tsx'
import LoginPage from './pages/LoginPage.tsx'
import LogoutPage from './pages/LogoutPage.tsx'
import TipPage from './pages/TipPage.tsx'
import TipNewPage from './pages/TipNewPage.tsx'
import TipDetailPage from './pages/TipDetailPage.tsx'
import TipEditPage from './pages/TipEditPage.tsx'
import ErrorPage from './pages/ErrorPage.tsx'
import { AuthRoute } from './components/AuthRoute.tsx'
import { theme } from './styles/theme.ts'
import { MemberResponse } from './ts/dto.ts'
import CustomHelmet from './components/helmet'

const router = createBrowserRouter([
  {
    path: '',
    element: <MainLayout />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: '/',
        element: <MainPage />,
      },
      {
        path: '/auth/login',
        element: <LoginPage />,
      },
      {
        path: '/auth/logout',
        element: <LogoutPage />,
      },
      {
        path: '/tips',
        element: <TipPage />,
      },
      {
        path: '/tips/new',
        element: <AuthRoute component={TipNewPage} />,
      },
      {
        path: '/tips/:id',
        element: <TipDetailPage />,
      },
      {
        path: '/tips/:id/edit',
        element: <AuthRoute component={TipEditPage} />,
      },
    ],
  },
])

const App = () => {
  const dispatch = useAppDispatch()

  useEffect(() => {
    const setAuthStore = async () => {
      const accessToken = window.localStorage.getItem('accessToken')

      if (accessToken) {
        try {
          const response = await defaultAxios.get<MemberResponse>('/api/auth')
          const { email, nickname } = response.data

          dispatch(setAccessToken(accessToken))
          dispatch(setMember({ email, nickname }))
        } catch (error) {
          alert('죄송합니다. 문제가 발생했습니다.')
          dispatch(setAccessToken(null))
          dispatch(setMember(null))
        }
      }
    }

    setAuthStore()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return (
    <HelmetProvider>
      <CustomHelmet
        title="다커"
        description="다이어트 커뮤니티 메인 페이지 입니다."
      />
      <ThemeProvider theme={theme}>
        <RouterProvider router={router} />
      </ThemeProvider>
    </HelmetProvider>
  )
}

export default App
