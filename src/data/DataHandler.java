package data;

import data.NetworkMessage.*;

import java.util.concurrent.LinkedBlockingDeque;

public class DataHandler {

    private DataHandlerHelper helper = new DataHandlerHelper();
    private LinkedBlockingDeque<Object> dataQueue = new LinkedBlockingDeque<>();

    public DataHandler() {

        Thread handleData = new Thread(this::handleDataQueue);
        handleData.setDaemon(true);
        handleData.start();
    }

    public void addToQueue(Object obj) {
        this.dataQueue.addLast(obj);
    }

    private void handleDataQueue() {
        while (true) {
            if (dataQueue.size() > 0) {

                Object data = dataQueue.poll();

                if (data instanceof Message) {
                    helper.receivedMessage(data);

                } else if (data instanceof User) {
                    helper.receivedUser(data);

                } else if (data instanceof ClientConnect) {
                    helper.receivedClientConnected((ClientConnect) data);

                } else if (data instanceof ClientDisconnect) {
                    helper.receivedClientDisconnected((ClientDisconnect) data);

                } else if (data instanceof RoomNameExists) {
                    helper.receivedRoomNameExists();

                } else if (data instanceof RoomCreate) {
                    helper.receivedRoomCreated((RoomCreate) data);

                } else if (data instanceof RoomDelete) {
                    helper.receivedRoomDeleted((RoomDelete) data);

                } else if (data instanceof RoomJoin) {
                    helper.receivedUserJoinedRoom((RoomJoin) data);

                } else if (data instanceof RoomLeave) {
                    helper.receivedUserLeftRoom((RoomLeave) data);

                } else if (data instanceof UserNameChange) {
                    helper.receivedUserChangedName(data);
                }
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
