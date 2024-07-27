import Box from '@mui/material/Box'
import FiberManualRecordIcon from '@mui/icons-material/FiberManualRecord'
import type { TextFieldProps } from '@mui/material/TextField'

import * as SC from './styled'

export const DefaultTextField = (props: TextFieldProps) => {
  const { id, label, required } = props

  return (
    <Box>
      <SC.Label htmlFor={id}>
        {label}
        {required && (
          <SC.RequiredDotWrap>
            <FiberManualRecordIcon
              sx={{ width: 6, height: 6, color: '#f66570' }}
            />
          </SC.RequiredDotWrap>
        )}
      </SC.Label>

      <SC.DefaultTextField {...props} label="" />
    </Box>
  )
}
