package com.dinhanh.battleship.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dinhanh.battleShipClient.ClientProgram;
import com.dinhanh.battleship.clientpack.PacketMessage;
import com.dinhanh.battleship.utils.State;
import com.dinhanh.myUtils.OverlapTester;

public class EnemyContainer {
	public static final EnemyContainer instance = new EnemyContainer();
	ArrayList<Player> listEnemy = new ArrayList<Player>();
	Player player;

	private EnemyContainer() {

	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void render(float deltaTime, OrthographicCamera camera,
			SpriteBatch batch) {
		for (int i = 0; i < listEnemy.size(); i++) {
			listEnemy.get(i).render(deltaTime, camera, batch);
		}
	}

	public void updateEnemySpecfic(PacketMessage packetMessage) {
		if (packetMessage.clientID == ClientProgram.clientID) {
			// player.updateSpecificPack(Gdx.graphics.getDeltaTime(),
			// packetMessage);
		} else {
			for (int i = 0; i < listEnemy.size(); i++) {
				if (packetMessage.clientID == listEnemy.get(i).clientID) {
					// listEnemy.get(i).setPosition(packetMessage.posX,
					// packetMessage.posY);
					// listEnemy.get(i).fire(packetMessage.isFire);
					// listEnemy.get(i).setStateMove(packetMessage.playerStateMove);
					// listEnemy.get(i).isRelease(packetMessage.isRelease);
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

	public void updateEnemySpecfic(ArrayList<PacketMessage> listPlayer) {
		PacketMessage packetMessage;
		for (int j = 0; j < listPlayer.size(); j++) {
			packetMessage = listPlayer.get(j);
			if (packetMessage.clientID == ClientProgram.clientID) {
				// player.updateSpecificPack(Gdx.graphics.getDeltaTime(),
				// packetMessage);
			} else {
				for (int i = 0; i < listEnemy.size(); i++) {
					if (packetMessage.clientID == listEnemy.get(i).clientID) {
						// listEnemy.get(i).setPosition(packetMessage.posX,
						// packetMessage.posY);
						// listEnemy.get(i).fire(packetMessage.isFire);
						// listEnemy.get(i).setStateMove(packetMessage.playerStateMove);
						// listEnemy.get(i).isRelease(packetMessage.isRelease);
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
	}

	public void addEnemy(Player player) {
		listEnemy.add(player);
		System.out.println("a new enemy has come");
	}

	public void checkCollision(Player player) {
		for (int i = 0; i < listEnemy.size(); i++) {
			if ((player.getState() == State.RUNNING)
					&& (listEnemy.get(i).getState() == State.RUNNING))
				if (OverlapTester.overlapRectangles(player.getBoundCollision(),
						listEnemy.get(i).getBoundCollision())) {
					player.setState(State.DISMISS);
					listEnemy.get(i).setState(State.DISMISS);
				}
		}

	}

}
