package de.denn.server;

import java.io.IOException;
import java.net.ServerSocket;

import de.denn.Main;

public class Server {

	private static Server inst;

	public static Server getInstance() {
		if (inst == null)
			inst = new Server();

		return inst;
	}

	private ServerSocket serverSocket;

	public boolean isRunning() {
		return serverSocket != null && !serverSocket.isClosed();
	}

	public boolean wasSetup() {
		return serverSocket != null;
	}

	public void start() throws IOException {
		if (serverSocket != null && !serverSocket.isClosed()) {
			System.out.println("Server ist bereits gestartet.");
			return;
		}

		serverSocket = new ServerSocket(Main.port);

		new Thread(() -> {
			try {
				System.out.println("Server wurde auf Port " + serverSocket.getLocalPort() + " gestartet.");
				while (!serverSocket.isClosed())
					new Connection(serverSocket.accept());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}).start();
	}

	public void stop() throws IOException {
		serverSocket.close();
	}
}
