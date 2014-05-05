package com.dinhanh.battleship.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dinhanh.battleship.clientpack.PacketMessage;
import com.dinhanh.battleship.game.GameConfig;
import com.dinhanh.battleship.utils.State;
import com.dinhanh.myUtils.Debug;
import com.dinhanh.myUtils.OverlapTester;

public class EnemyContainer {
	public static final EnemyContainer instance = new EnemyContainer();
	public ArrayList<Player> listEnemy = new ArrayList<Player>();
	public Player player;

	private EnemyContainer() {
		// Create several autoPlayer
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	Player object;

	public void update(float deltaTime) {
		if (player != null) {
			player.update(deltaTime);
		}
		for (int i = 0; i < listEnemy.size(); i++) {
			object = listEnemy.get(i);
			if (object.getState() == State.RUNNING) {
				object.update(deltaTime);
			} else if (object.getState() == State.DISMISS) {
				listEnemy.remove(i);
			}
		}

		checkCollision(player);
	}

	public void render(float deltaTime, OrthographicCamera camera,
			SpriteBatch batch) {
		update(deltaTime);
		for (int i = 0; i < listEnemy.size(); i++) {
			if (!listEnemy.get(i).equals(null))
				listEnemy.get(i).render(deltaTime, camera, batch);
		}
	}

	public void updateEnemySpecfic(PacketMessage packetMessage) {
		if (packetMessage.clientID == GameConfig.clientID) {
			player.updateSpecificPack(Gdx.graphics.getDeltaTime(),
					packetMessage);
		} else {
			for (int i = 0; i < listEnemy.size(); i++) {
				if (packetMessage.clientID == listEnemy.get(i).clientID) {
					listEnemy.get(i).updateSpecificPack(
							Gdx.graphics.getDeltaTime(), packetMessage);
					;
					return;
				}
			}
			Player enemy = new Player(Player.TYPE_OTHER_PLAYER);
			enemy.clientID = packetMessage.clientID;
			enemy.setPosition(packetMessage.posX, packetMessage.posY);
			enemy.setStateMove(packetMessage.playerStateMove);
			listEnemy.add(enemy);
		}
	}

	public void updateEnemySpecfic(ArrayList<PacketMessage> listPlayerPack) {
		PacketMessage packetMessage;
		for (int j = 0; j < listPlayerPack.size(); j++) {
			packetMessage = listPlayerPack.get(j);
			if (packetMessage.clientID == GameConfig.clientID) {
				player.updateSpecificPack(Gdx.graphics.getDeltaTime(),
						packetMessage);
			} else {
				for (int i = 0; i < listEnemy.size(); i++) {
					if (packetMessage.clientID == listEnemy.get(i).clientID) {
						listEnemy.get(i).updateSpecificPack(
								Gdx.graphics.getDeltaTime(), packetMessage);
						return;
					}
				}

				// Nếu gói tin gửi về từ một thằng không quen biết thì tạo người
				// chơi mới.
				Player enemy = new Player(Player.TYPE_OTHER_PLAYER);
				enemy.clientID = packetMessage.clientID;
				enemy.setPosition(packetMessage.posX, packetMessage.posY);
				enemy.setStateMove(packetMessage.playerStateMove);
				listEnemy.add(enemy);
			}
		}
	}

	public void removeAllElements() {
		for (int i = 0; i < listEnemy.size(); i++) {
			listEnemy.remove(i);
		}
	}

	public void addEnemy(Player player) {
		if (!listEnemy.contains(player))
			listEnemy.add(player);
		Debug.d("a new enemy has come");
	}

	Player enemy1, enemy2;

	public void checkCollision(Player player) {
		for (int i = 0; i < listEnemy.size(); i++) {
			if ((player.getState() == State.RUNNING)
					&& (listEnemy.get(i).getState() == State.RUNNING))
				if (OverlapTester.overlapRectangles(player.getBoundCollision(),
						listEnemy.get(i).getBoundCollision())) {
					Debug.d("Collision between player and other player");
					player.collision();
					listEnemy.get(i).collision();
					ExplosionContainer.instance.addExplosion(
							player.getOrinCenter().x / 2
									+ listEnemy.get(i).getOrinCenter().x / 2,
							player.getOrinCenter().y / 2
									+ listEnemy.get(i).getOrinCenter().y / 2);

				}
		}

		for (int i = 0; i < listEnemy.size() - 1; i++) {
			enemy1 = listEnemy.get(i);
			for (int j = i + 1; j < listEnemy.size(); j++) {
				enemy2 = listEnemy.get(j);

				if ((enemy1.getPlayerType() != enemy2.getPlayerType())
						&& (enemy1.getState() == State.RUNNING)
						&& (enemy2.getState() == State.RUNNING)) {
					if (OverlapTester.overlapRectangles(
							enemy1.getBoundCollision(),
							enemy2.getBoundCollision())) {
						Debug.d("Collision bettwen 2 enemy");
						enemy1.collision();
						enemy2.collision();
						ExplosionContainer.instance.addExplosion(
								enemy1.getOrinCenter().x / 2
										+ enemy2.getOrinCenter().x / 2,
								enemy1.getOrinCenter().y / 2
										+ enemy2.getOrinCenter().y / 2);
					}

				}

			}
		}

	}

	public ArrayList<Player> getListPlayer() {
		return listEnemy;
	}

	public Player getPlayer() {
		return player;
	}

}
