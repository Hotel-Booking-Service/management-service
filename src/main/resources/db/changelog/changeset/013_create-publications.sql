DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_publication WHERE pubname = 'hotel_pub') THEN
    CREATE PUBLICATION hotel_pub FOR TABLE hotel, hotel_room, room_type, amenity, hotel_amenities, hotel_photo, location;
  END IF;
END $$;