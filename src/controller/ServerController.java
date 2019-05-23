package controller;

import java.io.*;
import java.net.*;
import java.util.Date;

import model.Student;
import view.MultiThreadedServerA2;

/*
 * Author: Anqi Li
 * Data: 3/6/2019
 * Title: Assignment2 Multithreaded Client/Server - Student Database
 * 
 * Description: Server Controller for handling multiple request from client
 */

public class ServerController {
	// The number of clients
	private int count;
	// The view of server
	private MultiThreadedServerA2 serverview;

	// Class constructor
	public ServerController(MultiThreadedServerA2 serverview) {
		this.serverview = serverview;
		try {
			ServerSocket serverSocket = new ServerSocket(8000);
			this.serverview.setJta("Server started at " + new Date() + '\n');
			while (true) {
				Socket socket = serverSocket.accept();
				// Connect to a client Thread
				ThreadClass thread = new ThreadClass(socket, this.serverview);
				thread.start();
//				count++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Client Thread
	private class ThreadClass extends Thread {
		private Socket socket;
		private InetAddress address;
		private DataInputStream inputFromClient;
		private DataOutputStream outputToClient;

		public ThreadClass(Socket socket, MultiThreadedServerA2 serverview) {
			this.socket = socket;
			address = socket.getInetAddress();
			count++;

			try {
				inputFromClient = new DataInputStream(socket.getInputStream());
				outputToClient = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				System.err.println("Exception in class");
				e.printStackTrace();
			}
		}

		public void run() {
			JdbcOperation jdbc = new JdbcOperation();
			String result = null;
			try {
				while (true) {
					result = "Client-" + count + ":" + '\n' + "IP Address: " + address.getHostAddress() + "  Host Name: "
							+ address.getHostName() + '\n';

					String stud_id = inputFromClient.readUTF();
					Student student = jdbc.searchStudent(stud_id);

					if (student.getSid() != null) {
						result = result + "Student " + stud_id + " connected \n";
						String fullName = student.getFname() + " " + student.getSname();

						outputToClient.writeUTF(fullName);
						outputToClient.flush();

					} else {
						outputToClient.writeUTF("");
						outputToClient.flush();
						result = result + "Student " + stud_id + " is not registered \n";
						Thread.interrupted();
						socket.shutdownInput();
						socket.shutdownOutput();
						socket.close();
						result = result + "Socket is closed \n";
					}
					serverview.setJta(result + '\n');
				}
			} catch (Exception e) {
				System.err.println(e + "on " + socket);
				e.printStackTrace();
			}
		}
	}
}
