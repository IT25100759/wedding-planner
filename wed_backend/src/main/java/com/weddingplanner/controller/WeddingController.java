package com.weddingplanner.controller;

import com.weddingplanner.entity.Wedding;
import com.weddingplanner.repository.WeddingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weddings")
@CrossOrigin(origins = "http://localhost:3000")
public class WeddingController {
    @Autowired
    private WeddingRepository weddingRepository;

    @PostMapping
    public Wedding createWedding(@RequestBody Wedding wedding) {
        return weddingRepository.save(wedding);
    }

    @GetMapping
    public List<Wedding> getAllWeddings() {
        return weddingRepository.findAll();
    }
}