package com.hbs.managamentservice.mapper;

import com.hbs.managamentservice.dto.response.LocationResponse;
import com.hbs.managamentservice.model.Location;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface LocationMapper {

    LocationResponse toLocationResponse(Location location);
}
