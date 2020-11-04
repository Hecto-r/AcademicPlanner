import React, { Component } from "react";
import { Redirect } from 'react-router-dom';
import { connect } from "react-redux";
import { logout } from "../redux/actions/auth";
import { clearMessage } from "../redux/actions/message";

import { history } from '../redux/helpers/history';

class Profile extends Component {

  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      currentUser: undefined,
    };

    history.listen((location) => {
      props.dispatch(clearMessage()); // clear message when changing location
    });
  }


  logOut() {
    this.props.dispatch(logout());
  }

  render() {
    const { user: currentUser } = this.props;

    if (!currentUser) {
      return <Redirect to="/login" />;
    }

    return (
      <div className="profile-img-card">
        <a href="/login" className="nav-link" onClick={this.logOut}>
            LogOut
        </a>
        <header className="App-header">
        <img className="Header-Logo" src="./csudh.png" alt="Logo" />
        <h1 style={{color: '#860038'}}>Student Information</h1>
        <p>Student Name: {currentUser.firstName} {currentUser.lastName} <br /> </p>
        <p>Student ID#: {currentUser.id} <br /></p>
        <p>Student Email: {currentUser.email} <br /></p>
        <p>Major: Computer Science <br /> </p>
       
      </header>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { user } = state.auth;
  return {
    user,
  };
}

export default connect(mapStateToProps)(Profile);
