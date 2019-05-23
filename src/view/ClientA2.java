package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Author: Anqi Li
 * Data: 3/6/2019
 * Title: Assignment2 Multithreaded Client/Server - Student Database
 * 
 * Description: Client control entry for creating multiple clients
 */

public class ClientA2 extends JFrame {
	// Label to display the title 
	private JLabel jlb = new JLabel("Enter to Client");

	// Button to open sub clients 
	private JButton jbt = new JButton("Add Client");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ClientA2();
	}

	// Class constructor
	public ClientA2() {
		setComponents();
		registerListener();
	}

	private void registerListener() {
		// Register listener 
		jbt.addActionListener(new Listener());
	}

	private void setComponents() {
		// Frame to hold the components 
		this.add(jlb);
		jlb.setBounds(150, 50, 200, 40);
		jlb.setForeground(Color.black);
		jlb.setFont(new Font("Dialog", 1, 25));

		this.add(jbt);
		jbt.setBounds(180, 130, 100, 50);
		jbt.setBackground(new Color(123, 104, 238));
		jbt.setForeground(Color.white);
		jbt.setFont(new Font("Dialog", 1, 12));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(500, 300);
		
		// Show the frame 
		this.setVisible(true);
	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new Client();
		}

	}
}
