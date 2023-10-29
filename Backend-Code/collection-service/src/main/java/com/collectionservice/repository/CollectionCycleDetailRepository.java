package com.collectionservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.collectionservice.entity.CollectionCycleDetail;

public interface CollectionCycleDetailRepository extends JpaRepository<CollectionCycleDetail, Long> {
	
	Optional<CollectionCycleDetail> findTopByOrderByStartDateDesc();
}
