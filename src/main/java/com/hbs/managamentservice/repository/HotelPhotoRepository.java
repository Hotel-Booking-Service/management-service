package com.hbs.managamentservice.repository;

import com.hbs.managamentservice.model.HotelPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelPhotoRepository extends JpaRepository<HotelPhoto, Long> {

    List<HotelPhoto> findByHotelId(Long hotelId);
}