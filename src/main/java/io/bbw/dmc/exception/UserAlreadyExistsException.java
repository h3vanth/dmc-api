package io.bbw.dmc.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super(new StringBuilder().append("User with email ").append(email)
                .append(" already exist in our database")
                .toString());
    }
}
