package com.dinhanh.battleShipClient;

import java.util.Vector;

import com.dinhanh.battleship.response.ContentPackage;

public class GameClient implements Runnable {
	private int kindRequest;
	Vector<ContentPackage> outQueue;
	private boolean connected = false;
	SocketHandle handler;

	public GameClient() {
		kindRequest = Requests.RQ_UNVARIABLE;
		outQueue = new Vector<ContentPackage>();
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public void update() {
		if (isConnected()) {
			switch (kindRequest) {
			case Requests.RQ_UNVARIABLE:

				break;

			default:
				break;
			}
		}
	}

	public void setKindRequest(int kindRequest) {
		this.kindRequest = kindRequest;
	}

	public void rqConnect() {
		if (!isConnected()) {

		}
	}

	public void rqJoinRoom() {
		if (isConnected()) {
			String message = "";
			handler.sendMessage(message);
		}
	}

	public void rqGetTableInfor() {
		if (isConnected()) {
			String message = "";
			handler.sendMessage(message);
		}
	}

	public void rqGetGameState() {
		if (isConnected()) {
			String message = "";
			handler.sendMessage(message);
		}
	}

	// Game client co nhiem vu ghi du lieu va gui len server
	@Override
	public void run() {

	}

}
