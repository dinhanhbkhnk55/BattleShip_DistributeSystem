package com.dinhanh.battleship.response;

import java.util.Vector;

import com.dinhanh.battleship.game.GameConfig;

public class IncomingThread implements Runnable {

	public RequestListener listener;
	private final Vector<ContentPackage> inQueue;
	private final Vector<ContentPackage> waitQueue;
	private boolean running = true;
	public DataPackage dataP;

	public IncomingThread(Vector<ContentPackage> inQueue,
			Vector<ContentPackage> waitQueue) {
		this.inQueue = inQueue;
		this.waitQueue = waitQueue;
		if (waitQueue.size() > 0) {
			for (int i = 0; i < waitQueue.size(); i++) {
				inQueue.addElement(waitQueue.elementAt(i));
			}
			waitQueue.removeAllElements();
		}
		// start();
	}

	public void start() {
		running = true;
		Thread t = new Thread(this);
		t.start();
	}

	public void stop() {
		running = false;
	}

	public void putWaitQueue(ContentPackage cp) {
		waitQueue.addElement(cp);
	}

	public void putInQueue(ContentPackage cp) {
		inQueue.addElement(cp);
	}

	public void setRequestListener(RequestListener listeners) {
		this.listener = listeners;
	}

	public void handleData() {
		synchronized (inQueue) {
			if (inQueue.size() > 0) {
				ContentPackage cp = inQueue.elementAt(0);
				if (listener != null && listener.isProcessPackage(dataP)) {
					inQueue.removeElementAt(0);
					listener.handleContentPackage(cp);
				}
			}
		}
	}

	public void run() {
		while (running) {
			try {
				handleData();
				Thread.sleep(GameConfig.delay);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
