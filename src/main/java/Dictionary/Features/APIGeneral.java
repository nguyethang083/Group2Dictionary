package Dictionary.Features;

import java.io.InputStream;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface APIGeneral {
    public static HttpRequest buildHttpRequest(String text, String language) {
        return null;
    }

    public static HttpResponse<?> executeHttpRequest(HttpRequest request) {
        return null;
    }
}
