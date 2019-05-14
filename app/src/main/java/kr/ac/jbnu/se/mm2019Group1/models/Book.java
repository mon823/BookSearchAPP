package kr.ac.jbnu.se.mm2019Group1.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {

    private String openLibraryId;
    private String author;
    private String title;
    private String coverUrl;
    private String contents;
    private String status;
    private String translators;
    private String price;
    private String publisher;
    private String url;

    public void setOpenLibraryId(String openLibraryId) {
        this.openLibraryId = openLibraryId;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setTranslators(String translators) {
        this.translators = translators;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getOpenLibraryId() {
        return openLibraryId;
    }
    public String getAuthor() {
        return author;
    }
    // Get medium sized book cover from covers API
    public String getCoverUrl() {
        return coverUrl;
    }
    public String getContents() {
        return contents;
    }
    public String getStatus() {
        return status;
    }
    public String getTranslators() {
        return translators;
    }
    public String getPrice() {
        return price;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getTitle() {
        return title;
    }
    public String getUrl() {
        return url;
    }

    public Book() {

    }

    public Book(String openLibraryId, String author, String title, String coverUrl, String contents
                , String status, String translators, String price, String publisher,
                String url) {
        this.openLibraryId = openLibraryId;
        this.author = author;
        this.title = title;
        this.coverUrl = coverUrl;
        this.contents = contents;
        this.status = status;
        this.translators = translators;
        this.price = price;
        this.publisher = publisher;
        this.url = url;
    }





    // Returns a Book given the expected JSON
    public static Book fromJson(JSONObject jsonObject) {
        Book book = new Book();
        try {
            // Deserialize json into object fields
            // Check if a cover edition is available
            if (jsonObject.has("isbn")) {
                book.openLibraryId = jsonObject.getString("isbn");
            }
            if (jsonObject.has("contents")) {
                book.contents = jsonObject.getString("contents");
            }
            if (jsonObject.has("translators")) {
                book.translators = jsonObject.getString("translators");
                if (book.translators == ("[]")) {
                    book.translators = "No Data";
                }
            }
            if (jsonObject.has("price")) {
                book.price = jsonObject.getString("price");
                book.price = jsonObject.getString("sale_price");
            }
            book.url = jsonObject.getString("url");
            book.publisher = jsonObject.getString("publisher");
            book.status = jsonObject.getString("status");
            book.title = jsonObject.getString("title");
            book.author = jsonObject.getString("authors");
            book.coverUrl = jsonObject.getString("thumbnail");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return book;
    }


    // Decodes array of book json results into business model objects
    public static ArrayList<Book> fromJson(JSONArray jsonArray) {
        ArrayList<Book> books = new ArrayList<Book>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject bookJson = null;
            try {
                bookJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Book book = Book.fromJson(bookJson);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }
}
