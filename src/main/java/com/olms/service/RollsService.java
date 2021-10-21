package com.olms.service;

import com.olms.entity.RollsEntity;
import com.olms.repository.RollsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RollsService {
    private final RollsRepository rollsRepository;

    public RollsService(RollsRepository rollsRepository) {
        this.rollsRepository = rollsRepository;

    }

    public List<RollsEntity> getRollsList() {
        return rollsRepository.findAll();
    }

    public RollsEntity getRollsById(long id) {

        return rollsRepository.findById(id).get();
    }
}
