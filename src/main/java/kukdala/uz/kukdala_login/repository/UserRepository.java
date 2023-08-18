package kukdala.uz.kukdala_login.repository;

import kukdala.uz.kukdala_login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long id);
}
