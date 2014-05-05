package com.dinhanh.battleship.objects;

import java.util.Vector;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.dinhanh.battleship.utils.State;
import com.dinhanh.battleship.utils.Storage;
import com.dinhanh.myUtils.GameObject;
import com.dinhanh.myUtils.OverlapTester;

public class Explosion extends GameObject {
	boolean started = false;
	float time = 0;

	public Explosion(Animation animation) {
		super(animation);
		setState(State.RUNNING);
		animation.setPlayMode(Animation.PlayMode.NORMAL);
	}

	@Override
	public void update(float deltaTime) {
		if (OverlapTester.pointInRectangle(new Rectangle(0, 0,
				Storage.instance.WIDTH_SCREEN, Storage.instance.HEIGHT_SCREEN),
				position)) {

		} else {
			setState(State.DISMISS);
		}
	}

	@Override
	public void render(float deltaTime, OrthographicCamera camera,
			SpriteBatch batch) {
		if (started) {
			if (spriteObject == null) {
				spriteObject = new Sprite(animation.getKeyFrame(frame
						* animation.frameDuration));
			}
			time += deltaTime;
			spriteObject.setRegion(animation.getKeyFrame(time));
			spriteObject.setPosition(position.x
					- getTextureRegion().getRegionWidth() / 2, position.y
					- getTextureRegion().getRegionHeight() / 2);
			spriteObject.setRotation(rotation);
			spriteObject.setScale(scale);
			spriteObject.draw(batch);
			if (animation.isAnimationFinished(time)) {
				setState(State.DISMISS);
			}
		}
	}

	public void setAlpha(float alpha) {
		rotation = alpha;
	}

	public void start() {
		this.started = true;
	}

	@Override
	public void collision() {
		this.setState(State.DISMISS);
	}

	@Override
	public void collision(GameObject gameObject) {

	}

	@Override
	public void collision(Vector<GameObject> listGameObject) {

	}
}
