package com.example.demo.dto;

/**
 * DTO pour jouer un coup
 */
public class MoveRequest {
    private String playerId;
    private int row;
    private int col;

    public MoveRequest() {}

    public MoveRequest(String playerId, int row, int col) {
        this.playerId = playerId;
        this.row = row;
        this.col = col;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
