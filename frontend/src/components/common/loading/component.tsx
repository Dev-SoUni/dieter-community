import * as SC from './styled'

interface LoadingProps {
  size?: 'sm' | 'md' | 'lg'
}

export const Loading = ({ size = 'md' }: LoadingProps) => {
  return <SC.HourglassEmptyIcon size={size} />
}
