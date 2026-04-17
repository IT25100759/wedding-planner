package com.weddingplanner.repository;

import com.weddingplanner.entity.Wedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeddingRepository extends JpaRepository<Wedding, Long> {
}
