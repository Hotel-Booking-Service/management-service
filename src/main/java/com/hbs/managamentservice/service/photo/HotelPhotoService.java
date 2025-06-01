package com.hbs.managamentservice.service.photo;

import java.net.URI;

public interface HotelPhotoService {
    URI generatePresignedURIForPhoto(Long photoId);
}
