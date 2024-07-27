import { useEffect, useState } from 'react'
import { Link, useParams, useNavigate } from 'react-router-dom'
import Typography from '@mui/material/Typography'

import { defaultAxios } from '../config/axios.ts'
import type { TipResponseDTO } from '../ts/dto.ts'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import CustomHelmet from '../components/helmet'
import DefaultTextField from '../components/input/defaultTextField'

const TipEditPage = () => {
  const params = useParams()
  const navigate = useNavigate()
  const [tip, setTip] = useState<TipResponseDTO | null>(null)
  const [title, setTitle] = useState<string>('')
  const [content, setContent] = useState<string>('')

  useEffect(() => {
    const requestTip = async () => {
      try {
        const response = await defaultAxios.get(`/api/tips/${params.id}`)
        setTip(response.data)
        setTitle(response.data.title)
        setContent(response.data.content)
      } catch (error) {
        alert('관리자에게 문의주세요.')
      }
    }
    requestTip()
  }, [])

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      await defaultAxios.patch(`/api/tips/${params.id}`, {
        title,
        content,
      })

      alert('해당 게시물이 수정되었습니다.')
      navigate(`/tips/${params.id}`)
    } catch (error) {
      alert('죄송합니다. 문제가 발생했습니다.')
    }
  }

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
        <form onSubmit={handleSubmit}>
          <Box display="flex" flexDirection="column" gap={2}>
            <Box>
              <DefaultTextField
                id="title"
                label="제목"
                value={title}
                required
                onChange={(e) => setTitle(e.target.value)}
              />
            </Box>
            <Box>
              <DefaultTextField
                id="content"
                label="내용"
                value={content}
                required
                multiline
                rows={10}
                onChange={(e) => setContent(e.target.value)}
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
      </Box>
    </main>
  )
}

export default TipEditPage
