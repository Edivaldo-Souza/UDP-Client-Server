package server;

public class ThreadServer implements Runnable{
	int port;
	int nextPort;
	
	public ThreadServer(int port, int nextPort){
		this.port = port;
		this.nextPort = nextPort;
	}

	@Override
	public void run() {
		new MyImplServer(this.port,this.nextPort);
	}

	
}
