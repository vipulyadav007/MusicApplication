package com.example.music_app.service;

import com.example.music_app.entity.Song;
import java.util.List;
import java.util.Optional;

public interface SongService {
    Song addSong(Song song);
    List<Song> listSongs();
    List<Song> searchByTitle(String title);
    List<Song> searchByArtist(String artist);
    List<Song> searchByGenre(String genre);
    Optional<Song> getSong(Long id);
}

