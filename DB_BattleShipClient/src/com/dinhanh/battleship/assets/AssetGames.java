package com.dinhanh.battleship.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dinhanh.battleship.utils.CommonProcess;

public class AssetGames {

	public TextureRegion ninePath;
	public TextureRegion loading;
	public TextureRegion background;
	public TextureRegion gameOver;

	// Texture for mainmenu
	public TextureRegion reg_battleShip;
	public TextureRegion reg_startGame;
	public TextureRegion reg_MultiPlayer;
	public TextureRegion reg_Exit;

	// Texure for login Screen
	public TextureRegion reg_loginForm;
	public TextureRegion reg_btnGoUp;
	public TextureRegion reg_btnGoChecked;

	// Texture for GameControl
	public TextureRegion moveUp;
	public TextureRegion moveRight;
	public TextureRegion moveDown;
	public TextureRegion moveLeft;
	public TextureRegion fire;
	public TextureRegion refresh;
	public TextureRegion powerOff;

	// Texture and animation for player object
	public TextureRegion reg_PlayerRed;
	public TextureRegion reg_PlayerBlue;
	public TextureRegion reg_bullet_red;
	public TextureRegion reg_bullet_blue;
	public Animation ani_player_red;
	public Animation ani_player_blue;
	public Animation ani_bullet_red;
	public Animation ani_bullet_blue;

	private TextureAtlas textureAtlas;

	public AssetGames(TextureAtlas textureAtlas) {
		super();
		this.textureAtlas = textureAtlas;
		load();
	}

	public void load() {
		// ================================================
		ninePath = textureAtlas.findRegion("loadingbar");
		loading = textureAtlas.findRegion("loading");
		background = textureAtlas.findRegion("bg");
		gameOver = textureAtlas.findRegion("gameOver");

		// ================================================
		reg_loginForm = textureAtlas.findRegion("loginForm");
		reg_btnGoUp = textureAtlas.findRegion("btnGoUp");
		reg_btnGoChecked = textureAtlas.findRegion("btnGoChecked");

		// ================================================
		moveDown = textureAtlas.findRegion("moveDown");
		moveUp = textureAtlas.findRegion("MoveUp");
		moveLeft = textureAtlas.findRegion("moveLeft");
		moveRight = textureAtlas.findRegion("moveRight");
		fire = textureAtlas.findRegion("fireButton");
		refresh = textureAtlas.findRegion("refresh");
		powerOff = textureAtlas.findRegion("powerOff");

		// ================================================
		reg_bullet_red = textureAtlas.findRegion("bullet_red");
		reg_bullet_blue = textureAtlas.findRegion("bullet_blue");
		reg_PlayerBlue = textureAtlas.findRegion("player_blue");
		reg_PlayerRed = textureAtlas.findRegion("player_red");

		ani_player_red = CommonProcess.instance.creatAnimation(reg_PlayerRed,
				1, 1);
		ani_player_blue = CommonProcess.instance.creatAnimation(reg_PlayerBlue,
				1, 1);
		ani_bullet_red = CommonProcess.instance.creatAnimation(reg_bullet_red,
				1, 1);
		ani_bullet_blue = CommonProcess.instance.creatAnimation(
				reg_bullet_blue, 1, 1);

		// ====================== texture for MainmenuScreen ==============
		reg_battleShip = textureAtlas.findRegion("tex_battleShip");
		reg_startGame = textureAtlas.findRegion("tex_startGame");
		reg_MultiPlayer = textureAtlas.findRegion("tex-multiPlayer");
		reg_Exit = textureAtlas.findRegion("tex_exit");

	}
}
