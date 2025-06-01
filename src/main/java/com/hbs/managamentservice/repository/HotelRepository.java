package com.hbs.managamentservice.repository;

import com.hbs.managamentservice.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @NonNull
    @Override
    @EntityGraph(attributePaths = {"location"})
    Page<Hotel> findAll(@NonNull Pageable pageable);
}