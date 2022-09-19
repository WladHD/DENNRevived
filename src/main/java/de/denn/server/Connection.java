package de.denn.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;

import de.denn.errors.ClientMovedOnException;
import de.denn.graph.DENNAlgorithm;
import de.denn.graph.startnode.DENNStartNodeSelectType;
import de.denn.server.cmd.in.CommandInSelectProblem;
import de.denn.server.cmd.out.CommandOutAvailableProblems;
import de.denn.server.cmd.out.CommandOutDENNTour;
import de.denn.server.cmd.out.CommandOutNodeCoordsHelper;
import de.denn.server.sockets.SocketConnectionHelper;
import de.denn.server.sockets.WebSocketCommunicationHelper;

public class Connection implements SocketConnectionHelper, WebSocketCommunicationHelper {
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private boolean usingWebSocket = true;

	private DENNAlgorithm alg;

	public Connection(Socket socket) throws UnsupportedEncodingException, IOException {
		this.socket = socket;
		in = new Scanner(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		out = new PrintWriter(socket.getOutputStream(), true);

		startSocketListening(this);
	}

	public void resetVars() {
		socket = null;
		in = null;
		out = null;
		alg = null;
	}

	public Socket getSocket() {
		return socket;
	}

	public Scanner getScanner() {
		return in;
	}

	public PrintWriter getPrintWriter() {
		return out;
	}

	public boolean usingWebSocket() {
		return usingWebSocket;
	}

	public void setUsingWebsocket(boolean usingWebSocket) {
		this.usingWebSocket = usingWebSocket;
	}

	public void processCommand(String cmd) {
		System.out.println("Received '" + cmd + "'");

		String[] arg = cmd.split(" : ");

		switch (arg[0]) {
		case "AvailableProblems":
			new CommandOutAvailableProblems().send(this);
			break;
		case "SelectProblem":
			if (CommandInSelectProblem.process(this, arg[1]))
				CommandOutNodeCoordsHelper.send(this, alg.getNodeInterfaces());
			break;
		case "RequestTour":
			new Thread(() -> {
				try {
					System.out.println(arg.length);
					
					if(arg.length == 3) {
						new CommandOutDENNTour(alg.getTourById(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]))).send(this);
					} else {
						new CommandOutDENNTour(alg.getTourByPreference(alg.getRawData().getDimension() <= 120 ? DENNStartNodeSelectType.CALC_ALL : DENNStartNodeSelectType.FURTHEST_FROM_ALL)).send(this);
					}
				} catch (ClientMovedOnException e) {
					System.out.println("Berechnung wegen Verbindungsabbruchs gestoppt.");
				}
			}).start();
			break;

		default:
			System.out.println("??");
		}
	}

	public void sendMessage(String message) {
		if (socket.isClosed())
			return;

		if (usingWebSocket())
			sendToWebSocket(message, socket);
		else
			getPrintWriter().println(message);
	}

	public DENNAlgorithm getDENNAlgorithm() {
		return alg;
	}

	public void setDENNAlgorithm(DENNAlgorithm alg) {
		this.alg = alg;
	}
}
