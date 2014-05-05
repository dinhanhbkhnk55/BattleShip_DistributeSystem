/*******************************************************************************
 * Copyright 2013 Andreas Oehlke
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.dinhanh.myUtils;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.dinhanh.battleship.response.DataPackage;
import com.dinhanh.battleship.response.MessageType;

public abstract class AbstractGameScreen implements Screen {

	public final static int SCREEN_NONE = 0;
	public final static int SCREEN_LOGIN = 1;
	public final static int SCREEN_SELECT_GAME = 2;
	public final static int SCREEN_SELECT_ROOM = 3;
	public final static int SCREEN_SELECT_TABLE = 4;
	public final static int SCREEN_TABLE = 5;
	public final static int SCREEN_GAME = 6;

	protected DirectedGame game;
	private int messagesListener[];
	/* Command listener */
	private String commandsListener[];
//	public GameClient gameClient;
	protected int kindRequest = MessageType.RQ_UNVAILABLE;

	public AbstractGameScreen(DirectedGame game) {
		this.game = game;
	}

	public abstract void render(float deltaTime);

	public abstract void resize(int width, int height);

	public abstract void show();

	public abstract void hide();

	public abstract void pause();

	public abstract InputProcessor getInputProcessor();

	public void resume() {
	}

	public void dispose() {
	}

	public void setMessagesListener(int[] messagesListener) {
		this.messagesListener = messagesListener;
	}

	public void setCommandsListener(String[] commandsListener) {
		this.commandsListener = commandsListener;
	}

	public boolean isProcessPackage(DataPackage dataP) {
		if (messagesListener == null) {
			return false;
		}
		int idMsg = dataP.appID;
		boolean processPackage = false;
		boolean isMsgTypListener = false;
		for (int i = 0; i < messagesListener.length; i++) {
			if (idMsg == messagesListener[i]) {
				isMsgTypListener = true;
				break;
			}
		}
		// if (dataP instanceof ContentPackage) {
		// ContentPackage cp = (ContentPackage) dataP;
		// int index = CommonUtils.elementInArray(cp.getCommand(),
		// commandsListener);
		// boolean isCommandListener = true;
		// // if (index >= 0) {
		// // isCommandListener = true;
		// // }
		// processPackage = isCommandListener & isMsgTypListener;
		// } else if (dataP instanceof DataPackage) {
		// processPackage = isMsgTypListener;
		// }
		return processPackage;
	}

	public void switchScreen(int screen) {
		switch (screen) {
		case SCREEN_LOGIN:
			break;
		case SCREEN_SELECT_GAME:
			break;
		case SCREEN_SELECT_ROOM:
			break;
		case SCREEN_SELECT_TABLE:
			break;
		case SCREEN_TABLE:
			break;
		case SCREEN_GAME:
			break;
		}
	}

	public void setKindRequest(int kind) {
		this.kindRequest = kind;
	}
}
