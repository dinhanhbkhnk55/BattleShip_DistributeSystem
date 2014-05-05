package com.dinhanh.battleship.screens;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.dinhanh.battleship.assets.Assets;
import com.dinhanh.battleship.objects.EnemyContainer;
import com.dinhanh.battleship.objects.Player;
import com.dinhanh.battleship.utils.Storage;

public class Minmap {
	OrthographicCamera camera;
	SpriteBatch batch;
	private ArrayList<Player> enemyContainer;
	private Player player;
	private TextureRegion minmapBg;
	private Vector2 pos_minmap;

	public Minmap(ArrayList<Player> enemyContainers, Player player) {
		this.enemyContainer = enemyContainers;
		this.player = player;
		camera = new OrthographicCamera(Storage.instance.WIDTH_SCREEN,
				Storage.instance.HEIGHT_SCREEN);
		camera.position.set(Storage.instance.WIDTH_SCREEN / 2,
				Storage.instance.HEIGHT_SCREEN / 2, 0);
		batch = new SpriteBatch();
		minmapBg = Assets.instance.games.reg_minmap;
		pos_minmap = new Vector2(Storage.instance.WIDTH_SCREEN
				- minmapBg.getRegionWidth() - 10,
				Storage.instance.HEIGHT_SCREEN - 10
						- minmapBg.getRegionHeight());

	}

	public void update(float deltaTime) {
		camera.update();

	}

	public void render() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(minmapBg, pos_minmap.x, pos_minmap.y);
		batch.end();

	}

}
