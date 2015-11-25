package receiver;

/**
 * Created by Palys on 2015-11-25.
 */
public class Logic {

    private BluetoothSender sender;

    private static final String TEST_MESSAGE = "test";

    private static final String START_MESSAGE = "start";

    private static final String END_MESSAGE = "end";

    public void setSender(BluetoothSender sender) {
        this.sender = sender;
    }

    public String getPin() {
        return sender.getPin();
    }

    public void onMessage(byte[] message) {
        if (message.length < 6) {
            String msgString = new String(message);

            if (TEST_MESSAGE.equals(msgString)) {
                sender.send(TEST_MESSAGE.getBytes());
            } else {
                dataMessage(message);
            }

        } else {
            dataMessage(message);
        }
    }

    private void dataMessage(byte[] message) {
        //TODO save file
    }

    public void start() {
        sender.send(START_MESSAGE.getBytes());
    }

    public void end() {
        sender.send(END_MESSAGE.getBytes());
    }
}
