-- Update movie and showtime dates to current dates (starting June 29, 2025)
-- Run this in XAMPP phpMyAdmin to update existing data

USE cinemate_db;

-- Update movie dates to start from today onwards
UPDATE movies SET 
    start_date = '2025-06-29', 
    end_date = '2025-07-15' 
WHERE id = 1; -- How To Train Your Dragon

UPDATE movies SET 
    start_date = '2025-06-30', 
    end_date = '2025-07-20' 
WHERE id IN (2, 3, 4, 5, 6, 7, 8, 9, 10); -- All other movies

-- Update The Ritual price to correct value
UPDATE movies SET price = 200.00 WHERE id = 10 AND title = 'The Ritual';

-- Clear old showtimes and add new ones with current dates
DELETE FROM seats WHERE showtime_id IN (SELECT id FROM showtimes);
DELETE FROM showtimes;

-- Insert new showtimes starting from today
INSERT INTO showtimes (movie_id, show_date, show_time, available_seats) VALUES
-- Movie 1: How To Train Your Dragon
(1, '2025-06-29', '10:00:00', 48), (1, '2025-06-29', '14:00:00', 48), (1, '2025-06-29', '18:00:00', 48),
(1, '2025-06-30', '10:00:00', 48), (1, '2025-06-30', '14:00:00', 48), (1, '2025-06-30', '18:00:00', 48),
(1, '2025-07-01', '10:00:00', 48), (1, '2025-07-01', '14:00:00', 48), (1, '2025-07-01', '18:00:00', 48),

-- Movie 2: Megan 2.0
(2, '2025-06-30', '10:30:00', 48), (2, '2025-06-30', '14:30:00', 48), (2, '2025-06-30', '18:30:00', 48),
(2, '2025-07-01', '10:30:00', 48), (2, '2025-07-01', '14:30:00', 48), (2, '2025-07-01', '18:30:00', 48),
(2, '2025-07-02', '10:30:00', 48), (2, '2025-07-02', '14:30:00', 48), (2, '2025-07-02', '18:30:00', 48),

-- Movie 3: Lilo & Stitch
(3, '2025-06-30', '11:00:00', 48), (3, '2025-06-30', '15:00:00', 48), (3, '2025-06-30', '19:00:00', 48),
(3, '2025-07-01', '11:00:00', 48), (3, '2025-07-01', '15:00:00', 48), (3, '2025-07-01', '19:00:00', 48),
(3, '2025-07-02', '11:00:00', 48), (3, '2025-07-02', '15:00:00', 48), (3, '2025-07-02', '19:00:00', 48),

-- Movie 4: Ballerina
(4, '2025-06-30', '09:30:00', 48), (4, '2025-06-30', '13:30:00', 48), (4, '2025-06-30', '17:30:00', 48),
(4, '2025-07-01', '09:30:00', 48), (4, '2025-07-01', '13:30:00', 48), (4, '2025-07-01', '17:30:00', 48),
(4, '2025-07-02', '09:30:00', 48), (4, '2025-07-02', '13:30:00', 48), (4, '2025-07-02', '17:30:00', 48),

-- Movie 5: Elio
(5, '2025-06-30', '12:00:00', 48), (5, '2025-06-30', '16:00:00', 48), (5, '2025-06-30', '20:00:00', 48),
(5, '2025-07-01', '12:00:00', 48), (5, '2025-07-01', '16:00:00', 48), (5, '2025-07-01', '20:00:00', 48),
(5, '2025-07-02', '12:00:00', 48), (5, '2025-07-02', '16:00:00', 48), (5, '2025-07-02', '20:00:00', 48),

-- Movie 6: Pelikulaya: Young Hearts
(6, '2025-06-30', '11:30:00', 48), (6, '2025-06-30', '15:30:00', 48), (6, '2025-06-30', '19:30:00', 48),
(6, '2025-07-01', '11:30:00', 48), (6, '2025-07-01', '15:30:00', 48), (6, '2025-07-01', '19:30:00', 48),
(6, '2025-07-02', '11:30:00', 48), (6, '2025-07-02', '15:30:00', 48), (6, '2025-07-02', '19:30:00', 48),

