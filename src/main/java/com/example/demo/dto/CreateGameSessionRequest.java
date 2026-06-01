package com.example.demo.dto;

/**
 * DTO pour créer une nouvelle partie
 */
public class CreateGameSessionRequest {
    private int playerCount;
    private int boardSize;

    public CreateGameSessionRequest() {}

    public CreateGameSessionRequest(int playerCount, int boardSize) {
        this.playerCount = playerCount;
        this.boardSize = boardSize;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
}
