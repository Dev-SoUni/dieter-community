import { Outlet } from 'react-router-dom'
import Box from '@mui/material/Box'
import Container from '@mui/material/Container'

import { Navbar } from '../components/Navbar.tsx'

const MainLayout = () => {
  return (
    <>
      <Navbar />
      <Container>
        <Box mt={4}>
          <Outlet />
        </Box>
      </Container>
    </>
  )
}

export default MainLayout
