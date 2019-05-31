package kr.ac.jbnu.se.mm2019Group1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kr.ac.jbnu.se.mm2019Group1.R;

public class MainActivity extends Activity {

    Button btnSearchBook;
    Button btnCommunity;
    Button btnMyPage;
    Button btnInterest;
    Button community5;
    Button community6;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BtnOnClickListener btnOnClickListener = new BtnOnClickListener();

        setContentView(R.layout.activity_main);
        //버튼
        btnSearchBook = (Button) findViewById(R.id.btnSearchBook);
        btnCommunity = (Button) findViewById(R.id.btnCommunity);
        btnMyPage = (Button) findViewById(R.id.btnMyPage);
        btnInterest = (Button) findViewById(R.id.btnInterest);
        community5 = (Button) findViewById(R.id.community5);
        community6 = (Button) findViewById(R.id.community6);
        //1.값을 가져온다.
        //2.클릭을 감지한다.
        //3.1번의 값을 다음 액티비티로 넘기다.

        btnSearchBook.setOnClickListener(btnOnClickListener);
        btnCommunity.setOnClickListener(btnOnClickListener);
        btnMyPage.setOnClickListener(btnOnClickListener);
        btnInterest.setOnClickListener(btnOnClickListener);
    }



    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnSearchBook:
                    Intent intentLibrary = new Intent(MainActivity.this, BookListActivity.class);
                    startActivity(intentLibrary);
                    break;
                case R.id.btnCommunity:
                    Intent intentBlog = new Intent(MainActivity.this, CommunityListActivity.class);
                    startActivity(intentBlog);
                    break;
                case R.id.btnMyPage:
                    Intent intentMyPage = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intentMyPage);
                    break;
                case R.id.btnInterest:
                    Intent intentInterest = new Intent(MainActivity.this,InterestBookList.class);
                    startActivity(intentInterest);
                    break;
            }

        }
    };

}
