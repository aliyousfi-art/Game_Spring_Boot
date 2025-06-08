package com.example.demo.dao;

import com.example.demo.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameEntityRepository extends JpaRepository<GameEntity, String> {
    List<GameEntity> findByGameId(String gameId);
}
