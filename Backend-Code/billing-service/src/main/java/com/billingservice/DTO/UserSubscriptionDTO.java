package com.billingservice.DTO;

import com.billingservice.entity.Pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionDTO {
	
    private Long userId;
    private Pack pack;

}
