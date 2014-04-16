package com.dinhanh.ballteShip.actions;

public class MoveByAction {
	private float currentValues;
	private float amount;
	private float duration;

	private float returnValues;

	float percent = 0;
	float time = 0;
	boolean complete = false;

	float velocity = 0;
	Interpolation interpolation = new Interpolation();

	public MoveByAction(float currentValues, float amount, float duration) {
		super();
		this.currentValues = currentValues;
		this.amount = amount;
		this.duration = duration;
		this.returnValues = currentValues;
	}

	public void act(float deltaTime) {
		// velocity -= 0.0001f;
		time += (deltaTime + velocity);
		complete = time >= duration;
		if (complete) {
			percent = 1;
			returnValues = currentValues;
		} else {
			returnValues = (amount > 0) ? (Math.min(currentValues + percent
					* amount, currentValues + amount)) : (Math.max(
					currentValues + percent * amount, currentValues + amount));

		}
		percent = time / duration;
		percent = interpolation.applySwing(percent);
		// percent = applyElasticOut(percent);

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
