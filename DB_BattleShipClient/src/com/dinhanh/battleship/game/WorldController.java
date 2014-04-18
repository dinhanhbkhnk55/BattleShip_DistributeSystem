package com.dinhanh.battleship.game;

import com.badlogic.gdx.math.Vector2;
import com.dinhanh.battleship.objects.BulletContainer;
import com.dinhanh.battleship.objects.EnemyContainer;
import com.dinhanh.battleship.objects.Player;
import com.dinhanh.battleship.objects.PlayerContainer;

public class WorldController {
	public Player player;
//	PlayerContainer playerContainer ;

	public WorldController() {
		init();
	}

	private void init() {
		player = new Player(Player.TYPE_MYPLAYER);
		player.setPlayer(1, new Vector2(100, 100), Player.TYPE_MYPLAYER);
		EnemyContainer.instance.setPlayer(player);
//		playerContainer = new PlayerContainer();
//		playerContainer.setPlayer(player);

	}

	public void update(float deltaTime) {
		BulletContainer.instance.update(deltaTime);
//		playerContainer.update(deltaTime);
		BulletContainer.instance.collisionWithListPlayer();

	}
}
