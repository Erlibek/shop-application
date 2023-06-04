package erli.shop.service;

import erli.shop.entity.User;
import erli.shop.entity.enumeration.UserRole;
import erli.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public void createUser(String name, String lastName,String login,String password) {
        User user = new User();
        user.setRole(UserRole.USER);
        user.setDate(LocalDate.now());
        user.setName(name);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
    public User getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return userRepository.findByLogin(authentication.getName()).orElse(null);
    }
}
