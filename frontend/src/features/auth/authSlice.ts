import { createSlice, PayloadAction } from '@reduxjs/toolkit'

import type { MemberResponse } from '../../ts/dto.ts'

export interface AuthState {
  accessToken: string | null
  member: MemberResponse | null
}

const initialState: AuthState = {
  accessToken: null,
  member: null,
}

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    setAccessToken: (
      state,
      action: PayloadAction<AuthState['accessToken']>,
    ) => {
      state.accessToken = action.payload
    },
    setMember: (state, action: PayloadAction<AuthState['member']>) => {
      state.member = action.payload
    },
  },
})

export const { setAccessToken, setMember } = authSlice.actions

export default authSlice.reducer
