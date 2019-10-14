package in.gskitchen.indusnet.repository;

import in.gskitchen.indusnet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String emailId);
}