-- Movie 7: Only We Know
(7, '2025-06-30', '10:15:00', 48), (7, '2025-06-30', '14:15:00', 48), (7, '2025-06-30', '18:15:00', 48),
(7, '2025-07-01', '10:15:00', 48), (7, '2025-07-01', '14:15:00', 48), (7, '2025-07-01', '18:15:00', 48),
(7, '2025-07-02', '10:15:00', 48), (7, '2025-07-02', '14:15:00', 48), (7, '2025-07-02', '18:15:00', 48),

-- Movie 8: Unconditional
(8, '2025-06-30', '12:30:00', 48), (8, '2025-06-30', '16:30:00', 48), (8, '2025-06-30', '20:30:00', 48),
(8, '2025-07-01', '12:30:00', 48), (8, '2025-07-01', '16:30:00', 48), (8, '2025-07-01', '20:30:00', 48),
(8, '2025-07-02', '12:30:00', 48), (8, '2025-07-02', '16:30:00', 48), (8, '2025-07-02', '20:30:00', 48),

-- Movie 9: 28 Years Later
(9, '2025-06-30', '09:45:00', 48), (9, '2025-06-30', '13:45:00', 48), (9, '2025-06-30', '17:45:00', 48),
(9, '2025-07-01', '09:45:00', 48), (9, '2025-07-01', '13:45:00', 48), (9, '2025-07-01', '17:45:00', 48),
(9, '2025-07-02', '09:45:00', 48), (9, '2025-07-02', '13:45:00', 48), (9, '2025-07-02', '17:45:00', 48),

-- Movie 10: The Ritual (horror movie - late night showings)
(10, '2025-06-30', '21:00:00', 48), (10, '2025-06-30', '23:30:00', 48),
(10, '2025-07-01', '21:00:00', 48), (10, '2025-07-01', '23:30:00', 48),
(10, '2025-07-02', '21:00:00', 48), (10, '2025-07-02', '23:30:00', 48),
(10, '2025-07-03', '21:00:00', 48), (10, '2025-07-03', '23:30:00', 48);

-- Add seats for all showtimes (6 rows: A-F, 8 seats per row = 48 seats)
DELIMITER $$

CREATE PROCEDURE AddSeatsForAllShowtimes()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE showtime_id INT;
    DECLARE row_letter CHAR(1);
    DECLARE seat_num INT;
    
    DECLARE showtime_cursor CURSOR FOR SELECT id FROM showtimes;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    OPEN showtime_cursor;
    
    showtime_loop: LOOP
        FETCH showtime_cursor INTO showtime_id;
        IF done THEN
            LEAVE showtime_loop;
        END IF;
        
        -- Add seats for this showtime
        SET row_letter = 'A';
        WHILE row_letter <= 'F' DO
            SET seat_num = 1;
            WHILE seat_num <= 8 DO
                INSERT INTO seats (showtime_id, seat_row, seat_number, is_booked) 
                VALUES (showtime_id, row_letter, seat_num, FALSE);
                SET seat_num = seat_num + 1;
            END WHILE;
            SET row_letter = CHAR(ASCII(row_letter) + 1);
        END WHILE;
        
    END LOOP;
    
    CLOSE showtime_cursor;
END$$

DELIMITER ;

-- Execute the procedure
CALL AddSeatsForAllShowtimes();

-- Drop the procedure
DROP PROCEDURE AddSeatsForAllShowtimes;

-- Update existing bookings to current dates
UPDATE bookings SET booking_date = '2025-06-29' WHERE id = 1;
UPDATE bookings SET booking_date = '2025-06-30' WHERE id = 2;
UPDATE bookings SET booking_date = '2025-06-30' WHERE id = 3;
UPDATE bookings SET booking_date = '2025-07-01' WHERE id = 4;

-- Verify the updates
SELECT 'Updated Movies' as Status, COUNT(*) as Count FROM movies WHERE start_date >= '2025-06-29';
SELECT 'Updated Showtimes' as Status, COUNT(*) as Count FROM showtimes WHERE show_date >= '2025-06-29';
SELECT 'Updated Seats' as Status, COUNT(*) as Count FROM seats;

-- Show The Ritual showtimes specifically
SELECT 'The Ritual Showtimes' as Movie, show_date, show_time 
FROM showtimes s 
JOIN movies m ON s.movie_id = m.id 
WHERE m.title = 'The Ritual' 
ORDER BY show_date, show_time; 