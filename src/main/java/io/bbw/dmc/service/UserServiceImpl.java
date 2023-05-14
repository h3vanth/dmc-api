package io.bbw.dmc.service;

import java.util.Optional;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.bbw.dmc.exception.EntityNotFoundException;
import io.bbw.dmc.exception.UserAlreadyExistsException;
import io.bbw.dmc.pojo.User;
import io.bbw.dmc.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(email, User.class));
    }

    @Override
    public String[] getCategories(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getCategories();
        }
        throw new EntityNotFoundException(userId, User.class);
    }

    @Override
    public void updateCategories(String[] categories, String userId) {
        userRepository.findById(userId).ifPresentOrElse(user -> {
            user.setCategories(categories);
            userRepository.save(user);
            simpMessagingTemplate.convertAndSend(new StringBuilder().append("/topic/").append(userId).append("/categories").toString(), getCategories(userId));
        }, () -> {
            throw new EntityNotFoundException(userId, User.class);
        });
    }
}
