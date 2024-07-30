import { z } from 'zod'

const fields = {
  title: z.string().min(1, '제목은 필수 입력 사항입니다.'),
  content: z.string().min(1, '내용은 필수 입력 사항입니다.'),
}

export const TipSchema = z.object({
  title: fields.title,
  content: fields.content,
})
