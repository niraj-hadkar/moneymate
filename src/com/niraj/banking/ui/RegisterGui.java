package com.niraj.banking.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.niraj.banking.database.MyJDBC;

@SuppressWarnings("serial")
public class RegisterGui extends BaseFrame {

	// Define commonly used colors as constants for consistency across the UI
	private static final Color TEXT_COLOR = new Color(26, 17, 16);
	private static final Color BUTTON_BACKGROUND = TEXT_COLOR;
	private static final Color BUTTON_FOREGROUND = new Color(255, 250, 250);

	public RegisterGui() {
		// Set up GUI with a title and initialize components through BaseFrame
		super("Banking App Register");
	}

	@Override
	protected void addGuiComponents() {
		// Add all components such as logo, fields, buttons, and labels to the frame
		addLogo();
		addBankingAppLabel();
		JTextField usernameField = addLabelAndTextField("Username : ", 200);
		JPasswordField passwordField = addPasswordField("Password : ", 300);
		JPasswordField rePasswordField = addPasswordField("Re-type Password : ", 400);
		addRegisterButton(usernameField, passwordField, rePasswordField);
		addLoginLabel();
	}

	private void addLogo() {
		// Display a logo image at the top of the frame
		JLabel bankImage = createScaledLabel(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\bank.png", 90);
		add(bankImage);
	}

	private void addBankingAppLabel() {
		// Display the application name centered at the top
		JLabel bankingAppLabel = new JLabel("Banking Application");
		bankingAppLabel.setBounds(0, 100, getWidth(), 40);
		bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));
		bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bankingAppLabel.setForeground(TEXT_COLOR);
		add(bankingAppLabel);
	}

	private JTextField addLabelAndTextField(String labelText, int yPosition) {
		// Add a label and text field for the given label text at a specified position
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

	private JPasswordField addPasswordField(String labelText, int yPosition) {
		// Add a label and password field for secure password input
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

	private void addRegisterButton(JTextField usernameField, JPasswordField passwordField,
			JPasswordField rePasswordField) {
		// Add the Register button and handle user registration when clicked
		ImageIcon icon = createScaledIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\register.png", 30);
		JButton registerButton = new JButton("Register", icon);
		registerButton.setBounds(20, 560, getWidth() - 50, 50);
		registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
		registerButton.setBackground(BUTTON_BACKGROUND);
		registerButton.setForeground(BUTTON_FOREGROUND);

		registerButton.addActionListener(e -> {
			String username = usernameField.getText();
			String password = String.valueOf(passwordField.getPassword());
			String rePassword = String.valueOf(rePasswordField.getPassword());

			// Validate input and register the user in the database
			if (validateUserInput(username, password, rePassword)) {
				if (MyJDBC.register(username, password)) {
					RegisterGui.this.dispose();
					new LoginGui().setVisible(true);
					JOptionPane.showMessageDialog(null, "Registered Account Successfully!");
				} else {
					JOptionPane.showMessageDialog(RegisterGui.this, "Error: Username already exists", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(RegisterGui.this,
						"Error: Username must be at least 6 characters long\nand/or Password and Re-type Password must match",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(registerButton);
	}

	private void addLoginLabel() {
		// Add a clickable label to redirect users to the login screen if they already
		// have an account
		JLabel loginLabel = new JLabel("<html><a href=\"#\">Existing User? Sign-in Here</a></html>");
		loginLabel.setBounds(0, 610, getWidth() - 10, 30);
		loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				RegisterGui.this.dispose();
				new LoginGui().setVisible(true);
			}
		});
		add(loginLabel);
	}

	private ImageIcon createScaledIcon(String path, int size) {
		// Create and return a scaled icon from a specified image path
		ImageIcon icon = new ImageIcon(path);
		Image scaledImage = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
	}

	private JLabel createScaledLabel(String path, int size) {
		// Create and return a JLabel containing a scaled icon
		JLabel label = new JLabel();
		label.setIcon(createScaledIcon(path, size));
		label.setBounds(0, 0, getWidth() - 15, 100);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}

	private boolean validateUserInput(String username, String password, String rePassword) {
		// Ensure username meets length requirements and passwords match
		if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
			return false;
		}

		if (username.length() < 6) {
			return false;
		}

		return password.equals(rePassword);
	}
}
