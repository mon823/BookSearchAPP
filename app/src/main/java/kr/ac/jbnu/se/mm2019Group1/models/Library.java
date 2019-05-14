package kr.ac.jbnu.se.mm2019Group1.models;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Library implements Serializable {
    public String getId() {
        return id;
    }

    private String id;

    public String getPlaceName() {
        return placeName;
    }

    private String placeName;

    public String getRoadAddressName() {
        return roadAddressName;
    }

    private String roadAddressName;

    public String getPhone() {
        return phone;
    }

    private String phone;

    public String getPlaceUrl() {
        return placeUrl;
    }

    private String placeUrl;

    public String getDistance() {
        return distance;
    }

    private String distance;

    public String getCategroyName() {
        return categroyName;
    }

    private String categroyName;

    public String getLat() {
        return lat;
    }

    private String lat;

    public String getLng() {
        return lng;
    }

    private String lng;

    public static Library fromJson(JSONObject jsonObject) {
        Library library = new Library();
        JSONObject jsonObjecttmp;
        try {
            library.id = jsonObject.getString("id");
            library.placeName = jsonObject.getString("name");
//            library.roadAddressName = jsonObject.getString("road_address_name");
//            library.phone = jsonObject.getString("phone");
//            library.placeUrl = jsonObject.getString("place_url");
//            library.distance = jsonObject.getString("distance");
            library.categroyName = jsonObject.getString("types");
//            library.roadAddressName = jsonObject.getString("lat");
            jsonObjecttmp = jsonObject.getJSONObject("geometry");
            jsonObjecttmp = jsonObjecttmp.getJSONObject("location");
            library.lat = jsonObjecttmp.getString("lat");
            library.lng = jsonObjecttmp.getString("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return library;
    }

    public static ArrayList<Library> fromJson(JSONArray jsonArray) {
        ArrayList<Library> libraries = new ArrayList<Library>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject libraryJson = null;
            try {
                libraryJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Library library = Library.fromJson(libraryJson);

//            if ("교육,학문 > 학습시설 > 도서관".equals(library.categroyName) ||
//                    "교육,학문 > 학습시설 > 도서관 > 작은도서관".equals(library.categroyName)) {
//                libraries.add(library);
//            }
            if(library != null){
                libraries.add(library);
            }
        }

        return libraries;
    }

}
