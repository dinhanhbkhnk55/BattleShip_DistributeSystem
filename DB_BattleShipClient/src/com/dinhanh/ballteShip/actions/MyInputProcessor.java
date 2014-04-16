package com.dinhanh.ballteShip.actions;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.dinhanh.battleship.objects.EnemyContainer;
import com.dinhanh.battleship.objects.Player;
import com.dinhanh.battleship.utils.CommonProcess;
import com.dinhanh.battleship.utils.State;
import com.dinhanh.battleship.utils.Values;

public class MyInputProcessor implements GestureListener {
	float currentZoom, zoomAmount, zoomDuration;
	boolean restartGame = false;
	OrthographicCamera camera;
	Player player, enemy;

	int timeFire = 0;
	int maxTimeFire = 30;
	int countTime = 0, maxCountTime = 1;

	public MyInputProcessor() {

	}

	public void update() {
		player.isRelease(true);
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			player.setStateMove(Player.MOVE_LEFT);
			player.isRelease(false);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			player.setStateMove(Player.MOVE_RIGHT);
			player.isRelease(false);
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			player.setStateMove(Player.MOVE_UP);
			player.isRelease(false);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			player.setStateMove(Player.MOVE_DOWN);
			player.isRelease(false);
		}

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			if (timeFire == 0) {
				player.fire(true);
				timeFire = maxTimeFire;
			}
		}
		if (timeFire > 0) {
			timeFire--;
		}

		if (Gdx.input.isKeyPressed(Keys.O)) {
			if (timeFire == 0) {
				// create new enemy
				enemy = new Player(Player.TYPE_OTHER_PLAYER);
				enemy.setPosition(new Random().nextInt(800),
						new Random().nextInt(480));
				EnemyContainer.instance.addEnemy(enemy);
				timeFire = maxTimeFire;
			}

		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// set Player state
		// if(x > player.position.x && y > pl)

		// this is call when the game is over
		if (Values.instance.gameOver
				&& CommonProcess.getGameState() != State.RESTART) {
			CommonProcess.setGameState(State.RESTART);
			restartGame = true;
		}

		return true;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		if (count == 2) {
			player.createPlayer(x, y);
		}

		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		if (distance > initialDistance) {
			camera.zoom -= 0.008f;
		} else {
			camera.zoom += 0.008f;
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public GestureDetector getGestureDetector() {
		return new GestureDetector(this);
	}
}
