import React from 'react';
import Router from "./Router";
import './import'
import {createTheme, MuiThemeProvider} from "@material-ui/core";
import CssBaseline from "@material-ui/core/CssBaseline";

const theme = createTheme({
  typography: {
    fontFamily: 'ARIAL'
  }
});

function App() {
  return (
    <>
      <MuiThemeProvider theme={theme}>
        <CssBaseline/>
        <Router/>
      </MuiThemeProvider>
    </>
  );
}

export default App;
