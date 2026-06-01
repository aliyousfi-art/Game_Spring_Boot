package com.example.demo.dto;

import java.util.List;
import java.util.Map;

public class GameSessionDTO {

    private String sessionId;
    private String gameId;
    private int playerCount;
    private int boardSize;
    private String state;
    private String[][] board;
    private String currentPlayer;
    private Map<String, Integer> remainingTokens;
    private Map<String, Integer> removedTokens;
    private List<MoveDTO> legalMoves;

    public GameSessionDTO() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Map<String, Integer> getRemainingTokens() {
        return remainingTokens;
    }

    public void setRemainingTokens(Map<String, Integer> remainingTokens) {
        this.remainingTokens = remainingTokens;
    }

    public Map<String, Integer> getRemovedTokens() {
        return removedTokens;
    }

    public void setRemovedTokens(Map<String, Integer> removedTokens) {
        this.removedTokens = removedTokens;
    }

    public List<MoveDTO> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(List<MoveDTO> legalMoves) {
        this.legalMoves = legalMoves;
    }
}
