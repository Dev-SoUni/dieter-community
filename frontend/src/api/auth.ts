import { defaultAxios } from '../config/axios.ts'
import type {
  AuthenticationRequest,
  AuthenticateResponse,
  RefreshResponse,
} from '../ts/dto.ts'

export const authenticate = async (
  body: AuthenticationRequest,
): Promise<AuthenticateResponse> => {
  const response = await defaultAxios.post('/api/auth', body)

  return response.data
}

export const refresh = async (): Promise<RefreshResponse> => {
  const response = await defaultAxios.post('/api/auth/refresh')

  return response.data
}

export const logout = async () => {
  await defaultAxios.post('/api/auth/logout')
}
