package view;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

import controller.JdbcOperation;

import java.util.Date;

//import controller.ServerA2Controller;
import model.Student;

public class ServerNoMVC extends JFrame {
	private int count;
	/** Text area for displaying contents */
	private JTextArea jta = new JTextArea();

//	/** Create a ServerA2Controller object */
//	private ServerA2Controller sac;

	public static void main(String[] args) {
		new ServerNoMVC();
	}

	public ServerNoMVC() {
		/** Place text area on the frame */
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);

		setTitle("Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); // It is necessary to show the frame here!
		try {
			ServerSocket serverSocket = new ServerSocket(8000);
			jta.append("Server started at " + new Date() + '\n');

			while (true) {
				Socket socket = serverSocket.accept();
				// Connect to a client Thread
				ThreadClass thread = new ThreadClass(socket);
				thread.start();
				count++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class ThreadClass extends Thread {
		private Socket socket;
		private InetAddress address;
		private DataInputStream inputFromClient;
		private DataOutputStream outputToClient;

		public ThreadClass(Socket socket) {
			this.socket = socket;
			address = socket.getInetAddress();

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
						socket.shutdownInput();
						socket.shutdownOutput();
						socket.close();
						result = result + "Socket is closed \n";
					}
					jta.append(result + '\n');
				}
			} catch (Exception e) {
				System.err.println(e + "on " + socket);
				e.printStackTrace();
			}
		}

	}
}
