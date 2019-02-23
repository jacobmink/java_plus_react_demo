import React, {Component} from 'react';
import PostList from './PostList/PostList';
import NewPost from  './NewPost/NewPost';

export default class PostsContainer extends Component{
    constructor(){
        super()
        this.state = {
            posts: []
        }
    }
    getPosts  = async () => {
        const postResponse = await fetch('http://localhost:8080/posts');
        const posts = await postResponse.json();
        return posts;
    }
    componentDidMount(){
        this.getPosts().then((posts)=>{
            this.setState({
                posts
            })
        })
    }
    createPost = async (post) => {
        const newPost = await fetch('http://localhost:8080/posts', {
            method: "POST",
            body: JSON.stringify(post),
            credentials: 'include',
            headers: {
                "Content-Type": "application/json"
            }
        })
        const response = await newPost.json();
        this.setState({
            posts: [...this.state.posts, response]
        })
    }
    render(){
        return(
            <div>
                <h1>HERES THE POSTS</h1>
                <NewPost createPost={this.createPost} />
                <PostList posts={this.state.posts}/>
            </div>
        )
    }
}