package kr.ac.jbnu.se.mm2019Group1.models;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class BookList {
    @SerializedName("meta")
    Meta meta;
    @SerializedName("documents")
    Documents documents;


    public class Meta {
        @SerializedName("is_end")
        Integer isEnd;
        @SerializedName("pageable_count")
        Integer pageableCount;
        @SerializedName("total_count")
        Boolean totalCount;

        public Integer getPageableCount() {
            return pageableCount;
        }

        public Integer getIsEnd() {
            return isEnd;
        }

        public Boolean getTotalCount() {
            return totalCount;
        }
    }

    public class Documents {


        @SerializedName("title")
        String title;
        @SerializedName("context")
        String context;
        @SerializedName("url")
        String url;
        @SerializedName("isbn")
        String isbn;
        @SerializedName("datetime")
        String datetime;
        @SerializedName("authors")
        ArrayList<String> authors;
        @SerializedName("publisher")
        String publisher;
        @SerializedName("translators")
        String translators;
        @SerializedName("price")
        Integer price;
        @SerializedName("sale_price")
        Integer salePrice;
        @SerializedName("status")
        String status;
        @SerializedName("thumbnail")
        String thmbnail;

        public String getTitle() {
            return title;
        }

        public String getContext() {
            return context;
        }

        public String getUrl() {
            return url;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getDatetime() {
            return datetime;
        }

        public ArrayList<String> getAuthors() {
            return authors;
        }

        public String getPublisher() {
            return publisher;
        }

        public String getTranslators() {
            return translators;
        }

        public Integer getPrice() {
            return price;
        }

        public Integer getSalePrice() {
            return salePrice;
        }

        public String getThmbnail() {
            return thmbnail;
        }

        public String getStatus() {
            return status;
        }
    }

    public Meta getMeta() {
        return meta;
    }
    public Documents getDocuments() {
        return documents;
    }
}
