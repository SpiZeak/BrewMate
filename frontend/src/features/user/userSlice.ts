import { createSlice } from '@reduxjs/toolkit';
import type { RootState } from '../../app/store';

interface UserState {
  token: string;
  username?: string;
}

const initialState: UserState = {
  token: '',
  username: '',
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
export const selectUsername = (state: RootState) => state.user.username;
export default userSlice.reducer;
export type { UserState };
