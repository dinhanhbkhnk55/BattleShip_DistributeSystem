package com.dinhanh.battleship.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dinhanh.battleship.assets.Assets;
import com.dinhanh.battleship.objects.Player;
import com.dinhanh.battleship.utils.CommonProcess;
import com.dinhanh.battleship.utils.State;
import com.dinhanh.battleship.utils.Storage;

public class GameDialog {
	OrthographicCamera camera;
	private Stage stage;
	private Skin skin;
	Button refresh, powerOff, moveUp, moveDown, moveLeft, moveRight, fire;

	ButtonStyle style_refresh, style_powerOff, style_moveUp, style_moveDown,
			style_moveLeft, style_moveRight, style_fire;
	boolean showControlButton = false;
	boolean showGameOver = false;
	Label gameOver;
	Player player;

	public GameDialog(Player player) {
		super();
		this.player = player;
		init();
		showGameOver(false);

	}

	private void init() {
		camera = new OrthographicCamera(Storage.instance.WIDTH_SCREEN,
				Storage.instance.HEIGHT_SCREEN);
		stage = new Stage();
		skin = new Skin();
		initSkin();
		initStage(skin);
	}

	public void render() {
		if (moveUp.isPressed() || moveLeft.isPressed() || moveDown.isPressed()
				|| moveRight.isPressed()) {
			player.isRelease(false);
		} else {
			player.isRelease(true);
		}

		stage.act();
		stage.draw();
	}

	private void initSkin() {
		skin.add("refresh", new TextureRegion(Assets.instance.games.refresh));
		skin.add("powerOff", new TextureRegion(Assets.instance.games.powerOff));

		skin.add("moveUp", new TextureRegion(Assets.instance.games.moveUp));
		skin.add("moveDown", new TextureRegion(Assets.instance.games.moveDown));
		skin.add("moveRight",
				new TextureRegion(Assets.instance.games.moveRight));
		skin.add("moveLeft", new TextureRegion(Assets.instance.games.moveLeft));
		skin.add("fire", new TextureRegion(Assets.instance.games.fire));
	}

