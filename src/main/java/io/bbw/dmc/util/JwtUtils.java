package io.bbw.dmc.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import io.bbw.dmc.constant.SecurityConstants;
import io.bbw.dmc.pojo.User;

public class JwtUtils {

    private static String JWT_SECRET = System.getenv("JWT_SECRET");

    public static String generateToken(User user) {
        return JWT.create().withSubject(user.getUserId())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRES_IN))
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    public static String verifyToken(String token) {
        return JWT.require(Algorithm.HMAC512(JWT_SECRET)).build().verify(token)
                .getSubject();
    }

    public static void setSecret(String secret) {
        JWT_SECRET = secret;
    }
}
