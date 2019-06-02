package kr.ac.jbnu.se.mm2019Group1.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UsedBookClient {
    private static final String API_BASE_URL = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbey=";
    private static final String CLIENT_ID = "ttbmon8231843002";
    private static final String CLIENT_SECRET = "dgipa2SH9j";

    private AsyncHttpClient client;

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }


    public void getBlog(final String query, JsonHttpResponseHandler handler) {
        String url = getApiUrl(CLIENT_ID+"Query=");
//        client.addHeader("X-Naver-Client-Id",CLIENT_ID);
//        client.addHeader("X-Naver-Client-Secret",CLIENT_SECRET);

        client.get(url + query +"&QueryType=Keyword&SearchTarget=Used&Version=20131101", handler);
    }
}
