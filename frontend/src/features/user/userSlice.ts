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
    setToken: (state, action: { payload: string }) => {
      state.token = action.payload;
    },
    clearToken: state => {
      state.token = '';
    },
  },
});

export const { setToken, clearToken } = userSlice.actions;
export const selectToken = (state: RootState) => state.user.token;
export const selectIsAuthenticated = (state: RootState) => !!state.user.token;
export const selectUsername = (state: RootState) => state.user.username;
export default userSlice.reducer;
