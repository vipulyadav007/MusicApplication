package com.example.music_app.controller;

import com.example.music_app.entity.Playback;
import com.example.music_app.entity.Song;
import com.example.music_app.entity.User;
import com.example.music_app.service.PlaybackService;
import com.example.music_app.service.UserService;
import com.example.music_app.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/playback")
public class PlaybackController {
    @Autowired
    private PlaybackService playbackService;
    @Autowired
    private UserService userService;
    @Autowired
    private SongService songService;

    @PostMapping("/play")
    public ResponseEntity<?> play(@RequestParam Long userId, @RequestParam Long songId) {
        Optional<User> userOpt = userService.getProfile(userId);
        Optional<Song> songOpt = songService.getSong(songId);
        if (userOpt.isPresent() && songOpt.isPresent()) {
            Playback playback = playbackService.playSong(userOpt.get(), songOpt.get());
            return ResponseEntity.ok(playback.getStatus());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/pause")
    public ResponseEntity<?> pause(@RequestParam Long userId) {
        Optional<User> userOpt = userService.getProfile(userId);
        if (userOpt.isPresent()) {
            Optional<?> playback = playbackService.pauseSong(userOpt.get());
            if (playback.isPresent()) {
                return ResponseEntity.ok("PAUSED");
            } else {
                return ResponseEntity.notFound().build();
            }


        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/resume")
    public ResponseEntity<?> resume(@RequestParam Long userId) {
        Optional<User> userOpt = userService.getProfile(userId);
        if (userOpt.isPresent()) {
            return playbackService.resumeSong(userOpt.get())
                    .map(playback -> ResponseEntity.ok(playback.getStatus()))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/stop")
    public ResponseEntity<?> stop(@RequestParam Long userId) {
        Optional<User> userOpt = userService.getProfile(userId);
        if (userOpt.isPresent()) {
            return playbackService.stopSong(userOpt.get())
                    .map(playback -> ResponseEntity.ok(playback.getStatus()))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrent(@RequestParam Long userId) {
        Optional<User> userOpt = userService.getProfile(userId);
        if (userOpt.isPresent()) {
            Optional<Playback> playbackOpt = playbackService.getCurrentPlayback(userOpt.get());
            if (playbackOpt.isPresent()) {
                return ResponseEntity.ok(playbackOpt.get().getStatus());
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
