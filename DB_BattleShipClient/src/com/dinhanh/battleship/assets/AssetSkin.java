package com.dinhanh.battleship.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.dinhanh.battleship.utils.Storage;
import com.dinhanh.myUtils.Debug;

public class AssetSkin {
	public static Skin getDefaultInstace() {
		Skin skin = new Skin(Gdx.files.internal(Storage.instance.DEFAULT_SKIN));
		return skin;
	}
}
