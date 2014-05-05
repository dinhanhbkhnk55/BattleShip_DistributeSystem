package com.dinhanh.battleship.objects;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dinhanh.battleship.assets.Assets;
import com.dinhanh.battleship.utils.CommonProcess;
import com.dinhanh.battleship.utils.State;
import com.dinhanh.myUtils.GameObject;
import com.dinhanh.myUtils.OverlapTester;

public class BulletContainer {
	public static final BulletContainer instance = new BulletContainer();
	Vector<GameObject> listObjects = new Vector<GameObject>();
	GameObject object = null;

	public BulletContainer() {
		super();
	}

	public void update(float deltaTime) {
		for (int i = 0; i < listObjects.size(); i++) {
			object = listObjects.get(i);
			if (object.getState() == State.RUNNING) {
				object.update(deltaTime);
			} else if (object.getState() == State.DISMISS) {
				listObjects.remove(i);
			}
		}
	}

	public void render(float deltaTime, OrthographicCamera camera,
			SpriteBatch batch) {
		for (int i = 0; i < listObjects.size(); i++) {
			object = listObjects.get(i);
			if (object.getState() == State.RUNNING) {
				object.render(deltaTime, camera, batch);
			}
		}
	}

	public void sortByRenderPiority() {

	}

	public void addGameObject(GameObject object) {
		int index = listObjects.size() - 1;
		if (listObjects.isEmpty()) {
			listObjects.add(object);
		} else if (object.getRenderPiority() >= listObjects.get(index)
				.getRenderPiority()) {
			listObjects.addElement(object);
		} else if (CommonProcess.instance.checkIndomain(index, 0,
				listObjects.size() - 1)) {
			while (object.getRenderPiority() < listObjects.get(index)
					.getRenderPiority()) {
				index--;
			}
			listObjects.insertElementAt(object, index);
		}
	}

	public void removeAllElements() {
		listObjects.removeAllElements();
	}

	Bullet bullet;
	Player player;

	public void collisionWithListPlayer(ArrayList<Player> listPlayer,
			Player mainPlayer) {
		for (int j = 0; j < listObjects.size(); j++) {
			if (listObjects.get(j) instanceof Bullet) {
				bullet = (Bullet) listObjects.get(j);
				if (mainPlayer.getPlayerType() != bullet.getType()
						&& mainPlayer.getState() == State.RUNNING
						&& mainPlayer.getState() == State.RUNNING) {
					if (OverlapTester.overlapRectangles(
							mainPlayer.getBoundCollision(),
							bullet.getBoundCollision())) {

						mainPlayer.collision();
						bullet.collision();
						ExplosionContainer.instance.addExplosion(
								mainPlayer.getOrinCenter().x / 2
										+ bullet.getOrinCenter().x / 2,
								mainPlayer.getOrinCenter().y / 2
										+ bullet.getOrinCenter().y / 2);
					}
				}

				for (int i = 0; i < listPlayer.size(); i++) {
					player = listPlayer.get(i);
					if (player.getPlayerType() != bullet.getType()
							&& player.getState() == State.RUNNING
							&& bullet.getState() == State.RUNNING) {
						if (OverlapTester.overlapRectangles(
								player.getBoundCollision(),
								bullet.getBoundCollision())) {
							player.collision();
							bullet.collision();
							ExplosionContainer.instance.addExplosion(
									player.getOrinCenter().x / 2
											+ bullet.getOrinCenter().x / 2,
									player.getOrinCenter().y / 2
											+ bullet.getOrinCenter().y / 2);
						}
					}
				}
			}
		}
	}

}
