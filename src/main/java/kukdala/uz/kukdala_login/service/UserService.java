package kukdala.uz.kukdala_login.service;

import kukdala.uz.kukdala_login.entity.User;
import kukdala.uz.kukdala_login.repository.UserRepository;
import org.jvnet.hk2.annotations.Service;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(@Lazy UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public Boolean isUserRegister(Long id) {
        return userRepository.existsById(id);
    }
}
