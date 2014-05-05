package com.dinhanh.battleship.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dinhanh.battleship.assets.Assets;
import com.dinhanh.battleship.screens.MenuScreen;
import com.dinhanh.battleship.utils.Storage;

public class StageMainMenu {

	// private OrthographicCamera camera;
	private Stage stage;
	private Skin skin;
	private Button battleShip, startGame, multiPlayer, exit;

	private ButtonStyle style_battleShip, style_startGame, style_multiPlayer,
			style_exit;
	private boolean showStage = false;
	private boolean nextScreen = false;
	private MenuScreen menuScreen;

	BitmapFont bitmapFont = Assets.instance.assetFont.bitmapFontGameOver;
	SpriteBatch batch = new SpriteBatch();
	OrthographicCamera camera;
	boolean connecting = false, connected = false, connectFail = false,
			multiGame = false;
	int countTime = 0, maxCountTime = 20, time = 0;

	public StageMainMenu(MenuScreen menuScreen) {
		super();
		this.menuScreen = menuScreen;
		init();
		showStage(false);
	}

	private void init() {
		camera = new OrthographicCamera(Storage.instance.WIDTH_SCREEN,
				Storage.instance.HEIGHT_SCREEN);
		camera.position.set(Storage.instance.WIDTH_SCREEN / 2,
				Storage.instance.HEIGHT_SCREEN / 2, 0);
		stage = new Stage();
		skin = new Skin();
		initSkin();
		initStage(skin);
		countTime = maxCountTime;
	}

	public void render() {
		renderInfo();
		stage.act();
		stage.draw();
		updateConnect();
	}

	private void updateConnect() {
		if (connecting) {
			if (countTime > 0) {
				countTime--;
			} else {
				if (menuScreen.startConnect()) {
					connected = true;
				} else {
					connectFail = true;
				}
				connecting = false;
				countTime = maxCountTime;
			}

		}
	}

	private void renderInfo() {

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		if (connecting) {
			bitmapFont.draw(batch, "Connecting .....", 20, 40);
		}
		if (connected) {
			bitmapFont.draw(batch, "Connected", 20, 40);
		}
		if (connectFail) {
			if (countTime > 0) {
				countTime--;
				if (countTime % maxCountTime > (maxCountTime / 2)) {
					bitmapFont.draw(batch, "ConnectFail", 20, 40);
				} else {
					bitmapFont.draw(batch, "Touch Screen to turn back menu",
							20, 40);
				}
			} else {
				if (time < maxCountTime) {
					time++;
					if (time % 12 < 6) {
						bitmapFont.draw(batch,
								"Touch Screen to turn back menu", 20, 40);
					}
				} else {
					time = 0;
				}

				if (Gdx.input.justTouched()) {
					connected = false;
					connecting = false;
					connectFail = false;
					countTime = maxCountTime;
					fadeComponent(1f, 2f);
				}
			}
		}
		batch.end();
		if (connected) {
			if (countTime > 0) {
				countTime--;
			} else {
				if (!multiGame) {
					fadeComponent(0f, 0.5f);
					menuScreen.nextScreen(1f);
					multiGame = true;
					connected = false;
				}
			}
		}
	}

	private void initSkin() {
		skin.add("battleShip", new TextureRegion(
				Assets.instance.games.reg_battleShip));
		skin.add("startGame", new TextureRegion(
				Assets.instance.games.reg_startGame));
		skin.add("multiPlayer", new TextureRegion(
				Assets.instance.games.reg_MultiPlayer));
		skin.add("exit", new TextureRegion(Assets.instance.games.reg_Exit));

	}

