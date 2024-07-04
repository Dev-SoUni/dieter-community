import React from 'react'
import ReactDOM from 'react-dom/client'
import { Provider } from 'react-redux'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'

import '@fontsource/roboto/300.css'
import '@fontsource/roboto/400.css'
import '@fontsource/roboto/500.css'
import '@fontsource/roboto/700.css'

import { store } from './app/store.ts'
import MainPage from './pages/MainPage.tsx'
import TipPage from './pages/TipPage.tsx'
import TipNewPage from './pages/TipNewPage.tsx'
import TipDetailPage from './pages/TipDetailPage.tsx'
import TipEditPage from './pages/TipEditPage.tsx'

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
  {
    path: '/tips/:id',
    element: <TipDetailPage />,
  },
  {
    path: '/tips/:id/edit',
    element: <TipEditPage />,
  },
])

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <Provider store={store}>
      <RouterProvider router={router} />
    </Provider>
  </React.StrictMode>,
)
