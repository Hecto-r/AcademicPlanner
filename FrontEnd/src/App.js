import React, { Component } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import "./App.css";
import { ThemeProvider as MuiThemeProvider } from "@material-ui/core/styles";
import createMuiTheme from "@material-ui/core/styles/createMuiTheme";
import themeFile from "./util/theme";

// Redux
import { Provider } from 'react-redux';
import store from './redux/store';
//components
import Navbar from "./components/Navbar";
import Login from "./pages/login.component";
import Register from "./components/register.component";
import Advising from "./pages/advising";

//pages
import home from "./pages/home";
import profile from "./pages/profile";

const theme = createMuiTheme(themeFile);

class App extends Component {
  render() {
    return (
      <MuiThemeProvider theme={theme}>
        <Provider store={store}>
            <Router>
              <Navbar />
              <div className="container">
                <Switch>
                  <Route exact path="/" component={Login} />
                  <Route
                    exact
                    path="/profile"
                    component={profile}
                  />
                  <Route exact path="/home" component={home} />
                  <Route exact path="/register" component={Register} />
                  <Route exact path="/login" component={Login} />
                  <Route exact path="/advising" component={Advising} />
                </Switch>
              </div>
            </Router>
        </Provider>
      </MuiThemeProvider>
    );
  }
}

export default App;
