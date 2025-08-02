package com.example.musicApp.service;

import com.example.musicApp.dto.PlaylistDTO;
import com.example.musicApp.entity.Playlist;
import com.example.musicApp.entity.Song;
import com.example.musicApp.entity.User;
import java.util.List;
import java.util.Optional;

public interface PlaylistService {
    Playlist createPlaylist(User user, String name);
    Optional<Playlist> addSongToPlaylist(Long playlistId, Song song);
    Optional<Playlist> removeSongFromPlaylist(Long playlistId, Song song);
    List<PlaylistDTO> getUserPlaylists(User user);
    Optional<PlaylistDTO> getPlaylist(Long id);
}