	private void initStage(Skin skin) {

		style_battleShip = new ButtonStyle(skin.getDrawable("battleShip"),
				skin.getDrawable("battleShip"), skin.getDrawable("battleShip"));

		style_startGame = new ButtonStyle(skin.getDrawable("startGame"),
				skin.getDrawable("startGame"), skin.getDrawable("startGame"));

		style_multiPlayer = new ButtonStyle(skin.getDrawable("multiPlayer"),
				skin.getDrawable("multiPlayer"),
				skin.getDrawable("multiPlayer"));

		style_exit = new ButtonStyle(skin.getDrawable("exit"),
				skin.getDrawable("exit"), skin.getDrawable("exit"));

		battleShip = new Button(style_battleShip);
		startGame = new Button(style_startGame);
		exit = new Button(style_exit);
		multiPlayer = new Button(style_multiPlayer);

		battleShip.setPosition(
				Storage.instance.WIDTH_SCREEN / 2 - battleShip.getWidth() / 2,
				280);
		battleShip.addAction(Actions.alpha(0f));
		startGame.setPosition(
				Storage.instance.WIDTH_SCREEN / 2 - startGame.getWidth() / 2,
				-250);

		startGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (!nextScreen) {
					menuScreen.nextScreen();
					nextScreen = true;
				}
			}
		});

		multiPlayer.setPosition(
				Storage.instance.WIDTH_SCREEN / 2 - multiPlayer.getWidth() / 2,
				-250);
		exit.setPosition(Storage.instance.WIDTH_SCREEN / 2 - exit.getWidth()
				/ 2, -250);

		multiPlayer.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fadeComponent(0.2f, 1f);
				if (!connecting) {
					connecting = true;
				}

			}
		});

		exit.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});

		Table layer = new Table();
		layer.add(battleShip);
		layer.add(startGame);
		layer.addActor(multiPlayer);
		layer.addActor(exit);
		stage.addActor(layer);
	}

	public void showStage(boolean showStage) {
		this.showStage = showStage;
		if (showStage) {
			battleShip.addAction(Actions.moveTo(Storage.instance.WIDTH_SCREEN
					/ 2 - battleShip.getWidth() / 2, 280, 1f,
					Interpolation.swing));
			// battleShip.setPosition(Storage.instance.WIDTH_SCREEN
			// / 2 - battleShip.getWidth() / 2, 280);
			battleShip.addAction(Actions.alpha(1f, 2f));

			startGame.addAction(Actions.moveTo(Storage.instance.WIDTH_SCREEN
					/ 2 - startGame.getWidth() / 2, 200, 1f,
					Interpolation.swing));

			multiPlayer.addAction(Actions.moveTo(Storage.instance.WIDTH_SCREEN
					/ 2 - multiPlayer.getWidth() / 2, 120, 1f,
					Interpolation.swing));
			exit.addAction(Actions.moveTo(Storage.instance.WIDTH_SCREEN / 2
					- exit.getWidth() / 2, 40, 1f, Interpolation.swing));
		} else {
			// battleShip.addAction(Actions.moveTo(Storage.instance.WIDTH_SCREEN
			// / 2 - battleShip.getWidth() / 2, -250, 1f,
			// Interpolation.swing));
			battleShip.addAction(Actions.moveTo(Storage.instance.WIDTH_SCREEN
					/ 2 - battleShip.getWidth() / 2, 280, 1f,
					Interpolation.swing));
			battleShip.addAction(Actions.alpha(0f, 1f));

			startGame.addAction(Actions.moveTo(Storage.instance.WIDTH_SCREEN
					/ 2 - startGame.getWidth() / 2, -250, 1f,
					Interpolation.swing));

			multiPlayer.addAction(Actions.moveTo(Storage.instance.WIDTH_SCREEN
					/ 2 - multiPlayer.getWidth() / 2, -250, 1f,
					Interpolation.swing));
			exit.addAction(Actions.moveTo(Storage.instance.WIDTH_SCREEN / 2
					- exit.getWidth() / 2, -250, 1f, Interpolation.swing));
		}
	}

	public void fadeComponent(float alpha, float duration) {
		if (alpha < 0.5f) {
			startGame.setTouchable(Touchable.disabled);
			multiPlayer.setTouchable(Touchable.disabled);
			exit.setTouchable(Touchable.disabled);
		} else {
			startGame.setTouchable(Touchable.enabled);
			multiPlayer.setTouchable(Touchable.enabled);
			exit.setTouchable(Touchable.enabled);
		}
		startGame.addAction(Actions.alpha(alpha, duration));
		multiPlayer.addAction(Actions.alpha(alpha, duration));
		exit.addAction(Actions.alpha(alpha, duration));

	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public boolean isShowGameOver() {
		return showStage;
	}

	public boolean getShowStage() {
		return showStage;
	}

}
