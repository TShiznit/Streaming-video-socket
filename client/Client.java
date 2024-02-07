package client;

import java.io.*;
import java.net.*;
import java.util.Vector;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import javazoom.jl.player.advanced.AdvancedPlayer;
import thread.MyThread;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.nio.ByteBuffer;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 6666);
        InputStream data = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(data);
        int listeSize = ois.readInt();
        System.out.println("vous avez " + listeSize + " fichiers dans votre serveur");

        String[] song = new String[listeSize];
        String[] nameSong = new String[listeSize];
        for (int i = 0; i < listeSize; i++) {
            song[i] = (String) ois.readObject();
            System.out.println(song[i]);
        }
        FrameClient FC = new FrameClient(song);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        System.out.println(FC.getChoix());
        while (FC.getChoix() == -1) {
            System.out.println("test");
        }
        dos.writeUTF(song[FC.getChoix()]);
        System.out.println("lecture de " + song[FC.getChoix()]);
        // System.out.println(array.length);
        int nb = 0;
        int taille = 1024*500;
        byte[] bytes = new byte[taille];
        byte b;



        if (song[FC.getChoix()].endsWith(".mp3")) {
            while (true) {
                DataInputStream stream = new DataInputStream(data);
                AdvancedPlayer player = new AdvancedPlayer(stream);
                if (stream.available() != 0) {
                    System.out.println(stream.available() + " bytes");
                    player.play();
                } else {
                    System.out.println("Terminée");
                    player.close();
                    break;
                }
            }
        }
        if (song[FC.getChoix()].endsWith(".mp4")) {
            File temp = new File("temporary.mkv");
            if (temp.exists()) {
                temp.delete();
                temp.createNewFile();
                System.out.println("file created " + temp.getName());
            } else {
                temp.createNewFile();
                System.out.println("file created " + temp.getName());
            }
            MyThread T = new MyThread();
            T.start();
            while ((nb = data.read(bytes)) != -1) {
                FileOutputStream fos = new FileOutputStream(temp, true);
                fos.write(bytes, 0, nb);
                fos.close();
            }
        }


        if (song[FC.getChoix()].endsWith(".JPG") || song[FC.getChoix()].endsWith(".jpg")) {
            // Vector<Byte> bites = new Vector<>();

            DataInputStream stream = new DataInputStream(data);
            byte[] sizeAr = new byte[4];
            stream.readFully(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
            byte[] imageAr = new byte[size];
            int totalRead = 0;
            int currentRead;
            while (totalRead < size && (currentRead = stream.read(imageAr,totalRead,size-totalRead)) != -1) {
                totalRead += currentRead;
            }
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
            JFrame frame = new JFrame();
            frame.setLocationRelativeTo(null);
            frame.setSize(500,500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ImageIcon icon = new ImageIcon(image);
            frame.add(new JLabel(icon));
            frame.pack();
            frame.setVisible(true);


        }
    }
}

// System.out.println("----------");
// System.out.println(stream.available());
// b = stream.readByte();
// bites.add(b);
// System.out.println(bites.size());
// Object[] tab = bites.toArray();
// byte[] tobite = new byte[tab.length];
// for (int i = 0; i < tobite.length; i++) {
//     tobite[i] = (byte)tab[i];
// }
// ByteArrayInputStream bai = new ByteArrayInputStream(tobite);
// BufferedImage image = ImageIO.read(bai);