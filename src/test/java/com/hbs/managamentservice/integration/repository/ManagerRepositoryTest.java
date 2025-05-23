package com.hbs.managamentservice.integration.repository;

import com.hbs.managamentservice.model.Manager;
import com.hbs.managamentservice.model.Role;
import com.hbs.managamentservice.repository.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class ManagerRepositoryTest {

    @Autowired
    private ManagerRepository managerRepository;

    @Test
    void whenSaveManager_thenPersisted() {
        Manager manager = createManager("john.doe@example.com", "John");
        Manager saved = managerRepository.save(manager);

        assertNotNull(saved.getId());
        assertEquals("John", saved.getFirstName());
        assertNotNull(saved.getCreatedAt());
    }

    @Test
    void whenFindById_thenReturnManager() {
        Manager manager = managerRepository.save(createManager("anna.smith@example.com", "Anna"));
        Optional<Manager> found = managerRepository.findById(manager.getId());

        assertTrue(found.isPresent());
        assertEquals("Anna", found.get().getFirstName());
    }


    @Test
    void whenUpdateManager_thenChangesPersisted() {
        Manager manager = managerRepository.save(createManager("update.me@example.com", "OldName"));
        manager.setFirstName("UpdatedName");
        manager.setActive(true);
        Manager updated = managerRepository.save(manager);

        assertEquals("UpdatedName", updated.getFirstName());
        assertTrue(updated.isActive());
    }

    @Test
    void whenDeleteManager_thenNotFound() {
        Manager manager = managerRepository.save(createManager("delete.me@example.com", "ToDelete"));
        managerRepository.delete(manager);

        Optional<Manager> found = managerRepository.findById(manager.getId());
        assertFalse(found.isPresent());
    }

    private Manager createManager(String email, String firstName) {
        Manager manager = new Manager();
        manager.setFirstName(firstName);
        manager.setLastName("Smith");
        manager.setEmail(email);
        manager.setPasswordHash("securehash");
        manager.setPhone("+1234567890");
        manager.setRole(Role.ADMIN);
        manager.setHireDate(LocalDateTime.of(2024, 1, 1, 9, 0));
        manager.setActive(false);
        return manager;
    }
}
