package visagebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class MyController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String hello(){
        return "hello out there";
    }

    @GetMapping("/posts")
    public Iterable<Post> getPosts(){
        return postRepository.findAll();
    }

    @GetMapping("/posts/{id}")
    public Post showPost(@PathVariable Long id) throws Exception{
        Optional<Post> shownPost = postRepository.findById(id);
        if(shownPost.isPresent()){
            return shownPost.get();
        }else{
            throw new Exception("no post by that id");
        }
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post, HttpSession session) throws Exception{
        User author = userRepository.findByUsername(session.getAttribute("username").toString());
        if(author == null){
            throw new Exception("you gotta log in to make posts");
        }
        post.setUser(author);
        Post createdPost = postRepository.save(post);
        return createdPost;
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@RequestBody Post post, @PathVariable Long id) throws Exception{
        Optional<Post> editedPost = postRepository.findById(id);
        if(editedPost.isPresent()){
            Post postToEdit = editedPost.get();
            postToEdit.setText(post.getText());
            return postRepository.save(postToEdit);
        }else{
            throw new Exception("could not find that post");
        }
    }

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id){
        postRepository.deleteById(id);
        return "Successful delete";
    }

    @GetMapping("/users/{id}/post")
    public Iterable<Post> findPostsByUser(@PathVariable Long id){
        return postRepository.findByUserId(id);
    }

}
