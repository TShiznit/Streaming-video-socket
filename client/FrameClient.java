package client;

import javax.swing.*;
import java.awt.*;

public class FrameClient extends JFrame {
    private JPanel listePanel;
    private JPanel boutonPanel;
    private JButton ecouterButton;
    private JList<String> fileList;
    private ListenerClient listener;
    private int choix = -1;

    public FrameClient(String[] listeFile) {
        super("Deezer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 800);
        this.setLocationRelativeTo(null);

        // Utilisation du style Windows
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        listener = new ListenerClient(this);

        // Panel pour la liste des fichiers
        listePanel = new JPanel(new BorderLayout());
        fileList = new JList<>(listeFile);
        fileList.setPreferredSize(new Dimension(400, 700));
        listePanel.add(new JScrollPane(fileList), BorderLayout.CENTER);

        // Panel pour les boutons
        boutonPanel = new JPanel();
        ecouterButton = new JButton("Écouter");
        ecouterButton.addActionListener(listener);
        boutonPanel.add(ecouterButton);

        // Ajout des panels à la fenêtre
        this.add(listePanel, BorderLayout.CENTER);
        this.add(boutonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public JList<String> getFileList() {
        return fileList;
    }

    public int getChoix() {
        return choix;
    }

    public void setChoix(int choix) {
        this.choix = choix;
    }

    public JButton getEcouterButton() {
        return ecouterButton;
    }
}

