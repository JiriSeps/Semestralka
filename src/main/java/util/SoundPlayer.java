/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author fiwie
 */
import javax.sound.sampled.*;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public class SoundPlayer {

    public static void play(String src) {
        CompletableFuture.runAsync(() -> {
            playMusic(src);
        });
    }

    private static void playMusic(String src) {
        try {
            InputStream inputStream = SoundPlayer.class.getResourceAsStream("/" + src);
            if (inputStream == null) {
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            AudioFormat format = audioInputStream.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);

            line.start();

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }
            line.drain();
            line.close();
            audioInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
