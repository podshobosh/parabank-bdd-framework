package db.core;

import utils.DatabaseUtils;
import utils.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTemplate {
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bindParameters(statement, parameters);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(rowMapper.map(resultSet));
                }
                Log.info("JDBC query returned " + results.size() + " row(s)");
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException("JDBC query failed. SQL=" + sql, e);
        }
    }

    public <T> Optional<T> queryForObject(String sql, RowMapper<T> rowMapper, Object... parameters) {
        List<T> rows = query(sql, rowMapper, parameters);
        if (rows.isEmpty()) {
            return Optional.empty();
        }
        if (rows.size() > 1) {
            throw new IllegalStateException("Expected one row but got " + rows.size());
        }
        return Optional.of(rows.get(0));
    }

    private void bindParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }
}
