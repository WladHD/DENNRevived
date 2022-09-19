package de.denn.server.cmd;

import com.github.diogoduailibe.lzstring4j.LZString;
import com.google.gson.Gson;

import de.denn.server.Connection;

public class Command {
	
	private String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public void send(Connection c) {
		c.sendMessage(LZString.compressToBase64(toJson()));
	}
}
