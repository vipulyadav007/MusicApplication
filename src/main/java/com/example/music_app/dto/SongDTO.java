package com.example.music_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongDTO {
    private Long id;
    private String title;
    private String artist;
    private String genre;
}
