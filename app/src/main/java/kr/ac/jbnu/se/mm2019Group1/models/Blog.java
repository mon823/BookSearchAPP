package kr.ac.jbnu.se.mm2019Group1.models;

import android.util.Log;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Blog implements  Serializable{
    public String getLink() {
        return link;
    }

    private String link;

    public String getTitle() {
        return title;
    }

    private String title;

    public String getBloggerName() {
        return bloggerName;
    }

    private String bloggerName;

    public String getDescription() {
        return description;
    }

    private String description;
    public static Blog fromJson(JSONObject jsonObject) {
        Blog blog = new Blog();

        try {
            // Deserialize json into object fields
            // Check if a cover edition is available
            blog.link = jsonObject.getString("link");


            String orginalTitle = jsonObject.has("title") ? jsonObject.getString("title") : "";
            String unEscapedXMLTitle = StringEscapeUtils.unescapeXml(orginalTitle);
            String cleanXMLTitle = unEscapedXMLTitle.replaceAll("<[^>]+>", "");
            cleanXMLTitle = cleanXMLTitle.replace("[ DUTCH","");
            cleanXMLTitle = cleanXMLTitle.replace("]","");
            Log.d("tag", "cleanXML :" + cleanXMLTitle);
            blog.title = cleanXMLTitle;
            String orginaldescription = jsonObject.has("description") ? jsonObject.getString("description") : "";
            String unEscapedXMLDescription = StringEscapeUtils.unescapeXml(orginaldescription);
            String cleanXMLDescription = unEscapedXMLDescription.replaceAll("<[^>]+>", "");
            blog.bloggerName = jsonObject.getString("bloggername");
            blog.description = cleanXMLDescription;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return blog;
    }

    // Decodes array of book json results into business model objects
    public static ArrayList<Blog> fromJson(JSONArray jsonArray) {
        ArrayList<Blog> blogs = new ArrayList<Blog>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject blogJson = null;
            try {
                blogJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Blog blog = Blog.fromJson(blogJson);
            if (blog != null) {
                blogs.add(blog);
            }
        }
        return blogs;
    }
}
