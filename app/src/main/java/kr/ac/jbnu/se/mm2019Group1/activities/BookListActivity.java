package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.adapters.BookAdapter;
import kr.ac.jbnu.se.mm2019Group1.models.Book;
import kr.ac.jbnu.se.mm2019Group1.net.BookClient;

import com.kakao.sdk.newtoneapi.SpeechRecognizerActivity;
import com.kakao.sdk.newtoneapi.SpeechRecognizerClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class BookListActivity extends AppCompatActivity {
    public static final String BOOK_DETAIL_KEY = "book";
    private ListView lvBooks;
    private BookAdapter bookAdapter;
    private BookClient client;
    private ProgressBar progress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        lvBooks = (ListView) findViewById(R.id.lvBooks);

        ArrayList<Book> aBooks = new ArrayList<Book>();
        // initialize the adapter
        bookAdapter = new BookAdapter(this, aBooks);
        // attach the adapter to the ListView
        lvBooks.setAdapter(bookAdapter);



        progress = (ProgressBar) findViewById(R.id.progress_book);
        setupBookSelectedListener();
    }

    public void setupBookSelectedListener() {
        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
                intent.putExtra(BOOK_DETAIL_KEY, bookAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    // Executes an API call to the OpenLibrary search endpoint, parses the results
    // Converts them into an array of book objects and adds them to the adapter
    private void fetchBooks(String query) {
        // Show progress bar before making network request
        progress.setVisibility(ProgressBar.VISIBLE);
        client = new BookClient();
        client.getBooks(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // hide progress bar
                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if(response != null) {
                        // Get the docs json array

                        Log.d("booktag", "result:" + response.toString());
                        docs = response.getJSONArray("documents");

                        // Parse json array into array of model objects
                        final ArrayList<Book> books = Book.fromJson(docs);
                        // Remove all books from the adapter
                        bookAdapter.clear();

                        // Load model objects into the adapter
                        for (Book book : books) {
                            bookAdapter.add(book); // add book through the adapter
                        }
                        bookAdapter.notifyDataSetChanged();
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
                fetchBooks(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                BookListActivity.this.setTitle(query);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 0:
                    fetchBooks(data.getStringArrayListExtra("result_array").get(0));
                    break;
            }
        }
    }


}
