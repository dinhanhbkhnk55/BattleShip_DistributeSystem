package com.dinhanh.battleship.objects;

import java.util.Vector;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.dinhanh.battleship.utils.State;
import com.dinhanh.battleship.utils.Storage;
import com.dinhanh.myUtils.GameObject;
import com.dinhanh.myUtils.OverlapTester;

public class Bullet extends GameObject {
	float alpha = 0;
	float speed = 2f;

	public Bullet(Animation animation) {
		super(animation);
		setState(State.RUNNING);
		System.out.println("Bullet Created");
	}

	@Override
	public void update(float deltaTime) {
		if (OverlapTester.pointInRectangle(new Rectangle(0, 0,
				Storage.instance.WIDTH_SCREEN, Storage.instance.HEIGHT_SCREEN),
				position)) {
			move((float) (speed * Math.cos(alpha)),
					(float) (speed * Math.sin(alpha)));
		} else {
			setState(State.DISMISS);
		}
	}

	@Override
	public void render(float deltaTime, OrthographicCamera camera,
			SpriteBatch batch) {
		renderSprite(deltaTime, batch);
	}

	public void setAlpha(float alpha) {
		this.alpha = (float) (alpha * Math.PI / 180f);
	}

	@Override
	public void collision() {

	}

	@Override
	public void collision(GameObject gameObject) {

	}

	@Override
	public void collision(Vector<GameObject> listGameObject) {

	}

}
