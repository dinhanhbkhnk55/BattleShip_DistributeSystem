package com.dinhanh.ballteShip.actions;

import com.badlogic.gdx.math.MathUtils;

public class Interpolation {
	float scale = 2f;
	float value = 2, power = 10;

	public float applySwing(float a) {
		if (a <= 0.5f) {
			a *= 2;
			return a * a * ((scale + 1) * a - scale) / 2;
		}
		a--;
		a *= 2;
		return a * a * ((scale + 1) * a + scale) / 2 + 1;
	}

	public float applySwingOut(float a) {
		a--;
		return a * a * ((scale + 1) * a + scale) + 1;
	}

	public float applySwingIn(float a) {
		return a * a * ((scale + 1) * a - scale);
	}

	public float applyElasticIn(float a) {
		return (float) Math.pow(value, power * (a - 1)) * MathUtils.sin(a * 20)
				* 1.0955f;
	}

	public float applyElasticOut(float a) {
		a = 1 - a;
		return (1 - (float) Math.pow(value, power * (a - 1))
				* MathUtils.sin(a * 20) * 1.0955f);
	}

	public float applyElastic(float a) {
		if (a <= 0.5f) {
			a *= 2;
			return (float) Math.pow(value, power * (a - 1))
					* MathUtils.sin(a * 20) * 1.0955f / 2;
		}
		a = 1 - a;
		a *= 2;
		return 1 - (float) Math.pow(value, power * (a - 1))
				* MathUtils.sin((a) * 20) * 1.0955f / 2;
	}
}
