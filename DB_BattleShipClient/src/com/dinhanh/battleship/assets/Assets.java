package com.dinhanh.battleship.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.dinhanh.battleship.utils.Storage;

public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();

	public AssetManager assetManager;
	public AssetSounds assetSounds;
	public AssetGames games;
	public AssetFont assetFont;
	private TextureAtlas textureAtlas;

	private Assets() {
	}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		assetManager.load(Storage.instance.TEXTUREATLAS_HEPHANTAN,
				TextureAtlas.class);
		assetManager.load(Storage.instance.FONT_1, BitmapFont.class);
		assetManager.load(Storage.instance.FONT_2, BitmapFont.class);
		assetManager.load(Storage.instance.DEFAULT_SKIN, Skin.class);
		assetManager.finishLoading();

		textureAtlas = assetManager.get(
				Storage.instance.TEXTUREATLAS_HEPHANTAN, TextureAtlas.class);
		for (Texture t : textureAtlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		games = new AssetGames(textureAtlas);
		assetSounds = new AssetSounds(assetManager);
		assetFont = new AssetFont(assetManager);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'",
				(Exception) throwable);
	}

}
