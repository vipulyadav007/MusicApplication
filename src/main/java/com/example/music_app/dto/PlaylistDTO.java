package com.example.music_app.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PlaylistDTO {
    private Long id;
    private String name;
    private Set<SongDTO> songs;
}