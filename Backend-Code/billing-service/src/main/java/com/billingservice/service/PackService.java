package com.billingservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billingservice.entity.Pack;
import com.billingservice.exception.PackNotFoundException;
import com.billingservice.repository.PackRepository;

@Service
public class PackService {
	
    @Autowired
    private PackRepository packRepository;

    public List<Pack> getAllPacks() {
        return packRepository.findAll();
    }

    public Pack getPackById(Long packId) {
        return packRepository.findById(packId).orElseThrow(() -> new PackNotFoundException("Pack with ID " + packId + " not found"));
    }

    public Pack createPack(Pack pack) {
        return packRepository.save(pack);
    }

    public Pack updatePack(Long packId, Pack updatedPack) {
        // Check if the pack with the given ID exists
        Pack existingPack = packRepository.findById(packId).orElseThrow(() -> new PackNotFoundException("Pack with ID " + packId + " not found"));

        // Update the existing pack's properties
        existingPack.setProviderName(updatedPack.getProviderName());
        existingPack.setPrice(updatedPack.getPrice());
        existingPack.setDescription(updatedPack.getDescription());

        return packRepository.save(existingPack);
    }

    public void deletePack(Long packId) {
        packRepository.deleteById(packId);
    }
}

