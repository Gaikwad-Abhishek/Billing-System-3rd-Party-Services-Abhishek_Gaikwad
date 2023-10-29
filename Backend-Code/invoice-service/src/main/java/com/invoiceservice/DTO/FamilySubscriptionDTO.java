package com.invoiceservice.DTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilySubscriptionDTO {
	
    private Long familyAccountId;
    private List<UserSubscriptionDTO> userSubscriptions;
    private BigDecimal totalBillAmount;

}
