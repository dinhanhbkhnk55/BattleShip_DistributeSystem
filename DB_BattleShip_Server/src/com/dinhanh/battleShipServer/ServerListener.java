package com.dinhanh.battleShipServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import com.dinhanh.battleShip.packageMessage.PacketMessage;
import com.dinhanh.battleShip.packageMessage.PlayerAuthorizePack;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ServerListener extends Listener {
	ArrayList<InetSocketAddress> listSocketAdress = new ArrayList<InetSocketAddress>();
	InetSocketAddress socketAdress1, socketAdress2;

	Connection connection1, connection2;
	int count1 = 0, count2 = 0;
	PlayerAuthorizePack playerAuthorizePack = new PlayerAuthorizePack();;
	boolean isSetPlayer = false;
	ArrayList<PacketMessage> listPlayer = new ArrayList<PacketMessage>();

	public ServerListener() {
	}
	
	
	// This is run when a connection is received!
	public void connected(Connection c) {

		if (listSocketAdress.contains(c.getRemoteAddressTCP())) {
			System.out.println("Connection has been created");
		} else {
			listSocketAdress.add(c.getRemoteAddressTCP());
			System.out.println("New Client has been connected : "
					+ c.getRemoteAddressTCP());
			playerAuthorizePack.playerID = c.getID();
			playerAuthorizePack.listPlayer = listPlayer;
			// server gửi gói tin chấp nhận và định danh cho Server
			ServerProgram.server.sendToTCP(c.getID(), playerAuthorizePack);
		}
	}

	// This is run when we receive a packet.
	public void received(Connection c, Object p) {
		if (p instanceof PacketMessage) {
			// handle and process data here
			/*
			 * in present, we do nothing.mind
			 */
			// return to all client except
			// ServerProgram.server.sendToAllExceptTCP(c.getID(), p);
			updateListPlayer((PacketMessage) p);
			ServerProgram.server.sendToAllExceptTCP(c.getID(), p);
		}

	}

	private void updateListPlayer(PacketMessage packetMessage) {
		for (int i = 0; i < listPlayer.size(); i++) {
			if (listPlayer.get(i).clientID == packetMessage.clientID) {
				listPlayer.set(i, packetMessage);
				return;
			}
		}
		listPlayer.add(packetMessage);

	}

	// This is run when a client has disconnected.
	public void disconnected(Connection c) {
		System.out.println("A client disconnected!");
	}
}
