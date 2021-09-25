import {createSlice, PayloadAction} from "@reduxjs/toolkit";

export interface IUserLoginInfoReducer {
  email: string
  name: string
}

const userLoginInfoSlice = createSlice({
  name: "userLoginInfo",
  initialState: {
    email: '',
    name: ''
  } as IUserLoginInfoReducer,
  reducers: {
    setUserLoginInfo: (state, action: PayloadAction<IUserLoginInfoReducer>) => {
      state.email = action.payload.email;
      state.name = action.payload.name;
    },
  }
});

export const {setUserLoginInfo} = userLoginInfoSlice.actions;
export const userLoginInfoReducer = userLoginInfoSlice.reducer;
