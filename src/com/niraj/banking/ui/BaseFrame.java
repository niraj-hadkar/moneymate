package com.niraj.banking.ui;

import java.awt.*;
import javax.swing.*;

import com.niraj.banking.database.User;

/*
 * Create an abstract class to help to setup the blueprint that GUIs will follow, for example
 * in each of the GUIs, they will have the same size and will need to invoke their addGuiComponents()
 * which will be unique to each subclass
 */

@SuppressWarnings("serial")
public abstract class BaseFrame extends JFrame {
	
	// store user information
	protected User user;
	
	public BaseFrame(String title) {
		initialize(title);
	}
	
	public BaseFrame(String title, User user) {
		// initialize user
		this.user = user;
		
		initialize(title);
	}
	
	private void initialize(String title) {
		// set background color for the frame (Medium Spring Green)
		getContentPane().setBackground(new Color(0,250,154));
		
		// instantiate JFrame properties and add a title to the bar
		setTitle(title);
		
		// set the size of our GUI (in pixels)
		setSize(420, 700);
		
		// set the layout manager of the frame to null, allowing manual positioning of components
		setLayout(null);
		
		// disabling resizing of the frame by the user
		setResizable(false);
		
		// set the frame to be centered on the screen when displayed
		setLocationRelativeTo(null);
		
		// call on the subclass' addGuiComponent()
		addGuiComponents();
		
		// set the default close operation of the frame to exit after the application has been closed
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	// this method will need to be defined by subclasses when this class is being inherited from
	protected abstract void addGuiComponents();
	
}
