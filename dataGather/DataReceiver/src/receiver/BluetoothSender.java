package receiver;

/**
 * Created by Palys on 2015-11-25.
 */
public class BluetoothSender {

    private Logic logic;

    public BluetoothSender(Logic logic) {
        this.logic = logic;
    }

    public String getPin() {
        //TODO
        return "PIN";
    }

    public void send(byte[] bytes) {
        //TODO
    }

    public void receive() {
        //logic.onMessage(message);
    }
}
