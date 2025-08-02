# 🎵 Music Application

A RESTful Spring Boot application for managing music playlists, songs, and user playback with secure authentication and authorization.

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Authentication & Authorization](#authentication--authorization)
- [Database Schema](#database-schema)
- [Usage Examples](#usage-examples)
- [Contributing](#contributing)

## ✨ Features

- **User Management**: Registration and authentication with role-based access control
- **Song Library**: Add, search, and manage songs by title, artist, and genre
- **Playlist Management**: Create personal playlists and add/remove songs
- **Music Playback**: Control music playback (play, pause, resume, stop) for users
- **Security**: JWT-like token-based authentication with admin privileges
- **RESTful API**: Clean REST endpoints with proper HTTP status codes

## 🛠️ Tech Stack

- **Backend**: Spring Boot 
- **Database**: MySQL
- **Security**: Custom token-based authentication
- **Build Tool**: Maven
- **Java Version**: 17+

## 📁 Project Structure

```
src/main/java/com/example/music_app/
├── 🎮 controller/          # REST Controllers
│   ├── UserController.java
│   ├── SongController.java
│   ├── PlaylistController.java
│   └── PlaybackController.java
├── 🗃️ entity/             # JPA Entities
│   ├── User.java
│   ├── Song.java
│   ├── Playlist.java
│   └── Playback.java
├── 📊 dto/                # Data Transfer Objects
│   ├── PlaylistDTO.java
│   └── SongDTO.java
├── 🔧 service/            # Business Logic
│   ├── UserService.java
│   ├── SongService.java
│   ├── PlaylistService.java
│   └── PlaybackService.java
├── 💾 repository/         # Data Access Layer
│   ├── UserRepository.java
│   ├── SongRepository.java
│   ├── PlaylistRepository.java
│   └── PlaybackRepository.java
└── 🔐 security/           # Security Configuration
    └── SecurityManager.java
```

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/vipulyadav007/MusicApplication.git
   cd MusicApplication
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Base URL: `http://localhost:8080`
   - API Base: `http://localhost:8080/api`

## 🔌 API Endpoints

### 👤 User Management

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/users/register` | Register new user | Admin Only |
| POST | `/api/users/login` | User login | Public |
| GET | `/api/users/{id}` | Get user profile | Admin Only |

### 🎵 Song Management

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/songs` | Add new song | Yes |
| GET | `/api/songs` | List all songs | Yes |
| GET | `/api/songs/search` | Search songs | Yes |

### 📝 Playlist Management

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/playlists/create` | Create playlist | Yes |
| POST | `/api/playlists/{id}/add` | Add song to playlist | Yes |
| DELETE | `/api/playlists/{id}/remove` | Remove song from playlist | Yes |
| GET | `/api/playlists/{id}` | Get playlist details | Yes |
| GET | `/api/playlists/user/{userId}` | Get user's playlists | Yes |

### 🎮 Playback Control

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/playback/play` | Play song | Yes |
| POST | `/api/playback/pause` | Pause playback | Yes |
| POST | `/api/playback/resume` | Resume playback | Yes |
| POST | `/api/playback/stop` | Stop playback | Yes |
| GET | `/api/playback/current` | Get current playback | Yes |

## 🔐 Authentication & Authorization

### Token-Based Authentication

1. **Login** to get an authentication token:
   ```bash
   POST /api/users/login
   Content-Type: application/json
   
   {
     "username": "username",
     "password": "password"
   }
   ```

2. **Use the token** in subsequent requests:
   ```bash
   Authorization: Bearer YOUR_TOKEN_HERE
   ```

### User Roles

- **USER**: Can manage own playlists, songs, and playback
- **ADMIN**: Can register new users and access all user data

## 🗄️ Database Schema

### Core Entities

```sql
Users (id, username, password, role)
Songs (id, title, artist, genre)
Playlists (id, name, user_id)
Playlist_Songs (playlist_id, song_id)
Playback (id, user_id, song_id, status)
```

### Relationships

- User **1:N** Playlists
- Playlist **N:M** Songs
- User **1:1** Playback (current)

## 📝 Usage Examples

### Complete Workflow

1. **Admin registers a new user**:
   ```bash
   POST /api/users/register
   Authorization: Bearer ADMIN_TOKEN
   Content-Type: application/json
   
   {
     "username": "vipul",
     "password": "pass123",
     "role": "USER"
   }
   ```

2. **User logs in**:
   ```bash
   POST /api/users/login
   Content-Type: application/json
   
   {
     "username": "userVipul",
     "password": "1234"
   }
   
   Response: {"token": "abc123-def456-ghi789"}
   ```

3. **Add a song**:
   ```bash
   POST /api/songs
   Authorization: Bearer abc123-def456-ghi789
   Content-Type: application/json
   
   {
     "title": "Shape of You",
     "artist": "Ed Sheeran",
     "genre": "Pop"
   }
   ```

4. **Create a playlist**:
   ```bash
   POST /api/playlists/create?name=My%20Favorites&userId=1
   Authorization: Bearer abc123-def456-ghi789
   ```

5. **Add song to playlist**:
   ```bash
   POST /api/playlists/1/add?songId=1
   Authorization: Bearer abc123-def456-ghi789
   ```

6. **Play a song**:
   ```bash
   POST /api/playback/play?songId=1
   Authorization: Bearer abc123-def456-ghi789
   ```

## 🔧 Configuration

### Application Properties

```properties
# Database Configuration
spring.datasource.url=jdbc:h2:mem:musicdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update

# Server Configuration
server.port=8080

# H2 Console (for development)
spring.h2.console.enabled=true
```


## 🔨 Development

### Adding New Features

1. Create entity classes in `entity/` package
2. Add repository interfaces in `repository/` package
3. Implement business logic in `service/` package
4. Create REST endpoints in `controller/` package
5. Add DTOs if needed in `dto/` package

### Security Considerations

- All endpoints except `/login` and `/register` require authentication
- Admin-only endpoints are protected by role checking
- Passwords are stored as-is (consider adding encryption for production)
- Tokens are stored in memory (consider Redis for production)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🎯 Future Enhancements

- [ ] Add password encryption (BCrypt)
- [ ] Implement refresh tokens
- [ ] Add song file upload/streaming
- [ ] Create playlist sharing functionality
- [ ] Add music recommendations
- [ ] Implement real-time notifications
- [ ] Add comprehensive logging
- [ ] Docker containerization
- [ ] API documentation with Swagger

## 📞 Contact

**Developer**:Vipul    

**Email**: raovipul007@gmail.com

**GitHub**: [@vipulyadav007](https://github.com/vipulyadav007)

---

⭐ **Star this repository if you found it helpful!**
