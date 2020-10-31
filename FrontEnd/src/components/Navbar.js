import React, { Component } from "react";
import {Link} from "react-router-dom";

// MUI Stuff
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Button from "@material-ui/core/Button";

export class Navbar extends Component {
  render() {
    return (
      <AppBar>
        <Toolbar className="nav-container" >
          <Button color="inherit" component={Link} to="/profile">
            Profile
          </Button>
          <Button color="inherit" component={Link} to="/home">
            Academic
          </Button>
          <Button color="inherit" component={Link} to="/advising">
            Advising
          </Button>
        </Toolbar>
      </AppBar>
    );
  }
}

export default Navbar;
