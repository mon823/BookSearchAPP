package kr.ac.jbnu.se.mm2019Group1.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class BlogClient {

    private static final String API_BASE_URL = "https://openapi.naver.com";
    private static final String CLIENT_ID = "g4uu4rUdz7T5SqQflXxw";
    private static final String CLIENT_SECRET = "dgipa2SH9j";

    private AsyncHttpClient client;

    public BlogClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }


    public void getBlog(final String query, JsonHttpResponseHandler handler) {
        String url = getApiUrl("/v1/search/blog?query=");
        client.addHeader("X-Naver-Client-Id",CLIENT_ID);
        client.addHeader("X-Naver-Client-Secret",CLIENT_SECRET);
        client.get(url + query , handler);
    }
}
