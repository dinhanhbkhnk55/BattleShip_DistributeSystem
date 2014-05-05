package com.dinhanh.multiplayer;

/**
 * @author heroandtn3
 * @date Apr 13, 2014
 */
public interface ServerCallback<T> {
	void onSuccess(T result);
	void onFalure(Throwable caught);
}
