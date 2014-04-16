package com.dinhanh.battleship.utils;

import com.badlogic.gdx.math.Vector2;

public class Values {
	public static final Values instance = new Values();
	public int currentScore;
	public int highScore;
	public boolean gameOver = false;
	public int countTap = 0;
	public Vector2 jumpedLocation = new Vector2(0, 0);
	public boolean actAble = false;
	public boolean isPlayerRed = false;

	public Values() {
		super();
		reset();
	}

	public void reset() {
		currentScore = 0;
	}
}
