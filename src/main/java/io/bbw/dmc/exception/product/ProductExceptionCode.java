package io.bbw.dmc.exception.product;

import io.formulate.web.common.exception.ExceptionCode;
import io.formulate.web.common.exception.ExceptionContextKey;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static io.bbw.dmc.exception.product.ProductExceptionContextKey.ID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public enum ProductExceptionCode implements ExceptionCode {
    PRODUCT_NOT_FOUND("Product with ${ID} doesn't exist.", BAD_REQUEST, ID);

    private final String template;
    private final HttpStatus httpStatus;
    private final Map<String, ExceptionContextKey> context;

    ProductExceptionCode(String template, HttpStatus httpStatus, ExceptionContextKey... contextKeys) {
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
