import React, {Component} from 'react';

export default class Login extends Component{
    constructor(){
        super();
        this.state ={
            username: "",
            password: ""
        }
    }
    handleChange = (e) => {
        this.setState({
            [e.currentTarget.name] : e.currentTarget.value
        })
    }
    render(){
        return(
            <form onSubmit={(e)=>{
                e.preventDefault();
                this.props.attemptLogin(this.state);
            }}>
                <input type="text" name="username" onChange={this.handleChange} />
                <input type="password" name="password" onChange={this.handleChange} />
                <input type="submit" value="submit" />
            </form>
        )
    }
}