package receiver;

import javax.swing.*;

/**
 * Created by Palys on 2015-11-25.
 */
public class GUI extends JFrame {

    public GUI() {
        initUI();
    }

    void initUI() {
        setTitle("Data Receiver");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
