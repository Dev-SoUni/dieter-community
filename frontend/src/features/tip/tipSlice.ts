import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'

import type { Page, TipResponseDTO } from '../../ts/dto.ts'

export interface TipState {
  page: number
  search: {
    title: string
  }
  pageableData: Page<TipResponseDTO> | null
}

const initialState: TipState = {
  page: 0,
  search: {
    title: '',
  },
  pageableData: null,
}

export const tipSlice = createSlice({
  name: 'tip',
  initialState,
  reducers: {
    setPage: (state, action: PayloadAction<number>) => {
      state.page = action.payload
    },
    setSearch: (state, action: PayloadAction<TipState['search']>) => {
      state.search = action.payload
    },
    setPageableData: (
      state,
      action: PayloadAction<Page<TipResponseDTO> | null>,
    ) => {
      state.pageableData = action.payload
    },
  },
})

// Action creators are generated for each case reducer function
export const { setPage, setSearch, setPageableData } = tipSlice.actions

export default tipSlice.reducer
