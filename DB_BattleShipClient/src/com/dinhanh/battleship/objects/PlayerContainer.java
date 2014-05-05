package com.dinhanh.battleship.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerContainer {
	public Player myPlayer;// Chứa thông tin người chơi của mình và
							// các người chơi khác.
	public ArrayList<Player> listOtherPlayer = new ArrayList<Player>();

	public PlayerContainer() {

//		listOtherPlayer.add(new Player(Player.TYPE_AUTOMOVE_PLAYER));
//		listOtherPlayer.add(new Player(Player.TYPE_AUTOMOVE_PLAYER));
//		listOtherPlayer.add(new Player(Player.TYPE_AUTOMOVE_PLAYER));
	}

	public void update(float deltaTime) {
		if (myPlayer != null) {
			myPlayer.update(deltaTime);
		}

		for (int i = 0; i < listOtherPlayer.size(); i++) {
			if (!listOtherPlayer.get(i).equals(null)) {
				listOtherPlayer.get(i).update(deltaTime);
			}
		}

	}

	public void render(float deltaTime, OrthographicCamera camera,
			SpriteBatch batch) {
		if (!myPlayer.equals(null)) {
			myPlayer.render(deltaTime, camera, batch);

		}
		for (int i = 0; i < listOtherPlayer.size(); i++) {
			if (!listOtherPlayer.get(i).equals(null)) {
				listOtherPlayer.get(i).render(deltaTime, camera, batch);
			}
		}
	}

	public void setPlayer(Player player) {
		this.myPlayer = player;
	}

}
