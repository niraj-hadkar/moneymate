package com.niraj.banking.ui;

import javax.swing.*;
import com.niraj.banking.database.MyJDBC;
import com.niraj.banking.database.Transaction;
import com.niraj.banking.database.User;
import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.List;

/*
 * Displays a custom dialog for our BankingAppGui
 */

@SuppressWarnings("serial")
public class BankingAppDialog extends JDialog implements ActionListener {

	private User user;
	private BankingAppGui bankingAppGui;
	private JLabel balanceLabel, enterAmountLabel, enterUserLabel;
	private JTextField enterAmountField, enterUserField;
	private JButton actionButton;
	private JPanel pastTransactionPanel;
	private List<Transaction> pastTransactions;

	public BankingAppDialog(BankingAppGui bankingAppGui, User user) {
		// set the size
		setSize(400, 400);

		// add focus to the dialog (can't interact with anything else until the dialog
		// is closed)
		setModal(true);

		// loads in the center of our banking GUI
		setLocationRelativeTo(bankingAppGui);

		// when the user closes the dialog it releases its resources that are being used
		// by the dialog
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// prevents dialog from being resized
		setResizable(false);

		// allows us to manually specify the size and position of each component
		setLayout(null);

		// set background color for the dialog box (Medium Spring Green)
		getContentPane().setBackground(new Color(0, 250, 154));

		// we will need our reference to our GUI so that we can update the current
		// balance
		this.bankingAppGui = bankingAppGui;

		// we will need access to the user info to make updates to our database or
		// retrieve data about the user
		this.user = user;
	}

	public void addCurrentBalanceAndAmount() {
		// create balance label and insert current balance icon
		ImageIcon icon1 = new ImageIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\BankingApp\\icons\\current balance.png");
		Image icon2 = icon1.getImage().getScaledInstance(19, 19, Image.SCALE_SMOOTH);
		Icon balanceIcon = new ImageIcon(icon2);
		balanceLabel = new JLabel("Balance : Rs. " + user.getCurrentBalance(), balanceIcon, SwingConstants.CENTER);
		balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
		balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(balanceLabel);

		// create enter amount label and insert amount icon
		ImageIcon icon3 = new ImageIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\BankingApp\\icons\\amount.png");
		Image icon4 = icon3.getImage().getScaledInstance(19, 19, Image.SCALE_SMOOTH);
		Icon amountIcon = new ImageIcon(icon4);
		enterAmountLabel = new JLabel("Enter Amount : ", amountIcon, SwingConstants.CENTER);
		enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
		enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(enterAmountLabel);

		// create enter amount field
		enterAmountField = new JTextField();
		enterAmountField.setBounds(15, 80, getWidth() - 50, 40);
		enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
		enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
		add(enterAmountField);
	}

	public void addActionButton(String actionButtonType, Icon icontype) {
		actionButton = new JButton(actionButtonType, icontype);
		actionButton.setBounds(15, 300, getWidth() - 50, 40);
		actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
		actionButton.setBackground(new Color(191, 148, 228)); // // (Bright Lavender)
		actionButton.setForeground(new Color(26, 17, 16)); // (Licorice)
		actionButton.addActionListener(this);
		add(actionButton);
	}

	public void addUserField() {
		// create enter user label and insert user icon
		ImageIcon icon1 = new ImageIcon(
				"C:\\Users\\hp\\OneDrive\\Desktop\\Programs\\eclipse-workspace\\BankingApp\\icons\\username.png");
		Image icon2 = icon1.getImage().getScaledInstance(19, 19, Image.SCALE_SMOOTH);
		Icon userIcon = new ImageIcon(icon2);
		enterUserLabel = new JLabel("Enter User : ", userIcon, SwingConstants.CENTER);
		enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
		enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(enterUserLabel);

		// create enter user field
		enterUserField = new JTextField();
		enterUserField.setBounds(15, 190, getWidth() - 50, 40);
		enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
		enterUserField.setHorizontalAlignment(SwingConstants.CENTER);
		add(enterUserField);
	}

	public void addPastTransactionComponents() {
		// container where we will store each transaction
		pastTransactionPanel = new JPanel();

		// make layout 1x1
		pastTransactionPanel.setLayout(new BoxLayout(pastTransactionPanel, BoxLayout.Y_AXIS));

		// add scrollability to the container
		JScrollPane scrollPane = new JScrollPane(pastTransactionPanel);

		// displays the scroll only when it is required
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 15);

		// perform a database call to retrieve all of the past transactions and store
		// into array list
		pastTransactions = MyJDBC.getPastTransaction(user);

