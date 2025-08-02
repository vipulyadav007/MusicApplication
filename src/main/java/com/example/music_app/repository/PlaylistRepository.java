package com.example.music_app.repository;

import com.example.music_app.entity.Playlist;
import com.example.music_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUser(User user);
}
