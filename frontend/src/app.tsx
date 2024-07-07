import { useEffect } from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'

import { useAppDispatch } from './app/hook.ts'
import { setAccessToken } from './features/auth/authSlice.ts'
import MainLayout from './layouts/MainLayout.tsx'
import MainPage from './pages/MainPage.tsx'
import LoginPage from './pages/LoginPage.tsx'
import TipPage from './pages/TipPage.tsx'
import TipNewPage from './pages/TipNewPage.tsx'
import TipDetailPage from './pages/TipDetailPage.tsx'
import TipEditPage from './pages/TipEditPage.tsx'

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
    const accessToken = window.localStorage.getItem('accessToken')

    if (accessToken) {
      dispatch(setAccessToken(accessToken))
    }
  }, [])

  return <RouterProvider router={router} />
}

export default App
