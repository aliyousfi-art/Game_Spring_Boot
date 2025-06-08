package com.example.demo.controller;

import java.util.Collection;
import java.util.List;
import com.example.demo.dto.*;
import com.example.demo.service.GameSessionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameSessionController {

    private static final Logger log = LoggerFactory.getLogger(GameSessionController.class);

    private final GameSessionService gameSessionService;

    public GameSessionController(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @PostMapping("/{gameId}/sessions")
    public ResponseEntity<GameSessionDTO> createSession(
            @PathVariable String gameId,
            @Valid @RequestBody CreateGameSessionRequest request) {
        try {
            GameSessionDTO session = gameSessionService.createSession(gameId, request);
            return new ResponseEntity<>(session, HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn("createSession error for gameId={}: {}", gameId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{gameId}/sessions")
    public ResponseEntity<Collection<GameSessionDTO>> listSessions(@PathVariable String gameId) {
        return new ResponseEntity<>(gameSessionService.findByGameId(gameId), HttpStatus.OK);
    }

    @GetMapping("/{gameId}/sessions/{sessionId}")
    public ResponseEntity<GameSessionDTO> getSession(
            @PathVariable String gameId, @PathVariable String sessionId) {
        try {
            return new ResponseEntity<>(gameSessionService.getSession(sessionId), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{gameId}/sessions/{sessionId}/legal-moves")
    public ResponseEntity<LegalMovesResponse> getLegalMoves(
            @PathVariable String gameId, @PathVariable String sessionId) {
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

    @PostMapping("/{gameId}/sessions/{sessionId}/move")
    public ResponseEntity<MoveResponse> playMove(
            @PathVariable String gameId,
            @PathVariable String sessionId,
            @Valid @RequestBody MoveRequest moveRequest) {
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

    @DeleteMapping("/{gameId}/sessions/{sessionId}")
    public ResponseEntity<Void> deleteSession(
            @PathVariable String gameId, @PathVariable String sessionId) {
        gameSessionService.deleteSession(sessionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handler global — log le détail en interne, renvoie un message générique au client.
     * Ne jamais exposer e.getMessage() directement (peut contenir des infos sensibles).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Unhandled exception: {}", e.getMessage(), e);
        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
