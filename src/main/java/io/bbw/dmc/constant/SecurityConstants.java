package io.bbw.dmc.constant;

public final class SecurityConstants {

    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String BEARER = "Bearer ";
    public static final long EXPIRES_IN = 2 * 60 * 60 * 1000;

    public static final String GET = "GET";

    public static final String[] ALLOWED_METHODS = { GET, "POST", "PUT", "PATCH", "DELETE" };
    public static final String[] ALLOWED_HEADERS = { AUTHORIZATION, CONTENT_TYPE };
    public static final String[] EXPOSED_HEADERS = { AUTHORIZATION, CONTENT_TYPE };

    public static final String REGISTRATION_ROUTE = "/api/v1/register";
    public static final String AUTHENTICATION_ROUTE = "/api/v1/authenticate";

    public static final String JSON_CONTENT = "application/json";

}
