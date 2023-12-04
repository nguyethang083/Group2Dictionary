package Dictionary.Features;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface APIGeneral {
    static HttpRequest buildHttpRequest(String text, String language) {
        return null;
    }

    static HttpResponse<?> executeHttpRequest(HttpRequest request) {
        return null;
    }
}
