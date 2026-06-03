package com.example.demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class GameEntity {

    @Id
    public String id;
    public String gameId;
    public int playerCount;
    public int boardSize;
    public String state;
    public String currentPlayer;

    @Lob
    public String boardJson;

    @Lob
    public String legalMovesJson;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "session_id_fk")
    public List<GameTokenEntity> tokens = new ArrayList<>();
}
