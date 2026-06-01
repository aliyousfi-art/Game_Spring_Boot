package com.example.demo.controller;

import java.util.List;

import com.example.demo.dto.*;
import com.example.demo.service.GameSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour l'API des parties de jeu
 * Endpoints:
 * - POST /api/games/{gameId}/sessions - Créer une partie
 * - GET /api/games/{gameId}/sessions/{sessionId} - Obtenir l'état
 * - GET /api/games/{gameId}/sessions/{sessionId}/legal-moves - Coups possibles
 * - POST /api/games/{gameId}/sessions/{sessionId}/move - Jouer un coup
 */
@RestController
@RequestMapping("/api/games")
public class GameSessionController {

    private final GameSessionService gameSessionService;

    // Injection par constructeur - meilleure pratique Spring Boot moderne
    public GameSessionController(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    /**
     * 1. POST /api/games/{gameId}/sessions
     * Crée une nouvelle partie
     */
    @PostMapping("/{gameId}/sessions")
    public ResponseEntity<GameSessionDTO> createSession(
            @PathVariable String gameId,
            @RequestBody CreateGameSessionRequest request) {
        try {
            GameSessionDTO session = gameSessionService.createSession(gameId, request);
            return new ResponseEntity<>(session, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 2. GET /api/games/{gameId}/sessions/{sessionId}
     * Obtient l'état d'une partie en cours
     */
    @GetMapping("/{gameId}/sessions/{sessionId}")
    public ResponseEntity<GameSessionDTO> getSession(
            @PathVariable String gameId,
            @PathVariable String sessionId) {
        try {
            GameSessionDTO session = gameSessionService.getSession(sessionId);
            return new ResponseEntity<>(session, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 3. GET /api/games/{gameId}/sessions/{sessionId}/legal-moves
     * Obtient la liste des coups possibles
     */
    @GetMapping("/{gameId}/sessions/{sessionId}/legal-moves")
    public ResponseEntity<LegalMovesResponse> getLegalMoves(
            @PathVariable String gameId,
            @PathVariable String sessionId) {
        try {
            GameSessionDTO session = gameSessionService.getSession(sessionId);
            List<MoveDTO> legalMoves = gameSessionService.getLegalMoves(sessionId);
            
            LegalMovesResponse response = new LegalMovesResponse();
            response.setCurrentPlayer(session.getCurrentPlayer());
            response.setLegalMoves(legalMoves);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 4. POST /api/games/{gameId}/sessions/{sessionId}/move
     * Joue un coup
     */
    @PostMapping("/{gameId}/sessions/{sessionId}/move")
    public ResponseEntity<MoveResponse> playMove(
            @PathVariable String gameId,
            @PathVariable String sessionId,
            @RequestBody MoveRequest moveRequest) {
        try {
            GameSessionDTO session = gameSessionService.playMove(sessionId, moveRequest);
            
            MoveResponse response = new MoveResponse();
            response.setSessionId(sessionId);
            response.setBoard(session.getBoard());
            response.setCurrentPlayer(session.getCurrentPlayer());
            response.setState(session.getState());
            response.setLegalMoves(session.getLegalMoves());
            response.setMessage("Move played successfully");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gestion des exceptions globales
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
