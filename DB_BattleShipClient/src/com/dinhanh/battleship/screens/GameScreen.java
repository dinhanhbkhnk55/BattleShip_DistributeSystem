package com.dinhanh.battleship.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.dinhanh.ballteShip.actions.MyInputProcessor;
import com.dinhanh.battleship.dialog.GameDialog;
import com.dinhanh.battleship.game.WorldController;
import com.dinhanh.battleship.game.WorldRenderer;
import com.dinhanh.battleship.objects.EnemyContainer;
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
	boolean showStartGame = false;
	int countTime = 5;
	MyInputProcessor myInputProcessor;
	InputMultiplexer inputMultiplexer;
	int count = 0;

	public Minmap minmap;

	public GameScreen(DirectedGame game, boolean isMultiGame) {
		super(game);
		this.isMultiGame = isMultiGame;
		this.game = game;
		init();
		minmap = new Minmap(EnemyContainer.instance.listEnemy,
				EnemyContainer.instance.player);

	}

	private void init() {
		Gdx.input.setCatchBackKey(true);
		CommonProcess.setGameState(State.WAIT_GAME_START);

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

	public void update(float deltaTime) {
		minmap.update(deltaTime);
		updateScore();
		myInputProcessor.update();
		switch (CommonProcess.getGameState()) {
		case State.WAIT_GAME_START:
			
			break;
		case State.RUNNING:
			worldController.update(deltaTime);
			break;
		case State.GAME_OVER:
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

	}

	private void updateScore() {

	}

	private void resetGame() {
		Values.instance.currentScore = 0;
		Values.instance.gameOver = false;
	}

	@Override
	public void render(float deltaTime) {
		update(deltaTime);
		
		
		
		
		worldRenderer.render(deltaTime);
		dialog.render();
		minmap.render();
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

	public boolean isMultiGame() {
		return isMultiGame;
	}

	public void setMultiGame(boolean isMultiGame) {
		this.isMultiGame = isMultiGame;
	}

}
