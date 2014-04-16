package com.dinhanh.battleship.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ValuesUtils {
	public static ValuesUtils instace = new ValuesUtils();
	float duration;
	float percent;
	float amount;
	float time;
	boolean begin;
	boolean complete;
	boolean reverse = false;
	int int_values;
	float float_values;
	double double_values;
	Vector2 vector2_values;
	Vector3 vector3_values;
	float startValues, endValues;

	public boolean act(float delta) {
		if (!reverse) {
			complete = time >= duration;
			time += delta;
			if (complete) {
				percent = 1;
			} else {
				percent = time / duration;
			}
			
			float_values = (endValues > startValues) ? Math.min(startValues
					+ percent * (endValues - startValues), endValues) : Math
					.max(startValues + percent * (endValues - startValues),
							endValues);
		} else {
			complete = time >= duration;
			time += delta;
			if (complete) {
				percent = 1;
				reverse = false;
			} else {
				percent = time / duration;
			}
			if (percent < 0.5f) {
				float_values = startValues + percent * 2 * amount;
			} else {
				float_values = startValues + (0.5f - percent) * 2 * amount;
			}

		}
		return complete;
	}

	public void moveTo(float startValues, float endValues, float duration) {
		this.startValues = startValues;
		this.endValues = endValues;
		this.duration = duration;
		reset();
	}

	public void moveBy(float startValues, float amount, float duration) {
		this.startValues = startValues;
		this.endValues = startValues + amount;
		this.duration = duration;
		reset();
	}

	public void moveAround(float startValues, float amount, float duration) {
		this.startValues = startValues;
		this.amount = amount;
		this.duration = duration;
		this.reverse = true;
		reset();
	}

	private void reset() {
		time = 0;
		complete = false;
		begin = false;
		percent = 0;
	}

	public float getFloat_values() {
		return float_values;
	}

	public void setFloat_values(float float_values) {
		this.float_values = float_values;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

}
