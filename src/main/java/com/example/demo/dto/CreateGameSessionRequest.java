package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class CreateGameSessionRequest {

    @Min(value = 1, message = "playerCount doit être >= 1")
    @Max(value = 10, message = "playerCount doit être <= 10")
    private int playerCount;

    @Min(value = 2, message = "boardSize doit être >= 2")
    @Max(value = 20, message = "boardSize doit être <= 20")
    private int boardSize;

    public CreateGameSessionRequest() {}

    public CreateGameSessionRequest(int playerCount, int boardSize) {
        this.playerCount = playerCount;
        this.boardSize = boardSize;
    }

    public int getPlayerCount() { return playerCount; }
    public void setPlayerCount(int playerCount) { this.playerCount = playerCount; }

    public int getBoardSize() { return boardSize; }
    public void setBoardSize(int boardSize) { this.boardSize = boardSize; }
}
