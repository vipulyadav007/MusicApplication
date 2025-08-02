package com.example.music_app.repository;

import com.example.music_app.entity.Playback;
import com.example.music_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaybackRepository extends JpaRepository<Playback, Long> {
    Optional<Playback> findByUser(User user);
}

