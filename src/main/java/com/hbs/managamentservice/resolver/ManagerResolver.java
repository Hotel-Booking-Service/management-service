package com.hbs.managamentservice.resolver;

import com.hbs.managamentservice.exception.domain.hotel.ManagerNotFoundException;
import com.hbs.managamentservice.model.Manager;
import com.hbs.managamentservice.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ManagerResolver implements EntityResolver<Manager> {

    private final ManagerRepository managerRepository;

    @Override
    @Transactional(readOnly = true)
    public Manager resolveById(Long id) {
        return managerRepository.findById(id)
                .orElseThrow(ManagerNotFoundException::new);
    }
}
