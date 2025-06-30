# CineMate Database Setup Guide

## Prerequisites

### 1. Install XAMPP
- Download XAMPP from: https://www.apachefriends.org/
- Install XAMPP on your system
- Ensure MySQL is included in the installation

### 2. Start MySQL Service
1. Open XAMPP Control Panel
2. Click **"Start"** next to **MySQL**
3. Verify that MySQL status shows **"Running"**

## Database Setup Options

### Option 1: Automatic Setup (Recommended)
The application will automatically create the database when you run it for the first time.

1. **Start XAMPP MySQL** (as described above)
2. **Run the CineMate application**
3. The database `cinemate_db` will be created automatically
4. All tables and sample data will be populated

### Option 2: Manual Setup via phpMyAdmin
If automatic setup fails, use this manual method:

1. **Open phpMyAdmin**:
   - Go to: http://localhost/phpmyadmin/
   - Login with username: `root` (no password by default)

2. **Run the SQL Script**:
   - Click **"SQL"** tab in phpMyAdmin
   - Copy and paste the contents of `database_setup.sql`
   - Click **"Go"** to execute

3. **Verify Setup**:
   - You should see `cinemate_db` database created
   - Check that all tables contain data:
     - `movies` (9 movies)
     - `bookings` (4 sample bookings)
     - `users` (4 sample users)

## Database Configuration

### Default Settings
- **Host**: localhost
- **Port**: 3306
- **Database Name**: cinemate_db
- **Username**: root
- **Password**: (empty)

### Customizing Database Settings
If you need to change database settings, edit the file:
`src/main/java/com/mycompany/cinemate/database/DatabaseConfig.java`

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/cinemate_db";
private static final String DB_USERNAME = "root";
private static final String DB_PASSWORD = ""; // Change if you have a password
```

## Database Structure

### Tables Created:

#### 1. movies
- **id** (Primary Key)
- **title** (Movie title)
- **description** (Movie description)
- **start_date** (Showing start date)
- **end_date** (Showing end date)
- **price** (Ticket price in Philippine Peso)
- **poster_path** (Path to movie poster image)
- **is_active** (Active status)
- **created_at**, **updated_at** (Timestamps)

#### 2. bookings
- **id** (Primary Key)
- **customer_name** (Customer name)
- **customer_email** (Customer email)
- **movie_id** (Foreign key to movies table)
- **booking_date** (Date of booking)
- **seats** (Reserved seats)
- **total_price** (Total booking price)
- **status** (PENDING, CONFIRMED, CANCELLED)
- **created_at** (Timestamp)

#### 3. users
- **id** (Primary Key)
- **username** (Unique username)
- **email** (Unique email)
- **registration_date** (Registration date)
- **total_bookings** (Number of bookings made)
- **created_at** (Timestamp)

## Features Enabled

With the database connected, you get:

### 🎬 **Movies Management**
- View all movies from database
- Add new movies (Admin)
- Edit existing movies (Admin)
- Delete/deactivate movies (Admin)
- Real-time movie data

### 📊 **Dashboard Statistics**
- Total sales from confirmed bookings
- Number of tickets sold
- Number of active showings
- Real-time statistics

### 🎟️ **Booking System**
- View all customer bookings
- Track booking status
- Customer management
- Booking history

### 👤 **Admin Panel**
- Full CRUD operations
- Real data management
- User management
- Booking oversight

## Troubleshooting

### Common Issues:

#### 1. "Database Connection Failed"
**Solutions:**
- Ensure XAMPP MySQL is running
- Check if port 3306 is available
- Verify database credentials in `DatabaseConfig.java`

#### 2. "Table doesn't exist"
**Solutions:**
- Run the manual SQL setup script
- Restart the application
- Check phpMyAdmin for table creation

#### 3. "Access denied for user 'root'"
**Solutions:**
- Set MySQL root password in XAMPP
- Update password in `DatabaseConfig.java`
- Check MySQL user permissions

#### 4. Connection Timeout
**Solutions:**
- Increase connection timeout in `DatabaseConfig.java`
- Check firewall settings
- Restart MySQL service

### Getting Help

If you encounter issues:
1. Check the console output for detailed error messages
2. Verify XAMPP MySQL logs
3. Ensure all Maven dependencies are downloaded
4. Try the manual setup option

## Testing the Setup

### Verify Database Connection:
1. Run the CineMate application
2. Check console for "Database connection successful!" message
3. Login to admin panel (admin/admin)
4. Check if movies display real data from database

### Verify CRUD Operations:
1. **Create**: Add a new movie through admin panel
2. **Read**: View movies in the movies form
3. **Update**: Edit a movie through admin panel
4. **Delete**: Remove a movie through admin panel

## Sample Data Included

The database comes pre-populated with:
- **9 Movies**: From "How To Train Your Dragon" to "28 Years Later"
- **4 Bookings**: Sample customer reservations
- **4 Users**: Sample user accounts

This allows you to immediately test all features without needing to add data manually.

---

**Ready to use CineMate with full database functionality!** 🎬 