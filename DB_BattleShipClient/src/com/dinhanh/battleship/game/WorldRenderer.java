package com.dinhanh.battleship.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.dinhanh.battleship.assets.Assets;
import com.dinhanh.battleship.objects.BulletContainer;
import com.dinhanh.battleship.objects.EnemyContainer;
import com.dinhanh.battleship.utils.Storage;
import com.dinhanh.battleship.utils.Values;

public class WorldRenderer implements Disposable {

	public OrthographicCamera camera;
	public OrthographicCamera cameraUI;
	public SpriteBatch batch;
	public SpriteBatch batchUI;
	BitmapFont font;
	WorldController worldController;
	float followSpeed = 2.5f;
	Vector2 cam_pos = new Vector2(0, 0);

	public WorldRenderer(WorldController worldController) {
		super();
		this.worldController = worldController;
		camera = new OrthographicCamera(Storage.instance.WIDTH_SCREEN,
				Storage.instance.HEIGHT_SCREEN);
		camera.position.set(Storage.instance.WIDTH_SCREEN / 2,
				Storage.instance.HEIGHT_SCREEN / 2, 0);
		cameraUI = new OrthographicCamera(Storage.instance.WIDTH_SCREEN,
				Storage.instance.HEIGHT_SCREEN);
		cameraUI.position.set(Storage.instance.WIDTH_SCREEN / 2,
				Storage.instance.HEIGHT_SCREEN / 2, 0);
		batch = new SpriteBatch();
		batchUI = new SpriteBatch();
		font = Assets.instance.assetFont.bitmapFontDigital;
	}

	public void resize(int width, int height) {
		camera.update();
		cameraUI.update();
	}

	public void render(float deltaTime) {

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateCamera();
		batch.setProjectionMatrix(camera.combined);
		renderBackground(batchUI);
		renderGame(deltaTime, camera, batch);
		renderUI(cameraUI, batchUI);
	}

	private void renderGame(float deltaTime, OrthographicCamera camera,
			SpriteBatch batch) {
		batch.begin();
		BulletContainer.instance.render(Gdx.graphics.getDeltaTime(), camera, batch);
		EnemyContainer.instance.render(deltaTime, camera, batch);
		worldController.player.render(deltaTime, camera, batch);
//		worldController.playerContainer.render(deltaTime, camera, batch);
		batch.end();
	}

	private void renderUI(OrthographicCamera camera, SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		renderVariable(batch);
		batch.end();
	}

	// Update Camera flow Player
	private void updateCamera() {
		// cam_pos.lerp(new Vector2(worldController.player.character.getX(),
		// worldController.player.character.getY()),
		// followSpeed * 0.016f);
		// camera.position.set(cam_pos, 0);
		// camera.update();
	}

	private void renderVariable(SpriteBatch batch) {
		font.draw(batch, "Score : " + Values.instance.currentScore, 700, 460);
		font.draw(batch, "Best : " + Values.instance.highScore, 700, 440);
		font.draw(batch, "Delta Time : " + Gdx.graphics.getDeltaTime(), 20, 460);
	}

	private void renderBackground(SpriteBatch batch) {
		batch.begin();
		batch.draw(Assets.instance.games.background, 0, 0);
		batch.end();
	}

	@Override
	public void dispose() {
	}
}