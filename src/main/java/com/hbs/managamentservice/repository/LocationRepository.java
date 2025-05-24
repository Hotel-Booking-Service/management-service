package com.hbs.managamentservice.repository;

import com.hbs.managamentservice.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}