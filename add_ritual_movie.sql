-- Add The Ritual movie to cinemate_db
USE cinemate_db;

INSERT INTO movies (title, description, start_date, end_date, price, poster_path, is_active) VALUES 
('The Ritual', 'A psychological horror film that follows a group of friends who become lost in a Scandinavian forest, where they encounter ancient and terrifying forces. As paranoia sets in and the group begins to fracture, they must face both supernatural threats and their own darkest fears.', '2025-06-03', '2025-06-27', 200.00, '/assets/the ritual poster.png', true);

-- Verify the movie was added
SELECT id, title, price, start_date, end_date FROM movies WHERE title = 'The Ritual'; 