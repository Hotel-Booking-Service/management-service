package com.hbs.managamentservice.repository;

import com.hbs.managamentservice.model.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {
}