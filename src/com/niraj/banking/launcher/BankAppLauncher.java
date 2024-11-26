package com.niraj.banking.launcher;

/**
 * BankAppLauncher
 * 
 * This is the main launcher class for the banking application. 
 * It ensures the application initializes and opens the login GUI
 * on the Event Dispatch Thread (EDT) for thread safety.
 * 
 * Author: Niraj Hadkar
 * Date: Oct 03, 2024
 * Package: com.niraj.banking.launcher
 */

import javax.swing.*;

import com.niraj.banking.ui.LoginGui;

public class BankAppLauncher {

    public static void main(String[] args) {
        // Ensures that all Swing GUI-related tasks are executed on the Event Dispatch Thread (EDT)
        // to maintain thread safety in the GUI.
        SwingUtilities.invokeLater(BankAppLauncher::launchLoginGui);
    }

    /**
     * Launches the Login GUI.
     * Creates an instance of LoginGui and makes it visible.
     * This method is called on the EDT to safely initialize GUI components.
     */
    private static void launchLoginGui() {
        new LoginGui().setVisible(true);
    }
}
