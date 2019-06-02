package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.adapters.CommentAdapter;
import kr.ac.jbnu.se.mm2019Group1.models.Comment;
import kr.ac.jbnu.se.mm2019Group1.models.Community;

public class CommunityDetailActivity extends AppCompatActivity {
    private TextView tvTitleCommunity;
    private TextView tvContextCommunity;
    private TextView tvWriterCommunity;
    private ListView lvCommnet;
    private EditText etComment;
    private Button btComment;
    private View header;
    private CommentAdapter commentAdapter;
    private Community community;
    final ArrayList<Comment> comments = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);
        etComment = (EditText) findViewById(R.id.ETComment);
        btComment = (Button) findViewById(R.id.BTcomment);
        BtnOnClickListener onClickListener = new BtnOnClickListener() ;
        btComment.setOnClickListener(onClickListener);
        lvCommnet = (ListView) findViewById(R.id.LVcomment);

        community = (Community)getIntent().getSerializableExtra("Word");

        header = getLayoutInflater().inflate(R.layout.header_comment, null, false);
        tvTitleCommunity = (TextView) header.findViewById(R.id.tvTitleCommunitys);
        tvContextCommunity = (TextView) header.findViewById( R.id.tvContextCommunity);
        tvWriterCommunity = (TextView) header.findViewById((R.id.tvWriterCommunity));
        lvCommnet.addHeaderView(header);
        loadCommunity(community);
        makeCommentList();
    }

    private  void loadCommunity(Community community){
        tvTitleCommunity.setText(community.getTitle());
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", community.getTitle());
        setResult(RESULT_OK, resultIntent);
        tvContextCommunity.setText(community.getMainText());
        tvWriterCommunity.setText(community.getWriter());

    }
    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.BTcomment:
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String getTime = sdf.format(date);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Comment comment = new Comment(etComment.getText().toString(),getTime,MainActivity.userName,LoginActivity.userUUID);
                    db.collection("Community").document(community.getReference())
                            .collection("Comment")
                            .add(comment);

                    etComment.setText("");
                    makeCommentList();
                    break;
            }

        }
    };

    private void makeCommentList(){
        comments.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Community").document(community.getReference())
                .collection("Comment")
                .orderBy("date", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                comments.add(document.toObject(Comment.class));
                            }
                            setComment();
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void setComment(){
        commentAdapter = new CommentAdapter(CommunityDetailActivity.this, comments);

        lvCommnet.setAdapter(commentAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return true;
    }
}
