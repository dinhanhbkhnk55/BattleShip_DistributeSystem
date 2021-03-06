package com.dinhanh.battleship.utils;

import com.badlogic.gdx.math.Rectangle;

public class Storage {
	public static Storage instance = new Storage();
	// Game preferences file
	public static final String PREFERENCES = "DistributeSystem.prefs";
	public float WIDTH_SCREEN = 800f;
	public float HEIGHT_SCREEN = 480f;
	public final String TEXTUREATLAS_HEPHANTAN = "data/packs/HePhanTan.pack";

	public final String FONT_1 = "data/fonts/font_digital.fnt";
	public final String FONT_2 = "data/fonts/regularHeiro.fnt";
	public final String DEFAULT_SKIN = "data/skins/uiskin.json";
}
