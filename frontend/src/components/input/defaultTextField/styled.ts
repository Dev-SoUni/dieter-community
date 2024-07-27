import TextField from '@mui/material/TextField'

import styled from '@emotion/styled'

export const DefaultTextField = styled(TextField)`
  width: 100%;
  height: 100%;
`

export const Label = styled.label`
  display: inline-block;
  position: relative;
  color: #4e5968;
  padding: 4px 4px 4px 0;
`

export const RequiredDotWrap = styled.div`
  display: flex;
  position: absolute;
  top: 0;
  right: 0;
`

export const RequiredDot = styled.img`
  width: 4px;
`
