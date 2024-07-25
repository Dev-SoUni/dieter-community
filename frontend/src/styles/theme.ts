import { createTheme } from '@mui/material'

export const theme = createTheme({
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          padding: '11px 20px',
          fontSize: '13px',
          borderRadius: '12px',
          boxShadow: 'none',
          transition: 'none',

          '&.MuiButton-contained.MuiButton-colorPrimary': {
            color: '#4e5968',
            backgroundColor: '#f2f4f6',

            '&:hover': {
              backgroundColor: '#e5e8eb',
              boxShadow: 'none',
            },
          },
        },
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          '--TextField-brandBorderColor': '#E0E3E7',
          '--TextField-brandBorderHoverColor': '#B2BAC2',
          '--TextField-brandBorderFocusedColor': '#6F7E8C',
        },
      },
    },
    MuiOutlinedInput: {
      styleOverrides: {
        root: {
          borderRadius: '65px',
          backgroundColor: '#f8f9fa',
        },
        input: {
          fontSize: '14px',
          paddingLeft: '40px',
        },
        notchedOutline: {
          border: 'none',
          outline: 'none',
        },
      },
    },
    MuiInput: {
      styleOverrides: {
        root: {
          '&::before, &::after': {
            borderBottom: '2px solid var(--TextField-brandBorderColor)',
          },
          '&:hover:not(.Mui-disabled, .Mui-error):before': {
            borderBottom: '2px solid var(--TextField-brandBorderHoverColor)',
          },
          '&.Mui-focused:after': {
            borderBottom: '2px solid var(--TextField-brandBorderFocusedColor)',
          },
        },
      },
    },
    MuiFormLabel: {
      styleOverrides: {
        root: {
          '&.Mui-focused': {
            color: '#000',
          },
        },
        asterisk: {
          display: 'none',
        },
      },
    },
  },
})
