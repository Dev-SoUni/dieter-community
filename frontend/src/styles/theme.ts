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
  },
})
