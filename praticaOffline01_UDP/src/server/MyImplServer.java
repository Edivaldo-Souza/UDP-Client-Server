 package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import client.MyImplClient;

public class MyImplServer {
	DatagramSocket socket = null;
	private InetAddress host;
	private int port;
	
	public MyImplServer(int port,int nextPort) {
		this.port = port;
		this.run(port,nextPort);
	}
	
	public void run(int port,int nextPort) {
		try {
			
			System.out.println("Servidor executando na porta: "+port);
			
			byte[] receiveBuffer = new byte[1024];
			byte[] sendBuffer;
			String reply;
			String endPort;
			boolean pushFoward;
			String stringPort, leftBlock, msg, lastMsg;
			
			
			while(true) {
				socket = new DatagramSocket(port);
				pushFoward = true;
				DatagramPacket receivedPacket = new DatagramPacket(
						receiveBuffer,
						receiveBuffer.length);
				
				socket.receive(receivedPacket);
				receiveBuffer = receivedPacket.getData();
				
				reply = new String(receiveBuffer);
				
				if(reply.contains("ersf_")) {
					reply = reply.split("ersf_")[0]+"ersf"+reply.split("ersf_")[1];
					System.out.println("Mensagem enviada -> "+reply.split("ersf")[0]);
				}
				else {
					msg = "Mensagem recebida -> "+reply.split("ersf")[0];
					leftBlock = reply.split("ersf")[1].trim();
					endPort = leftBlock.substring(0, leftBlock.length()-1);
					stringPort = Integer.toString(port);
					char[] caracters= leftBlock.toCharArray();
					
					if(endPort.equals(stringPort)) {
						pushFoward = false;
						if(caracters[caracters.length-1]=='b')
							msg = "";
					}
					
					System.out.println(msg);
					
				}
				
				
				if(pushFoward) {
					sendBuffer = reply.getBytes();
						
					DatagramPacket sentPacket = new DatagramPacket(
								sendBuffer,
								sendBuffer.length,
								receivedPacket.getAddress(),
								nextPort);
						
					socket.send(sentPacket);
					
				}
				msg = "";
				reply = null;
				socket.close();
			}
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
