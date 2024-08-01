import { Link, useNavigate } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'

import { defaultAxios } from '../config/axios.ts'
import CustomHelmet from '../components/helmet'
import DefaultTextField from '../components/input/defaultTextField'
import { TipSchema } from '../schemas/tip.tsx'

const TipNewPage = () => {
  const navigate = useNavigate()

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<z.infer<typeof TipSchema>>({
    resolver: zodResolver(TipSchema),
    defaultValues: {
      title: '',
      content: '',
    },
  })

  const onSubmit = handleSubmit(async (value) => {
    try {
      await defaultAxios.post(`/api/tips`, value)
      alert('꿀팁이 등록되었습니다.')
      navigate('/tips')
    } catch (e) {
      alert('죄송합니다. 관리자에게 문의주세요.')
    }
  })

  return (
    <div>
      <CustomHelmet
        title="꿀팁 등록 | 꿀팁"
        description="꿀팁 등록 페이지입니다."
      />
      <div>
        <form onSubmit={onSubmit}>
          <Box display="flex" flexDirection="column" gap={2}>
            <div>
              <DefaultTextField
                label="제목"
                id="title"
                name="title"
                control={control}
                error={!!errors.title}
                helperText={errors.title && errors.title.message}
              />
            </div>
            <div>
              <DefaultTextField
                label="내용"
                id="content"
                name="content"
                control={control}
                multiline
                rows={10}
                error={!!errors.content}
                helperText={errors.content && errors.content.message}
              />
            </div>
            <Box display="flex" justifyContent="end" gap={2}>
              <Button type="submit" variant="contained" color="info">
                등록
              </Button>
              <Link to="/tips">
                <Button variant="contained" color="secondary">
                  목록
                </Button>
              </Link>
            </Box>
          </Box>
        </form>
      </div>
    </div>
  )
}

export default TipNewPage
