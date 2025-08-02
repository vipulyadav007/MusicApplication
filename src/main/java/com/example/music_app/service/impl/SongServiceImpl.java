package com.example.music_app.service.impl;

import com.example.music_app.entity.Song;
import com.example.music_app.repository.SongRepository;
import com.example.music_app.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongRepository songRepository;

    @Override
    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }

    @Override
    public List<Song> searchByTitle(String title) {
        return songRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Song> searchByArtist(String artist) {
        return songRepository.findByArtistContainingIgnoreCase(artist);
    }

    @Override
    public List<Song> searchByGenre(String genre) {
        return songRepository.findByGenreContainingIgnoreCase(genre);
    }

    @Override
    public Optional<Song> getSong(Long id) {
        return songRepository.findById(id);
    }
}
