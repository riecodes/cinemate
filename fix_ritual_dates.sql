-- Fix The Ritual movie to have only 3 dates instead of 4
-- This removes the July 3rd showtimes for movie ID 10 (The Ritual)

USE cinemate_db;

-- Remove showtimes for July 3rd for The Ritual (movie_id = 10)
DELETE FROM showtimes 
WHERE movie_id = 10 
AND show_date = '2025-07-03';

-- Verify the fix - should show only 3 dates for The Ritual
SELECT movie_id, show_date, COUNT(*) as showtime_count 
FROM showtimes 
WHERE movie_id = 10 
GROUP BY movie_id, show_date 
ORDER BY show_date;

-- Show total showtimes per movie to verify consistency
SELECT movie_id, COUNT(DISTINCT show_date) as unique_dates, COUNT(*) as total_showtimes
FROM showtimes 
GROUP BY movie_id 
ORDER BY movie_id; 