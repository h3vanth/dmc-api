package io.bbw.dmc.service;

import io.bbw.dmc.event.producer.UserEventProducer;
import io.bbw.dmc.exception.user.UserExceptionCode;
import io.bbw.dmc.model.User;
import io.bbw.dmc.repository.UserRepository;
import io.formulate.common.event.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.bbw.dmc.exception.user.UserExceptionContextKey.EMAIL;
import static io.bbw.dmc.exception.user.UserExceptionContextKey.VALUE;
import static io.formulate.web.common.exception.ExceptionBuilder.exception;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventHandler eventHandler;

    @Override
    public void createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw exception(UserExceptionCode.USER_ALREADY_EXISTS).put(EMAIL, email).build();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        eventHandler.emitEvent(UserEventProducer.produceUserCreatedEvent(userRepository.save(user)));
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> exception(UserExceptionCode.USER_NOT_FOUND).put(VALUE, email).build());
    }

    @Override
    public String[] getCategories(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getCategories();
        }
        throw exception(UserExceptionCode.USER_NOT_FOUND).put(VALUE, userId).build();
    }

    @Override
    public void updateCategories(String[] categories, String userId) {
        userRepository.findById(userId).ifPresentOrElse(user -> {
            user.setCategories(categories);
            userRepository.save(user);
            eventHandler.emitEvent(UserEventProducer.produceProductCategoryCreatedEvent(user));
        }, () -> {
            throw exception(UserExceptionCode.USER_NOT_FOUND).put(VALUE, userId).build();
        });
    }
}
