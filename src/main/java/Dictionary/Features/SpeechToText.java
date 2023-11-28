package Dictionary.Features;

import javazoom.jl.player.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class SpeechToText {
    private static final String API_KEY = "71dc1b6092msh3f8ee17bd0825bap163fb1jsnb9be8fbaa2ba";
    // //System.getenv("RAPIDAPI_KEY");  // Read API key from environment variable

    public static void textToSpeech(String text, String language) {
        try {
            HttpRequest request = buildHttpRequest(text, language);
            HttpResponse<String> response = executeHttpRequest(request);
            System.out.println(response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static HttpRequest buildHttpRequest(String text, String language) {
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
        return HttpRequest.newBuilder()
                .uri(URI.create("https://whisper-speech-to-text1.p.rapidapi.com/speech-to-text"))
                .header("content-type", "multipart/form-data; boundary=---011000010111000001101001")
                .header("X-RapidAPI-Key", "4fe989700cmsh7f3c2df50281767p1cc134jsn1fc2645ddf35")
                .header("X-RapidAPI-Host", "whisper-speech-to-text1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"D:\\hoc2\\b1\\test.mp3\"\r\n\r\n\r\n-----011000010111000001101001--\r\n\r\n"))
                .build();
    }

    public static HttpResponse<String> executeHttpRequest(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }


    public static void main(String[] args) {
        textToSpeech("いらっしゃい ませ", "en-US");
    }


}
