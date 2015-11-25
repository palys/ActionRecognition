package receiver;

import java.awt.*;

/**
 * Created by Palys on 2015-11-25.
 */
public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Logic logic = new Logic();
                BluetoothSender sender = new BluetoothSender(logic);
                logic.setSender(sender);
                GUI gui = new GUI(logic);
                gui.setVisible(true);
            }
        });
    }
}
