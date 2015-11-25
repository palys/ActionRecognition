package receiver;

/**
 * Created by Palys on 2015-11-25.
 */
public class Logic {

    private BluetoothSender sender;

    public void setSender(BluetoothSender sender) {
        this.sender = sender;
    }

    public String getPin() {
        return sender.getPin();
    }
}
