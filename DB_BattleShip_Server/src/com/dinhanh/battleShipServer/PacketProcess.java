package com.dinhanh.battleShipServer;

import com.esotericsoftware.kryonet.Connection;

public class PacketProcess {
	public Connection c;
	public Object p;

	public PacketProcess(Connection c, Object p) {
		this.c = c;
		this.p = p;
	}
}
