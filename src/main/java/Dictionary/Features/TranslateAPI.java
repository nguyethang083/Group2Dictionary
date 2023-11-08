package Dictionary.Features;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TranslateAPI {
    private final List<String> languages = Arrays.asList("English", "Vietnamese", "Chinese", "French", "Korea", "Japanese");
    private static String sourceLanguage;
    private static String targetLanguage;
    // thich thi dung
    private boolean isToVietnameseLang = true;
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String translateWord(String textToTranslate, String sourceLanguage, String targetLanguage) throws IOException {
        String encodedText = URLEncoder.encode(textToTranslate, StandardCharsets.UTF_8);

        String apiUrl = "https://translate.googleapis.com/translate_a/single?client=gtx&sl="
                + sourceLanguage + "&tl=" + targetLanguage + "&dt=t&text=" + encodedText + "&op=translate";

        HttpGet httpGet = new HttpGet(apiUrl);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String jsonResponse = EntityUtils.toString(entity);
            return parseTranslationResponse(jsonResponse);
        }

        return "Error: Unable to get translation response.";
    }

    public static String parseTranslationResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);

            JSONArray translationArray = jsonArray.getJSONArray(0);

            StringBuilder translatedTextBuilder = new StringBuilder();
            for (int i = 0; i < translationArray.length(); i++) {
                String translationSegment = translationArray.getJSONArray(i).getString(0);
                translatedTextBuilder.append(translationSegment);
            }

            return translatedTextBuilder.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "Error: Unable to parse the translation response.";
        }
    }

    // input là thuộc tính languages ở trên output là source/target
    private String getLanguageCode(String languageName) {
        switch (languageName) {
            case "Chinese":
                return "zh";
            case "Japanese":
                return "ja";
            case "Vietnamese":
                return "vi";
            case "Korea":
                return "ko";
            case "French":
                return "fr";
            // Add more cases for additional languages
            case "English":
            default:
                return "en";
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(translateWord("hello i am hangdog ", "en", "zh"));
    }
}
