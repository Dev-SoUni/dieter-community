import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'

import { defaultAxios } from '../config/axios.ts'
import CustomHelmet from '../components/helmet'
import DefaultTextField from '../components/input/defaultTextField'

const TipNewPage = () => {
  const navigate = useNavigate()

  const [title, setTitle] = useState<string>('')
  const [content, setContent] = useState<string>('')

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    if (!title) {
      alert('제목은 필수 입력 사항입니다.')
      return
    }
    if (!content) {
      alert('내용은 필수 입력 사항입니다.')
      return
    }

    try {
      await defaultAxios.post('/api/tips', {
        title,
        content,
      })
      alert('꿀팁이 등록되었습니다.')
      navigate('/tips')
    } catch (e) {
      alert('죄송합니다. 관리자에게 문의주세요.')
    }
  }

  return (
    <div>
      <CustomHelmet
        title="꿀팁 등록 | 꿀팁"
        description="꿀팁 등록 페이지입니다."
      />
      <div>
        <form onSubmit={handleSubmit}>
          <Box display="flex" flexDirection="column" gap={2}>
            <div>
              <DefaultTextField
                id="title"
                label="제목"
                required
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              />
            </div>
            <div>
              <DefaultTextField
                id="content"
                label="내용"
                required
                multiline
                rows={10}
                value={content}
                onChange={(e) => setContent(e.target.value)}
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
