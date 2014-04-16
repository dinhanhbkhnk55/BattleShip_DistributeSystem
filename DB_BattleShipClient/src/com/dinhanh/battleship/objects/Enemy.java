package com.dinhanh.battleship.objects;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dinhanh.battleship.assets.Assets;
import com.dinhanh.battleship.utils.State;
import com.dinhanh.myUtils.GameObject;

public class Enemy extends GameObject {
	int timeFire = 10;
	int maxTimeFire = 15;

	public Enemy(Animation animation) {
		super(animation);
		setPosition(new Vector2(400, 200));
	}

	@Override
	public void update(float deltaTime) {
		if (Gdx.input.isKeyPressed(Keys.A)) {
			move(-2f, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			move(2f, 0f);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			move(0, 2f);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			move(0, -2f);
		}

		if (Gdx.input.isKeyPressed(Keys.E) && (timeFire == 0)) {
			Bullet bullet = new Bullet(Assets.instance.games.ani_bullet_red);
			bullet.setState(State.RUNNING);
			bullet.setAlpha(180);
			bullet.setPosition(new Vector2(position.x, position.y
					+ getTextureRegion().getRegionHeight() / 2 - 10));
			BulletContainer.instance.addGameObject(bullet);
			timeFire = maxTimeFire;
		}
		if (timeFire > 0) {
			timeFire--;
		}
	}

	@Override
	public void render(float deltaTime, OrthographicCamera camera,
			SpriteBatch batch) {
		update(deltaTime);
		renderSprite(deltaTime, batch);
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
