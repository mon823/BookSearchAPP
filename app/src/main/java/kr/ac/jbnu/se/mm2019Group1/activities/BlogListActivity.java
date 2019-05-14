package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.adapters.BlogAdapter;
import kr.ac.jbnu.se.mm2019Group1.models.Blog;
import kr.ac.jbnu.se.mm2019Group1.net.BlogClient;

public class BlogListActivity extends AppCompatActivity {

    public static final String BLOG_DETAIL_KEY = "blog";
    private ListView lvBlogs;
    private BlogAdapter blogAdapter;
    private BlogClient client;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_list);
        lvBlogs = (ListView) findViewById(R.id.IvBlogs);
        ArrayList<Blog> arrBlogs = new ArrayList<Blog>();
        // initialize the adapter
        blogAdapter = new BlogAdapter(this, arrBlogs);
        // attach the adapter to the ListView
        lvBlogs.setAdapter(blogAdapter);
        progress = (ProgressBar) findViewById(R.id.progressBar_Blog);
        setupBlogSelectedListener();
    }

    public void setupBlogSelectedListener() {
        lvBlogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(blogAdapter.getItem(position).getLink()));
                startActivity(intent);
            }
        });
    }

    // Executes an API call to the OpenLibrary search endpoint, parses the results
    // Converts them into an array of book objects and adds them to the adapter
    private void fetchBooks(String query) {
        // Show progress bar before making network request
        progress.setVisibility(ProgressBar.VISIBLE);
        client = new BlogClient();
        client.getBlog(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // hide progress bar
                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if(response != null) {
                        // Get the docs json array

                        Log.d("booktag", "result:" + response.toString());
                        docs = response.getJSONArray("items");

                        // Parse json array into array of model objects
                        final ArrayList<Blog> blogs = Blog.fromJson(docs);
                        // Remove all books from the adapter
                        blogAdapter.clear();

                        // Load model objects into the adapter
                        for (Blog blog : blogs) {
                            blogAdapter.add(blog); // add book through the adapter
                        }
                        blogAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.d("tag", "result:" + response.toString(), e);
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response)
            {
                Log.d("tag", "result:" + response.toString(), throwable);

                progress.setVisibility(ProgressBar.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Intent intent;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blog_list, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_share_blog);
        intent = getIntent();
        fetchBooks(intent.getStringExtra("book"));
        return true;
    }
;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share_blog) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
