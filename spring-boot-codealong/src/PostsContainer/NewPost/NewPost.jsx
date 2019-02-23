import React, {Component} from 'react';

export default class NewPost extends Component{
    constructor(){
        super()
        this.state = {
            text: ''
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
                this.props.createPost(this.state);
            }}>
                <input type="text" name="text" onChange={this.handleChange} />
                <input type="submit" value="submit" onChange={this.handleChange} />
            </form>
        )
    }
}