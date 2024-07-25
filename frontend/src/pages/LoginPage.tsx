import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'
import Stack from '@mui/material/Stack'
import TextField from '@mui/material/TextField'
import Button from '@mui/material/Button'

import { useAppDispatch, useAppSelector } from '../app/hook.ts'
import { setAccessToken, setMember } from '../features/auth/authSlice.ts'
import { authenticate } from '../api/auth.ts'

interface FormInputs {
  email: string
  password: string
}

const LoginPage = () => {
  const navigate = useNavigate()
  const dispatch = useAppDispatch()
  const authStore = useAppSelector((state) => state.auth)

  const [formInputs, setFormInputs] = useState<FormInputs>({
    email: '',
    password: '',
  })

  useEffect(() => {
    if (authStore.accessToken != null) {
      navigate('/')
    }
  }, [authStore.accessToken])

  const handleFormInputsChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormInputs((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }))
    e.target.name
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      const { email, nickname, accessToken } = await authenticate(formInputs)

      window.localStorage.setItem('accessToken', accessToken)
      dispatch(setAccessToken(accessToken))
      dispatch(setMember({ email, nickname }))
    } catch (error) {
      alert('관리자에게 문의주세요.')
    }
  }

  return (
    <Box
      component="main"
      sx={{
        height: '100%',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <Box
        p={4}
        borderRadius={1}
        boxShadow={1}
        sx={{
          width: '100%',
          maxWidth: '400px',
        }}
      >
        <Typography component="h1" variant="h4" textAlign="center" mb={4}>
          로그인
        </Typography>

        <form onSubmit={handleSubmit}>
          <Stack direction="column" gap={2}>
            <Box>
              <TextField
                variant="standard"
                type="email"
                name="email"
                label="이메일 주소"
                required
                fullWidth
                value={formInputs.email}
                onChange={handleFormInputsChange}
              />
            </Box>
            <Box>
              <TextField
                variant="standard"
                type="password"
                name="password"
                label="비밀번호"
                required
                fullWidth
                value={formInputs.password}
                onChange={handleFormInputsChange}
              />
            </Box>
            <Box>
              <Button type="submit" variant="contained" fullWidth size="large">
                로그인
              </Button>
            </Box>
          </Stack>
        </form>
      </Box>
    </Box>
  )
}

export default LoginPage
