import { useEffect } from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'

import { useAppDispatch } from './app/hook.ts'
import { setAccessToken, setMember } from './features/auth/authSlice.ts'
import { defaultAxios } from './config/axios.ts'
import MainLayout from './layouts/MainLayout.tsx'
import MainPage from './pages/MainPage.tsx'
import LoginPage from './pages/LoginPage.tsx'
import TipPage from './pages/TipPage.tsx'
import TipNewPage from './pages/TipNewPage.tsx'
import TipDetailPage from './pages/TipDetailPage.tsx'
import TipEditPage from './pages/TipEditPage.tsx'
import { MemberResponse } from './ts/dto.ts'

const router = createBrowserRouter([
  {
    path: '',
    element: <MainLayout />,
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
        path: '/tips',
        element: <TipPage />,
      },
      {
        path: '/tips/new',
        element: <TipNewPage />,
      },
      {
        path: '/tips/:id',
        element: <TipDetailPage />,
      },
      {
        path: '/tips/:id/edit',
        element: <TipEditPage />,
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
  }, [])

  return <RouterProvider router={router} />
}

export default App
