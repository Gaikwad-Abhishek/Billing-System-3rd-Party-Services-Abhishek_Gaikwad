package com.usermanagement.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackDTO {

	private Long id;
    private String providerName;
    private BigDecimal price;
    private String description;

}

