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
	
	public MyImplServer(String[] ports) {
		this.port = Integer.parseInt(ports[0]);
		this.run(ports);
	}
	
	public void run(String[] ports) {
		try {
			
			System.out.println("Servidor executando na porta: "+port);
			
			byte[] receiveBuffer = new byte[1024];
			byte[] sendBuffer;
			String reply;
			String endPort;
			boolean pushFoward;
			String stringPort;
			
			while(true) {
				socket = new DatagramSocket(port);
				pushFoward = true;
				DatagramPacket receivedPacket = new DatagramPacket(
						receiveBuffer,
						receiveBuffer.length);
				
				socket.receive(receivedPacket);
				receiveBuffer = receivedPacket.getData();
					
				reply = new String(receiveBuffer);
				String[] replyParts;
				
				if(reply.contains("ersf_")) {
			
					replyParts = reply.split("ersf_");
					reply = replyParts[0]+"ersf"+replyParts[1];
					
					System.out.println("Mensagem enviada -> "+reply.split("ersf")[0]);
					// Caso mensagem parta de P4
					if(ports.length>2) {
						String leftBlock = reply.split("ersf")[1].trim();
						endPort = leftBlock.substring(0, leftBlock.length()-1);
						stringPort = Integer.toString(port);
						char[] caracters= leftBlock.toCharArray();
						
						if(endPort.equals(stringPort) && caracters[caracters.length-1]=='+') {
							pushFoward = false;
						} 
						else if(!endPort.equals(stringPort) && caracters[caracters.length-1]=='+') {
							pushFoward = false;
						}
						else if(caracters[caracters.length-1]=='u') {
							caracters[caracters.length-1]='+';
							reply = reply.split("ersf")[0]+"ersf"+(new String(caracters));
						}
						else if(caracters[caracters.length-1]=='b') {
							caracters[caracters.length-1]='-';
							reply = reply.split("ersf")[0]+"ersf"+(new String(caracters));
						}
						else if(caracters[caracters.length-1]=='-') {
							pushFoward = false;
						}
					}
				}
				else if(reply.contains("ersf")){
				
					String leftBlock = reply.split("ersf")[1].trim();
					endPort = leftBlock.substring(0, leftBlock.length()-1);
					stringPort = Integer.toString(port);
					char[] caracters= leftBlock.toCharArray();
					
					if(endPort.equals(stringPort) && caracters[caracters.length-1]=='+') {
						pushFoward = false;
					
						System.out.println("Mensagem recebida -> "+reply.split("ersf")[0]);
					} 
					else if(!endPort.equals(stringPort) && caracters[caracters.length-1]=='+') {
						pushFoward = false;
					}
					else if(caracters[caracters.length-1]=='u') {
						System.out.println("Mensagem recebida -> "+reply.split("ersf")[0]);
						caracters[caracters.length-1]='+';
						reply = reply.split("ersf")[0]+"ersf"+(new String(caracters));
					}
					else if(caracters[caracters.length-1]=='b') {
						System.out.println("Mensagem recebida -> "+reply.split("ersf")[0]);
						caracters[caracters.length-1]='-';
						reply = reply.split("ersf")[0]+"ersf"+(new String(caracters));
					}
					else if(caracters[caracters.length-1]=='-') {
						pushFoward = false;
						
						System.out.println("Mensagem recebida -> "+reply.split("ersf")[0]);
					}
				}
				
				sendBuffer = reply.getBytes();
				if(pushFoward) {
					for(int i = 1; i<ports.length; i++) {
						int tempPort = Integer.parseInt(ports[i]);
						if(tempPort!=receivedPacket.getPort()) {
							DatagramPacket sentPacket = new DatagramPacket(
									sendBuffer,
									sendBuffer.length,
									receivedPacket.getAddress(),
									tempPort);
							
							socket.send(sentPacket);
						}
					}
				}
				
				
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
