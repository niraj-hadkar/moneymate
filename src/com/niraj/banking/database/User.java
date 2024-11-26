package com.niraj.banking.database;

import java.math.*;

/*
 * User entity which is used to store user information (i.e. id, username, hashed password, and current balance)
 */

public class User {

    private final int id;
    private final String username;
    private final String hashedPassword; // Renamed to clarify it's hashed
    private BigDecimal currentBalance;

    public User(int id, String username, String hashedPassword, BigDecimal currentBalance) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword; // Store the hashed password
        this.currentBalance = currentBalance;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    // Removed direct access to password for better security
    public String getHashedPassword() {
        return hashedPassword; // Provide access to hashed password only
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal newBalance) {
        // Store new value to second decimal place
        currentBalance = newBalance.setScale(2, RoundingMode.FLOOR);
    }

    // Method to check if the provided password matches the hashed password
    public boolean validatePassword(String password) {
        String hashedInput = MyJDBC.hashPassword(password); // Hash the input password
        return hashedInput != null && hashedInput.equals(hashedPassword); // Compare hashes
    }
}

