import { useRef, useState } from 'react'
import { Link } from 'react-router-dom'

import Box from '@mui/material/Box'
import Stack from '@mui/material/Stack'
import Container from '@mui/material/Container'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'
import AccountCircleIcon from '@mui/icons-material/AccountCircle'

import { useSession } from '../hooks/useSession.tsx'
import { useLogout } from '../hooks/useLogout'

const AccountMenuButton = () => {
  const buttonRef = useRef<HTMLButtonElement>(null)
  const [open, setOpen] = useState<boolean>(false)
  const logout = useLogout()

  const handleMenuOpen = () => {
    setOpen(true)
  }

  const handleMenuClose = () => {
    setOpen(false)
  }

  const handleLogout = () => {
    logout('/auth/login')
  }

  return (
    <>
      <IconButton ref={buttonRef} onClick={handleMenuOpen}>
        <AccountCircleIcon fontSize="large" />
      </IconButton>
      <Menu open={open} onClose={handleMenuClose} anchorEl={buttonRef.current}>
        <MenuItem onClick={handleLogout}>로그아웃</MenuItem>
      </Menu>
    </>
  )
}

export const Navbar = () => {
  const { isLoggedIn } = useSession()

  return (
    <Box component="nav" boxShadow={1} py={1}>
      <Container>
        <Stack direction="row" justifyContent="space-between">
          {/* 로고 */}
          <Box>
            <Typography component="h1" variant="h4">
              <Link to="/">DC</Link>
            </Typography>
          </Box>
          {/* 아이콘 버튼들 */}
          <Box>
            <Stack direction="row" alignItems="center" height="100%">
              {isLoggedIn && <AccountMenuButton />}
              {!isLoggedIn && <Link to="/auth/login">로그인</Link>}
            </Stack>
          </Box>
        </Stack>
      </Container>
    </Box>
  )
}
