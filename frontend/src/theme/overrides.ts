import type { Theme } from '@mui/material/styles'

export const overrides = (theme: Theme): Theme['components'] => {
  return {
    MuiCssBaseline: {
      styleOverrides: {
        '*': {
          boxSizing: 'border-box',
        },
        html: {
          margin: 0,
          padding: 0,
          width: '100%',
          height: '100%',
          WebkitOverflowScrolling: 'touch',
        },
        body: {
          margin: 0,
          padding: 0,
          width: '100%',
          height: '100%',
        },
        'div#root': {
          width: '100%',
          height: '100%',
        },
        ul: {
          padding: 0,
        },
        a: {
          color: theme.palette.common.black,
          textDecoration: 'none',
          outline: 'none',
        },
      },
    },
    MuiButton: {
      styleOverrides: {
        contained: {
          color: theme.palette.common.white,
          backgroundColor: theme.palette.grey[800],
          '&:hover': {
            color: theme.palette.common.white,
            backgroundColor: theme.palette.grey[800],
          },
        },
        sizeLarge: {
          minHeight: 48,
        },
      },
    },
    MuiFormLabel: {
      styleOverrides: {
        asterisk: {
          display: 'none',
        },
      },
    },
  }
}
