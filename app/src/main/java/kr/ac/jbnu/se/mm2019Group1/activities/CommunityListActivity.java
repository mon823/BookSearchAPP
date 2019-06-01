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
    final ArrayList<Community> communities = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_list);
        BtnOnClickListener onClickListener = new BtnOnClickListener() ;
        btnWrite = (Button)findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(onClickListener);
        lvCommunity = (ListView) findViewById(R.id.lvCommunity);
        progress = (ProgressBar) findViewById(R.id.progress_community);

        setupBlogSelectedListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        communities.clear();
        makeList();
    }

    public void makeList(){
        progress.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Community").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Community community;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                community =document.toObject(Community.class);
                                community.setReference(document.getReference().getId());
                                communities.add(community);
                            }
                            setupBlogSelectedListener();
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
            }

        }
    };

    public void setupBlogSelectedListener() {
        communityAdapter = new CommunityAdapter(CommunityListActivity.this, communities);
        lvCommunity.setAdapter(communityAdapter);
        progress.setVisibility(View.GONE);
        lvCommunity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(CommunityListActivity.this, CommunityDetailActivity.class);
                intent.putExtra("Word", communityAdapter.getItem(position));
//                intent.putExtra("Community",)
//                intent.putExtra("writer", communityAdapter.getItem(position).writer);
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
