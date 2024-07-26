import { createTheme } from '@mui/material'
import { orange, red } from '@mui/material/colors'

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
    MuiInput: {
      styleOverrides: {
        root: {
          '&::before, &::after': {
            borderBottom: `2px solid #E0E3E7`,
          },

          '&.MuiInputBase-colorPrimary': {
            '&:hover:not(.Mui-disabled, .Mui-error):before': {
              borderBottom: '2px solid #B2BAC2',
            },

            '&.Mui-focused:after': {
              borderBottom: '2px solid #6F7E8C',
            },
          },
          '&.MuiInputBase-colorWarning': {
            '&:hover:not(.Mui-disabled, .Mui-error):before': {
              borderBottom: `2px solid ${orange['200']}`,
            },

            '&.Mui-focused:after': {
              borderBottom: `2px solid ${orange['500']}`,
            },
          },
          '&.MuiInputBase-colorError': {
            '&:hover:not(.Mui-disabled, .Mui-error):before': {
              borderBottom: `2px solid ${red['200']}`,
            },

            '&.Mui-focused:after': {
              borderBottom: `2px solid ${red['500']}`,
            },
          },
        },
      },
    },
    MuiFormLabel: {
      styleOverrides: {
        root: {
          '&.Mui-focused': {
            '&.MuiFormLabel-colorPrimary': {
              color: '#000',
            },
            '&.MuiFormLabel-colorWarning': {
              color: orange['500'],
            },
            '&.MuiFormLabel-colorError': {
              color: red['500'],
            },
          },
        },
        asterisk: {
          display: 'none',
        },
      },
    },
  },
})
