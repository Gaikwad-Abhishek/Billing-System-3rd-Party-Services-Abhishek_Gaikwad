package com.usermanagement.dto;

import com.usermanagement.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String contactNo;

}