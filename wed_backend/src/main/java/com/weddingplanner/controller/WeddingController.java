package com.weddingplanner.controller;

import com.weddingplanner.entity.Wedding;
import com.weddingplanner.repository.WeddingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weddings")
@CrossOrigin(origins = "*")
public class WeddingController {

    @Autowired
    private WeddingRepository weddingRepository;

    @GetMapping
    public List<Wedding> getAllWeddings() {
        return weddingRepository.findAll();
    }

    @PostMapping
    public Wedding createWedding(@RequestBody Wedding wedding) {
        wedding.setStatus("planned");
        return weddingRepository.save(wedding);
    }

    @GetMapping("/{id}")
    public Wedding getWeddingById(@PathVariable Long id) {
        return weddingRepository.findById(id).orElseThrow();
    }
}
