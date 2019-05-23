package view;

import java.util.regex.Pattern;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.ClientController;

/*
 * Author: Anqi Li
 * Data: 3/6/2019
 * Title: Assignment2 Multithreaded Client/Server - Student Database
 * 
 * Description: Client for logging in a student account
 */

public class Client extends JFrame {

	// Text field to input student id
	private JTextField jtf = new JTextField();

	// Label to guide input
	private JLabel jlb = new JLabel("Enter Student ID: ");

	// Text area to display result
	private JTextArea jta = new JTextArea();

	// Button to send request
	private JButton jbt = new JButton("Login");

	// A ClientA2Controller object
	private ClientController cac;

	// Class constructor
	public Client() {
		setComponents();
		registerListener();

		// Initialize a ClientController object
		cac = new ClientController();
	}

	private void registerListener() {
		// Register listener
		jtf.addActionListener(new Listener());
		jbt.addActionListener(new Listener());
	}

	private void setComponents() {
		// Panel Field to hold the components for input data
		JPanel field = new JPanel();
		field.setLayout(new BorderLayout());
		field.add(jlb, BorderLayout.WEST);
		field.add(jtf, BorderLayout.CENTER);
		field.add(jbt, BorderLayout.EAST);
		jtf.setHorizontalAlignment(JTextField.RIGHT);
		
		setLayout(new BorderLayout());
		add(field, BorderLayout.NORTH);
		add(new JScrollPane(jta), BorderLayout.CENTER);
		setTitle("Client");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		// Show the frame
		setVisible(true);
	}

	private boolean idChecker(String stud_id) {
		// Input rule of text field
		Pattern id = Pattern.compile("[0-9]\\d*");

		// Check the input format of student number
		if (!(id.matcher(stud_id).matches())) {
			JOptionPane.showMessageDialog(null, "Please input a student id");
			return false;
		} else {
			return true;
		}
	}

	public class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Get the student number from the text field
			String stud_id = jtf.getText().trim();

			// Send and receive information from server
			if (idChecker(stud_id)) {
				if (!cac.getToStop()) {
					String name = cac.run(stud_id);
					if (!name.equals("")) {
						jta.append("Welcome " + name + ". You are now connected to the server.\n");
					} else {
						jta.append("Sorry " + stud_id + ", You are not a registered student.\n");
						jta.append("The socket is closed.\n");
					}
				}
			}
		}
	}
}
