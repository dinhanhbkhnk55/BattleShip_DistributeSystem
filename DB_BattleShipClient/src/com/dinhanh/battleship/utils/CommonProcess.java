package com.dinhanh.battleship.utils;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class CommonProcess {
	public static CommonProcess instance = new CommonProcess();
	private static int gameState = State.CREATE;

	public Animation creatAnimation(TextureRegion textureRegion,
			float frameDuration, int FRAME_COLS, int FRAME_RAWS) {
		if (frameDuration >= 0) {
			return new Animation(frameDuration, getArrayTextureRegion(
					textureRegion, FRAME_COLS, FRAME_RAWS));
		} else {
			System.out.println("Error Create Animation in CommonProcess Class. Error Type = null");
			return null;
		}
	}

	public Animation creatAnimation(TextureRegion textureRegion,
			int FRAME_COLS, int FRAME_RAWS) {

		return new Animation(1.0f / 10.0f, getArrayTextureRegion(textureRegion,
				FRAME_COLS, FRAME_RAWS));
	}

	public TextureRegion[] getArrayTextureRegion(TextureRegion textureRegion,
			int FRAME_COLS, int FRAME_ROWS) {
		float width = textureRegion.getRegionWidth() / FRAME_COLS;
		float height = textureRegion.getRegionHeight() / FRAME_ROWS;

		TextureRegion[] textureRegions = new TextureRegion[FRAME_COLS
				* FRAME_ROWS];
		TextureRegion[][] temp = textureRegion.split((int) width, (int) height);
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				textureRegions[index++] = temp[i][j];
			}
		}
		return textureRegions;
	}

	public static int getGameState() {
		return gameState;
	}

	public static void setGameState(int state) {
		gameState = state;
	}

	public boolean checkIndomain(int variable, int minValues, int maxValues) {
		if (variable < minValues || variable > maxValues) {
			return false;
		} else {
			return true;
		}
	}

	public boolean checkIndomain(float variable, float minValues,
			float maxValues) {
		if (variable < minValues || variable > maxValues) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Hàm này tính vị trí tương đối giữa 2 đối tượng thông qua position của nó,
	 * tham số đầu vào là khoảng giới hạn minRoot, maxRoot và độ giới hạn độ
	 * scale. Cuối cùng là tham số scale theo x hoặc theo y. Giá trị scalse được
	 * tính tương đối theo maxRoot. + nếu isScaleX = true thì scale theo x + nếu
	 * isScaleX = false thì scale theo y Tham số trả về là độ scale cần tính
	 */

	public float calScale(Vector2 currentPosition, Vector2 min_rootPosition,
			Vector2 max_rootPosion, float minScaleX, float maxScaleX,
			float minScaleY, float maxScaleY, boolean isScaleX) {
		float scale = 1.0f;

		float rootDistanceX = Math.abs(max_rootPosion.x - min_rootPosition.x);
		float rootDistanceY = Math.abs(max_rootPosion.y - min_rootPosition.y);

		float currentDistanX = Math.abs(currentPosition.x - min_rootPosition.x);
		float currentDistanY = Math.abs(currentPosition.y - min_rootPosition.y);

		float scaleX = Math.abs(maxScaleX - minScaleX) / rootDistanceX;
		float scaleY = Math.abs(maxScaleY - minScaleY) / rootDistanceY;

		if (isScaleX) {
			scale = scaleX * (currentDistanX);
		} else {
			scale = scaleY * (currentDistanY);
		}
		return scale;
	}

	public float calScale(Vector2 currentPosition, Vector2 min_rootPosition,
			Vector2 max_rootPosion, float minScale, float maxScale) {
		float scale = minScale;
		scale = minScale
				+ (float) (CommonProcess.calDistance(currentPosition,
						min_rootPosition) * ((maxScale - minScale) / CommonProcess
						.calDistance(min_rootPosition, max_rootPosion)));
		return scale;
	}

	public Vector2 calPosition(Vector2 currentPosition, float speed, float alpha) {
		Vector2 nextPosition = new Vector2();
		// alpha = (float) (Math.PI / alpha);

		nextPosition.x = (float) (currentPosition.x + speed * Math.cos(alpha));
		nextPosition.y = (float) (currentPosition.y + speed * Math.sin(alpha));
		return nextPosition;
	}

	/**
	 * Hàm này dùng để tính toán góc Alpha của point2 đối với hệ quy chiếu gắn
	 * với point1
	 */
	public static double calDegree(Vector2 point1, Vector2 point2) {
		double dx = point2.x - point1.x;
		// Minus to correct for coord re-mapping
		double dy = -(point2.y - point1.y);

		double inRads = Math.atan2(dy, dx);

		// We need to map to coord system when 0 degree is at 3 O'clock, 270 at
		// 12 O'clock
		if (inRads < 0)
			inRads = Math.abs(inRads);
		else
			inRads = 2 * Math.PI - inRads;
		return (Math.toDegrees(inRads)) % 360;
	}

	public static int getRandomInt(int n) {
		return new Random().nextInt(n);
	}

	/**
	 * Hàm này dùng để tính toán khoảng cách của point2 đối với hệ quy chiếu gắn
	 * với point1
	 */
	public static double calDistance(Vector2 point1, Vector2 point2) {
		double dx = Math.abs(point2.x - point1.x);
		// Minus to correct for coord re-mapping
		double dy = Math.abs(point2.y - point1.y);

		return Math.sqrt(dx * dx + dy * dy);
	}

	public static Vector2 randomPositionInLine(Vector2 beginNode,
			Vector2 endNode) {
		Random randomLocal = new Random();
		float x = Math.min(beginNode.x, endNode.x)
				+ randomLocal.nextInt((int) Math.abs(endNode.x - beginNode.x));

		float y = ((endNode.y - beginNode.y) / (endNode.x - beginNode.x)) * x
				+ (beginNode.y * endNode.x - beginNode.x * endNode.y)
				/ (endNode.x - beginNode.x);

		return new Vector2(x, y);
	}

	public static Vector2 randomPositionInLine(Vector2 beginNode,
			Vector2 endNode, float offsetDistanceXFromMinRoot) {
		float x = endNode.x + offsetDistanceXFromMinRoot;
		float y = ((endNode.y - beginNode.y) / (endNode.x - beginNode.x)) * x
				+ (beginNode.y * endNode.x - beginNode.x * endNode.y)
				/ (endNode.x - beginNode.x);

		return new Vector2(x, y);
	}

	public static Vector2 getPositionInLine(float positionX, Vector2 beginNode,
			Vector2 endNode) {
		// Random randomLocal = new Random();
		// float x = Math.min(beginNode.x, endNode.x)
		// + randomLocal.nextInt((int) Math.abs(endNode.x - beginNode.x));
		float x = positionX;

		float y = ((endNode.y - beginNode.y) / (endNode.x - beginNode.x)) * x
				+ (beginNode.y * endNode.x - beginNode.x * endNode.y)
				/ (endNode.x - beginNode.x);

		return new Vector2(x, y);
	}
}
