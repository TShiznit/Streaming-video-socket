package thread;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.io.File;
public class MyThread extends Thread {
    @Override
    public void run() {
        // TODO Auto-generated method stub
        JFrame frame = new JFrame("Video");
        frame.setLocationRelativeTo(null);
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        EmbeddedMediaPlayerComponent mediaplayerComponent = new EmbeddedMediaPlayerComponent();
        File temp = new File("temporary.mkv");
        try {
            this.sleep(500);
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (temp.exists() == true) {
            try {
                frame.add(mediaplayerComponent);
                frame.setVisible(true);
                mediaplayerComponent.mediaPlayer().media().play(temp.getPath());
                System.out.println(temp.length());
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            // temp.delete();
            // System.out.println("temp was deleted");
        }
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int reponse = JOptionPane.showConfirmDialog(frame, "Voulez-vous quitter l'application", "confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (reponse == JOptionPane.YES_OPTION) {
                    if(temp.delete() == true){
                        System.out.println(temp.getName()+ " was deleted");
                        temp.deleteOnExit();
                    }
                    System.exit(0);
                    System.out.println("mbola");

                    //System.exit(0);

                }
            }
        });
    }
}