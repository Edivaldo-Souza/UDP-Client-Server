package main;

import client.MyImplClient;
import client.ThreadClient;
import server.MyImplServer;
import server.ThreadServer;

public class Process {
	public static void main(String[] args) {
		ThreadClient c = new ThreadClient(Integer.parseInt(args[0])+1);
		ThreadServer s = new ThreadServer(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		Thread t0 = new Thread(c);
		Thread t1 = new Thread(s);
		t0.start();
		t1.start();
		
	}
}
