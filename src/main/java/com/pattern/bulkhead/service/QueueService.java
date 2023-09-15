package com.pattern.bulkhead.service;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.concurrent.Semaphore;

@Service
public class QueueService {
    private static final String DB_URL = "jdbc:sqlite:queue.db";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS messages (id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT)";
    private static final int MAX_DB_CONNECTIONS = 5;
    private final Semaphore connectionSemaphore = new Semaphore(MAX_DB_CONNECTIONS, true);

    public QueueService() {
        initializeDatabase();
    }
    private void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement createTableStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
                createTableStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void enqueue(String message) throws InterruptedException {
        try {
            connectionSemaphore.acquire();
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String insertSql = "INSERT INTO messages (message) VALUES (?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                    preparedStatement.setString(1, message);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectionSemaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String dequeue() throws InterruptedException {
        try {
            connectionSemaphore.acquire();
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String selectSql = "SELECT * FROM messages";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            String message = resultSet.getString("message");
                            String deleteSql = "DELETE FROM messages WHERE id = ?";
                            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                                deleteStatement.setInt(1, resultSet.getInt("id"));
                                deleteStatement.executeUpdate();
                            }
                            return message;
                        } else {
                            return "No messages in the queue";
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "Error reading from the queue";
            } finally {
                connectionSemaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Interrupted";
        }
    }
}