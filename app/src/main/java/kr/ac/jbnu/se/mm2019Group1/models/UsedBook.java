package kr.ac.jbnu.se.mm2019Group1.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class UsedBook implements Serializable {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private String link;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String author;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    private String pubDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    private String isbn;

    public String getPriceSales() {
        return priceSales;
    }

    public void setPriceSales(String priceSales) {
        this.priceSales = priceSales;
    }

    private String priceSales;

    public String getPriceStandard() {
        return priceStandard;
    }

    public void setPriceStandard(String priceStandard) {
        this.priceStandard = priceStandard;
    }

    private String priceStandard;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    private String cover;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    private String itemId;


    public UsedBook() {

    }

    public UsedBook(String title, String link, String author, String pubDate, String description, String isbn,
                    String priceSales, String priceStandard, String cover,String itemId) {
        this.title = title;
        this.link = link;
        this.author = author;
        this.pubDate = pubDate;
        this.description = description;
        this.isbn = isbn;
        this.priceSales = priceSales;
        this.priceStandard = priceStandard;
        this.cover = cover;
        this.itemId = itemId;
    }

    public static UsedBook fromJson(JSONObject jsonObject) {
        UsedBook usedBook = new UsedBook();
        try {

            usedBook.title = jsonObject.getString("title");
            usedBook.link = jsonObject.getString("link");
            usedBook.author = jsonObject.getString("author");
            usedBook.pubDate = jsonObject.getString("pubDate");
            usedBook.description = jsonObject.getString("description");
            usedBook.isbn = jsonObject.getString("isbn");
            usedBook.priceSales = jsonObject.getString("priceSales");
            usedBook.priceStandard = jsonObject.getString("priceStandard");
            usedBook.cover = jsonObject.getString("cover");
            usedBook.itemId = jsonObject.getString("itemId");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return usedBook;
    }

    public static ArrayList<UsedBook> fromJson(JSONArray jsonArray) {
        ArrayList<UsedBook> usedBooks = new ArrayList<UsedBook>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject usedBookJson = null;
            try {
                usedBookJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            UsedBook usedBook = UsedBook.fromJson(usedBookJson);
            if (usedBook != null) {

                usedBooks.add(usedBook);
            }

        }
        return usedBooks;
    }
}