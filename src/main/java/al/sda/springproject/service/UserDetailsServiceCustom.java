package al.sda.springproject.service;

import al.sda.springproject.dto.ServiceResponse;
import al.sda.springproject.entity.User;
import al.sda.springproject.entity.UserDetailsAdapter;
import al.sda.springproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
//@Primary
@AllArgsConstructor
public class UserDetailsServiceCustom implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(UserDetailsAdapter::new)
                .orElseThrow(() ->new UsernameNotFoundException("Couldn't find user with name: " + username));
    }

    public ServiceResponse<User> findUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .map(ServiceResponse::of)
                .orElseGet(() -> ServiceResponse.ofError("Couldn't find user with username " + username));
    }

    public User saveNewUser(User user) {
        // Check if user exists with that username, return error
        Optional<User> optionalUser = this.userRepository.findByUsername(user.getUsername());

        if (optionalUser.isPresent()) {
            throw new RuntimeException("User already exists for that username");
        }

        return this.userRepository.save(user);
    }

    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

}
