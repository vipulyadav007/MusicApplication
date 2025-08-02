package com.example.musicApp.repository;

import com.example.musicApp.entity.Playback;
import com.example.musicApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaybackRepository extends JpaRepository<Playback, Long> {
    Optional<Playback> findByUser(User user);
}

