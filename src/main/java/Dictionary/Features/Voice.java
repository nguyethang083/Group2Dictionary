package Dictionary.Features;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Voice {
    public static String getVoice(String word) {
        return "https://dict.youdao.com/dictvoice?audio=" + word + "&type=2";
    }

    public static void downloadUrl(String word) {
        try {
            word = word.replaceAll(" ", "%20");
            String audioUrl = getVoice(word);
            URL url = new URL(audioUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = conn.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream("a.wav")) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                System.err.println("Failed to download audio. Response Code: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playVoice(String w) {
        String word = StringProcessing.normalizeStringForVoice(w);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                downloadUrl(word);
                String audioFilePath = "a.wav";
                try {
                    FileInputStream fis = new FileInputStream(audioFilePath);
                    AdvancedPlayer player = new AdvancedPlayer(fis, FactoryRegistry.systemRegistry().createAudioDevice());
                    System.out.println("Playing audio...");
                    player.play();
                } catch (IOException | JavaLayerException e) {
                    e.printStackTrace();
                } finally {
                    // Delete the file after playing the audio
                    File fileToDelete = new File(audioFilePath);
                    if (fileToDelete.exists()) {
                        if (fileToDelete.delete()) {
                            System.out.println("File deleted successfully.");
                        } else {
                            System.err.println("Failed to delete file.");
                        }
                    }
                }
            } catch (Exception e) {
                return;
            }
        } );
    }

    public static void main(String[] args) {
        playVoice("i'm so happy, i'm 6 years old; i'm hangdog");
    }
}
