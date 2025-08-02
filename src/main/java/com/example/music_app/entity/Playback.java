package com.example.music_app.entity;

import jakarta.persistence.*;
import com.example.music_app.entity.Song;
import lombok.Data;

@Entity
@Data
public class Playback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    private String status; // PLAYING, PAUSED, STOPPED


}
