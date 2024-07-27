import * as React from 'react'
import type { Theme } from '@mui/material/styles'

declare module '@mui/material/styles' {
  interface TypeBackground {
    neutral: string
  }

  export function createTheme(options: ThemeOptions): Theme
}

declare module '@mui/material/styles/createTypography' {
  export interface FontStyle {
    fontWeightSemiBold: React.CSSProperties['fontWeight']
  }
}
