package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.adapters.CommunityAdapter;
import kr.ac.jbnu.se.mm2019Group1.models.Community;

public class CommunityListActivity extends AppCompatActivity {
    private ListView lvCommunity;
    private CommunityAdapter communityAdapter;
    private ProgressBar progress;
    private Button btnWrite;
    private Button btnLode;
    final ArrayList<Community> communities = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_list);
        BtnOnClickListener onClickListener = new BtnOnClickListener() ;
        btnWrite = (Button)findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(onClickListener);
        btnLode = (Button) findViewById(R.id.btnLode);
        btnLode.setOnClickListener(onClickListener);
        lvCommunity = (ListView) findViewById(R.id.lvCommunity);
        makeList();
        setupBlogSelectedListener();

    }

    public void makeList(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Community").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                communities.add(document.toObject(Community.class));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnWrite:
                    Intent intent = new Intent(CommunityListActivity.this, CommunityWriteActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.btnLode:
                    makeList();
                    setupBlogSelectedListener();
                    break;
            }

        }
    };

    public void setupBlogSelectedListener() {
        communityAdapter = new CommunityAdapter(CommunityListActivity.this, communities);
        lvCommunity.setAdapter(communityAdapter);
        progress = (ProgressBar) findViewById(R.id.progressBar_Blog);
        lvCommunity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(CommunityListActivity.this, CommunityDetailActivity.class);
                intent.putExtra("Word", communityAdapter.getItem(position));
                startActivity(intent);
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
        return true;
    }
}
