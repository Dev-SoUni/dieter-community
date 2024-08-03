import { useEffect, useState } from 'react'
import { Link, useParams, useNavigate } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { toast } from 'sonner'
import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'

import { defaultAxios } from '../config/axios.ts'
import type { TipResponseDTO } from '../ts/dto.ts'

import CustomHelmet from '../components/helmet'
import DefaultTextField from '../components/input/defaultTextField'
import { TipSchema } from '../schemas/tip.tsx'

const EditForm = ({ tip }: { tip: TipResponseDTO }) => {
  const navigate = useNavigate()

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<z.infer<typeof TipSchema>>({
    resolver: zodResolver(TipSchema),
    defaultValues: {
      title: tip.title,
      content: tip.content,
    },
  })

  const onSubmit = handleSubmit(async (value) => {
    try {
      await defaultAxios.patch(`/api/tips/${tip.id}`, value)
      navigate('/tips')
      toast.success('꿀팁이 등록되었습니다.')
    } catch (e) {
      alert('꿀팁 등록이 안되었습니다. 잠시 후 다시 시도해 주세요.')
    }
  })

  return (
    <form onSubmit={onSubmit}>
      <Box display="flex" flexDirection="column" gap={2}>
        <Box>
          <DefaultTextField
            label="제목"
            id="title"
            name="title"
            control={control}
            error={!!errors.title}
            helperText={errors.title && errors.title.message}
          />
        </Box>
        <Box>
          <DefaultTextField
            label="내용"
            id="content"
            name="content"
            multiline
            rows={10}
            control={control}
            error={!!errors.content}
            helperText={errors.content && errors.content.message}
          />
        </Box>
        <Box display="flex" justifyContent="end" gap={2}>
          <Button type="submit" variant="contained" color="info">
            수정
          </Button>
          <Link to={`/tips/${tip.id}`}>
            <Button type="button" variant="contained" color="secondary">
              목록
            </Button>
          </Link>
        </Box>
      </Box>
    </form>
  )
}

const TipEditPage = () => {
  const params = useParams()
  const [tip, setTip] = useState<TipResponseDTO | null>(null)

  useEffect(() => {
    const requestTip = async () => {
      try {
        const response = await defaultAxios.get(`/api/tips/${params.id}`)
        setTip(response.data)
      } catch (error) {
        alert('관리자에게 문의주세요.')
      }
    }
    requestTip()
  }, [])

  if (tip == null) {
    return (
      <Box>
        <Typography variant="h5" component="p">
          데이터를 불러오는 중..
        </Typography>
      </Box>
    )
  }
  return (
    <main>
      <CustomHelmet
        title="꿀팁 수정 | 다커"
        description="꿀팁 수정 페이지입니다."
      />
      <Typography component="h1" variant="h5">
        꿀팁 수정
      </Typography>
      <Box mt={2}>
        <EditForm tip={tip} />
      </Box>
    </main>
  )
}

export default TipEditPage
