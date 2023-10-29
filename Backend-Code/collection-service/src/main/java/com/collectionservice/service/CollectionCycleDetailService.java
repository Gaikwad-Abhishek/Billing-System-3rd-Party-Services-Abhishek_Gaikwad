package com.collectionservice.service;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collectionservice.entity.CollectionCycleDetail;
import com.collectionservice.repository.CollectionCycleDetailRepository;


@Service
public class CollectionCycleDetailService {

    @Autowired
	private CollectionCycleDetailRepository collectionCycleDetailRepository;


    public CollectionCycleDetail createCollectionCycleDetail(LocalDate startDate, LocalDate suspensionDate, LocalDate terminationDate) {
        CollectionCycleDetail collectionCycleDetail = new CollectionCycleDetail();
        collectionCycleDetail.setStartDate(startDate);
        collectionCycleDetail.setSuspensionDate(suspensionDate);
        collectionCycleDetail.setTerminationDate(terminationDate);
        return collectionCycleDetailRepository.save(collectionCycleDetail);
    }
}