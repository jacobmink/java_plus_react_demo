import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import PostsContainer from './PostsContainer/PostsContainer';
import Login from './Login/Login';

class App extends Component {
  constructor(){
    super();
    this.state = {
      loggedIn: false
    }
  }
  attemptLogin = async (loginData) => {
    const thisUser = await fetch("http://localhost:8080/login", {
      method: "POST",
      body: JSON.stringify(loginData),
      credentials: 'include',
      headers: {
        "Content-Type": "application/json"
      }
    })
    const response = await thisUser.json()
    if(response.status !== 500){
      console.log("YOURE IN");
      this.setState({
        loggedIn: true
      })
    }else{
      console.log("WOMP WOMP")
    }
  }
  render() {
    return (
      <div className="App">
        {  this.state.loggedIn ? 
          <PostsContainer /> :
          <Login attemptLogin={this.attemptLogin} />
        }
      </div>
    );
  }
}

export default App;
