package com.example.musicApp.service.impl;

import com.example.musicApp.entity.Playback;
import com.example.musicApp.entity.Song;
import com.example.musicApp.entity.User;
import com.example.musicApp.repository.PlaybackRepository;
import com.example.musicApp.service.PlaybackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaybackServiceImpl implements PlaybackService {
    @Autowired
    private PlaybackRepository playbackRepository;

    @Override
    public Playback playSong(User user, Song song) {
        Optional<Playback> playbackOpt = playbackRepository.findByUser(user);
        Playback playback = playbackOpt.orElse(new Playback());
        playback.setUser(user);
        playback.setSong(song);
        playback.setStatus("PLAYING");
        return playbackRepository.save(playback);
    }

    @Override
    public Optional<Playback> pauseSong(User user) {
        Optional<Playback> playbackOpt = playbackRepository.findByUser(user);
        playbackOpt.ifPresent(p -> {
            p.setStatus("PAUSED");
            playbackRepository.save(p);
        });
        return playbackOpt;
    }

    @Override
    public Optional<Playback> resumeSong(User user) {
        Optional<Playback> playbackOpt = playbackRepository.findByUser(user);
        playbackOpt.ifPresent(p -> {
            p.setStatus("PLAYING");
            playbackRepository.save(p);
        });
        return playbackOpt;
    }

    @Override
    public Optional<Playback> stopSong(User user) {
        Optional<Playback> playbackOpt = playbackRepository.findByUser(user);
        playbackOpt.ifPresent(p -> {
            p.setStatus("STOPPED");
            playbackRepository.save(p);
        });
        return playbackOpt;
    }

    @Override
    public Optional<Playback> getCurrentPlayback(User user) {
        return playbackRepository.findByUser(user);
    }
}

