package com.dinhanh.battleShip.packageMessage;

import java.io.Serializable;

public class PacketMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int clientID;
	public boolean isRelease = false;
	public boolean isFire = false;
	public int playerStateMove;
	public float posX;
	public float posY;
}
