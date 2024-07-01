import React from 'react'
import ReactDOM from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'

import MainPage from './pages/MainPage.tsx'
import TipPage from './pages/TipPage.tsx'
import TipNewPage from './pages/TipNewPage.tsx'

const router = createBrowserRouter([
  {
    path: '/',
    element: <MainPage />,
  },
  {
    path: '/tips',
    element: <TipPage />,
  },
  {
    path: '/tips/new',
    element: <TipNewPage />,
  },
])

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)
