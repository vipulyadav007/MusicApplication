package com.example.music_app.service;

import com.example.music_app.dto.PlaylistDTO;
import com.example.music_app.entity.Playlist;
import com.example.music_app.entity.Song;
import com.example.music_app.entity.User;
import java.util.List;
import java.util.Optional;

public interface PlaylistService {
    Playlist createPlaylist(User user, String name);
    Optional<Playlist> addSongToPlaylist(Long playlistId, Song song);
    Optional<Playlist> removeSongFromPlaylist(Long playlistId, Song song);
    List<PlaylistDTO> getUserPlaylists(User user);
    Optional<PlaylistDTO> getPlaylist(Long id);
}
