package com.hbs.managamentservice.repository;

import com.hbs.managamentservice.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    boolean existsByName(String name);
}