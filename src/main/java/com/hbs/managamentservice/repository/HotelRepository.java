package com.hbs.managamentservice.repository;

import com.hbs.managamentservice.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}