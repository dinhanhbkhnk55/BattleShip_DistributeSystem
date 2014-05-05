package com.dinhanh.myUtils;

import java.util.Vector;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {

	public Vector2 position;
	public Vector2 velocity;
	public Vector2 origin;
	public Vector2 scaleVector;
	public Rectangle bound;
	public Rectangle boundCollision;
	public float rotation;
	public float scale;
	public boolean collision = false;

	public Animation animation;
	public int frame;
	public int state;
	public int[] sequenceFrame;
	public int renderPiority;
	public Sprite spriteObject;
	private float time = 0f;
	private float maxTime = 0f;

	public GameObject(Animation animation) {
		this.animation = animation;
		animation.setPlayMode(Animation.PlayMode.LOOP);
		spriteObject = new Sprite(animation.getKeyFrame(0));
		position = new Vector2();
		velocity = new Vector2();
		origin = new Vector2();
		scaleVector = new Vector2();

		rotation = 0;
		scale = 1.0f;

		frame = 0;
		renderPiority = 0;
		maxTime = 10 * animation.animationDuration;
		sequenceFrame = new int[(int) (animation.animationDuration / animation.frameDuration)];
		for (int i = 0; i < sequenceFrame.length; i++) {
			sequenceFrame[i] = i;
		}

		boundCollision = new Rectangle();
		boundCollision.x = position.x;
		boundCollision.y = position.y;
		boundCollision.width = getTextureRegion().getRegionWidth();
		boundCollision.height = getTextureRegion().getRegionHeight();
		bound = new Rectangle(boundCollision);

	}

	public void setFrameSequence(int[] sequenceFrame) {
		int size = (int) (animation.animationDuration / animation.frameDuration);
		for (int i = 0; i < sequenceFrame.length; i++) {
			sequenceFrame[i] = MathUtils.clamp(sequenceFrame[i], 0, size);
		}
		this.sequenceFrame = sequenceFrame;
	}

	public void nextFrame() {
		if (frame == (sequenceFrame.length - 1)) {
			frame = 0;
		} else {
			frame += 1;
		}
	}

	public void previousFrame() {
		if (frame == 0) {
			frame = (sequenceFrame.length - 1);
		} else {
			frame -= 1;
		}
	}

	public int getRenderPiority() {
		return renderPiority;
	}

	public void setRenderPiority(int renderPiority) {
		this.renderPiority = renderPiority;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public TextureRegion getTextureRegion() {
		return animation.getKeyFrame(frame * animation.frameDuration);
	}

	public void setTextureRegion(TextureRegion[] textureRegions) {
		animation = new Animation(animation.frameDuration, textureRegions);
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	public void setPosition(Vector2 position) {
		// this.position = position;
		this.position.set(position.x - getTextureRegion().getRegionWidth() / 2,
				position.y - getTextureRegion().getRegionHeight() / 2);
	}

	public void move(float x, float y) {
		position.x += x;
		position.y += y;

	}

	public Rectangle getBoundCollision() {
		boundCollision.set(position.x, position.y, getTextureRegion()
				.getRegionWidth(), getTextureRegion().getRegionHeight());
		return boundCollision;
	}

	public void renderSprite(float deltaTime, SpriteBatch batch) {

		if (spriteObject == null) {
			spriteObject = new Sprite(animation.getKeyFrame(frame
					* animation.frameDuration));
		}
		if (time < maxTime) {
			time += deltaTime;
		} else {
			time = 0;
		}
		spriteObject.setRegion(animation.getKeyFrame(time));
		spriteObject.setPosition(position.x, position.y);
		spriteObject.setRotation(rotation);
		spriteObject.setScale(scale);
		spriteObject.draw(batch);

	}

	public Vector2 getOrinCenter() {
		return new Vector2(
				position.x + getTextureRegion().getRegionWidth() / 2,
				position.y + getTextureRegion().getRegionHeight() / 2);
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public boolean collisionable() {
		return !collision;
	}

	public abstract void update(float deltaTime);

	public abstract void render(float deltaTime, OrthographicCamera camera,
			SpriteBatch batch);

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setBoundCollision(Rectangle collisonRectangle) {
		this.boundCollision = collisonRectangle;
	}

	public abstract void collision();

	public abstract void collision(GameObject gameObject);

	public abstract void collision(Vector<GameObject> listGameObject);
}
