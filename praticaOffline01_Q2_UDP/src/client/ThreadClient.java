package client;

public class ThreadClient implements Runnable{
	int port;
	
	public ThreadClient(int port){
		this.port = port;
	}
	
	@Override
	public void run() {
		new MyImplClient(this.port+1);
	}
	
}
