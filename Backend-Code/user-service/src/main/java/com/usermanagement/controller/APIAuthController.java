package com.usermanagement.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.dto.LoginBody;
import com.usermanagement.dto.TokenDTO;
import com.usermanagement.dto.UserRegistrationDTO;
import com.usermanagement.entity.User;
import com.usermanagement.service.UserService;

@RestController
@RequestMapping("/api/auth")

public class APIAuthController {

	@Autowired
	private JwtEncoder jwtEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	
	@PostMapping("/token")
	public ResponseEntity<TokenDTO> token(@RequestBody LoginBody loginBody) {

		Instant now = Instant.now();

		long expiry = 3600L;

		var username = loginBody.getUsername();
		var password = loginBody.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		User user = userService.findByUsername(authentication.getName());
		String userId = Long.toString(user.getUserId());

		JwtClaimsSet claims = JwtClaimsSet.builder()

				.issuer("self")

				.issuedAt(now)

				.expiresAt(now.plusSeconds(expiry))

				.subject(userId)

				.build();

		TokenDTO token = new TokenDTO();
		token.setToken(this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());

		return ResponseEntity.ok(token);
	}
	
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        try {
            userService.registerUser(registrationDTO);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User registration failed: " + e.getMessage());
        }
    }


}