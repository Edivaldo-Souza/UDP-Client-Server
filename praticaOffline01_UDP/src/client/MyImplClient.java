package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import server.MyImplServer;

public class MyImplClient {
	DatagramSocket socket = null;
	Scanner input = new Scanner(System.in);
	
	int port;
	
	public MyImplClient(int port){
		this.port = port;
		this.run(port);
	}
	
	public void run(int port) {
		try {
			socket = new DatagramSocket();
			
			InetAddress address = InetAddress.getByName("localhost");
			
			System.out.println("Cliente de "+(port));
			
			byte[] inputBuffer;
			String msg, option, fullMsg;
			
			while(true) {
				socket = new DatagramSocket();
				System.out.println("Escolha uma opção:\n"
						+ "1 - Broadcast\n"
						+ "2 - Unicast");
								
				option = input.nextLine();
				input.nextLine();
				
				
				while(!option.equals("1") && !option.equals("2")) {
					System.out.println("Erro na entrada de\r\n"
							+ "dados. Tente outra vez!”:");
					option = input.nextLine();
					input.nextLine();
				}
				
				System.out.println("Digite uma mensagem: ");
				
				msg = input.nextLine();
				input.nextLine();
				
				if(option.equals("2")) {
					System.out.println("Informe o numero da porta do processo alvo: ");
					option = input.nextLine();
					input.nextLine();
					fullMsg = msg+"ersf_"+option+"u"; 
				}
				else {
					option = Integer.toString(port);
					fullMsg = msg+"ersf_"+option+"b";
				}
				
				
				inputBuffer = fullMsg.getBytes();
			
				DatagramPacket packet = new DatagramPacket(
					inputBuffer,
					inputBuffer.length,
					address,
					this.port);
			
				System.out.println("Mensagem enviada -> "+msg);
				socket.send(packet);
				
				msg = "";
				option = "";
				fullMsg = "";
				
				socket.close();
			}
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
