package com.usermanagement.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginBody {

    private String username;

    private String password;
    
    public String getUsername() {
    	return username;
    }
    
    public String getPassword() {
    	return password;
    }
}