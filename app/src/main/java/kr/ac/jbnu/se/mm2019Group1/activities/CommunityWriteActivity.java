package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.models.Community;

public class CommunityWriteActivity extends AppCompatActivity {
    private EditText etTitleCommunity;
    private EditText etContextCommunity;
    private Button btnFinsh;
    private String userName;
    private String userUid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_write);
        etTitleCommunity = (EditText)findViewById(R.id.etTitleCommunity);
        etContextCommunity = (EditText)findViewById(R.id.etContextCommunity);
        btnFinsh = (Button)findViewById(R.id.btnFinsh);
        userUid=LoginActivity.userUUID;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("User").document(userUid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> dataMap = new HashMap<>();
                        dataMap.putAll(document.getData());
                        userName = ""+dataMap.get("NickName");
                        Log.d("TAG", "No such document"+userName);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

        btnFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String getTime = sdf.format(date);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Community community = new Community(etTitleCommunity.getText().toString(),getTime,
                        etContextCommunity.getText().toString(),userName,userUid);
                db.collection("Community")
                        .add(community);
                Intent intent = new Intent(CommunityWriteActivity.this,CommunityListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
