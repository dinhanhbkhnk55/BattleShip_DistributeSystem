package com.dinhanh.battleShipClient;

import java.io.IOException;
import java.util.ArrayList;

import com.dinhanh.battleship.clientpack.PacketMessage;
import com.dinhanh.battleship.clientpack.PlayerAuthorizePack;
import com.dinhanh.battleship.game.GameConfig;
import com.esotericsoftware.kryonet.Client;

public class SocketHandle {
	private Client client;
	private ClientListener clientListener;

	public SocketHandle() {
	}
	public void addListener(ClientListener clientListener) {
		if (this.clientListener == null) {
			this.clientListener = clientListener;
			client.addListener(clientListener);
		}
	}
	public void connect(String ip, int tcpPort, int udpPort) {
		if (!client.isConnected()) {
			try {

				client.getKryo().register(PacketMessage.class);
				client.getKryo().register(PlayerAuthorizePack.class);
				client.getKryo().register(ArrayList.class);
				client.start();
				client.connect(5000, ip, tcpPort, udpPort);
				GameConfig.clientID = client.getID();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void sendMessage(String request) {
		if (client.isConnected()) {
			client.sendTCP(request);

		}
	}

}
