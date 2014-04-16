package com.dinhanh.battleShipClient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.dinhanh.battleship.assets.Assets;
import com.dinhanh.battleship.screens.MenuScreen;
import com.dinhanh.myUtils.DirectedGame;

public class BattleShipClient extends DirectedGame {
	@Override
	public void create() {
		Assets.instance.init(new AssetManager());
		Gdx.input.setCatchBackKey(true);
		setScreen(new MenuScreen(this));
	}
}
