//package com.dinhanh.battleship.response;
//
//import java.net.Socket;
//import java.util.Vector;
//import java.util.logging.SocketHandler;
//
//import com.dinhanh.json.JSONBuilder;
//
//public class GameClient {
//
//    public String host = "localhost";
//    public int port = 8888;
//    public String hostReConnect = "113.52.37.65";
//    public int portReConnect = 8888;
//    public boolean connected;
//    public Socket socket;
//    public SocketHandler handler;
//    public Vector outQueue;
//    private JSONBuilder jsonBuilder;
//
//    public GameClient(String host, int port, Vector outQueue,  boolean master) {
//        this.host = host;
//        this.port = port;
//        this.outQueue = outQueue;
//        this.jsonBuilder = new JSONBuilder();
//        connect(master);
//    }
//
//    public void setConfig(String hostReconnect, int portReConnect) {
//        this.hostReConnect = hostReconnect;
//        this.portReConnect = portReConnect;
//    }
//
//    public boolean connect(boolean master) {
//        try {
//            if (connected) {
//                return false;
//            }
//            String url = "socket://" + host + ":" + port;
//            socket = (SocketConnection) Connector.open(url);
//            socket.setSocketOption(SocketConnection.KEEPALIVE, 1);
//            handler = new ConnectionHandler(socket, pipoGame);
//            connected = true;
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            if (!master) {
//                redundancy();
//            }
//            try {
//                String url = "socket://" + hostReConnect + ":" + portReConnect;
//                socket = (SocketConnection) Connector.open(url);
//                handler = new ConnectionHandler(socket, pipoGame);
//                connected = true;
//                return true;
//            } catch (Exception e) {
//                System.out.println("Can not reconnect");
//            }
//            return false;
//        }
//    }
//
//    public void redundancy() {
////        if (GameConfig.listServer != null) {
////            if (GameConfig.listServer.size() >= 2) {
////                while (true) {
////                    Random r = new Random();
////                    int i = r.nextInt(GameConfig.listServer.size());
////                    ServerInfo server = (ServerInfo) GameConfig.listServer.elementAt(i);
////                    if (!server.host.equalsIgnoreCase(host) || !server.port.equalsIgnoreCase(Integer.toString(port))) {
////                        hostReConnect = server.host;
////                        portReConnect = Integer.parseInt(server.port);
////                        break;
////                    }
////                }
////            }
////        }
//    }
//
//    public boolean isConnected() {
//        return pipoGame.isConnect();
//    }
//    
//    // ================================================
//    // SMARTFOX SERVER BASIC REQUEST
//    // ================================================
//    
//    public void loginMaster(String username, String password) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.USER_NAME, username)
//                    .put(ExtParamsKey.PASSWORD, password).build();
//            handler.sendMessages(CommandRequest.LOGIN + MessageType.SPLIT + request);
//        }
//    }
//
//    public void joinSFSRoom(String roomName, boolean aspector) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.ROOM_NAME, roomName).build();
//            handler.sendMessages(CommandRequest.JOIN_SFS_ROOM + MessageType.SPLIT + request);
//        }
//    }
//
//    public void leaveSFSRoom() {
//        if (isConnected()) {
//            handler.sendMessages(CommandRequest.LEAVE_SFS_ROOM);
//        }
//    }
//
//    public void disconnect() {
//        if (isConnected()) {
//            handler.sendMessages(CommandRequest.DISCONNECT);
//        }
//    }
//
//    public void gameLeaveRoom(int seat) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.SEAT, seat).build();
//            handler.sendMessages(CommonString.GAME_PREFIX + CommandRequest.LEAVE_ROOM
//                    + MessageType.SPLIT + request);
//        }
//    }
//
//    public void chat(String username, String message) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.USER_NAME, username)
//                    .put(ExtParamsKey.MESSAGE, message).build();
//            handler.sendMessages(CommandRequest.PUBLIC_CHAT + MessageType.SPLIT + request);
//        }
//    }
//
//    public void listRoom(int gameId) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.GAME_ID, gameId).build();
//            handler.sendMessages(CommonString.LOBBY_PREFIX + CommandRequest.LIST_ROOMS + MessageType.SPLIT + request);
//        }
//    }
//
//    public void listTable(String roomId) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.GROUP_NAME, roomId).build();
//            handler.sendMessages(CommonString.LOBBY_PREFIX + CommandRequest.LIST_TABLES + MessageType.SPLIT + request);
//        }
//    }
//
//    public void getTaleInfo() {
//        if (isConnected()) {
//            handler.sendMessages(CommonString.GAME_PREFIX + CommandRequest.GET_TABLE_INFO);
//        }
//    }
//
//    public void chooseSeat(int seat) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.SEAT, seat).build();
//            handler.sendMessages(CommonString.GAME_PREFIX + CommandRequest.CHOOSE_SEAT
//                    + MessageType.SPLIT + request);
//        }
//    }
//
//    public void playerSeat(int seat) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.SEAT, seat).build();
//            handler.sendMessages(CommonString.GAME_PREFIX + CommandRequest.PLAYER_SEAT
//                    + MessageType.SPLIT + request);
//        }
//    }
//
//    public void standUp(int seat) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.SEAT, seat).build();
//            handler.sendMessages(CommonString.GAME_PREFIX + CommandRequest.STAND_UP
//                    + MessageType.SPLIT + request);
//        }
//    }
//
//    public void ready(int seat) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.SEAT, seat).build();
//            handler.sendMessages(CommonString.GAME_PREFIX + CommandRequest.READY
//                    + MessageType.SPLIT + request);
//        }
//    }
//
//    public void bacayBetMaster(int seat, int betMoney) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.SEAT, seat)
//                    .put("bet_money", betMoney).build();
//            handler.sendMessages(CommonString.GAME_PREFIX + CommandRequest.BET_MASTER
//                    + MessageType.SPLIT + request);
//        }
//    }
//
//    public void bacayShowCards(int seat) {
//        if (isConnected()) {
//            String request = jsonBuilder.builder().put(ExtParamsKey.SEAT, seat).build();
//            handler.sendMessages(CommonString.GAME_PREFIX + CommandRequest.SHOW_CARDS + MessageType.SPLIT + request);
//        }
//    }
//    
//    
//
//    public void testPing() {
//        String request = jsonBuilder.builder().put(CommonString.COMMAND, CommandRequest.TEST_PING)
//                .put(ExtParamsKey.MESSAGE, "Hello Smartfox").build();
//        handler.sendMessages(request);
//    }
//
//    public void loginSlave(String username, String password) {
//        if (isConnected()) {
//        }
//    }
//
//    public void logout() {
//        if (isConnected()) {
//        }
//    }
//
//    public void register(String username, String password, int sex, String media, String mobileName) {
//        if (isConnected()) {
//        }
//    }
//
//    public void close() {
//        try {
//            handler.close();
//            outQueue.removeAllElements();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
