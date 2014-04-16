package com.dinhanh.battleship.objects;

import java.util.Vector;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dinhanh.battleShipClient.ClientProgram;
import com.dinhanh.battleship.assets.Assets;
import com.dinhanh.battleship.clientpack.PacketMessage;
import com.dinhanh.battleship.utils.State;
import com.dinhanh.myUtils.GameObject;

public class Player extends GameObject {
	/**
	 * 
	 */
	public static final int DIE = 0;
	public static final int MOVE_LEFT = 1;
	public static final int MOVE_RIGHT = 2;
	public static final int MOVE_UP = 3;
	public static final int MOVE_DOWN = 4;
	public static final int MOVE_ALPHA = 5;
	public static final int MOVE_NONE = 6;

	// Biến quy định loại người chơi.
	private int playerType;
	public static final int TYPE_MYPLAYER = 10;
	public static final int TYPE_OTHER_PLAYER = 11;
	public static final int TYPE_AUTOMOVE_PLAYER = 12;

	// Thông tin người chơi. ClientID là cái định danh đối với server
	public int clientID = 0;
	public String playerName;

	public int stateMove;
	private boolean isFire = false;
	private boolean isRealse = false;

	private Animation ani_Bullet = Assets.instance.games.ani_bullet_blue;
	private float SPEED = 2.0f;
	private float tempSpeed = 2.0f;

	private float alpha = 0;
	PacketMessage packetMessage = new PacketMessage();
	private boolean isSendingPack = false;

	public Player(int playerType) {
		super(Assets.instance.games.ani_player_red);
		this.playerType = playerType;
	}

	private void init() {
		setPlayer(playerType);
		setPosition(100, 200);
		stateMove = MOVE_NONE;
	}

	@Override
	public void update(float deltaTime) {
		switch (playerType) {
		case TYPE_MYPLAYER:
			updateMyPlayer(deltaTime);
			break;
		case TYPE_OTHER_PLAYER:
			updateOtherPlayer(deltaTime);
			break;
		case TYPE_AUTOMOVE_PLAYER:
			updateAutoMovePlayer(deltaTime);
			break;

		default:
			break;
		}
		updateCommon(deltaTime);
	}

	private void updateCommon(float deltaTime) {

		// ====================== update state fire ========
		if (isFire) {
			udateFire();
			isFire = false;
		}

		// ===================Update State Move Player ===============
		switch (stateMove) {
		case MOVE_NONE:
			// do nothing here, player will stand.
			break;
		case DIE:
			die(deltaTime);
			break;
		case MOVE_LEFT:
			moveLeft(deltaTime);
			break;
		case MOVE_RIGHT:
			moveRight(deltaTime);
			break;
		case MOVE_UP:
			moveUp(deltaTime);
			break;
		case MOVE_DOWN:
			moveDown(deltaTime);
			break;
		case MOVE_ALPHA:
			moveByAlpha(alpha);
			break;
		default:
			System.out.println(" another State ");
			// do nothing here
			break;
		}

		// ============== Update move decrease speed ===========
		if (isRealse) {
			moveSlowly(deltaTime);
			// setStateMove(MOVE_NONE);
		} else {
			SPEED = 2;
			tempSpeed = SPEED;
		}
	}

	private void updateMyPlayer(float deltaTime) {
		sendTCPPack();
	}

	private void updateOtherPlayer(float deltaTime) {

	}

	private void updateAutoMovePlayer(float deltaTime) {
		switch (getState()) {
		case State.RUNNING:

			break;
		case State.DISMISS:

			break;
		case State.RESTART:
			init();
			break;

		default:
			break;
		}

	}

	public void updateSpecificPack(float deltaTime, PacketMessage packetMessage) {
		// ===============Update when user click fire button=============
		// setPosition(packetMessage.posX, packetMessage.posY);
		fire(packetMessage.isFire);
		isRelease(packetMessage.isRelease);
		stateMove = packetMessage.playerStateMove;

	}

	private void sendTCPPack() {

		if (playerType == TYPE_MYPLAYER) {
			// send TCP here
			// ======Begin Send Process=============
			if (isSendingPack) {
				packetMessage.clientID = ClientProgram.clientID;
				packetMessage.playerStateMove = this.stateMove;
				packetMessage.isRelease = isRealse;
				packetMessage.isFire = isFire;
				packetMessage.posX = position.x;
				packetMessage.posX = position.y;
				ClientProgram.client.sendTCP(packetMessage);
				isSendingPack = false;
			} else {
				packetMessage.playerStateMove = MOVE_NONE;
				packetMessage.isRelease = false;
				packetMessage.isFire = false;
			}
			// ======End Send Process=============
		}
	}

