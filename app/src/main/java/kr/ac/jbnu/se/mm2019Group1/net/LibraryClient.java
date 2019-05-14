package kr.ac.jbnu.se.mm2019Group1.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LibraryClient {
    private static final String API_BASE_URL = "https://maps.googleapis.com/";
    private static final String API_KEY = "AIzaSyBx1pfDpViDxl8-FdBvj81qb-vCVq2cur8";

    private AsyncHttpClient client;

    public LibraryClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }


    public void getLibrary(final String query, String location, JsonHttpResponseHandler handler) {
        String url = getApiUrl("maps/api/place/nearbysearch/json?");
        client.get(url + location + API_KEY, handler);
    }
}
