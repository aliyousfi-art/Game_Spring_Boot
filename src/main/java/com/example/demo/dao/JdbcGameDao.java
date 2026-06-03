package com.example.demo.dao;

import com.example.demo.dto.GameSessionDTO;
import com.example.demo.dto.MoveDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcGameDao implements GameDao {

    private final NamedParameterJdbcTemplate template;
    private final ObjectMapper objectMapper;

    public JdbcGameDao(NamedParameterJdbcTemplate template, ObjectMapper objectMapper) {
        this.template = template;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        String ddl = "CREATE TABLE IF NOT EXISTS game_session ("
                + "id VARCHAR(64) PRIMARY KEY,"
                + "game_id VARCHAR(64),"
                + "player_count INT,"
                + "board_size INT,"
                + "state VARCHAR(32),"
                + "current_player VARCHAR(64),"
                + "board VARCHAR(4000),"
                + "remaining_tokens VARCHAR(4000),"
                + "removed_tokens VARCHAR(4000),"
                + "legal_moves VARCHAR(4000))";
        template.getJdbcTemplate().execute(ddl);
    }

    @Override
    public Collection<GameSessionDTO> findAll() {
        return template.query("SELECT * FROM game_session", this::mapRow);
    }

    @Override
    public Optional<GameSessionDTO> findById(String id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        List<GameSessionDTO> result = template.query(
                "SELECT * FROM game_session WHERE id = :id", params, this::mapRow);
        return result.stream().findFirst();
    }

    @Override
    public GameSessionDTO save(GameSessionDTO session) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", session.getSessionId())
                .addValue("gameId", session.getGameId())
                .addValue("playerCount", session.getPlayerCount())
                .addValue("boardSize", session.getBoardSize())
                .addValue("state", session.getState())
                .addValue("currentPlayer", session.getCurrentPlayer())
                .addValue("board", writeJson(session.getBoard()))
                .addValue("remainingTokens", writeJson(session.getRemainingTokens()))
                .addValue("removedTokens", writeJson(session.getRemovedTokens()))
                .addValue("legalMoves", writeJson(session.getLegalMoves()));
        template.update("DELETE FROM game_session WHERE id = :id",
                new MapSqlParameterSource("id", session.getSessionId()));
        template.update("INSERT INTO game_session "
                + "(id, game_id, player_count, board_size, state, current_player, board, remaining_tokens, removed_tokens, legal_moves) "
                + "VALUES (:id, :gameId, :playerCount, :boardSize, :state, :currentPlayer, :board, :remainingTokens, :removedTokens, :legalMoves)",
                params);
        return session;
    }

    @Override
    public void delete(String id) {
        template.update("DELETE FROM game_session WHERE id = :id",
                new MapSqlParameterSource("id", id));
    }

    private GameSessionDTO mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        GameSessionDTO session = new GameSessionDTO();
        session.setSessionId(rs.getString("id"));
        session.setGameId(rs.getString("game_id"));
        session.setPlayerCount(rs.getInt("player_count"));
        session.setBoardSize(rs.getInt("board_size"));
        session.setState(rs.getString("state"));
        session.setCurrentPlayer(rs.getString("current_player"));
        session.setBoard(readJson(rs.getString("board"), new TypeReference<String[][]>() {}));
        session.setRemainingTokens(readJson(rs.getString("remaining_tokens"), new TypeReference<Map<String, Integer>>() {}));
        session.setRemovedTokens(readJson(rs.getString("removed_tokens"), new TypeReference<Map<String, Integer>>() {}));
        session.setLegalMoves(readJson(rs.getString("legal_moves"), new TypeReference<List<MoveDTO>>() {}));
        return session;
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
