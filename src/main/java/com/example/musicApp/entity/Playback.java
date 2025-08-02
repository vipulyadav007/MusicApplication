package com.example.musicApp.entity;

import jakarta.persistence.*;
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
