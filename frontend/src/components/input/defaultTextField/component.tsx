import * as SC from './styled'
import Box from '@mui/material/Box'
import React from 'react'

interface DefaultTextFieldProps {
  id: string
  label: string
  value: string
  placeholder?: string
  required?: boolean
  multiline?: boolean
  rows?: number
  onChange: (
    e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>,
  ) => void
}

// TODO: 오류났을 경우 color = 'warning'으로 변경 필요
export const DefaultTextField = ({
  id,
  label,
  value,
  placeholder,
  required = false,
  multiline = false,
  rows,
  onChange,
}: DefaultTextFieldProps) => {
  return (
    <Box>
      <SC.Label htmlFor={id}>
        {label}
        {required && (
          <SC.RequiredDotWrap>
            <SC.RequiredDot
              src="data:image/svg+xml;base64,PHN2ZyB2aWV3Qm94PSIwIDAgMjIgMjIiIGZpbGw9IiNGNjY1NzAiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CiAgICAgICAgICAgICAgPGNpcmNsZSBjeD0iMTEiIGN5PSIxMSIgcj0iMTEiIC8+Cjwvc3ZnPg=="
              alt="필수 요소"
            />
          </SC.RequiredDotWrap>
        )}
      </SC.Label>

      <SC.DefaultTextField
        id={id}
        value={value}
        placeholder={placeholder}
        required={required}
        multiline={multiline}
        rows={rows}
        onChange={onChange}
      />
    </Box>
  )
}
