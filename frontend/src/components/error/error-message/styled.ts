import styled from '@emotion/styled'
import MuiErrorIcon from '@mui/icons-material/Error'

export const Wrapper = styled.section`
  width: 100%;
  height: 100%;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 20px 0;
`

export const ErrorIcon = styled(MuiErrorIcon)`
  color: #ef4444;
  font-size: 60px;
`

export const TitleText = styled.h2`
  font-size: 56px;
  font-weight: 800;
`

export const MessageParagraph = styled.p`
  color: rgba(0, 0, 0, 0.8);
  font-size: 20px;
  font-weight: 600;
`
