import React from 'react'
import { InputAdornment, IconButton } from '@mui/material'
import type { OutlinedInputProps } from '@mui/material'
import { Search as SearchIcon } from '@mui/icons-material'

import * as SC from './styled'

interface SearchProps extends OutlinedInputProps {}

export const Search = React.forwardRef<HTMLInputElement, SearchProps>(
  ({ ...props }, ref) => {
    const { placeholder = '검색어를 입력해주세요.' } = props

    return (
      <SC.Search>
        <SC.SearchInput
          inputRef={ref}
          placeholder={placeholder}
          endAdornment={
            <InputAdornment position="end">
              <IconButton type="submit">
                <SearchIcon />
              </IconButton>
            </InputAdornment>
          }
          {...props}
          sx={{ border: 'none', '& fieldset': { border: 'none' } }}
        />
      </SC.Search>
    )
  },
)
