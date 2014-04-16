package com.dinhanh.battleShipServer;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class BattleShipServerDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "DB_BattleShip_Server";
		cfg.width = 800;
		cfg.height = 480;
		new LwjglApplication(new BattleShipServer(), cfg);
	}
}
