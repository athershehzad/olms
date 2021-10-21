package com.olms.controller;

import com.olms.entity.RollsEntity;
import com.olms.service.RollsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/rolls")
@RestController
public class RollsController {

    private final RollsService rollsService;

    public RollsController(RollsService rollsService) {

        this.rollsService = rollsService;
    }

    /**
     * Get all rolls list.
     *
     * @return the list
     */
    @GetMapping
    public List<RollsEntity> getAllUsers() {

        return rollsService.getRollsList();

    }

    /**
     * Get all books list.
     *
     * @return the list
     */
    @GetMapping("/{id}")
    public RollsEntity getRollsById(@PathVariable long id) {

        return rollsService.getRollsById(id);

    }


}