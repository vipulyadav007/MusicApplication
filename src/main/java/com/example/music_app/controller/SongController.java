package com.example.music_app.controller;

import com.example.music_app.entity.Song;
import com.example.music_app.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    @Autowired
    private SongService songService;

    @PostMapping
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        return ResponseEntity.ok(songService.addSong(song));
    }

    @GetMapping
    public List<Song> listSongs() {
        return songService.listSongs();
    }

    @GetMapping("/search")
    public List<Song> searchSongs(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String artist,
                                  @RequestParam(required = false) String genre) {
        if (title != null) return songService.searchByTitle(title);
        if (artist != null) return songService.searchByArtist(artist);
        if (genre != null) return songService.searchByGenre(genre);
        return songService.listSongs();
    }
}

