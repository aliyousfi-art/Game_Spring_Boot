package com.example.demo.dao;

import com.example.demo.dto.GameSessionDTO;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryGameDao implements GameDao {

    private final Map<String, GameSessionDTO> sessions = new HashMap<>();

    @Override
    public Collection<GameSessionDTO> findAll() {
        return sessions.values();
    }

    @Override
    public Optional<GameSessionDTO> findById(String id) {
        return Optional.ofNullable(sessions.get(id));
    }

    @Override
    public GameSessionDTO save(GameSessionDTO session) {
        sessions.put(session.getSessionId(), session);
        return session;
    }

    @Override
    public void delete(String id) {
        sessions.remove(id);
    }
}