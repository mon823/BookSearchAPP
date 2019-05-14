package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.models.Book;
import kr.ac.jbnu.se.mm2019Group1.net.BookClient;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView ivBookCover;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvPublisher;
    private TextView tvContents;
    private TextView tvTranslators;
    private TextView tvPrice;
    private TextView tvStatus;
    private TextView tvLink;
    public static String isbn;
    private BookClient client;
    private String tmp;
    private String bookName;
    private Button btnIntersetBook;
    private FirebaseUser user;
    private Book saveBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = LoginActivity.firebaseAuth.getCurrentUser();
        setContentView(R.layout.activity_book_detail);
        // Fetch views
        ivBookCover = (ImageView) findViewById(R.id.ivBookCover);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        tvContents = (TextView) findViewById(R.id.tvContext);
        tvTranslators = (TextView) findViewById(R.id.tvTranslators);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvLink = (TextView) findViewById(R.id.tvLink);
        btnIntersetBook = (Button) findViewById(R.id.btnInterestBook);

        // Use the book to populate the data into our views
        Book book = (Book) getIntent().getSerializableExtra(BookListActivity.BOOK_DETAIL_KEY);
        loadBook(book);
        BtnOnClickListener onClickListener = new BtnOnClickListener() ;
        Button libraryBtn = findViewById(R.id.LibraryButton);
        libraryBtn.setOnClickListener(onClickListener);
        Button btnBlog = findViewById(R.id.BtnBlog);
        btnIntersetBook .setOnClickListener(onClickListener);
        btnBlog.setOnClickListener(onClickListener);

    }

    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.LibraryButton:
                    Intent intentLibrary = new Intent(BookDetailActivity.this, LibraryListActivity.class);
                    intentLibrary.putExtra("BookISBN", isbn);
                    startActivity(intentLibrary);
                    break;
                case R.id.BtnBlog:
                    Intent intentBlog = new Intent(BookDetailActivity.this, BlogListActivity.class);
                    intentBlog.putExtra("book", bookName);
                    startActivity(intentBlog);
                    break;
                case R.id.btnInterestBook:
                    saveInterestBook();

                    break;

            }

        }
    };

    private void saveInterestBook(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        isbn = isbn.trim();
        Log.d("TAG1","1"+isbn+"1");
        db.collection("InterestBook").document(user.getUid())
                .collection("Book").document(isbn)
                .set(saveBook);
        Toast.makeText(BookDetailActivity.this, "저장 성공",Toast.LENGTH_SHORT).show();

    }


    // Populate data for the book
    private void loadBook(Book book) {
        saveBook = book;
        Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher matcher, String url) {
                return "";
            }
        };

        //change activity title
        this.setTitle(book.getTitle());
        // Populate data
        Picasso.with(this).load(Uri.parse(book.getCoverUrl())).error(R.drawable.ic_nocover).into(ivBookCover);
        tvTitle.setText(book.getTitle());
        bookName = book.getTitle();
        tvAuthor.setText(book.getAuthor());
        tvContents.setText(book.getContents());
        tvPublisher.setText(book.getPublisher());
        tvTranslators.setText(book.getTranslators());
        tvPrice.setText(book.getPrice());
        tvStatus.setText(book.getStatus());
        isbn = book.getOpenLibraryId();
        isbn = isbn.substring(isbn.lastIndexOf(" "));
        Log.d("isbn", isbn);
        tmp = book.getUrl();
        Pattern pattern1 = Pattern.compile("상세 링크");
        Linkify.addLinks(tvLink, pattern1, tmp, null, mTransform);
        tmp = book.getTitle();
        // fetch extra book data from books API


        client = new BookClient();

//        client.getExtraBookDetails(book.getOpenLibraryId(), new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    if (response.has("publishers")) {
//                        // display comma separated list of publishers
//                        final JSONArray publisher = response.getJSONArray("publishers");
//                        final int numPublishers = publisher.length();
//                        final String[] publishers = new String[numPublishers];
//                        for (int i = 0; i < numPublishers; ++i) {
//                            publishers[i] = publisher.getString(i);
//                        }
//                        tvPublisher.setText(TextUtils.join(", ", publishers));
//                    }
//                    if (response.has("number_of_pages")) {
//                        tvPageCount.setText(Integer.toString(response.getInt("number_of_pages")) + " pages");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_share_book) {
            setShareIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent() {
        ImageView ivImage = (ImageView) findViewById(R.id.ivBookCover);
        final TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(ivImage);
        // Construct a ShareIntent with link to image
        Intent shareIntent = new Intent();
        // Construct a ShareIntent with link to image
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, (String) tvTitle.getText());
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        // Launch share menu
        startActivity(Intent.createChooser(shareIntent, "Share Image"));

    }

    // Returns the URI path to the Bitmap displayed in cover imageview
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
