import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'
import Stack from '@mui/material/Stack'
import TextField from '@mui/material/TextField'
import Button from '@mui/material/Button'

import CustomHelmet from '../components/helmet'
import { useAppDispatch, useAppSelector } from '../app/hook.ts'
import { setAccessToken, setMember } from '../features/auth/authSlice.ts'
import { authenticate } from '../api/auth.ts'
import { LoginSchema } from '../schemas/login.tsx'

import { z } from 'zod'

const LoginPage = () => {
  const navigate = useNavigate()
  const dispatch = useAppDispatch()
  const authStore = useAppSelector((state) => state.auth)

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<z.infer<typeof LoginSchema>>({
    resolver: zodResolver(LoginSchema),
    defaultValues: {
      email: '',
      password: '',
    },
  })

  useEffect(() => {
    if (authStore.accessToken != null) {
      navigate('/')
    }
  }, [authStore.accessToken])

  const onSubmit = handleSubmit(async (values) => {
    try {
      const { email, nickname, accessToken } = await authenticate(values)

      window.localStorage.setItem('accessToken', accessToken)
      dispatch(setAccessToken(accessToken))
      dispatch(setMember({ email, nickname }))
    } catch (error) {
      alert('로그인에 실패했습니다.')
    }
  })

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
      <CustomHelmet
        title="로그인 | 꿀팁"
        description="다이어트커뮤니티 로그인 페이지입니다."
      />
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

        <form onSubmit={onSubmit}>
          <Stack direction="column" gap={2}>
            <Box>
              <TextField
                variant="standard"
                type="email"
                label="이메일 주소"
                fullWidth
                error={!!errors.email}
                helperText={errors.email && errors.email.message}
                {...register('email')}
              />
            </Box>
            <Box>
              <TextField
                variant="standard"
                type="password"
                label="비밀번호"
                fullWidth
                error={!!errors.password}
                helperText={errors.password && errors.password.message}
                {...register('password')}
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
