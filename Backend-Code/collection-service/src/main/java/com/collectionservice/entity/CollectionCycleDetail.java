package com.collectionservice.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CollectionCycleDetail {
	
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate startDate;
    private LocalDate suspensionDate;
    private LocalDate terminationDate;

    // Constructors, getters, setters, and other methods as needed
}
