package com.dinhanh.battleShipClient;

import com.dinhanh.battleship.clientpack.PacketMessage;
import com.dinhanh.battleship.clientpack.PlayerAuthorizePack;
import com.dinhanh.battleship.game.GameConfig;
import com.dinhanh.battleship.objects.EnemyContainer;
import com.dinhanh.myUtils.Debug;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {
	int count = 0;
	static boolean messageReceived = false;
	PacketMessage packetMessage;
	PlayerAuthorizePack playerAuthorizePack;

	public ClientListener() {
		super();
	}

	public void received(Connection c, Object p) {
		// Begin registered client when a authorize pack coming up
		if (p instanceof PlayerAuthorizePack) {
			if (!ClientProgram.registered) {
				playerAuthorizePack = (PlayerAuthorizePack) p;
				if (GameConfig.clientID == playerAuthorizePack.playerID) {
					Debug.d("Client registered! : " + GameConfig.clientID);
				}
				EnemyContainer.instance
						.updateEnemySpecfic(playerAuthorizePack.listPlayer);
				ClientProgram.registered = true;
			}

		}

		// Truong hop connected
		if (ClientProgram.registered) {
			if (p instanceof PacketMessage) {
				// handle and process package
				packetMessage = (PacketMessage) p;
				System.out.println(" recieve pack from client "
						+ packetMessage.clientID);
				EnemyContainer.instance.updateEnemySpecfic(packetMessage);

			}
		}
	}


	public void disconect(Connection connection) {
		System.out.println(" Client disconected ");
	}

}
