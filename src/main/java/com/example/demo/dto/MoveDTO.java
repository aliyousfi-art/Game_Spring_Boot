package com.example.demo.dto;

/**
 * DTO représentant un coup possible
 */
public class MoveDTO {
    private int row;
    private int col;

    public MoveDTO() {}

    public MoveDTO(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
}
