package com.example.demo;

import java.util.*;
import org.springframework.stereotype.Service;

/**
 * Service pour gérer les parties de jeu
 */
@Service
public class GameSessionService {
    
    // Stockage en mémoire des sessions (en production, utiliser une BD)
    private final Map<String, GameSessionDTO> sessions = new HashMap<>();

    /**
     * Crée une nouvelle partie
     */
    public GameSessionDTO createSession(String gameId, CreateGameSessionRequest request) {
        String sessionId = UUID.randomUUID().toString();
        GameSessionDTO session = new GameSessionDTO();
        
        session.setSessionId(sessionId);
        session.setGameId(gameId);
        session.setPlayerCount(request.getPlayerCount());
        session.setBoardSize(request.getBoardSize());
        session.setState("IN_PROGRESS");
        
        // Initialiser le plateau vide
        String[][] board = new String[request.getBoardSize()][request.getBoardSize()];
        for (int i = 0; i < request.getBoardSize(); i++) {
            for (int j = 0; j < request.getBoardSize(); j++) {
                board[i][j] = "EMPTY";
            }
        }
        session.setBoard(board);
        
        // Premier joueur
        String player1 = UUID.randomUUID().toString();
        session.setCurrentPlayer(player1);
        
        // Tokens initiaux
        Map<String, Integer> remainingTokens = new HashMap<>();
        Map<String, Integer> removedTokens = new HashMap<>();
        for (int i = 0; i < request.getPlayerCount(); i++) {
            remainingTokens.put(UUID.randomUUID().toString(), request.getBoardSize() * request.getBoardSize());
            removedTokens.put(UUID.randomUUID().toString(), 0);
        }
        session.setRemainingTokens(remainingTokens);
        session.setRemovedTokens(removedTokens);
        
        // Coups légaux initiaux
        List<MoveDTO> legalMoves = new ArrayList<>();
        for (int i = 0; i < request.getBoardSize(); i++) {
            for (int j = 0; j < request.getBoardSize(); j++) {
                legalMoves.add(new MoveDTO(i, j));
            }
        }
        session.setLegalMoves(legalMoves);
        
        sessions.put(sessionId, session);
        return session;
    }

    /**
     * Obtient l'état d'une partie
     */
    public GameSessionDTO getSession(String sessionId) {
        GameSessionDTO session = sessions.get(sessionId);
        if (session == null) {
            throw new IllegalArgumentException("Session not found: " + sessionId);
        }
        return session;
    }

    /**
     * Joue un coup
     */
    public GameSessionDTO playMove(String sessionId, MoveRequest moveRequest) {
        GameSessionDTO session = getSession(sessionId);
        
        // Valider que c'est le bon joueur
        if (!session.getCurrentPlayer().equals(moveRequest.getPlayerId())) {
            throw new IllegalArgumentException("It's not this player's turn");
        }
        
        // Valider la position
        if (moveRequest.getRow() < 0 || moveRequest.getRow() >= session.getBoardSize() ||
            moveRequest.getCol() < 0 || moveRequest.getCol() >= session.getBoardSize()) {
            throw new IllegalArgumentException("Invalid move position");
        }
        
        // Valider que la case est vide
        if (!session.getBoard()[moveRequest.getRow()][moveRequest.getCol()].equals("EMPTY")) {
            throw new IllegalArgumentException("Cell is already occupied");
        }
        
        // Placer le token
        session.getBoard()[moveRequest.getRow()][moveRequest.getCol()] = "X"; // Simplifié
        
        // Passer au joueur suivant
        List<String> players = new ArrayList<>(session.getRemainingTokens().keySet());
        int currentIndex = players.indexOf(session.getCurrentPlayer());
        String nextPlayer = players.get((currentIndex + 1) % players.size());
        session.setCurrentPlayer(nextPlayer);
        
        // Recalculer les coups légaux
        List<MoveDTO> legalMoves = new ArrayList<>();
        for (int i = 0; i < session.getBoardSize(); i++) {
            for (int j = 0; j < session.getBoardSize(); j++) {
                if (session.getBoard()[i][j].equals("EMPTY")) {
                    legalMoves.add(new MoveDTO(i, j));
                }
            }
        }
        session.setLegalMoves(legalMoves);
        
        sessions.put(sessionId, session);
        return session;
    }

    /**
     * Obtient les coups légaux pour la partie actuelle
     */
    public List<MoveDTO> getLegalMoves(String sessionId) {
        return getSession(sessionId).getLegalMoves();
    }
}
