import { createTheme } from '@mui/material'

export const theme = createTheme({
  palette: {
    primary: {
      main: '#94a3b8',
    },
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          padding: '11px 20px',
          fontSize: '13px',
          borderRadius: '12px',
          boxShadow: 'none',
          transition: 'none',
        },
      },
    },
    MuiFormLabel: {
      styleOverrides: {
        // root:
        asterisk: {
          display: 'none',
        },
      },
    },
  },
})
