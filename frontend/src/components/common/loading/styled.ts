import styled from '@emotion/styled'
import MuiHourglassEmptyIcon from '@mui/icons-material/HourglassEmpty'

import { rotate } from '../../../styles/keyframes.ts'

interface HourglassEmptyIconProps {
  size: 'sm' | 'md' | 'lg'
}

export const HourglassEmptyIcon = styled(
  MuiHourglassEmptyIcon,
)<HourglassEmptyIconProps>`
  font-size: ${(props) => {
    if (props.size === 'sm') return '20px'
    else if (props.size === 'md') return '28px'
    else if (props.size === 'lg') return '36px'
  }};
  animation: ${rotate} 2s ease-in-out 0.5s infinite;
`
