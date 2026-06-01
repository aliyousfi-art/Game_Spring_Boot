package com.example.demo.service;

import com.example.demo.dao.GameDao;
import com.example.demo.dto.CreateGameSessionRequest;
import com.example.demo.dto.GameSessionDTO;
import com.example.demo.dto.MoveDTO;
import com.example.demo.dto.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameSessionService {

    private final GameDao gameDao;
    private final GameCatalogImpl gameCatalog;

    public GameSessionService(GameDao gameDao, GameCatalogImpl gameCatalog) {
        this.gameDao = gameDao;
        this.gameCatalog = gameCatalog;
    }

    public GameSessionDTO createSession(String gameId, CreateGameSessionRequest request) {
        // Vérifier que le jeu existe
        gameCatalog.getGamePlugin(gameId);
        
        String sessionId = UUID.randomUUID().toString();
        GameSessionDTO session = new GameSessionDTO();

        session.setSessionId(sessionId);
        session.setGameId(gameId);
        session.setPlayerCount(request.getPlayerCount() > 0 ? request.getPlayerCount() : 2);
        session.setBoardSize(request.getBoardSize() > 0 ? request.getBoardSize() : 3);
        session.setState("IN_PROGRESS");

        // Initialiser le board
        int size = session.getBoardSize();
        String[][] board = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = "EMPTY";
            }
        }
        session.setBoard(board);

        // Premier joueur
        String currentPlayer = UUID.randomUUID().toString();
        session.setCurrentPlayer(currentPlayer);

        // Jetons par joueur
        Map<String, Integer> remainingTokens = new HashMap<>();
        Map<String, Integer> removedTokens = new HashMap<>();
        
        for (int i = 0; i < session.getPlayerCount(); i++) {
            String playerId = UUID.randomUUID().toString();
            remainingTokens.put(playerId, size * size);
            removedTokens.put(playerId, 0);
        }

        session.setRemainingTokens(remainingTokens);
        session.setRemovedTokens(removedTokens);

        // Coups légaux initiaux
        List<MoveDTO> legalMoves = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                legalMoves.add(new MoveDTO(row, col));
            }
        }
        session.setLegalMoves(legalMoves);

        gameDao.save(session);
        return session;
    }

    public GameSessionDTO getSession(String sessionId) {
        return gameDao.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found : " + sessionId));
    }

    public List<MoveDTO> getLegalMoves(String sessionId) {
        return getSession(sessionId).getLegalMoves();
    }

    public GameSessionDTO playMove(String sessionId, MoveRequest moveRequest) {
        GameSessionDTO session = getSession(sessionId);

        if (!session.getCurrentPlayer().equals(moveRequest.getPlayerId())) {
            throw new IllegalArgumentException("It's not this player's turn");
        }

        int row = moveRequest.getRow();
        int col = moveRequest.getCol();

        if (row < 0 || row >= session.getBoardSize() || col < 0 || col >= session.getBoardSize()) {
            throw new IllegalArgumentException("Invalid move position");
        }

        if (!session.getBoard()[row][col].equals("EMPTY")) {
            throw new IllegalArgumentException("Cell is already occupied");
        }

        session.getBoard()[row][col] = "X";

        // Recalculer coups légaux
        List<MoveDTO> legalMoves = new ArrayList<>();
        for (int r = 0; r < session.getBoardSize(); r++) {
            for (int c = 0; c < session.getBoardSize(); c++) {
                if ("EMPTY".equals(session.getBoard()[r][c])) {
                    legalMoves.add(new MoveDTO(r, c));
                }
            }
        }
        session.setLegalMoves(legalMoves);

        gameDao.save(session);
        return session;
    }

    public void deleteSession(String sessionId) {
        gameDao.delete(sessionId);
    }

    public Collection<GameSessionDTO> findAll() {
        return gameDao.findAll();
    }
}
