package com.usermanagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionDetailsDTO {

	private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private List<SubscriptionDTO> subscriptions;

}

