package com.niraj.banking.database;

import java.sql.*;
import java.util.*;
import java.math.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyJDBC {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bankapp";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "niraj_123";

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: Password hashing algorithm not found.");
            return null;
        }
    }

    public static User validateLogin(String username, String password) {
        String hashedPassword = hashPassword(password);
        String query = "SELECT id, username, current_balance FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement validateLoginQuery = connection.prepareStatement(query)) {

            validateLoginQuery.setString(1, username);
            validateLoginQuery.setString(2, hashedPassword);

            try (ResultSet resultSet = validateLoginQuery.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    BigDecimal currentBalance = resultSet.getBigDecimal("current_balance");
                    return new User(userId, username, hashedPassword, currentBalance);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: Unable to validate login due to database connection issues.");
        }
        return null;
    }

    public static boolean updatePassword(String username, String newPassword) {
        String hashedPassword = hashPassword(newPassword);
        String query = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement updatePasswordQuery = connection.prepareStatement(query)) {

            updatePasswordQuery.setString(1, hashedPassword);
            updatePasswordQuery.setString(2, username);
            int rowsUpdated = updatePasswordQuery.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error: Failed to update password for username: " + username);
        }
        return false;
    }

    public static boolean register(String username, String password) {
        if (!checkUser(username)) {
            String hashedPassword = hashPassword(password);
            String query = "INSERT INTO users(username, password, current_balance) VALUES(?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 PreparedStatement registerQuery = connection.prepareStatement(query)) {

                registerQuery.setString(1, username);
                registerQuery.setString(2, hashedPassword);
                registerQuery.setBigDecimal(3, BigDecimal.ZERO);
                registerQuery.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.err.println("Error: Registration failed for username: " + username);
            }
        } else {
            System.err.println("Error: Username already exists: " + username);
        }
        return false;
    }

    private static boolean checkUser(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement checkUserQuery = connection.prepareStatement(query)) {

            checkUserQuery.setString(1, username);
            try (ResultSet resultSet = checkUserQuery.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: Failed to check if username exists: " + username);
        }
        return false;
    }

    public static boolean addTransactionToDatabase(Transaction transaction) {
        String query = "INSERT INTO transactions(user_id, transaction_type, transaction_amount, transaction_date) VALUES(?, ?, ?, NOW())";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement insertTransactionQuery = connection.prepareStatement(query)) {

            insertTransactionQuery.setInt(1, transaction.getUserId());
            insertTransactionQuery.setString(2, transaction.getTransactionType());
            insertTransactionQuery.setBigDecimal(3, transaction.getTransactionAmount());
            insertTransactionQuery.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error: Failed to record transaction for user ID: " + transaction.getUserId());
        }
        return false;
    }

    public static boolean updateCurrentBalance(User user) {
        String query = "UPDATE users SET current_balance = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement updateBalanceQuery = connection.prepareStatement(query)) {

            updateBalanceQuery.setBigDecimal(1, user.getCurrentBalance());
            updateBalanceQuery.setInt(2, user.getId());
            updateBalanceQuery.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error: Failed to update current balance for user ID: " + user.getId());
        }
        return false;
    }

    public static boolean transfer(User user, String transferredUsername, float transferAmount) {
        String queryUser = "SELECT id, username, current_balance FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement queryUserStmt = connection.prepareStatement(queryUser)) {

            queryUserStmt.setString(1, transferredUsername);
            try (ResultSet resultSet = queryUserStmt.executeQuery()) {
                if (resultSet.next()) {
                    User transferredUser = new User(resultSet.getInt("id"), transferredUsername,
                            resultSet.getString("password"), resultSet.getBigDecimal("current_balance"));

                    if (transferAmount <= user.getCurrentBalance().floatValue() && transferAmount > 0) {
                        Transaction transferTransaction = new Transaction(user.getId(), "Transfer", BigDecimal.valueOf(-transferAmount), null);
                        Transaction receivedTransaction = new Transaction(transferredUser.getId(), "Transfer", BigDecimal.valueOf(transferAmount), null);

                        transferredUser.setCurrentBalance(transferredUser.getCurrentBalance().add(BigDecimal.valueOf(transferAmount)));
                        user.setCurrentBalance(user.getCurrentBalance().subtract(BigDecimal.valueOf(transferAmount)));

                        connection.setAutoCommit(false);
                        try {
                            updateCurrentBalance(transferredUser);
                            updateCurrentBalance(user);
                            addTransactionToDatabase(transferTransaction);
                            addTransactionToDatabase(receivedTransaction);
                            connection.commit();
                        } catch (SQLException e) {
                            connection.rollback();
                            System.err.println("Error: Transfer failed. Transaction rolled back.");
                            return false;
                        }
                        return true;
                    } else {
                        System.err.println("Error: Invalid transfer amount.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: Unable to complete transfer due to database connection issues.");
        }
        return false;
    }

    public static List<Transaction> getPastTransaction(User user) {
        List<Transaction> pastTransactions = new ArrayList<>();
        String query = "SELECT transaction_type, transaction_amount, transaction_date FROM transactions WHERE user_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement selectAllTransactionQuery = connection.prepareStatement(query)) {

            selectAllTransactionQuery.setInt(1, user.getId());
            try (ResultSet resultSet = selectAllTransactionQuery.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = new Transaction(user.getId(), resultSet.getString("transaction_type"),
                            resultSet.getBigDecimal("transaction_amount"), resultSet.getDate("transaction_date"));
                    pastTransactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: Failed to retrieve transactions for user ID: " + user.getId());
        }
        return pastTransactions;
    }
}
