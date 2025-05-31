package com.hbs.managamentservice.unit.dto.response;

import com.hbs.managamentservice.dto.response.PagedResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PagedResponseTest {

    @Test
    void fromPage_shouldMapAllFieldsCorrectly() {
        List<String> data = List.of("A", "B");
        Page<String> page = new PageImpl<>(data, PageRequest.of(1, 2), 4);

        PagedResponse<String> response = PagedResponse.fromPage(page);

        assertEquals(data, response.content());
        assertEquals(1, response.pageNumber());
        assertEquals(2, response.pageSize());
        assertEquals(4, response.totalElements());
        assertEquals(2, response.totalPages());
        assertTrue(response.last());
    }
}
