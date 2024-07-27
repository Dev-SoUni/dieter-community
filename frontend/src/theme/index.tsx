import { useMemo } from 'react'
import {
  createTheme,
  ThemeProvider as MuiThemeProvider,
} from '@mui/material/styles'
import CssBaseline from '@mui/material/CssBaseline'

import { palette } from './palette.ts'
import { typography } from './typography.ts'
import { overrides } from './overrides.ts'

const ThemeProvider = ({ children }: React.PropsWithChildren) => {
  const memoizedValue = useMemo(
    () => ({
      palette: palette(),
      typography,
    }),
    [],
  )

  const theme = createTheme(memoizedValue)

  theme.components = overrides(theme)

  return (
    <MuiThemeProvider theme={theme}>
      <CssBaseline />
      {children}
    </MuiThemeProvider>
  )
}

export default ThemeProvider
