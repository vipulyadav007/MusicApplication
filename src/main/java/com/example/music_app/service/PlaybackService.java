package com.example.music_app.service;

import com.example.music_app.entity.Playback;
import com.example.music_app.entity.Song;
import com.example.music_app.entity.User;
import java.util.Optional;

public interface PlaybackService {
    Playback playSong(User user, Song song);
    Optional<Playback> pauseSong(User user);
    Optional<Playback> resumeSong(User user);
    Optional<Playback> stopSong(User user);
    Optional<Playback> getCurrentPlayback(User user);
}

