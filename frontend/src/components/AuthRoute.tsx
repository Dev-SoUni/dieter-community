import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Stack from '@mui/material/Stack'

import { useSession } from '../hooks/useSession.tsx'
import React from 'react'

interface AuthRouteProps {
  component: React.ElementType
}

export const AuthRoute = ({ component: Component }: AuthRouteProps) => {
  const { session } = useSession()

  if (session === 'authenticated') {
    return (
      <Box>
        <Stack>
          <Typography>해당 페이지 접근 권한이 없습니다.</Typography>
        </Stack>
      </Box>
    )
  }

  return <Component />
}
