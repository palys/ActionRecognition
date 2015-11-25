package receiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Palys on 2015-11-25.
 */
public class Logic {

    private BluetoothSender sender;

    private static final String TEST_MESSAGE = "test";

    private static final String START_MESSAGE = "start";

    private static final String END_MESSAGE = "end";

    private File currentDirectory = new File(".");

    private String fileNamePrefix = "file";

    private int counter = 0;

    public void setSender(BluetoothSender sender) {
        this.sender = sender;
    }

    public String getPin() {
        return sender.getPin();
    }

    public void setCurrentDirectory(File newDirectory) {
        this.currentDirectory = newDirectory;
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

    private void saveFile(String name, byte[] bytes) throws IOException {
        FileOutputStream fout = new FileOutputStream(name);
        fout.write(bytes);
        fout.close();
    }

    private void dataMessage(byte[] message) {

        try {
            saveFile(fileNamePrefix + (counter++), message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        sender.send(START_MESSAGE.getBytes());
    }

    public void end() {
        sender.send(END_MESSAGE.getBytes());
    }
}
