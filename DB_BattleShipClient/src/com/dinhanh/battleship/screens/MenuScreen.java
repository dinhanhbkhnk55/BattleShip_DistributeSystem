package com.dinhanh.battleship.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dinhanh.battleShipClient.ClientListener;
import com.dinhanh.battleShipClient.ClientProgram;
import com.dinhanh.battleship.assets.Assets;
import com.dinhanh.battleship.dialog.DialogLogin;
import com.dinhanh.battleship.dialog.StageMainMenu;
import com.dinhanh.battleship.utils.Storage;
import com.dinhanh.myUtils.AbstractGameScreen;
import com.dinhanh.myUtils.DirectedGame;
import com.dinhanh.myUtils.ScreenTransitionFade;

public class MenuScreen extends AbstractGameScreen {
	public DirectedGame game;

	SpriteBatch batch;
	OrthographicCamera camera;
	private boolean isNextScreen = false;
	private boolean isNextMultiGameScreen = false;
	int loading = 0;
	int countTime = 0;
	int timeTouch = 10;
	GameScreen gameScreen;
	TextureRegion tex_loading;
	NinePatch ninePatch;
	StageMainMenu stageMainMenu;
	DialogLogin dialogLogin;
	InputMultiplexer inputMultiplexer;

	boolean connected = false;

	boolean showLogin = false;
	boolean showStartGame = false;

	ClientListener clientListener;

	public MenuScreen(DirectedGame game) {
		super(game);
		this.game = game;
		gameScreen = new GameScreen(game, false);
		stageMainMenu = new StageMainMenu(this);
		dialogLogin = new DialogLogin(this);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(dialogLogin.getStage());
		inputMultiplexer.addProcessor(stageMainMenu.getStage());
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		// startConnect();
	}

	@Override
	public void show() {
		ninePatch = new NinePatch(Assets.instance.games.ninePath);
		tex_loading = Assets.instance.games.loading;
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Storage.instance.WIDTH_SCREEN,
				Storage.instance.HEIGHT_SCREEN);
		camera.position.set(Storage.instance.WIDTH_SCREEN / 2,
				Storage.instance.HEIGHT_SCREEN / 2, 0);

	}

	@Override
	public void resize(int width, int height) {
		camera.update();
	}

	@Override
	public void render(float deltaTime) {
		switchScreen();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (!dialogLogin.isShowing() && !stageMainMenu.getShowStage()) {
			renderLoading(camera, batch);
		}
		stageMainMenu.render();
		dialogLogin.render(deltaTime);

	}

	private void switchScreen() {
		if (isNextMultiGameScreen) {
			game.setScreen(gameScreen, ScreenTransitionFade.init(1f));
			isNextMultiGameScreen = false;
		}

		if (Gdx.input.justTouched()) {
			if (!showLogin) {
				dialogLogin.showStage(true);
				showLogin = true;
			}
		}
	}

	public void showStartGame() {
		if (!showStartGame) {
			// game.setScreen(gameScreen);
			stageMainMenu.showStage(true);
			showStartGame = true;
		}
	}

	private void renderLoading(OrthographicCamera camera, SpriteBatch batch) {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(tex_loading, 200, 300);
		ninePatch.draw(batch, 40, 100,
				Assets.instance.assetManager.getProgress() * 700, 15);
		if (Assets.instance.assetManager.update()) {
			countTime++;
			if (countTime % 8 < 4) {
				Assets.instance.assetFont.bitmapFontGameOver.draw(batch,
						"Get Ready !", 320, 200);
			}

		} else {
			Assets.instance.assetFont.bitmapFontGameOver.draw(batch,
					"LOADING ... ", 320, 200);
		}
		batch.end();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	public boolean startConnect() {
		clientListener = new ClientListener();
		// clientListener.setEnemy(worldController.enemy);
		ClientProgram clientProcess;
		try {
			clientProcess = new ClientProgram();
			clientProcess.addListener(clientListener);
			connected = true;
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception in BattleShipClient ");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public InputProcessor getInputProcessor() {
		return inputMultiplexer;
	}

	public void nextScreen() {
		gameScreen.setMultiGame(false);
		game.setScreen(gameScreen);
	}

	public void nextScreen(float duration) {
		if (!isNextMultiGameScreen)
			gameScreen.setMultiGame(true);
		isNextMultiGameScreen = true;
	}

}
