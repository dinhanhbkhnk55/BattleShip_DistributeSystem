package com.dinhanh.battleship.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.dinhanh.battleship.utils.Storage;

public class AssetFont {
	public BitmapFont bitmapFontDigital;
	public BitmapFont bitmapFontGameOver;

	public AssetFont(AssetManager assetManager) {
		super();
		bitmapFontDigital = assetManager.get(Storage.instance.FONT_1,
				BitmapFont.class);
		bitmapFontGameOver = assetManager.get(Storage.instance.FONT_2,
				BitmapFont.class);

	}

}
