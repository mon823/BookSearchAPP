package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.kakao.sdk.newtoneapi.SpeechRecognizerActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.adapters.UsedBookAdapter;
import kr.ac.jbnu.se.mm2019Group1.models.UsedBook;
import kr.ac.jbnu.se.mm2019Group1.net.UsedBookClient;

public class UsedBookActivity extends AppCompatActivity {
    private ListView lvUsedBook;
    private UsedBookAdapter usedBookAdapter;
    private ProgressBar progressBar;

    private UsedBookClient client;


    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_usedbook_list);
        lvUsedBook = (ListView) findViewById(R.id.lvUsedBook);
        progressBar = (ProgressBar) findViewById(R.id.progress_used_book);
        ArrayList<UsedBook> usedBooks = new ArrayList<UsedBook>();


        usedBookAdapter = new UsedBookAdapter(this, usedBooks);
        lvUsedBook.setAdapter(usedBookAdapter);
        progressBar.setVisibility(View.GONE);

        usedBookAdapter.clear();

        setupBookSelectedListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void fetchBooks(String query) {
        // Show progress bar before making network request
//        progressBar.setVisibility(View.VISIBLE);
        client = new UsedBookClient();
        client.getUsedbooks(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // hide progress bar
//                    progressBar.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if (response != null) {
                        // Get the docs json array

                        Log.d("booktag", "result:" + response.toString());
                        docs = response.getJSONArray("item");

                        // Parse json array into array of model objects
                        final ArrayList<UsedBook> usedBooks = UsedBook.fromJson(docs);
                        // Remove all books from the adapter
                        usedBookAdapter.clear();

                        // Load model objects into the adapter
                        for (UsedBook usedBook : usedBooks) {
                            usedBookAdapter.add(usedBook); // add book through the adapter
                        }
                        usedBookAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.d("tag", "result:" + response.toString(), e);
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Log.d("tag", "result:" + response.toString(), throwable);

                progressBar.setVisibility(ProgressBar.GONE);
            }
        });
    }

    public void setupBookSelectedListener() {
        lvUsedBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Uri uri = Uri.parse("https://www.aladin.co.kr/shop/UsedShop/wuseditemall.aspx?ItemId=" +
                        usedBookAdapter.getItem(position).getItemId());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem voiceSearch = menu.findItem(R.id.action_voice_serch);
        voiceSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent(getApplicationContext(), VoiceRecoActivity.class);

//                if (serviceType.equals(SpeechRecognizerClient.SERVICE_TYPE_WORD)) {
//                    EditText words = (EditText)findViewById(R.id.words_edit);
//                    String wordList = words.getText().toString();
//
//                    Log.i("SpeechSampleActivity", "word list : " + wordList.replace('\n', ','));
//
//                    i.putExtra(SpeechRecognizerActivity.EXTRA_KEY_USER_DICTIONARY, wordList);
//                }

                i.putExtra(SpeechRecognizerActivity.EXTRA_KEY_SERVICE_TYPE, "WEB");

                startActivityForResult(i, 0);
                return true;
            }
        });
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                //                searchArrayList.add(0,new Search(query));

                fetchBooks(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                UsedBookActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }


        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
