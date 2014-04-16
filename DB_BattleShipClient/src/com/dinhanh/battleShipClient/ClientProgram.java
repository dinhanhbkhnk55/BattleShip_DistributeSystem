package com.dinhanh.battleShipClient;

import java.io.IOException;
import java.util.ArrayList;

import com.dinhanh.battleship.clientpack.PacketMessage;
import com.dinhanh.battleship.clientpack.PlayerAuthorizePack;
import com.esotericsoftware.kryonet.Client;

public class ClientProgram {
	public static final Client client = new Client();
	ClientListener clientListener;
//	static String ip = "localhost";
//	static String ip = "192.168.1.30";
	static String ip = "127.0.0.1";
	static int tcpPort = 1992, udpPort = 27960;
	public static int clientID = 0;
	public static boolean registered = false;
	public ClientProgram() throws IOException {
		super();

		client.getKryo().register(PacketMessage.class);
		client.getKryo().register(PlayerAuthorizePack.class);
		client.getKryo().register(ArrayList.class);

		// Start the client
		client.start();
		// The client MUST be started before connecting can take place.

		// Connect to the server - wait 5000ms before failing.
		// client.connect(5000, ip, tcpPort, udpPort);
		client.connect(5000, ip, tcpPort, udpPort);
	}

	public void addListener(ClientListener clientListener) {
		if (this.clientListener == null) {
			this.clientListener = clientListener;
			client.addListener(clientListener);
		}
	}

}