	@Override
	public void render(float deltaTime, OrthographicCamera camera,
			SpriteBatch batch) {
		if (getState() != State.DISABLE) {
			update(deltaTime);
			renderSprite(deltaTime, batch);
		}
	}

	boolean isPlayerCreated = false;

	public void createPlayer(float posX, float posY) {
		if (!isPlayerCreated) {
			setPosition(posX - spriteObject.getWidth() / 2,
					posY - spriteObject.getHeight() / 2);
			setState(State.RUNNING);
			isPlayerCreated = true;
		}
	}

	@Override
	public void collision() {

	}

	@Override
	public void collision(GameObject gameObject) {

	}

	@Override
	public void collision(Vector<GameObject> listGameObject) {

	}

	private void udateFire() {
		Bullet bullet = new Bullet(ani_Bullet);
		bullet.setState(State.RUNNING);
		bullet.setAlpha(0);
		bullet.setPosition(new Vector2(position.x
				+ getTextureRegion().getRegionWidth() - 20, position.y
				+ getTextureRegion().getRegionHeight() / 2 - 10));
		BulletContainer.instance.addGameObject(bullet);
	}

	private void moveLeft(float deltaTime) {
		move(-SPEED, 0);
	}

	private void moveRight(float deltaTime) {
		move(SPEED, 0);
	}

	private void moveUp(float deltaTime) {
		move(0, SPEED);
	}

	private void moveDown(float deltaTime) {
		move(0, -SPEED);
	}

	private void die(float deltaTime) {

	}

	private void moveByAlpha(float alpha) {
		move((float) (SPEED * Math.cos(alpha * Math.PI / 180)),
				(float) (SPEED * Math.cos(alpha * Math.PI / 180)));
	}

	private void moveSlowly(float deltaTime) {
		if (getStateMove() != MOVE_NONE) {
			if (tempSpeed > 0) {
				tempSpeed -= 4 * 0.016;
				// SPEED = tempSpeed;
			} else {
				tempSpeed = 2.0f;
				// SPEED = tempSpeed;
				setStateMove(MOVE_NONE);
			}
		}
		// if (getStateMove() != MOVE_NONE) {
		// if (tempSpeed > 0) {
		// tempSpeed -= 4 * deltaTime;
		// SPEED = tempSpeed;
		// } else {
		// tempSpeed = 2.0f;
		// SPEED = tempSpeed;
		// setStateMove(MOVE_NONE);
		// }
		// }
	}

	public void setStateMove(int stateMove) {
		// if (stateMove != this.stateMove) {
		if (this.stateMove != DIE) {
			this.stateMove = stateMove;
			// send TCP control here
			isSendingPack = true;
		}
	}

	public int getStateMove() {
		return stateMove;
	}

	public boolean isFire() {
		return isFire;
	}

	public void fire(boolean fire) {
		isFire = fire;
		if (isFire) {
			// send TCP control here
			isSendingPack = true;
		}
	}

	public void isRelease(boolean b) {
		this.isRealse = b;
		// ======Begin Send Process=============

		// ======End Send Process=============
	}

	public void setPlayer(int clientID, Vector2 position, int playerType) {
		setPlayer(playerType);
		this.clientID = clientID;
		this.position = position;
	}

	public void setPlayer(int playerType) {
		this.playerType = playerType;
		switch (playerType) {
		case TYPE_MYPLAYER:
			ani_Bullet = Assets.instance.games.ani_bullet_red;
			this.animation = Assets.instance.games.ani_player_red;
			setState(State.DISABLE);
			break;
		case TYPE_OTHER_PLAYER:
			ani_Bullet = Assets.instance.games.ani_bullet_blue;
			this.animation = Assets.instance.games.ani_player_blue;
			break;
		case TYPE_AUTOMOVE_PLAYER:
			ani_Bullet = Assets.instance.games.ani_bullet_blue;
			this.animation = Assets.instance.games.ani_player_blue;
			break;

		default:
			break;
		}

	}

	public int getClientID() {
		return clientID;
	}

}
