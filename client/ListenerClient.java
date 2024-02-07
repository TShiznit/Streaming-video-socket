package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerClient implements ActionListener {
    private FrameClient frame;

    public ListenerClient(FrameClient frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton play = frame.getEcouterButton(); // Correction de getButton() à getEcouterButton()
        if (e.getSource() == play) {
            frame.setChoix(frame.getFileList().getSelectedIndex()); // Correction de getList() à getFileList()
            System.out.println(frame.getChoix());
        }
    }
}
