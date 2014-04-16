package com.dinhanh.battleShipClient;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class BattleShipClientDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "DB_BattleShipClient";
		cfg.width = 800;
		cfg.height = 480;

		new LwjglApplication(new BattleShipClient(), cfg);
	}
}
