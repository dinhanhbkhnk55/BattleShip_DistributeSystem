package com.dinhanh.battleship.dialog;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.dinhanh.battleship.assets.AssetSkin;
import com.dinhanh.battleship.assets.Assets;
import com.dinhanh.battleship.screens.MenuScreen;
import com.dinhanh.battleship.utils.Storage;
import com.dinhanh.myUtils.Debug;

public class DialogLogin {
	private OrthographicCamera camera;
	private Stage stage;
	public Skin skin;
	boolean isShowing;

	private Vector2 pos_loginForm;
	private Vector2 pos_loginFormOff;

	// element
	private Button btn_go;
	private Image img_login;
	private TextField tex_username;
	private TextField tex_password;

	MenuScreen menuScreen;

	public DialogLogin(MenuScreen menuScreen) {
		super();
		this.menuScreen = menuScreen;
		initSkin();
		initStage();
		showStage(false);
	}

	// ==================== Begin initialization ======================
	private void initSkin() {
		skin = AssetSkin.getDefaultInstace();
		skin.add("btnGoUp",
				new TextureRegion(Assets.instance.games.reg_btnGoUp));
		skin.add("btnGoChecked", new TextureRegion(
				Assets.instance.games.reg_btnGoChecked));
	}

	private void initStage() {

		stage = new Stage();
		camera = new OrthographicCamera(Storage.instance.WIDTH_SCREEN,
				Storage.instance.HEIGHT_SCREEN);

		img_login = new Image(Assets.instance.games.reg_loginForm);
		pos_loginForm = new Vector2(Storage.instance.WIDTH_SCREEN / 2
				- img_login.getWidth() / 2, Storage.instance.HEIGHT_SCREEN / 2
				- img_login.getHeight() / 2);
		pos_loginFormOff = new Vector2(pos_loginForm.x, pos_loginForm.y - 500);

		ButtonStyle btnGoStyle = new ButtonStyle(skin.getDrawable("btnGoUp"),
				skin.getDrawable("btnGoUp"), skin.getDrawable("btnGoChecked"));
		btn_go = new Button(btnGoStyle);
		btn_go.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showStage(false);
				menuScreen.showStartGame();
			}
		});

		tex_username = new TextField("", skin);

		tex_password = new TextField("", skin);

		img_login.setPosition(pos_loginFormOff.x, pos_loginFormOff.y);
		btn_go.setPosition(pos_loginFormOff.x, pos_loginFormOff.y);
		tex_username.setBounds(47 + pos_loginFormOff.x,
				pos_loginFormOff.y + 100, 130, 28);
		tex_password.setBounds(235 + pos_loginFormOff.x,
				pos_loginFormOff.y + 100, 130, 28);

		Table layer = new Table();
		layer.addActor(img_login);
		layer.addActor(btn_go);
		layer.addActor(tex_username);
		layer.addActor(tex_password);
		stage.addActor(layer);

	}

	// ==================== End initialization ======================

	public void update(float deltaTime) {

	}

	public void render(float deltaTime) {
		update(deltaTime);
		camera.update();
		stage.act();
		stage.draw();
	}

	public void showStage(boolean isShowing) {
		this.isShowing = isShowing;
		if (isShowing) {

			img_login.addAction(Actions.moveTo(pos_loginForm.x,
					pos_loginForm.y, 1f, Interpolation.swing));
			btn_go.addAction(Actions.moveTo(pos_loginForm.x, pos_loginForm.y,
					1f, Interpolation.swing));

			tex_username.addAction(Actions.moveTo(47 + pos_loginForm.x,
					pos_loginForm.y + 100, 1f, Interpolation.swing));
			tex_password.addAction(Actions.moveTo(235 + pos_loginForm.x,
					pos_loginForm.y + 100, 1f, Interpolation.swing));

		} else {
			img_login.addAction(Actions.moveTo(pos_loginFormOff.x,
					pos_loginFormOff.y, 1f, Interpolation.swing));
			btn_go.addAction(Actions.moveTo(pos_loginFormOff.x,
					pos_loginFormOff.y, 1f, Interpolation.swing));

			tex_username.addAction(Actions.moveTo(47 + pos_loginFormOff.x,
					pos_loginFormOff.y + 100, 1f, Interpolation.swing));
			tex_password.addAction(Actions.moveTo(235 + pos_loginFormOff.x,
					pos_loginFormOff.y + 100, 1f, Interpolation.swing));
			pos_loginFormOff.set(pos_loginForm.x, pos_loginForm.y + 500);
		}

	}

	public boolean isShowing() {
		return isShowing;
	}

	public Stage getStage() {
		if (stage.equals(null)) {
			Debug.d(" Stage can't be null ! ");
		}
		return stage;
	}
}
