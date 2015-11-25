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

    private JFileChooser chooser;

    public GUI(Logic logic) {
        this.logic = logic;
        initUI();
    }

    void initUI() {
        setTitle("Data Receiver");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(3, 1));

        chooseDirectoryButton = new JButton("Choose directory");
        startStopGatheringDataButton = new JButton("START");
        currentDirectoryLabel = new JLabel();

        try {
            String path = currentDirectory.getCanonicalPath().toString();
            currentDirectoryLabel.setText(path);
        } catch (IOException e) {
            e.printStackTrace();
        }


        add(currentDirectoryLabel);
        add(chooseDirectoryButton);
        add(startStopGatheringDataButton);

        chooseDirectoryButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(currentDirectory);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentDirectory = chooser.getSelectedFile();
            currentDirectoryLabel.setText(currentDirectory.toString());
        }

    }
}
