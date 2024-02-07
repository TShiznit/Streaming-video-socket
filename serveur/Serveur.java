package serveur;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Serveur {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6666)) {
            System.out.println("En attente d'un client");
            Socket socket = serverSocket.accept();

            File songDirectory = new File("./playlist");
            File[] songList = songDirectory.listFiles();

            try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                 DataInputStream dis = new DataInputStream(socket.getInputStream())) {

                oos.writeInt(songList.length);
                for (File file : songList) {
                    oos.writeObject(file.getName());
                    System.out.println(file.getName());
                }

                String title = dis.readUTF();
                File selectedFile = new File("./playlist/" + title);
                System.out.println(selectedFile.getName());

                try (FileInputStream fileInputStream = new FileInputStream(selectedFile);
                     DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

                    if (selectedFile.getName().endsWith(".mp4") || selectedFile.getName().endsWith(".mp3")) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                            dos.write(buffer, 0, bytesRead);
                        }
                        dos.flush();
                        System.out.println("Fichier envoyé");
                    } else if (selectedFile.getName().endsWith(".jpg") || selectedFile.getName().endsWith(".JPG")) {
                        BufferedImage image = ImageIO.read(selectedFile);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(image, "JPG", baos);

                        byte[] size = ByteBuffer.allocate(4).putInt(baos.size()).array();
                        dos.write(size);
                        dos.write(baos.toByteArray());
                        dos.flush();
                        System.out.println("Image envoyée");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
