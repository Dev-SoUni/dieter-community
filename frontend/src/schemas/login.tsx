import { z } from 'zod'

export const LoginSchema = z.object({
  email: z.string().min(1, {
    message: '이메일은 필수 입력 사항입니다.',
  }),
  password: z.string().min(1, {
    message: '비밀번호는 필수 입력 사항입니다.',
  }),
})
