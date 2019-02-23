package visagebook;

// This will be AUTO IMPLEMENTED by Spring into a Bean called postRepository
// CRUD refers Create, Read, Update, Delete

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
}