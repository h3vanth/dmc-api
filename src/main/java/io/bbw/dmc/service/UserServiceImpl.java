package io.bbw.dmc.service;

import io.bbw.dmc.event.handler.EventHandler;
import io.bbw.dmc.event.producer.UserEventProducer;
import io.bbw.dmc.exception.EntityNotFoundException;
import io.bbw.dmc.exception.UserAlreadyExistsException;
import io.bbw.dmc.model.User;
import io.bbw.dmc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventHandler eventHandler;

    @Override
    public void createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        eventHandler.emitEvent(UserEventProducer.produceUserCreatedEvent(userRepository.save(user)));
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
            eventHandler.emitEvent(UserEventProducer.produceProductCategoryCreatedEvent(user));
        }, () -> {
            throw new EntityNotFoundException(userId, User.class);
        });
    }
}
