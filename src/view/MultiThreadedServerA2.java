package view;

import java.awt.*;
import javax.swing.*;

import controller.ServerController;

/*
 * Author: Anqi Li
 * Data: 3/6/2019
 * Title: Assignment2 Multithreaded Client/Server - Student Database
 * 
 * Description: Server for showing the information of login
 */

public class MultiThreadedServerA2 extends JFrame{
	// Text area for displaying contents 
	private JTextArea jta = new JTextArea();
	
	// Class constructor
	public MultiThreadedServerA2() {
		setComponents(); 
	}

	private void setComponents() {
		// Place text area on the frame 
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);

		setTitle("Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Show the frame 
		setVisible(true);
	}
	
	// Add the content of Text area
	public void setJta(String info) {
		this.jta.append(info);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MultiThreadedServerA2 serverA2 = new MultiThreadedServerA2();
		ServerController sc = new ServerController(serverA2);
	}

}
