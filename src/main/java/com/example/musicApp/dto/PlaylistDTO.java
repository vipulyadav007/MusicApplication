package com.example.musicApp.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PlaylistDTO {
    private Long id;
    private String name;
    private Set<SongDTO> songs;
}