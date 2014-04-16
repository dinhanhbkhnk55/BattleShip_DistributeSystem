package com.dinhanh.battleShipServer;

import java.io.IOException;
import java.util.ArrayList;

import com.dinhanh.battleShip.packageMessage.PacketMessage;
import com.dinhanh.battleShip.packageMessage.PlayerAuthorizePack;
import com.esotericsoftware.kryonet.Server;

public class ServerProgram {
	ServerListener serverListener;
	// Server object
	public static Server server;
	// Ports to listen on
	static int udpPort = 27960, tcpPort = 1992;
	public ServerProgram() {
		super();
		System.out.println("Creating the server...");
		server = new Server();
		// Register a packet class.
		server.getKryo().register(PacketMessage.class);
		server.getKryo().register(PlayerAuthorizePack.class);
		server.getKryo().register(ArrayList.class);
		
//		try {
//			System.out.println(" Current IP : "+ InetAddress.getLocalHost());
//		} catch (UnknownHostException e1) {
//			System.out.println(" Loi Roi Nhe :( ");
//			e1.printStackTrace();
//		}
		
		try {
			// Bind to a port
			server.bind(tcpPort, udpPort);
			server.start();
		} catch (IOException e) {
			System.out.println("Exception fromm ServerProgram class");
			e.printStackTrace();
		}
	}

	public void addListener(ServerListener serverListener) {
		if (this.serverListener == null && serverListener != null) {
			this.serverListener = serverListener;
			server.addListener(serverListener);
		}
	}

	public void update() {
	}
}
