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

public class TranslateAPI {
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

    public static void main(String[] args) throws IOException {
        System.out.println(translateWord("hello i love you very much ", "en", ""));
    }
}
