package com.hbs.managamentservice.unit.resolver;

import com.hbs.managamentservice.exception.domain.hotel.ManagerNotFoundException;
import com.hbs.managamentservice.model.Manager;
import com.hbs.managamentservice.repository.ManagerRepository;
import com.hbs.managamentservice.resolver.ManagerResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManagerResolverTest {

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private ManagerResolver managerResolver;

    @Test
    void resolveById_shouldReturnManager() {
        Long id = 1L;
        Manager manager = new Manager();
        manager.setId(id);
        when(managerRepository.findById(id)).thenReturn(Optional.of(manager));

        Manager result = managerResolver.resolveById(id);

        assertEquals(manager, result);
    }

    @Test
    void resolveById_shouldThrowNotFound() {
        Long id = 1L;
        when(managerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ManagerNotFoundException.class, () -> managerResolver.resolveById(id));
    }
}
