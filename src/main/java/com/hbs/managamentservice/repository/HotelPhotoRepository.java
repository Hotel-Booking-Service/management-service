package com.hbs.managamentservice.repository;

import com.hbs.managamentservice.model.HotelPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelPhotoRepository extends JpaRepository<HotelPhoto, Long> {
}