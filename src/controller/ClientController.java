package controller;

import java.io.*;
import java.net.*;

/*
 * Author: Anqi Li
 * Data: 3/6/2019
 * Title: Assignment2 Multithreaded Client/Server - Student Database
 * 
 * Description: Client Controller for handling the student number inputed from clients
 */

public class ClientController extends Thread {
	// The flag of closed socket
	private boolean toStop;

	// The socket the client is connected through 
	private Socket socket;

	// The input and output streams to the client 
	private DataOutputStream outputFromClient;
	private DataInputStream inputToClient;

	// Class constructor
	public ClientController() {
		// Initialize socket and input/output streams 
		try {
			socket = new Socket("localhost", 8000);
			outputFromClient = new DataOutputStream(socket.getOutputStream());
			inputToClient = new DataInputStream(socket.getInputStream());
			setToStop(false);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Start to send and receive result
	public String run(String str) {
		String result = null;

		// Send and receive information using stream
		try {
			outputFromClient.writeUTF(str);
			outputFromClient.flush();

			result = inputToClient.readUTF();

			if (result.equals("")) {
				closeSocket();
				setToStop(true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	// Close socket
	public void closeSocket() {
		try {
			socket.shutdownOutput();
			socket.shutdownInput();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setToStop(boolean toStop) {
		this.toStop = toStop;
	}

	public boolean getToStop() {
		return toStop;
	}
}
