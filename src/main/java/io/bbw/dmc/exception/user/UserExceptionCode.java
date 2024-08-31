package io.bbw.dmc.exception.user;

import io.formulate.web.common.exception.ExceptionCode;
import io.formulate.web.common.exception.ExceptionContextKey;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static io.bbw.dmc.exception.user.UserExceptionContextKey.EMAIL;
import static io.bbw.dmc.exception.user.UserExceptionContextKey.VALUE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public enum UserExceptionCode implements ExceptionCode {
    USER_ALREADY_EXISTS("User with email ${EMAIL} already exist in our database.", BAD_REQUEST, EMAIL),
    USER_NOT_FOUND("User with ${VALUE} doesn't exist in our database.", BAD_REQUEST, VALUE);

    private final String template;
    private final HttpStatus httpStatus;
    private final Map<String, ExceptionContextKey> context;

    UserExceptionCode(String template, HttpStatus httpStatus, UserExceptionContextKey... contextKeys) {
        this.template = template;
        this.httpStatus = httpStatus;
        this.context = createContext(contextKeys);
    }

    public String getTemplate() {
        return template;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, ExceptionContextKey> getContext() {
        return context;
    }
}
