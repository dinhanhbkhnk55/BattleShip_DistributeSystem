package com.dinhanh.ballteShip.actions;

import com.badlogic.gdx.math.MathUtils;

public class MoveByActionInterpolation {
	private float currentValues;
	private float amount;
	private float duration;
	float interpolation, amountInterpolation;

	private float returnValues;

	float percent = 0;
	float time = 0;
	boolean complete = false;

	public MoveByActionInterpolation(float currentValues,
			float amountInterpolation, float amount, float duration) {
		super();
		this.currentValues = currentValues;
		this.amount = amount;
		this.duration = duration;
		this.returnValues = currentValues;
		this.amountInterpolation = amountInterpolation;

		interpolation = MathUtils.clamp(amountInterpolation, 0, amount)
				/ amount;
	}

	public void act(float deltaTime) {
		time += deltaTime;
		complete = time >= duration;
		if (complete) {
			percent = 1;
//			returnValues = currentValues + amount;
		} else {
			if (percent < interpolation) {
				returnValues = currentValues + percent * amount;
			} else {
				returnValues = currentValues + (2 * interpolation - percent)
						* amount;
			}
		}

		percent = time / duration;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public float getReturnValues() {
		return returnValues;
	}

	public void setReturnValues(float returnValues) {
		this.returnValues = returnValues;
	}

}
