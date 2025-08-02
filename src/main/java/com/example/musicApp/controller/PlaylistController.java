package com.example.musicApp.controller;

import com.example.musicApp.dto.PlaylistDTO;
import com.example.musicApp.entity.Playlist;
import com.example.musicApp.entity.Song;
import com.example.musicApp.entity.User;
import com.example.musicApp.service.PlaylistService;
import com.example.musicApp.service.UserService;
import com.example.musicApp.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private UserService userService;
    @Autowired
    private SongService songService;

    @PostMapping("/create")
    public ResponseEntity<PlaylistDTO> createPlaylist(@RequestParam String name, @RequestParam Long userId) {
        Optional<User> userOpt = userService.getProfile(userId);
        if (userOpt.isPresent()) {
            Playlist playlist = playlistService.createPlaylist(userOpt.get(), name);
            // Convert to DTO before returning
            Optional<PlaylistDTO> playlistDTO = playlistService.getPlaylist(playlist.getId());
            return playlistDTO.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{playlistId}/add")
    public ResponseEntity<PlaylistDTO> addSong(@PathVariable Long playlistId, @RequestParam Long songId) {
        Optional<Song> songOpt = songService.getSong(songId);
        if (songOpt.isPresent()) {
            Optional<Playlist> result = playlistService.addSongToPlaylist(playlistId, songOpt.get());
            if (result.isPresent()) {
                // Convert to DTO before returning
                Optional<PlaylistDTO> playlistDTO = playlistService.getPlaylist(playlistId);
                return playlistDTO.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{playlistId}/remove")
    public ResponseEntity<PlaylistDTO> removeSong(@PathVariable Long playlistId, @RequestParam Long songId) {
        Optional<Song> songOpt = songService.getSong(songId);
        if (songOpt.isPresent()) {
            Optional<Playlist> result = playlistService.removeSongFromPlaylist(playlistId, songOpt.get());
            if (result.isPresent()) {
                Optional<PlaylistDTO> playlistDTO = playlistService.getPlaylist(playlistId);
                return playlistDTO.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PlaylistDTO>> getUserPlaylists(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getProfile(userId);
        return userOpt.map(user -> ResponseEntity.ok(playlistService.getUserPlaylists(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistDTO> getPlaylist(@PathVariable Long playlistId) {
        Optional<PlaylistDTO> playlistOpt = playlistService.getPlaylist(playlistId);
        return playlistOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
