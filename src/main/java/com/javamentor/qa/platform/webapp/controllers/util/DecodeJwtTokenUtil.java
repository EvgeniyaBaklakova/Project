package com.javamentor.qa.platform.webapp.controllers.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DecodeJwtTokenUtil {
    public static String decodeJwtToken (String token){
        String jwt = token.substring(7);
        DecodedJWT decodedJWT = JWT.decode(jwt);
        return decodedJWT.getClaim("sub").asString();
    }
}
