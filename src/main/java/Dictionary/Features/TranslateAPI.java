package Dictionary.Features;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class TranslateAPI {
    private static final String API_KEY = System.getenv("RAPIDAPI_KEY");  // Read API key from environment variable

    public static String translateWord(String textToTranslate, String sourceLanguage, String targetLanguage) {
        try {
            String encodedText = URLEncoder.encode(textToTranslate, StandardCharsets.UTF_8);
            HttpRequest request = buildHttpRequest(encodedText, sourceLanguage, targetLanguage);
            HttpResponse<String> response = executeHttpRequest(request);
            return parseTranslationResponse(response.body());
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private static HttpRequest buildHttpRequest(String encodedText, String sourceLanguage, String targetLanguage) {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("q=" + encodedText + "&target=" + targetLanguage + "&source=" + sourceLanguage))
                .build();
    }

    private static HttpResponse<String> executeHttpRequest(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String parseTranslationResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getJSONObject("data").getJSONArray("translations").getJSONObject(0).getString("translatedText");
    }

    public static void main(String[] args) {
        System.out.println(translateWord("Hello, world!", "en", "vi"));
    }
}