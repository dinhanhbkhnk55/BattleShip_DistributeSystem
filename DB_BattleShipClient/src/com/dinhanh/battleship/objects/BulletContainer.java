package com.dinhanh.battleship.objects;

import java.util.Vector;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dinhanh.battleship.utils.CommonProcess;
import com.dinhanh.battleship.utils.State;
import com.dinhanh.myUtils.GameObject;

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
			} else if (object.getState() == State.RUNNING) {
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

	public void collisionWithListPlayer() {
		
	}
}
