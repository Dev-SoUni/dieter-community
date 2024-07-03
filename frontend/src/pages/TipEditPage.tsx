import { useEffect, useState } from 'react'
import { Link, useParams, useNavigate } from 'react-router-dom'
import Typography from '@mui/material/Typography'

import type { TipResponseDTO } from '../ts/dto.ts'
import Box from '@mui/material/Box'
import TextField from '@mui/material/TextField'
import Button from '@mui/material/Button'

const TipEditPage = () => {
  const params = useParams()
  const navigate = useNavigate()
  const [tip, setTip] = useState<TipResponseDTO | null>(null)
  const [title, setTitle] = useState<string>('')
  const [content, setContent] = useState<string>('')

  useEffect(() => {
    const requestTip = async () => {
      const response = await fetch(
        `http://localhost:8080/api/tips/${params.id}`,
      )
      const body = await response.json()

      setTip(body)
      setTitle(body.title)
      setContent(body.content)
    }
    requestTip()
  }, [])

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      await fetch(`http://localhost:8080/api/tips/${params.id}`, {
        method: 'PATCH',
        body: JSON.stringify({ title, content }),
        headers: {
          'Content-Type': 'application/json',
        },
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
      <Typography component="h1" variant="h5">
        꿀팁 수정
      </Typography>
      <Box mt={2}>
        <form onSubmit={handleSubmit}>
          <Box display="flex" flexDirection="column" gap={2}>
            <Box>
              <TextField
                label="제목"
                placeholder="제목을 입력해주세요."
                fullWidth
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              />
            </Box>
            <Box>
              <TextField
                label="내용"
                placeholder="내용을 입력해주세요."
                multiline
                fullWidth
                rows={10}
                value={content}
                onChange={(e) => setContent(e.target.value)}
              />
            </Box>
            <Box display="flex" justifyContent="end" gap={2}>
              <Link to={`/tips/${tip.id}`}>
                <Button type="button" variant="outlined">
                  취소
                </Button>
              </Link>
              <Button type="submit" variant="contained">
                변경사항 저장
              </Button>
            </Box>
          </Box>
        </form>
      </Box>
    </main>
  )
}

export default TipEditPage
