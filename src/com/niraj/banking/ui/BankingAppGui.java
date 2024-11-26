package com.niraj.banking.ui;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import com.niraj.banking.database.User;

/*
 * BankingAppGui performs banking functions such as depositing, withdrawing,
 * viewing past transactions, and transferring. This class extends BaseFrame,
 * which means we need to define our own addGuiComponents() method.
 */

@SuppressWarnings("serial")
public class BankingAppGui extends BaseFrame implements ActionListener {

	private JTextField currentBalanceField;

	public JTextField getCurrentBalanceField() {
		return currentBalanceField;
	}

	public BankingAppGui(User user) {
		// setup our GUI and add a title
		super("Banking App", user);
	}

	@Override
	public void addGuiComponents() {
		// insert user image
		ImageIcon icon1 = new ImageIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\user.png");
		Image icon2 = icon1.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		Icon bankIcon = new ImageIcon(icon2);
		JLabel bankImage = new JLabel(bankIcon);
		bankImage.setBounds(0, 0, getWidth() - 15, 100);
		bankImage.setHorizontalAlignment(SwingConstants.CENTER);
		add(bankImage);

		// create welcome message
		String welcomeMessage = "<html>" + "<body style = 'text-align : center'>" + "<b>Hello " + user.getUsername()
				+ "</b><br>" + "What would you like to do today?</body></html>";
		JLabel welcomeMessageLabel = new JLabel(welcomeMessage);
		welcomeMessageLabel.setBounds(0, 100, getWidth() - 10, 40);
		welcomeMessageLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
		welcomeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeMessageLabel.setForeground(new Color(26, 17, 16));
		add(welcomeMessageLabel);

		// create current balance label and insert current balance icon
		ImageIcon icon3 = new ImageIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\current balance.png");
		Image icon4 = icon3.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
		Icon currentBalanceIcon = new ImageIcon(icon4);
		JLabel currentBalanceLabel = new JLabel("Current Balance : ", currentBalanceIcon, SwingConstants.CENTER);
		currentBalanceLabel.setBounds(0, 160, getWidth() - 10, 30);
		currentBalanceLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currentBalanceLabel.setForeground(new Color(26, 17, 16)); // (Licorice)
		add(currentBalanceLabel);

		// create current balance field
		currentBalanceField = new JTextField("Rs. " + user.getCurrentBalance());
		currentBalanceField.setBounds(15, 200, getWidth() - 50, 40);
		currentBalanceField.setFont(new Font("Dialog", Font.BOLD, 28));
		currentBalanceField.setHorizontalAlignment(SwingConstants.RIGHT);
		currentBalanceField.setForeground(new Color(26, 17, 16));
		currentBalanceField.setEditable(false);
		add(currentBalanceField);

		// create deposit button
		ImageIcon icon5 = new ImageIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\deposit.png");
		Image icon6 = icon5.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon depositIcon = new ImageIcon(icon6);
		JButton depositButton = new JButton("Deposit", depositIcon);
		depositButton.setBounds(15, 260, getWidth() - 50, 50);
		depositButton.setFont(new Font("Dialog", Font.BOLD, 20));
		depositButton.setBackground(new Color(191, 148, 228)); // (Bright Lavender)
		depositButton.setForeground(new Color(26, 17, 16));
		depositButton.addActionListener(this);
		add(depositButton);

		// create withdraw button
		ImageIcon icon7 = new ImageIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\withdraw.png");
		Image icon8 = icon7.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon withdrawIcon = new ImageIcon(icon8);
		JButton withdrawButton = new JButton("Withdraw", withdrawIcon);
		withdrawButton.setBounds(15, 330, getWidth() - 50, 50);
		withdrawButton.setFont(new Font("Dialog", Font.BOLD, 20));
		withdrawButton.setBackground(new Color(191, 148, 228));
		withdrawButton.setForeground(new Color(26, 17, 16));
		withdrawButton.addActionListener(this);
		add(withdrawButton);

		// create past transaction button
		ImageIcon icon9 = new ImageIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\past transaction.png");
		Image icon10 = icon9.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon pastTransactionIcon = new ImageIcon(icon10);
		JButton pastTransactionButton = new JButton("Past Transaction", pastTransactionIcon);
		pastTransactionButton.setBounds(15, 400, getWidth() - 50, 50);
		pastTransactionButton.setFont(new Font("Dialog", Font.BOLD, 20));
		pastTransactionButton.setBackground(new Color(191, 148, 228));
		pastTransactionButton.setForeground(new Color(26, 17, 16));
		pastTransactionButton.addActionListener(this);
		add(pastTransactionButton);

		// create transfer button
		ImageIcon icon11 = new ImageIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\transfer.png");
		Image icon12 = icon11.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon transferIcon = new ImageIcon(icon12);
		JButton transferButton = new JButton("Transfer", transferIcon);
		transferButton.setBounds(15, 470, getWidth() - 50, 50);
		transferButton.setFont(new Font("Dialog", Font.BOLD, 20));
		transferButton.setBackground(new Color(191, 148, 228));
		transferButton.setForeground(new Color(26, 17, 16));
		transferButton.addActionListener(this);
		add(transferButton);

		// create logout button
		ImageIcon icon13 = new ImageIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\logout.png");
		Image icon14 = icon13.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon logoutIcon = new ImageIcon(icon14);
		JButton logoutButton = new JButton("Logout", logoutIcon);
		logoutButton.setBounds(15, 600, getWidth() - 50, 50);
		logoutButton.setFont(new Font("Dialog", Font.BOLD, 20));
		logoutButton.setBackground(new Color(26, 17, 16));
		logoutButton.setForeground(new Color(255, 250, 250));
		logoutButton.addActionListener(this);
		add(logoutButton);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String buttonPressed = ae.getActionCommand();

		// user pressed logout
		if (buttonPressed.equalsIgnoreCase("Logout")) {
			// return user to the login GUI
			new LoginGui().setVisible(true);

			// dispose of this GUI
			this.dispose();

			return;
		}

		// other functions
		BankingAppDialog bankingAppDialog = new BankingAppDialog(this, user);

		// set the title of the dialog header to the action that was performed
		bankingAppDialog.setTitle(buttonPressed);

		// if the button pressed is a deposit, withdraw, or transfer
		if (buttonPressed.equalsIgnoreCase("Deposit") || buttonPressed.equalsIgnoreCase("Withdraw")
				|| buttonPressed.equalsIgnoreCase("Transfer")) {
			Icon icon = null;
			// add in the current balance and amount GUI components to the dialog
			bankingAppDialog.addCurrentBalanceAndAmount();

			// insert icon on the action button
			if (buttonPressed.equalsIgnoreCase("Deposit")) {
				ImageIcon imageIcon = new ImageIcon(
						"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\deposit.png");
				Image image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
			} else if (buttonPressed.equalsIgnoreCase("Withdraw")) {
				ImageIcon imageIcon = new ImageIcon(
						"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\withdraw.png");
				Image image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
			} else if (buttonPressed.equalsIgnoreCase("Transfer")) {
				ImageIcon imageIcon = new ImageIcon(
						"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\bankingapp\\icons\\transfer.png");
				Image image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
			}

			// add action button
			bankingAppDialog.addActionButton(buttonPressed, icon);

			// for the transfer action it will require more components
			if (buttonPressed.equalsIgnoreCase("Transfer")) {
				bankingAppDialog.addUserField();
			}
		}
		// if the button pressed is past transaction
		else if (buttonPressed.equalsIgnoreCase("Past Transaction")) {
			// Add past transaction GUI components to the dialog
			bankingAppDialog.addPastTransactionComponents();
		}

		// make the application dialog visible
		bankingAppDialog.setVisible(true);
	}

}