		// iterate through the array list and add to the GUI
		for (int i = 0; i < pastTransactions.size(); i++) {
			// store current transaction
			Transaction pastTransaction = pastTransactions.get(i);

			// create a container to store individual transaction
			JPanel pastTransactionContainer = new JPanel();
			pastTransactionContainer.setLayout(new BorderLayout());

			// create transaction type label
			JLabel transactionTypeLabel = new JLabel(pastTransaction.getTransactionType());
			transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 20));
			transactionTypeLabel.setForeground(new Color(240, 255, 240)); // (Honey Dew)

			// create transaction amount label
			JLabel transactionAmountLabel = new JLabel(String.valueOf(pastTransaction.getTransactionAmount()));
			transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 20));
			transactionAmountLabel.setForeground(new Color(240, 255, 240));

			// create transaction date label
			JLabel transactionDateLabel = new JLabel(String.valueOf(pastTransaction.getTransactionDate()));
			transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 20));
			transactionDateLabel.setForeground(new Color(240, 255, 240));

			// add to the container
			pastTransactionContainer.add(transactionTypeLabel, BorderLayout.WEST); // place this on the west side
			pastTransactionContainer.add(transactionAmountLabel, BorderLayout.EAST); // place this on the east side
			pastTransactionContainer.add(transactionDateLabel, BorderLayout.SOUTH); // place this on the south side

			// give a white background to each container
			pastTransactionContainer.setBackground(new Color(26, 17, 16)); // (Licorice)

			// give a black border to each transaction container
			pastTransactionContainer.setBorder(BorderFactory.createLineBorder(new Color(240, 255, 240))); // (Honey Dew)

			// add transaction component to the transaction panel
			pastTransactionPanel.add(pastTransactionContainer);
		}

		// add to the dialog
		add(scrollPane);
	}

	private void handleTransaction(String transactionType, float amountValue) {
		Transaction transaction;

		if (transactionType.equalsIgnoreCase("Deposit")) {
			// deposit transaction type
			// add to current balance
			user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountValue)));

			// create transaction
			// we leave date null because we are going to be using the NOW() in SQL which
			// will get the current date
			transaction = new Transaction(user.getId(), transactionType, new BigDecimal(amountValue), null);
		} else {
			// withdraw transaction type
			// subtract from current balance
			user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amountValue)));

			// we want to show a negative sign for the amount value when withdrawing
			transaction = new Transaction(user.getId(), transactionType, new BigDecimal(-amountValue), null);
		}

		// update database
		if (MyJDBC.addTransactionToDatabase(transaction) && MyJDBC.updateCurrentBalance(user)) {
			// show success dialog
			JOptionPane.showMessageDialog(this, transactionType + " Successful!");

			// reset the fields
			resetFieldsAndUpdateCurrentBalance();
		} else {
			// show failure dialog
			JOptionPane.showMessageDialog(this, transactionType + " Failed!");
		}
	}

	private void resetFieldsAndUpdateCurrentBalance() {
		// reset fields
		enterAmountField.setText("");

		// appears only when transfer is clicked
		if (enterUserField != null) {
			enterUserField.setText("");
		}

		// update current balance on dialog
		balanceLabel.setText("Balance : Rs. " + user.getCurrentBalance());

		// update current balance on main GUI
		bankingAppGui.getCurrentBalanceField().setText("Rs. " + user.getCurrentBalance());

	}

	private void handleTransfer(User user, String transferredUser, float amount) {
		// attempt to perform transfer
		if (MyJDBC.transfer(user, transferredUser, amount)) {
			// show success dialog
			JOptionPane.showMessageDialog(this, "Transfer Success!");
			resetFieldsAndUpdateCurrentBalance();
		} else {
			JOptionPane.showMessageDialog(this, "Transfer Failed!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String buttonPressed = ae.getActionCommand();

		// get amount value
		float amountValue = Float.parseFloat(enterAmountField.getText());

		// pressed deposit button
		if (buttonPressed.equalsIgnoreCase("Deposit")) {
			// we want to handle the deposit transaction
			handleTransaction(buttonPressed, amountValue);
		} else {
			// pressed withdraw or transfer

			// validate input by making sure that withdraw or transfer amount is less than
			// current balance
			// if the result is -1 the that the entered amount is more, 0 means they are
			// equal and 1 means that the entered amount is less
			int result = user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountValue));
			if (result < 0) {
				// display error dialog
				JOptionPane.showMessageDialog(this, "Error: Input value is more than current balance", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// check to see if withdraw or transfer was pressed
			if (buttonPressed.equalsIgnoreCase("Withdraw")) {
				handleTransaction(buttonPressed, amountValue);
			} else {
				// transfer
				String transferredUser = enterUserField.getText();

				// handle transfer
				handleTransfer(user, transferredUser, amountValue);
			}
		}
	}

}
