package com.example.demo.dto;

import java.util.List;

/**
 * Réponse pour l'endpoint legal-moves
 */
public class LegalMovesResponse {
    private String currentPlayer;
    private List<MoveDTO> legalMoves;

    public LegalMovesResponse() {}

    public String getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(String currentPlayer) { this.currentPlayer = currentPlayer; }

    public List<MoveDTO> getLegalMoves() { return legalMoves; }
    public void setLegalMoves(List<MoveDTO> legalMoves) { this.legalMoves = legalMoves; }
}
