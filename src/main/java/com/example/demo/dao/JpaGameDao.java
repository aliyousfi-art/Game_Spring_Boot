package com.example.demo.dao;

import com.example.demo.dto.GameSessionDTO;
import com.example.demo.dto.MoveDTO;
import com.example.demo.entity.GameEntity;
import com.example.demo.entity.GameTokenEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
@Profile("jpa")
public class JpaGameDao implements GameDao {

    private final GameEntityRepository repository;
    private final ObjectMapper objectMapper;

    public JpaGameDao(GameEntityRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Collection<GameSessionDTO> findAll() {
        List<GameSessionDTO> result = new ArrayList<>();
        for (GameEntity entity : repository.findAll()) {
            result.add(toDto(entity));
        }
        return result;
    }

    @Override
    public Optional<GameSessionDTO> findById(String id) {
        return repository.findById(id).map(this::toDto);
    }

    @Override
    public GameSessionDTO save(GameSessionDTO session) {
        repository.save(toEntity(session));
        return session;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    private GameEntity toEntity(GameSessionDTO session) {
        GameEntity entity = new GameEntity();
        entity.id = session.getSessionId();
        entity.gameId = session.getGameId();
        entity.playerCount = session.getPlayerCount();
        entity.boardSize = session.getBoardSize();
        entity.state = session.getState();
        entity.currentPlayer = session.getCurrentPlayer();
        entity.boardJson = writeJson(session.getBoard());
        entity.legalMovesJson = writeJson(session.getLegalMoves());
        entity.tokens = toTokens(session.getRemainingTokens(), session.getRemovedTokens());
        return entity;
    }

    private GameSessionDTO toDto(GameEntity entity) {
        GameSessionDTO session = new GameSessionDTO();
        session.setSessionId(entity.id);
        session.setGameId(entity.gameId);
        session.setPlayerCount(entity.playerCount);
        session.setBoardSize(entity.boardSize);
        session.setState(entity.state);
        session.setCurrentPlayer(entity.currentPlayer);
        session.setBoard(readJson(entity.boardJson, new TypeReference<String[][]>() {}));
        session.setLegalMoves(readJson(entity.legalMovesJson, new TypeReference<List<MoveDTO>>() {}));
        Map<String, Integer> remaining = new HashMap<>();
        Map<String, Integer> removed = new HashMap<>();
        for (GameTokenEntity token : entity.tokens) {
            remaining.put(token.ownerId, token.remaining);
            removed.put(token.ownerId, token.removed);
        }
        session.setRemainingTokens(remaining);
        session.setRemovedTokens(removed);
        return session;
    }

    private List<GameTokenEntity> toTokens(Map<String, Integer> remaining, Map<String, Integer> removed) {
        List<GameTokenEntity> tokens = new ArrayList<>();
        Set<String> owners = new HashSet<>();
        if (remaining != null) {
            owners.addAll(remaining.keySet());
        }
        if (removed != null) {
            owners.addAll(removed.keySet());
        }
        for (String owner : owners) {
            GameTokenEntity token = new GameTokenEntity();
            token.ownerId = owner;
            token.remaining = remaining != null && remaining.get(owner) != null ? remaining.get(owner) : 0;
            token.removed = removed != null && removed.get(owner) != null ? removed.get(owner) : 0;
            tokens.add(token);
        }
        return tokens;
    }

    private String writeJson(Object value) {
        try {
            return value == null ? null : objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private <T> T readJson(String json, TypeReference<T> type) {
        try {
            return json == null ? null : objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
