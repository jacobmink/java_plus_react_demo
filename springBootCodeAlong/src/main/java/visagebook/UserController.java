package visagebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/users")
    public Iterable<User> getUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        User createdUser = userService.saveUser(user);
        return createdUser;
    }

    @GetMapping("/users/{id}")
    public HashMap show(@PathVariable Long id) throws Exception{
        Optional<User> foundUser = userRepository.findById(id);
        if(foundUser.isPresent()){
            User user = foundUser.get();
            Iterable<Post> posts = postRepository.findByUserId(id);
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("user", user);
            result.put("posts", posts);
            return result;
        }else{
            throw new Exception("no user by that id");
        }
    }

    @PutMapping("/users/{id}")
    public User putReq(@RequestBody User user, @PathVariable Long id) throws Exception{
        Optional<User> foundUser = userRepository.findById(id);
        if(foundUser.isPresent()){
            User UserToEdit = foundUser.get();
            UserToEdit.setUsername(user.getUsername());
            UserToEdit.setPassword(user.getPassword());
            return userRepository.save(UserToEdit);
        }else {
            throw new Exception("could not find that user");
        }
    }

    @DeleteMapping("/users/{id}")
    public String deleteReq(@PathVariable Long id){
        userRepository.deleteById(id);
        return "user " + id + " deleted";
    }
    // as another route listed after class variables
    @PostMapping("/login")
    public User login(@RequestBody User login, HttpSession session) throws IOException {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByUsername(login.getUsername());
        if(user ==  null){
            throw new IOException("Invalid Credentials");
        }
        boolean valid = bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword());
        if(valid){
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userId", user.getId());
            return user;
        }else{
            throw new IOException("Invalid Credentials");
        }
    }

}