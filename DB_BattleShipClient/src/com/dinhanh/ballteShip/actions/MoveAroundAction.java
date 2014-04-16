package com.dinhanh.ballteShip.actions;

public class MoveAroundAction {
	private float currentZoom;
	private float zoomAmount;
	private float zoomDuration;

	private float returnValues;

	float percent = 0;
	float time = 0;
	boolean complete = false;

	public MoveAroundAction(float currentZoom, float zoomAmount,
			float zoomDuration) {
		super();
		this.currentZoom = currentZoom;
		this.zoomAmount = zoomAmount;
		this.zoomDuration = zoomDuration;
		this.returnValues = currentZoom;
	}

	public void act(float deltaTime) {
		time += deltaTime;
		complete = time >= zoomDuration;
		if (complete) {
			percent = 1;
			returnValues = currentZoom;
		} else {
			if (percent < 0.5f) {
				returnValues = currentZoom + percent * zoomAmount;
			} else {
				returnValues = currentZoom + (1 - percent) * zoomAmount;
			}
		}
		percent = time / zoomDuration;
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
