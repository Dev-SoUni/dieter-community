import { useEffect, useState } from 'react'
import { useParams, Link, useNavigate } from 'react-router-dom'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Button from '@mui/material/Button'

import { DeleteButton } from '../components/DeleteButton.tsx'
import type { TipResponseDTO } from '../ts/dto.ts'

const TipDetailPage = () => {
  const params = useParams()
  const navigate = useNavigate()
  const [tip, setTip] = useState<TipResponseDTO | null>(null)

  useEffect(() => {
    const requestTip = async () => {
      const response = await fetch(
        `http://localhost:8080/api/tips/${params.id}`,
      )
      const body = await response.json()
      setTip(body)
    }

    requestTip()
  }, [])

  const handleDelete = async () => {
    try {
      await fetch(`http://localhost:8080/api/tips/${params.id}`, {
        method: 'DELETE',
      })
      navigate('/tips')
    } catch (error) {
      alert('죄송합니다. 관리자에게 문의 주세요.')
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
        꿀팁 상세
      </Typography>
      <Box>
        <Typography component="h2" variant="h5">
          {tip.title}
        </Typography>
        <Typography>{tip.content}</Typography>
        <Box display="flex" gap={2}>
          <DeleteButton onConfirm={handleDelete} />
          <Link to="/tips">
            <Button variant="outlined">목록</Button>
          </Link>
        </Box>
      </Box>
    </main>
  )
}

export default TipDetailPage
