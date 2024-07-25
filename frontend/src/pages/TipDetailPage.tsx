import { useEffect, useState } from 'react'
import { useParams, Link, useNavigate } from 'react-router-dom'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Button from '@mui/material/Button'
import IconButton from '@mui/material/IconButton'
import FavoriteIcon from '@mui/icons-material/Favorite'
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder'

import { useAppSelector } from '../app/hook.ts'
import { useSession } from '../hooks/useSession.tsx'
import { defaultAxios } from '../config/axios.ts'
import { DeleteButton } from '../components/DeleteButton.tsx'
import type { TipResponseDTO, TipLikeResponseDTO } from '../ts/dto.ts'
import CustomHelmet from '../components/helmet'

interface LikeButtonProps {
  tipId: string
}

const LikeButton = ({ tipId }: LikeButtonProps) => {
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [isLiked, setIsLiked] = useState<boolean>(false)

  useEffect(() => {
    getIsLiked()
  }, [])

  const getIsLiked = async () => {
    try {
      setIsLoading(true)
      const response = await defaultAxios.get<TipLikeResponseDTO>(
        `/api/tip-likes/auth?tipId=${tipId}`,
      )

      setIsLiked(response.status === 200)
    } catch (error) {
      alert('관리자에게 문의주세요.')
    } finally {
      setIsLoading(false)
    }
  }

  const handleLike = async () => {
    try {
      await defaultAxios.post<TipLikeResponseDTO>('/api/tip-likes', { tipId })
      alert('꿀팁 좋아요되었습니다.')
      setIsLiked(true)
    } catch (error) {
      alert('관리자에게 문의주세요.')
    }
  }

  const handleDislike = async () => {
    try {
      await defaultAxios.delete(`/api/tips/${tipId}/tip-likes`)
      alert('꿀팁 해제되었습니다.')
      setIsLiked(false)
    } catch (error) {
      alert('관리자에게 문의주세요.')
    }
  }

  if (isLoading) return null

  return (
    <IconButton onClick={isLiked ? handleDislike : handleLike}>
      {isLiked ? <FavoriteIcon /> : <FavoriteBorderIcon />}
    </IconButton>
  )
}

const TipDetailPage = () => {
  const params = useParams()
  const navigate = useNavigate()
  const tipId = params.id

  const [tip, setTip] = useState<TipResponseDTO | null>(null)
  const [canEdit, setCanEdit] = useState<boolean>(false)

  const authStore = useAppSelector((state) => state.auth)
  const { session } = useSession()

  useEffect(() => {
    const requestTip = async () => {
      try {
        const response = await defaultAxios.get<TipResponseDTO>(
          `/api/tips/${tipId}`,
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
      await defaultAxios.delete(`/api/tips/${tipId}`)
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
      <CustomHelmet
        title="꿀팁 상세 | 다커"
        description="꿀팁 상세 페이지입니다."
      />
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
          {tipId && session === 'authenticated' && <LikeButton tipId={tipId} />}
        </Box>
      </Box>
    </main>
  )
}

export default TipDetailPage
