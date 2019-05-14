package kr.ac.jbnu.se.mm2019Group1.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.models.Community;

public class CommunityDetailActivity extends AppCompatActivity {
    private TextView tvTitleCommunity;
    private TextView tvContextCommunity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);
        tvTitleCommunity = (TextView)findViewById(R.id.tvTitleCommunitys);
        tvContextCommunity = (TextView) findViewById( R.id.tvContextCommunity);


        Community community = (Community)getIntent().getSerializableExtra("Word");

        loadCommunity(community);
    }
    private  void loadCommunity(Community community){
        tvTitleCommunity.setText(community.getTitle());
        tvContextCommunity.setText(community.getMainText());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return true;
    }
}
