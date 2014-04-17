package com.dinhanh.battleship.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.dinhanh.ballteShip.actions.MyInputProcessor;
import com.dinhanh.battleship.dialog.GameDialog;
import com.dinhanh.battleship.game.WorldController;
import com.dinhanh.battleship.game.WorldRenderer;
import com.dinhanh.battleship.utils.CommonProcess;
import com.dinhanh.battleship.utils.GamePreferences;
import com.dinhanh.battleship.utils.State;
import com.dinhanh.battleship.utils.Values;
import com.dinhanh.myUtils.AbstractGameScreen;
import com.dinhanh.myUtils.DirectedGame;

public class GameScreen extends AbstractGameScreen {
	DirectedGame game;
	boolean isMultiGame = false;

	WorldController worldController;
	WorldRenderer worldRenderer;
	GameDialog dialog;
	boolean isNextScreen = false;
	int countTime = 5;
	MyInputProcessor myInputProcessor;
	InputMultiplexer inputMultiplexer;
	int count = 0;

	public GameScreen(DirectedGame game, boolean isMultiGame) {
		super(game);
		this.isMultiGame = isMultiGame;
		Gdx.input.setCatchBackKey(true);
		this.game = game;
		CommonProcess.setGameState(State.RUNNING);
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		myInputProcessor = new MyInputProcessor();
		myInputProcessor.setPlayer(worldController.player);
		dialog = new GameDialog(worldController.player);

		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(dialog.getStage());
		inputMultiplexer.addProcessor(myInputProcessor.getGestureDetector());
		Gdx.input.setInputProcessor(getInputProcessor());
		Values.instance.highScore = (int) GamePreferences.instance.highScore;

	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	public void update() {
		updateScore();
		myInputProcessor.update();
		switch (CommonProcess.getGameState()) {
		case State.RUNNING:
			worldController.update(Gdx.graphics.getDeltaTime());
			break;
		case State.PAUSE:
			if (!dialog.isShowGameOver()) {
				dialog.showGameOver(true);
			}
			break;
		case State.RESTART:
			if (!isNextScreen) {
				GamePreferences.instance.highScore = Math.max(
						GamePreferences.instance.highScore,
						Values.instance.currentScore);
				resetGame();
				game.setScreen(new MenuScreen(game));
				isNextScreen = true;
			}
			break;
		case State.RESTART_INGAME:
			if (!isNextScreen) {
				GamePreferences.instance.highScore = Math.max(
						GamePreferences.instance.highScore,
						Values.instance.currentScore);
				GamePreferences.instance.save();
				resetGame();
				game.setScreen(new GameScreen(game, isMultiGame));
				isNextScreen = true;
			}
			break;
		default:
			break;
		}
		
		if (Gdx.input.isKeyPressed(Keys.Z)) {
			CommonProcess.setGameState(State.RESTART);
		}
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			setPlayerRed(true);
		}
		if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
			setPlayerRed(false);
		}

	}

	private void updateScore() {

	}

	private void resetGame() {
		Values.instance.currentScore = 0;
		Values.instance.gameOver = false;
	}

	@Override
	public void render(float deltaTime) {
		update();
		worldRenderer.render(deltaTime);
		dialog.render();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public InputProcessor getInputProcessor() {
		return inputMultiplexer;
	}

	public void setPlayerRed(boolean isPlayerRed) {
		if (!isPlayerRed) {
			// clientListener.setEnemy(worldController.player);
			// dialog.setPlayer(worldController.enemy);
			// myInputProcessor.setPlayer(worldController.enemy);
		} else {
			// clientListener.setEnemy(worldController.enemy);
			dialog.setPlayer(worldController.player);
			myInputProcessor.setPlayer(worldController.player);
		}
	}

	public boolean isMultiGame() {
		return isMultiGame;
	}

	public void setMultiGame(boolean isMultiGame) {
		this.isMultiGame = isMultiGame;
	}

}
