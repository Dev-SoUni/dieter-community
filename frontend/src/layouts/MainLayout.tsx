import { Outlet } from 'react-router-dom'
import Box from '@mui/material/Box'
import Container from '@mui/material/Container'

import Navbar from '../components/navbar'
import Footer from '../components/footer'

const MainLayout = () => {
  return (
    <>
      <Navbar />
      <Container sx={{ pb: 8 }}>
        <Box mt={4}>
          <Outlet />
        </Box>
      </Container>
      <Footer />
    </>
  )
}

export default MainLayout
