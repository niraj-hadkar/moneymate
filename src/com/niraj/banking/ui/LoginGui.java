package com.niraj.banking.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.niraj.banking.database.MyJDBC;
import com.niraj.banking.database.User;

/*
 * This class provides the Login GUI for the banking application.
 * Users can login, register, or access the 'Forgot Password' feature from here.
 * It extends BaseFrame, requiring the implementation of addGuiComponents().
 */

@SuppressWarnings("serial")
public class LoginGui extends BaseFrame {

	// Define commonly used colors for text and button styling
	private static final Color TEXT_COLOR = new Color(26, 17, 16);
	private static final Color BUTTON_BACKGROUND = TEXT_COLOR;
	private static final Color BUTTON_FOREGROUND = new Color(255, 250, 250);

	public LoginGui() {
		// Set up the GUI and specify the title of the window
		super("Banking App Login");
	}

	@Override
	protected void addGuiComponents() {
		// Add GUI components sequentially
		addLogo();
		addBankingAppLabel();
		JTextField usernameField = addLabelAndTextField("Username : ", 200);
		JPasswordField passwordField = addPasswordField("Password : ", 360);
		addForgotPasswordLabel();
		addLoginButton(usernameField, passwordField);
		addRegisterLabel();
	}

	// Adds the bank logo to the GUI
	private void addLogo() {
		JLabel bankImage = createScaledLabel(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\bank.png", 90);
		add(bankImage);
	}

	// Adds the "Banking Application" title label to the GUI
	private void addBankingAppLabel() {
		JLabel bankingAppLabel = new JLabel("Banking Application");
		bankingAppLabel.setBounds(0, 100, getWidth(), 40);
		bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));
		bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bankingAppLabel.setForeground(TEXT_COLOR);
		add(bankingAppLabel);
	}

	// Adds a label and text field for username input
	private JTextField addLabelAndTextField(String labelText, int yPosition) {
		ImageIcon icon = createScaledIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\"
						+ labelText.split(" : ")[0].toLowerCase() + ".png",
				24);
		JLabel label = new JLabel(labelText, icon, SwingConstants.LEFT);
		label.setBounds(20, yPosition, getWidth() - 30, 24);
		label.setFont(new Font("Dialog", Font.PLAIN, 20));
		label.setForeground(TEXT_COLOR);
		add(label);

		JTextField textField = new JTextField();
		textField.setBounds(20, yPosition + 40, getWidth() - 50, 40);
		textField.setFont(new Font("Dialog", Font.PLAIN, 28));
		textField.setForeground(TEXT_COLOR);
		add(textField);

		return textField;
	}

	// Adds a label and password field for password input
	private JPasswordField addPasswordField(String labelText, int yPosition) {
		ImageIcon icon = createScaledIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\password.png", 24);
		JLabel label = new JLabel(labelText, icon, SwingConstants.LEFT);
		label.setBounds(20, yPosition, getWidth() - 30, 24);
		label.setFont(new Font("Dialog", Font.PLAIN, 20));
		label.setForeground(TEXT_COLOR);
		add(label);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(20, yPosition + 40, getWidth() - 50, 40);
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
		passwordField.setForeground(TEXT_COLOR);
		add(passwordField);

		return passwordField;
	}

	// Adds a clickable "Forgot Password?" label with an event listener
	private void addForgotPasswordLabel() {
		JLabel forgotPasswordLabel = new JLabel("<html><a href=\"#\">Forgot Password?</a></html>");
		forgotPasswordLabel.setBounds(20, 450, getWidth() - 10, 30);
		forgotPasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		forgotPasswordLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Opens the Forgot Password GUI
				LoginGui.this.dispose();
				new ForgotPasswordGui().setVisible(true);
			}
		});
		add(forgotPasswordLabel);
	}

	// Adds the login button and its associated action listener
	private void addLoginButton(JTextField usernameField, JPasswordField passwordField) {
		ImageIcon icon = createScaledIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\login.png", 30);
		JButton loginButton = new JButton("Login", icon);
		loginButton.setBounds(20, 560, getWidth() - 50, 50);
		loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
		loginButton.setBackground(BUTTON_BACKGROUND);
		loginButton.setForeground(BUTTON_FOREGROUND);

		loginButton.addActionListener(e -> {
			// Capture the entered username and password for validation
			String username = usernameField.getText();
			String password = String.valueOf(passwordField.getPassword());

			// Validate user credentials with the database
			User user = MyJDBC.validateLogin(username, password);
			if (user != null) {
				LoginGui.this.dispose();
				new BankingAppGui(user).setVisible(true);
				JOptionPane.showMessageDialog(null, "Login Successfully!");
			} else {
				JOptionPane.showMessageDialog(LoginGui.this, "Invalid Username or Password!");
			}
		});
		add(loginButton);
	}

	// Adds a clickable "Register" label for new users
	private void addRegisterLabel() {
		JLabel registerLabel = new JLabel("<html><a href=\"#\">New User? Register Here</a></html>");
		registerLabel.setBounds(0, 610, getWidth() - 10, 30);
		registerLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Opens the Register GUI
				LoginGui.this.dispose();
				new RegisterGui().setVisible(true);
			}
		});
		add(registerLabel);
	}

	// Creates a scaled ImageIcon from a given path
	private ImageIcon createScaledIcon(String path, int size) {
		ImageIcon icon = new ImageIcon(path);
		Image scaledImage = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
	}

	// Creates a JLabel with a scaled image as an icon
	private JLabel createScaledLabel(String path, int size) {
		JLabel label = new JLabel();
		label.setIcon(createScaledIcon(path, size));
		label.setBounds(0, 0, getWidth() - 15, 100);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
}
