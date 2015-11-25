package com.example.palys.datasender;

/**
 * Created by Palys on 2015-11-21.
 */
public class BluetoothSender {

    private DataSender dataSender;

    public BluetoothSender(DataSender dataSender) {
        this.dataSender = dataSender;
    }

    public void send(byte[] message) {
        //TODO
    }

    public void enterPin(String pin) {

    }

    private void receive() {
        //dataSender.onMessage(message);
    }
}
