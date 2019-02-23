import React from 'react';

const PostList = (props) => {
    const posts = props.posts.map((post)=>{
        return <p key={post.id}>{post.user.username} says {post.text}</p>
    })
    return(
        <div>
            {posts}
        </div>
    )
}

export default PostList;