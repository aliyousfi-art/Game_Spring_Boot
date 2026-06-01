package com.example.demo.dto;

public class GameInfoDTO {

    private String gameId;
    private String name;
    private int defaultPlayerCount;
    private int defaultBoardSize;

    public GameInfoDTO() {
    }

    public GameInfoDTO(String gameId, String name, int defaultPlayerCount, int defaultBoardSize) {
        this.gameId = gameId;
        this.name = name;
        this.defaultPlayerCount = defaultPlayerCount;
        this.defaultBoardSize = defaultBoardSize;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDefaultPlayerCount() {
        return defaultPlayerCount;
    }

    public void setDefaultPlayerCount(int defaultPlayerCount) {
        this.defaultPlayerCount = defaultPlayerCount;
    }

    public int getDefaultBoardSize() {
        return defaultBoardSize;
    }

    public void setDefaultBoardSize(int defaultBoardSize) {
        this.defaultBoardSize = defaultBoardSize;
    }
}