	private void initStage(Skin skin) {
		style_refresh = new ButtonStyle(skin.getDrawable("refresh"),
				skin.getDrawable("refresh"), skin.getDrawable("refresh"));

		style_powerOff = new ButtonStyle(skin.getDrawable("powerOff"),
				skin.getDrawable("powerOff"), skin.getDrawable("powerOff"));

		style_moveUp = new ButtonStyle(skin.getDrawable("moveUp"),
				skin.getDrawable("moveUp"), skin.getDrawable("moveUp"));

		style_moveDown = new ButtonStyle(skin.getDrawable("moveDown"),
				skin.getDrawable("moveDown"), skin.getDrawable("moveDown"));

		style_moveLeft = new ButtonStyle(skin.getDrawable("moveLeft"),
				skin.getDrawable("moveLeft"), skin.getDrawable("moveLeft"));

		style_moveRight = new ButtonStyle(skin.getDrawable("moveRight"),
				skin.getDrawable("moveRight"), skin.getDrawable("moveRight"));
		style_fire = new ButtonStyle(skin.getDrawable("fire"),
				skin.getDrawable("fire"), skin.getDrawable("fire"));

		refresh = new Button(style_refresh);
		powerOff = new Button(style_powerOff);

		moveDown = new Button(style_moveDown);
		moveLeft = new Button(style_moveLeft);
		moveRight = new Button(style_moveRight);
		moveUp = new Button(style_moveUp);
		fire = new Button(style_fire);

		refresh.setPosition(Storage.instance.WIDTH_SCREEN / 2 - 40,
				Storage.instance.HEIGHT_SCREEN - 60);
		powerOff.setPosition(Storage.instance.WIDTH_SCREEN / 2 + 20,
				Storage.instance.HEIGHT_SCREEN - 60);
		refresh.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				CommonProcess.setGameState(State.RESTART_INGAME);
			}
		});

		powerOff.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});

		moveUp.setPosition(90, -90);
		moveDown.setPosition(90, -10);
		moveLeft.setPosition(40, -50);
		moveRight.setPosition(140, -50);
		fire.setPosition(710, -50);

		moveUp.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				player.setStateMove(Player.MOVE_UP);
			}
		});

		moveDown.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				player.setStateMove(Player.MOVE_DOWN);
			}
		});
		moveLeft.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				player.setStateMove(Player.MOVE_LEFT);
			}
		});
		moveRight.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				player.setStateMove(Player.MOVE_RIGHT);
			}
		});
		fire.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				player.fire(true);
			}
		});

		LabelStyle style = new LabelStyle(
				Assets.instance.assetFont.bitmapFontGameOver, Color.RED);
		gameOver = new Label("Game Over", style);
		gameOver.setPosition(240, 800);
		Table layer = new Table();
		layer.add(gameOver);
		layer.add(powerOff);
		layer.add(refresh);

		layer.addActor(moveUp);
		layer.addActor(moveDown);
		layer.addActor(moveRight);
		layer.addActor(moveLeft);
		layer.addActor(fire);
		stage.addActor(layer);
	}

	public void showGameOver(boolean showGameOver) {
		this.showGameOver = showGameOver;
		if (showGameOver) {
			gameOver.addAction(Actions.moveTo(
					Storage.instance.WIDTH_SCREEN / 2 - 60, 300, 1f,
					Interpolation.swing));
			refresh.addAction(Actions.moveTo(
					Storage.instance.WIDTH_SCREEN / 2 - 40,
					Storage.instance.HEIGHT_SCREEN - 60, 1f,
					Interpolation.swing));
			powerOff.addAction(Actions.moveTo(
					Storage.instance.WIDTH_SCREEN / 2 + 20,
					Storage.instance.HEIGHT_SCREEN - 60, 1f,
					Interpolation.swing));

			moveUp.addAction(Actions.moveTo(90, -90, 1f, Interpolation.swing));
			moveDown.addAction(Actions.moveTo(90, -10, 1f, Interpolation.swing));
			moveLeft.addAction(Actions.moveTo(40, -50, 1f, Interpolation.swing));
			moveRight.addAction(Actions.moveTo(140, -50, 1f,
					Interpolation.swing));
			fire.addAction(Actions.moveTo(710, -50, 1f, Interpolation.swing));
		} else {
			gameOver.addAction(Actions.moveTo(
					Storage.instance.WIDTH_SCREEN / 2 - 40, 800, 1f,
					Interpolation.swing));
			refresh.addAction(Actions.moveTo(
					Storage.instance.WIDTH_SCREEN / 2 - 40,
					Storage.instance.HEIGHT_SCREEN - 60, 1f,
					Interpolation.swing));
			powerOff.addAction(Actions.moveTo(
					Storage.instance.WIDTH_SCREEN / 2 + 20,
					Storage.instance.HEIGHT_SCREEN - 60, 1f,
					Interpolation.swing));

			moveUp.addAction(Actions.moveTo(90, 90, 1f, Interpolation.swing));
			moveDown.addAction(Actions.moveTo(90, 10, 1f, Interpolation.swing));
			moveLeft.addAction(Actions.moveTo(40, 50, 1f, Interpolation.swing));
			moveRight.addAction(Actions
					.moveTo(140, 50, 1f, Interpolation.swing));
			fire.addAction(Actions.moveTo(710, 50, 1f, Interpolation.swing));

		}
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public boolean isShowControlButton() {
		return showControlButton;
	}

	public void setShowControlButton(boolean showControlButton) {
		this.showControlButton = showControlButton;
	}

	public boolean isShowGameOver() {
		return showGameOver;
	}

	public void setShowGameOver(boolean showGameOver) {
		this.showGameOver = showGameOver;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
