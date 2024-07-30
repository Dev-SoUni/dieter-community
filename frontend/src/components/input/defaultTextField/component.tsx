import { useController } from 'react-hook-form'
import Box from '@mui/material/Box'
import FiberManualRecordIcon from '@mui/icons-material/FiberManualRecord'

import type { Control, FieldValues, Path } from 'react-hook-form'
import type { TextFieldProps } from '@mui/material/TextField'

import * as SC from './styled'

interface DefaultTextFieldProps<T extends FieldValues> {
  control: Control<T>
  name: Path<T>
}

export const DefaultTextField = <T extends FieldValues>(
  props: DefaultTextFieldProps<T> & TextFieldProps,
) => {
  const { id, label, name, control, required } = props

  const { field } = useController({ name, control })

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

      <SC.DefaultTextField
        {...props}
        label=""
        value={field.value}
        onChange={field.onChange}
      />
    </Box>
  )
}
