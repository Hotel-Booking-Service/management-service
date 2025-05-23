package com.hbs.managamentservice.repository;

import com.hbs.managamentservice.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}