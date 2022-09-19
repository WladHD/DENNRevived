package de.denn.server.sockets;

import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;

import de.denn.server.Connection;

public interface SocketConnectionHelper {

	default void startSocketListening(Connection connection) {
		new Thread(() -> {
			while (connection.getSocket().isConnected()) {
				try {
					String cmd = (connection.usingWebSocket()
							? connection.getScanner().useDelimiter("\\r\\n\\r\\n").next()
							: connection.getScanner().nextLine());

					if (cmd != null && cmd.length() == 0)
						continue;

					if (connection.usingWebSocket() && Pattern.compile("^GET").matcher(cmd).find()) {
						if (isWebSocket(cmd, connection.getSocket())) {
							System.out.println("WebSocket Client wurde akzeptiert.");
						} else {
							System.out.println("WebSocket Client konnte nicht akzeptiert werden.");
							break;
						}

						startWebSocketListening(connection);
						return;
					}

					if (connection.usingWebSocket())
						connection.setUsingWebsocket(false);

					if (cmd == null)
						break;

					connection.processCommand(cmd);
				} catch (Exception e) {
					break;
				}
			}
			
			connection.resetVars();
			
			System.out.println("Verbindung zum Socket wurde geschlossen.");
		}).start();
		
		
	}

	default boolean isWebSocket(String s, Socket connection) {
		try {
			Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(s);
			match.find();
			sendSocketMessage("HTTP/1.1 101 Switching Protocols\r\n" + "Connection: Upgrade\r\n" + "Upgrade: websocket\r\n"
					+ "Sec-WebSocket-Accept: "
					+ DatatypeConverter.printBase64Binary(MessageDigest.getInstance("SHA-1")
							.digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes("UTF-8")))
					+ "\r\n\r\n", connection);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	default void sendSocketMessage(String msg, Socket connection) {
		if (!connection.isConnected())
			return;
		try {
			connection.getOutputStream().write(msg.getBytes("UTF-8"), 0, msg.getBytes("UTF-8").length);
			connection.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Source von
	// https://stackoverflow.com/questions/8125507/how-can-i-send-and-receive-websocket-messages-on-the-server-side/12471677#12471677
	default void startWebSocketListening(Connection connection) throws IOException {
		int len = 0;
		byte[] b = new byte[127];

		while (connection.getSocket().isConnected()) {
			try {
				len = connection.getSocket().getInputStream().read(b);
			} catch (IOException e) {
				e.printStackTrace();
				connection.getSocket().close();
				break;
			}

			if (len != -1) {
				byte rLength = 0;
				int rMaskIndex = 2;
				int rDataStart = 0;
				// b[0] is always text in my case so no need to check;
				byte data = b[1];
				byte op = (byte) 127;
				rLength = (byte) (data & op);

				if (rLength == (byte) 126)
					rMaskIndex = 4;
				if (rLength == (byte) 127)
					rMaskIndex = 10;

				byte[] masks = new byte[4];

				int j = 0;
				int i = 0;
				for (i = rMaskIndex; i < (rMaskIndex + 4); i++) {
					masks[j] = b[i];
					j++;
				}

				rDataStart = rMaskIndex + 4;

				int messLen = len - rDataStart;

				byte[] message = new byte[messLen];

				for (i = rDataStart, j = 0; i < len; i++, j++) {
					message[j] = (byte) (b[i] ^ masks[j % 4]);
				}

				b = new byte[127];

				String m = new String(message);

				if (m.equalsIgnoreCase("é"))
					break;

				connection.processCommand(m);
			}
		}
		
		connection.resetVars();
		System.out.println("Verbindung zum WebSocket wurde geschlossen.");
	}

}
