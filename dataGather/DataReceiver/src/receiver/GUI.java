package receiver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Palys on 2015-11-25.
 */
public class GUI extends JFrame implements ActionListener {

    private final Logic logic;

    private JButton chooseDirectoryButton;

    private JButton startStopGatheringDataButton;

    private JLabel currentDirectoryLabel;

    private File currentDirectory = new File(".");

    private JLabel pinLabel;

    private JFileChooser chooser;

    private final static String FILE_CHOOSE_ACTION = "FileChoose";

    private final static String START_END_ACTION = "StartEnd";

    private final static String START_BUTTON_LABEL = "START";

    private final static String END_BUTTON_LABEL = "END";

    private boolean running = false;

    public GUI(Logic logic) {
        this.logic = logic;
        initUI();
    }

    void initUI() {
        setTitle("Data Receiver");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(4, 1));

        chooseDirectoryButton = new JButton("Choose directory");
        chooseDirectoryButton.setActionCommand(FILE_CHOOSE_ACTION);

        startStopGatheringDataButton = new JButton(START_BUTTON_LABEL);
        startStopGatheringDataButton.setActionCommand(START_END_ACTION);

        currentDirectoryLabel = new JLabel();
        pinLabel  = new JLabel();

        try {
            String path = currentDirectory.getCanonicalPath().toString();
            currentDirectoryLabel.setText(path);
        } catch (IOException e) {
            e.printStackTrace();
        }


        add(currentDirectoryLabel);
        add(chooseDirectoryButton);
        add(startStopGatheringDataButton);
        add(pinLabel);

        chooseDirectoryButton.addActionListener(this);
        startStopGatheringDataButton.addActionListener(this);
        pinLabel.setText("PIN: " + logic.getPin());

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (FILE_CHOOSE_ACTION.equals(e.getActionCommand())) {

            chooser = new JFileChooser();
            chooser.setCurrentDirectory(currentDirectory);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                currentDirectory = chooser.getSelectedFile();
                currentDirectoryLabel.setText(currentDirectory.toString());
            }
        } else if (START_END_ACTION.equals(e.getActionCommand())) {
            if (running) {
                logic.end();
                running = false;
                startStopGatheringDataButton.setText(START_BUTTON_LABEL);
            } else {
                logic.start();
                running = true;
                startStopGatheringDataButton.setText(END_BUTTON_LABEL);
            }
        }

    }
}
