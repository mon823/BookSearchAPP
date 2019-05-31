package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.adapters.BookAdapter;
import kr.ac.jbnu.se.mm2019Group1.models.Book;

public class InterestBookList extends AppCompatActivity {
    private ListView lvBook;
    private BookAdapter bookAdapter;
    private ProgressBar progress;
    private ArrayList<Book> intersetBook;
//    private Button btnRelode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_list);
        lvBook = (ListView) findViewById(R.id.lvBook);
//        btnRelode = (Button) findViewById(R.id.btnRelode);
//        btnRelode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                intersetBook.clear();
//                makeBook();
//                setupBookSelectedListener();
//            }
//        });
        progress = (ProgressBar) findViewById(R.id.progress_book_interest);
        intersetBook = new ArrayList<Book>();
        intersetBook.clear();

        setupBookSelectedListener();



    }

    @Override
    protected void onResume() {
        super.onResume();
        makeBook();
    }

    public void makeBook() {
        progress.setVisibility(View.VISIBLE);
        String userUID = LoginActivity.userUUID;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("InterestBook").document(userUID);
        docRef.collection("Book").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                intersetBook.add(document.toObject(Book.class));
                            }
                            setupBookSelectedListener();
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    public void setupBookSelectedListener() {
        bookAdapter = new BookAdapter(this, intersetBook);
        lvBook.setAdapter(bookAdapter);
        progress.setVisibility(View.GONE);
        progress = (ProgressBar) findViewById(R.id.progress_book_interest);
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(InterestBookList.this, BookDetailActivity.class);
                intent.putExtra("Book", bookAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return true;
    }
}