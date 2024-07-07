import { useEffect, useState } from 'react'
import { useParams, Link, useNavigate } from 'react-router-dom'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Button from '@mui/material/Button'

import { useAppSelector } from '../app/hook.ts'
import { defaultAxios } from '../config/axios.ts'
import { DeleteButton } from '../components/DeleteButton.tsx'
import type { TipResponseDTO } from '../ts/dto.ts'

const TipDetailPage = () => {
  const params = useParams()
  const navigate = useNavigate()

  const [tip, setTip] = useState<TipResponseDTO | null>(null)
  const [canEdit, setCanEdit] = useState<boolean>(false)

  const authStore = useAppSelector((state) => state.auth)

  useEffect(() => {
    const requestTip = async () => {
      try {
        const response = await defaultAxios.get<TipResponseDTO>(
          `/api/tips/${params.id}`,
        )
        setTip(response.data)
      } catch (error) {
        alert('관리자에게 문의주세요.')
      }
    }

    requestTip()
  }, [])

  useEffect(() => {
    setCanEdit(tip?.writer.email === authStore.member?.email)
  }, [tip, authStore.member])

  const handleDelete = async () => {
    try {
      await defaultAxios.delete(`/api/tips/${params.id}`)
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
          {canEdit && (
            <Link to={`/tips/${tip.id}/edit`}>
              <Button variant="outlined">수정</Button>
            </Link>
          )}
          {canEdit && <DeleteButton onConfirm={handleDelete} />}
          <Link to="/tips">
            <Button variant="outlined">목록</Button>
          </Link>
        </Box>
      </Box>
    </main>
  )
}

export default TipDetailPage
