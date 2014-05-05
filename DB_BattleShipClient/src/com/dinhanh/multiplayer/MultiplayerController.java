package com.dinhanh.multiplayer;

import java.util.List;

/**
 * @author heroandtn3
 * @date Apr 13, 2014
 */
public interface MultiplayerController {

	/**
	 * Join server
	 * @param name nickname
	 * @param callback return True if join successful
	 */
	void join(String name, ServerCallback<Boolean> callback);
	
	/**
	 * get list of online players in server
	 * @param callback list of players in string
	 */
	void getOnlinePlayers(ServerCallback<List<String>> callback);
	
	/**
	 * invite a player to play
	 * @param player nickname of player to be invited
	 * @param callback start game when receive True
	 */
	void invite(String player, ServerCallback<Boolean> callback);
	
	/**
	 * accept invitation from player
	 * @param player nickname of player invited
	 * @param callback start game when receive True
	 */
	void accept(String player, ServerCallback<Boolean> callback);
	
	
	void listenInvite(InviteListener listener);
	
	

}
