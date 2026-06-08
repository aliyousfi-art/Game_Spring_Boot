package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MoveRequest {

    @NotBlank(message = "playerId ne peut pas être vide")
    @Size(max = 100, message = "playerId trop long")
    private String playerId;

    @Min(value = 0, message = "row doit être >= 0")
    private int row;

    @Min(value = 0, message = "col doit être >= 0")
    private int col;

    public MoveRequest() {}

    public MoveRequest(String playerId, int row, int col) {
        this.playerId = playerId;
        this.row = row;
        this.col = col;
    }

    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }

    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
}
