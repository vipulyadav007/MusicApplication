package com.example.musicApp.service;

import com.example.musicApp.entity.Playback;
import com.example.musicApp.entity.Song;
import com.example.musicApp.entity.User;
import java.util.Optional;

public interface PlaybackService {
    Playback playSong(User user, Song song);
    Optional<Playback> pauseSong(User user);
    Optional<Playback> resumeSong(User user);
    Optional<Playback> stopSong(User user);
    Optional<Playback> getCurrentPlayback(User user);
}

