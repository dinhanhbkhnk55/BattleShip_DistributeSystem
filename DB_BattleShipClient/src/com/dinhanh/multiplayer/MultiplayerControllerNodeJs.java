package com.dinhanh.multiplayer;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONObject;

/**
 * @author heroandtn3
 * @date Apr 13, 2014
 */
public class MultiplayerControllerNodeJs implements MultiplayerController {

	private SocketIO socket;
	private Logger logger = Logger.getLogger(MultiplayerControllerNodeJs.class.getName());
	private ServerCallback<Boolean> joinCallback;
	private ServerCallback<List<String>> getOnlinePlayersCallback;
	private ServerCallback<Boolean> inviteCallback;
	private InviteListener inviteListener;
	private ServerCallback<Boolean> acceptCallback;

	/**
	 * @throws MalformedURLException 
	 * 
	 */
	public MultiplayerControllerNodeJs() throws MalformedURLException {
		//socket = new SocketIO("http://localhost:5000");
		socket = new SocketIO("http://lit-shelf-5311.herokuapp.com");
		socket.connect(new IOCallback() {
			
			@Override
			public void onMessage(JSONObject json, IOAcknowledge ack) {
				System.out.println("On json: " + json.toString());
			}
			
			@Override
			public void onMessage(String msg, IOAcknowledge ack) {
				System.out.println("On string: " + msg);
			}
			
			@Override
			public void onError(SocketIOException ex) {
				ex.printStackTrace();
			}
			
			@Override
			public void onDisconnect() {
				logger.warning("Disconnected");
			}
			
			@Override
			public void onConnect() {
				logger.info("Connected");
			}
			
			@Override
			public void on(String event, IOAcknowledge ack, Object... args) {
				logger.info("Server send event: " + event + " with msg: " + args);
				
				if (event.equalsIgnoreCase("join")) {
					joinCallback.onSuccess((Boolean) args[0]);
				}
				
				if (event.equalsIgnoreCase("getOnlinePlayers")) {
					for (Object arg : args) {
						System.out.println(arg);
					}
					//getOnlinePlayersCallback.onSuccess(Arrays.asList(args));
				}
				
				if (event.equalsIgnoreCase("inviteRs")) {
					inviteCallback.onSuccess((Boolean)args[0]);
				}
				
				if (event.equalsIgnoreCase("invite")) {
					inviteListener.onInvite(args[0].toString());
				}
				
				if (event.equalsIgnoreCase("acceptRs")) {
					acceptCallback.onSuccess((Boolean)args[0]);
				}
			}
		});
	}

	@Override
	public void join(String name, ServerCallback<Boolean> callback) {
		this.joinCallback = callback;
		socket.emit("join", name);
	}

	@Override
	public void getOnlinePlayers(ServerCallback<List<String>> callback) {
		this.getOnlinePlayersCallback = callback;
		socket.emit("getOnlinePlayers", "");
	}

	@Override
	public void invite(String player, ServerCallback<Boolean> callback) {
		this.inviteCallback = callback;
		socket.emit("invite", player);
	}

	@Override
	public void accept(String player, ServerCallback<Boolean> callback) {
		this.acceptCallback = callback;
		socket.emit("accept", player);
	}

	@Override
	public void listenInvite(InviteListener listener) {
		this.inviteListener = listener;
	}
}
