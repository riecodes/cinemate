-- CineMate Database Setup Script
-- Run this in XAMPP phpMyAdmin if automatic initialization fails

-- Create database
CREATE DATABASE IF NOT EXISTS cinemate_db;
USE cinemate_db;

-- Create movies table
CREATE TABLE IF NOT EXISTS movies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    poster_path VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create bookings table
CREATE TABLE IF NOT EXISTS bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255),
    movie_id INT NOT NULL,
    booking_date DATE NOT NULL,
    seats VARCHAR(255),
    total_price DECIMAL(10, 2) NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'CANCELLED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    registration_date DATE NOT NULL,
    total_bookings INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert initial movie data with current dates (starting June 29, 2025)
INSERT INTO movies (title, description, start_date, end_date, price, poster_path, is_active) VALUES
('How To Train Your Dragon', 'A live-action adaptation of the beloved animated classic, this film follows the unlikely friendship between a young Viking named Hiccup and a dragon named Toothless. Together, they challenge long-standing myths and change their world forever.', '2025-06-29', '2025-07-15', 290.00, '/assets/how to train your dragon poster.png', true),
('Megan 2.0', 'The terrifying AI doll returns in this chilling sequel. After the events of the first film, M3GAN is reactivated—smarter, deadlier, and harder to control. With her new programming evolving beyond human understanding, no one is safe.', '2025-06-30', '2025-07-20', 310.00, '/assets/Megan2.png', true),
('Lilo & Stitch', 'This heartfelt live-action remake reintroduces the story of a lonely Hawaiian girl named Lilo who adopts a mysterious alien creature, Stitch. What begins as chaos transforms into a beautiful lesson about family, love, and ohana.', '2025-06-30', '2025-07-20', 250.00, '/assets/lilo and stitch poster.png', true),
('Ballerina', 'Set in the high-octane John Wick universe, Ballerina follows Rooney (Ana de Armas), a deadly assassin trained in ballet, as she seeks revenge for her family''s murder. Graceful yet brutal, her journey uncovers deep secrets and powerful enemies.', '2025-06-30', '2025-07-20', 360.00, '/assets/ballerina poster.png', true),
('Elio', 'An imaginative boy named Elio is accidentally beamed into space and mistakenly appointed as Earth''s ambassador to a galaxy full of aliens. A charming coming-of-age story from Pixar about identity, bravery, and finding one''s voice in a strange universe.', '2025-06-30', '2025-07-20', 230.00, '/assets/elio poster.png', true),
('Pelikulaya: Young Hearts', 'A Filipino youth-centered film showcasing diverse love stories and modern struggles, set in a vibrant campus environment. This entry in the Pelikulaya series captures the joys and heartbreaks of young adulthood with authenticity and cultural flair.', '2025-06-30', '2025-07-20', 250.00, '/assets/young hearts poster.png', true),
('Only We Know', 'A romantic mystery that follows two soulmates who keep finding each other in different lifetimes. As they uncover pieces of their pasts, they realize some love stories are destined to repeat—until they finally get it right.', '2025-06-30', '2025-07-20', 200.00, '/assets/only we know poster.png', true),
('Unconditional', 'A heart-tugging drama about the unbreakable bond between a mother and her child. As life tests their limits, they discover that true love is not based on conditions—but on sacrifice, understanding, and unwavering support.', '2025-06-30', '2025-07-20', 200.00, '/assets/uncoditional poster.png', true),
('28 Years Later', 'The long-awaited sequel to 28 Days Later and 28 Weeks Later. The rage virus resurfaces, more evolved and deadlier than ever. Survivors must navigate a ruined world as civilization teeters on the edge of total collapse.', '2025-06-30', '2025-07-20', 200.00, '/assets/28 years later poster.png', true),
('The Ritual', 'A psychological horror film that follows a group of friends who become lost in a Scandinavian forest, where they encounter ancient and terrifying forces. As paranoia sets in and the group begins to fracture, they must face both supernatural threats and their own darkest fears.', '2025-06-30', '2025-07-20', 200.00, '/assets/the ritual poster.png', true);

-- Insert sample booking data with current dates
INSERT INTO bookings (customer_name, customer_email, movie_id, booking_date, seats, total_price, status) VALUES
('Juan dela Cruz', 'juan@email.com', 1, '2025-06-29', 'A12, A13', 580.00, 'CONFIRMED'),
('Maria Santos', 'maria@email.com', 2, '2025-06-30', 'B05', 310.00, 'CONFIRMED'),
('Jose Rizal', 'jose@email.com', 3, '2025-06-30', 'C10, C11, C12', 750.00, 'PENDING'),
('Ana Garcia', 'ana@email.com', 4, '2025-07-01', 'D15, D16', 720.00, 'CONFIRMED');

-- Insert sample user data with recent registration dates
INSERT INTO users (username, email, registration_date, total_bookings) VALUES
('juandc', 'juan@email.com', '2025-06-15', 3),
('mariasantos', 'maria@email.com', '2025-06-20', 1),
('joserizal', 'jose@email.com', '2025-06-25', 2),
('anagarcia', 'ana@email.com', '2025-06-28', 1);

-- Verify the setup
SELECT 'Movies Table' as Table_Name, COUNT(*) as Record_Count FROM movies
UNION ALL
SELECT 'Bookings Table', COUNT(*) FROM bookings
UNION ALL
SELECT 'Users Table', COUNT(*) FROM users; 