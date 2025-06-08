package com.example.demo.dao;

import com.example.demo.dto.GameSessionDTO;

import java.util.Collection;
import java.util.Optional;

public interface GameDao {

    Collection<GameSessionDTO> findAll();

    Optional<GameSessionDTO> findById(String id);

    GameSessionDTO save(GameSessionDTO session);

    void delete(String id);

    Collection<GameSessionDTO> findByGameId(String gameId);
}