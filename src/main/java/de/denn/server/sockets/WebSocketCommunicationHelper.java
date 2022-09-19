package de.denn.server.sockets;

import java.io.IOException;
import java.net.Socket;

public interface WebSocketCommunicationHelper {

	// Source von
	// https://stackoverflow.com/questions/8125507/how-can-i-send-and-receive-websocket-messages-on-the-server-side/12471677#12471677
	default void sendToWebSocket(String message, Socket con) {
		byte[] rawData = message.getBytes();

		int frameCount = 0;
		byte[] frame = new byte[10];

		frame[0] = (byte) 129;

		if (rawData.length <= 125) {
			frame[1] = (byte) rawData.length;
			frameCount = 2;
		} else if (rawData.length >= 126 && rawData.length <= 65535) {
			frame[1] = (byte) 126;
			int len = rawData.length;
			frame[2] = (byte) ((len >> 8) & (byte) 255);
			frame[3] = (byte) (len & (byte) 255);
			frameCount = 4;
		} else {
			frame[1] = (byte) 127;
			int len = rawData.length;
			frame[2] = (byte) ((len >> 56) & (byte) 255);
			frame[3] = (byte) ((len >> 48) & (byte) 255);
			frame[4] = (byte) ((len >> 40) & (byte) 255);
			frame[5] = (byte) ((len >> 32) & (byte) 255);
			frame[6] = (byte) ((len >> 24) & (byte) 255);
			frame[7] = (byte) ((len >> 16) & (byte) 255);
			frame[8] = (byte) ((len >> 8) & (byte) 255);
			frame[9] = (byte) (len & (byte) 255);
			frameCount = 10;
		}

		byte[] reply = new byte[frameCount + rawData.length];

		int bLim = 0;

		for (int i = 0; i < frameCount; i++) {
			reply[bLim] = frame[i];
			bLim++;
		}

		for (int i = 0; i < rawData.length; i++) {
			reply[bLim] = rawData[i];
			bLim++;
		}

		try {
			if (con == null || con.isClosed())
				return;

			con.getOutputStream().write(reply);
			con.getOutputStream().flush();
		} catch (IOException e) {
		}
	}

}
