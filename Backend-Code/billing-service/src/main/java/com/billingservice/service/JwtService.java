package com.billingservice.service;

import java.security.interfaces.RSAPublicKey;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.public.key}")
	RSAPublicKey key;

	public Long getUserId(String jwtToken) {
	    // Use a regular expression to extract the JWT token
	    Pattern pattern = Pattern.compile("^Bearer\\s+(\\S+)$");
	    Matcher matcher = pattern.matcher(jwtToken);

	    if (matcher.find()) {
	        jwtToken = matcher.group(1);
	        System.out.println(jwtToken);
	        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();

	        String userId = claims.getSubject();
	        System.out.println(userId);
	        return Long.parseLong(userId);
	    } else {
	        throw new IllegalArgumentException("Invalid JWT token format");
	    }
	}
}
