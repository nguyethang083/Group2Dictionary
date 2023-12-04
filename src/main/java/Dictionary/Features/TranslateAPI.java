package Dictionary.Features;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class TranslateAPI implements APIGeneral {
    private static final String API_KEY = "4fe989700cmsh7f3c2df50281767p1cc134jsn1fc2645ddf35";  // Read API key from environment variable

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
                .uri(URI.create("https://google-translate113.p.rapidapi.com/api/v1/translator/text"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("from=" + sourceLanguage + "&to=" + targetLanguage + "&text=" + encodedText))
                .build();
    }

    private static HttpResponse<String> executeHttpRequest(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String parseTranslationResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        String translatedText = jsonResponse.getString("trans");
        translatedText = StringEscapeUtils.unescapeHtml4(translatedText);

        return translatedText;
    }

    public static void main(String[] args) {
        System.out.println(translateWord("Hằng bị gì thế", "vi", "en"));
    }
}