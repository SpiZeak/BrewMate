import { createSlice } from '@reduxjs/toolkit';
import type { RootState } from '../../app/store';

interface UserState {
  token: string;
  email?: string;
}

const initialState: UserState = {
  token: '',
  email: '',
};

export const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setUser: (state, action: { payload: Partial<UserState> }) => ({
      ...state,
      ...action.payload,
    }),
    clearUser: () => initialState,
  },
});

export const { setUser, clearUser } = userSlice.actions;
export const selectToken = (state: RootState) => state.user.token;
export const selectIsAuthenticated = (state: RootState) => !!state.user.token;
export const selectEmail = (state: RootState) => state.user.email;
export default userSlice.reducer;
export type { UserState };
