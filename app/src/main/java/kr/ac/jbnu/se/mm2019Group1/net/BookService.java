package kr.ac.jbnu.se.mm2019Group1.net;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.ac.jbnu.se.mm2019Group1.models.BookList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface BookService {

    @GET("/v2/local/search/address.json")
    Call<JsonArray> getAddress(@Header("Authoriztion") String API_KEY, @Query("x") String lon, @Query("y") String lat, @Query("input_coord") String type);

    @GET("/v3/search/book.json")
    Call<BookList> getBook(@Header("Authriztion")String API_KEY, @Query("target") String target, @Query("query") String query);

                            //@Query("title") String title,@Query("contents") String contents, @Query("isbn") String isbn, @Query("datetime") String datetime,
                            //@Query("authors") String authors, @Query("qublisher")String publisher,@Query("translators") String translators, @Query("price")int price,@Query("sale_price") int sale_price,
                            //@Query("thumbnail") String thumbnailUrl, @Query("status") String status);

}