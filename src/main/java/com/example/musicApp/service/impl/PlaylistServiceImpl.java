package com.example.musicApp.service.impl;

import com.example.musicApp.dto.PlaylistDTO;
import com.example.musicApp.dto.SongDTO;
import com.example.musicApp.entity.Playlist;
import com.example.musicApp.entity.Song;
import com.example.musicApp.entity.User;
import com.example.musicApp.repository.PlaylistRepository;
import com.example.musicApp.repository.SongRepository;
import com.example.musicApp.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private SongRepository songRepository;

    @Override
    public Playlist createPlaylist(User user, String name) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setUser(user);
        return playlistRepository.save(playlist);
    }

    @Override
    public Optional<Playlist> addSongToPlaylist(Long playlistId, Song song) {
        Optional<Playlist> playlistOpt = playlistRepository.findById(playlistId);
        if (playlistOpt.isPresent()) {
            Playlist playlist = playlistOpt.get();
            playlist.getSongs().add(song);
            playlistRepository.save(playlist);
            return Optional.of(playlist);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Playlist> removeSongFromPlaylist(Long playlistId, Song song) {
        Optional<Playlist> playlistOpt = playlistRepository.findById(playlistId);
        if (playlistOpt.isPresent()) {
            Playlist playlist = playlistOpt.get();
            playlist.getSongs().remove(song);
            playlistRepository.save(playlist);
            return Optional.of(playlist);
        }
        return Optional.empty();
    }

    @Override
    public List<PlaylistDTO> getUserPlaylists(User user) {
        List<Playlist> playlists = playlistRepository.findByUser(user);
        return playlists.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PlaylistDTO> getPlaylist(Long id) {
        return playlistRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Helper method to convert Playlist entity to PlaylistDTO
    private PlaylistDTO convertToDTO(Playlist playlist) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(playlist.getId());
        dto.setName(playlist.getName());

        // Convert songs to SongDTOs
        Set<SongDTO> songDTOs = playlist.getSongs().stream()
                .map(this::convertSongToDTO)
                .collect(Collectors.toSet());
        dto.setSongs(songDTOs);

        return dto;
    }

    // Helper method to convert Song entity to SongDTO
    private SongDTO convertSongToDTO(Song song) {
        return new SongDTO(song.getId(), song.getTitle(), song.getArtist(), song.getGenre());
    }
}
