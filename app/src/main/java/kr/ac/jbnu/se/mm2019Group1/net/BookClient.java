package kr.ac.jbnu.se.mm2019Group1.net;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.ac.jbnu.se.mm2019Group1.models.BookList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BookClient {
    private static final String API_BASE_URL = "https://dapi.kakao.com/";
    private static final String API_KEY = "KakaoAK 7bb0633c5067f1f71191a2c9120517a6";

    private BookService bookService;
    private BookList bookList;
    private AsyncHttpClient client;

    public BookClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getBooks(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("/v3/search/book?target=title&query=");
            client.addHeader("Authorization", API_KEY);
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

//    public void getBook(final String query) {
//        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        final BookService service = retrofit.create(BookService.class);
//        Call<BookList> call = service.getBook(API_KEY, "title", query);
//
//
//        call.enqueue(new Callback<BookList>() {
//            @Override
//            public void onResponse(Call<BookList> call, Response<BookList> response) {
//                // Code...
//                if(response.isSuccessful()){
//                    bookList = response.body();
//                    Log.d("booktag","reponse.raw:"+response.raw());
//                }
//                else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BookList> call, Throwable t) {
//                // Code...
//            }
//
//        });
//    }

}
