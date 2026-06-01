package com.example.demo.dto;

import java.util.List;

/**
 * Réponse après avoir joué un coup
 */
public class MoveResponse {
    private String sessionId;
    private String[][] board;
    private String currentPlayer;
    private String state;
    private List<MoveDTO> legalMoves;
    private String message;

    public MoveResponse() {}

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String[][] getBoard() { return board; }
    public void setBoard(String[][] board) { this.board = board; }

    public String getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(String currentPlayer) { this.currentPlayer = currentPlayer; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public List<MoveDTO> getLegalMoves() { return legalMoves; }
    public void setLegalMoves(List<MoveDTO> legalMoves) { this.legalMoves = legalMoves; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
