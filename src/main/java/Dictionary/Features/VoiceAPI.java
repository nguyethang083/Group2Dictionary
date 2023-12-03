package Dictionary.Features;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import javazoom.jl.player.Player;

public class VoiceAPI {
    private static final String API_KEY = "71dc1b6092msh3f8ee17bd0825bap163fb1jsnb9be8fbaa2ba";
    // //System.getenv("RAPIDAPI_KEY");  // Read API key from environment variable

    public static void textToSpeech(String text, String language) {
        try {
            HttpRequest request = buildHttpRequest(text, language);
            HttpResponse<InputStream> response = executeHttpRequest(request);
            playAudio(response.body());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static HttpRequest buildHttpRequest(String text, String language) {
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
        return HttpRequest.newBuilder()
                .uri(URI.create("https://text-to-speech-api3.p.rapidapi.com/speak?text=" + encodedText + "&lang=" + language))
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", "text-to-speech-api3.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
    }

    private static HttpResponse<InputStream> executeHttpRequest(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());
    }

    private static void playAudio(InputStream audioStream) throws Exception {
        Player player = new Player(audioStream);
        player.play();
    }

    public static void main(String[] args) {
        textToSpeech("いらっしゃい ませ", "ja");
    }
}
