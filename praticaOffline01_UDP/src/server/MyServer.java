package server;

public class MyServer {
	public static void main(String[] args) {
		new MyImplServer(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
	}
}
