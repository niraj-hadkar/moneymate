package com.niraj.banking.ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.niraj.banking.database.MyJDBC;

@SuppressWarnings("serial")
public class ForgotPasswordGui extends BaseFrame {

	private static final Color TEXT_COLOR = new Color(26, 17, 16);
	private static final Color BUTTON_BACKGROUND = TEXT_COLOR;
	private static final Color BUTTON_FOREGROUND = new Color(255, 250, 250);

	// Constructor sets up the frame with a title
	public ForgotPasswordGui() {
		super("Reset Password");
	}

	@Override
	public void addGuiComponents() {
		addLogo(); // Adds the logo image at the top of the GUI
		addResetPasswordLabel(); // Adds the "Reset Your Password" label
		JTextField usernameField = addLabelAndTextField("Username : ", 200); // Username input field
		JPasswordField newPasswordField = addPasswordField("New Password : ", 300); // New password field
		JPasswordField reNewPasswordField = addPasswordField("Re-Type New Password : ", 400); // Confirm password field
		addResetButton(usernameField, newPasswordField, reNewPasswordField); // Adds the reset button with action
																				// listener
		addCancelLabel(); // Adds a cancel link to navigate back to the login screen
	}

	private void addLogo() {
		// Adds a logo image at the top center of the GUI
		JLabel forgotPasswordImage = createScaledLabel(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\forgot password.png",
				90);
		add(forgotPasswordImage);
	}

	private void addResetPasswordLabel() {
		// Adds a label to prompt the user to reset their password
		JLabel resetPasswordLabel = new JLabel("Reset Your Password");
		resetPasswordLabel.setBounds(0, 100, getWidth(), 40);
		resetPasswordLabel.setFont(new Font("Dialog", Font.BOLD, 32));
		resetPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resetPasswordLabel.setForeground(TEXT_COLOR);
		add(resetPasswordLabel);
	}

	private JTextField addLabelAndTextField(String labelText, int yPosition) {
		// Adds a label with an associated text field for user input
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
		// Adds a label with an associated password field for user input
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

	private void addResetButton(JTextField usernameField, JPasswordField newPasswordField,
			JPasswordField reNewPasswordField) {
		// Adds the "Reset Password" button with an action listener for reset
		// functionality
		ImageIcon icon = createScaledIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\reset.png", 30);
		JButton resetButton = new JButton("Reset Password", icon);
		resetButton.setBounds(20, 560, getWidth() - 50, 50);
		resetButton.setFont(new Font("Dialog", Font.BOLD, 20));
		resetButton.setBackground(BUTTON_BACKGROUND);
		resetButton.setForeground(BUTTON_FOREGROUND);
		add(resetButton);

		resetButton.addActionListener(e -> {
			String username = usernameField.getText();
			String newPassword = String.valueOf(newPasswordField.getPassword());
			String reNewPassword = String.valueOf(reNewPasswordField.getPassword());

			// Validate input: check for empty fields and matching passwords
			if (username.isEmpty() || newPassword.isEmpty() || reNewPassword.isEmpty()) {
				JOptionPane.showMessageDialog(ForgotPasswordGui.this, "Please fill all fields.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!newPassword.equals(reNewPassword)) {
				JOptionPane.showMessageDialog(ForgotPasswordGui.this, "Passwords do not match.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Update password in the database and display success or failure message
			boolean success = MyJDBC.updatePassword(username, newPassword);
			if (success) {
				JOptionPane.showMessageDialog(ForgotPasswordGui.this, "Password reset successfully!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				ForgotPasswordGui.this.dispose();
				new LoginGui().setVisible(true);
			} else {
				JOptionPane.showMessageDialog(ForgotPasswordGui.this, "Failed to reset password. User may not exist.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void addCancelLabel() {
		// Adds a "Cancel" link, allowing users to return to the login GUI
		JLabel cancelLabel = new JLabel("<html><a href=\"#\">Cancel</a></html>");
		cancelLabel.setBounds(0, 610, getWidth() - 10, 30);
		cancelLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		cancelLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelLabel.setHorizontalAlignment(SwingConstants.CENTER);

		cancelLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				ForgotPasswordGui.this.dispose();
				new LoginGui().setVisible(true);
			}
		});
		add(cancelLabel);
	}

	private ImageIcon createScaledIcon(String path, int size) {
		// Creates an ImageIcon by scaling the image to the specified size
		ImageIcon icon = new ImageIcon(path);
		Image scaledImage = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
	}

	private JLabel createScaledLabel(String path, int size) {
		// Creates a JLabel with a scaled icon at the specified size
		JLabel label = new JLabel();
		label.setIcon(createScaledIcon(path, size));
		label.setBounds(0, 0, getWidth() - 15, 100);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
}
