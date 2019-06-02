package kr.ac.jbnu.se.mm2019Group1.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.Service.MusicService;

public class MainActivity extends AppCompatActivity {
    static public String userName;
    Button btnSearchBook;
    Button btnCommunity;
    Button btnMyPage;
    Button btnInterest;
    Button community5;
    Button community6;
    Switch bgmSwitch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();
        BtnOnClickListener btnOnClickListener = new BtnOnClickListener();

        setContentView(R.layout.activity_main);
        //버튼
        btnSearchBook = (Button) findViewById(R.id.btnSearchBook);
        btnCommunity = (Button) findViewById(R.id.btnCommunity);
        btnMyPage = (Button) findViewById(R.id.btnMyPage);
        btnInterest = (Button) findViewById(R.id.btnInterest);
        community5 = (Button) findViewById(R.id.community5);
        community6 = (Button) findViewById(R.id.community6);

        bgmSwitch = (Switch) findViewById(R.id.BgmSwitch);

        bgmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                intent.putExtra("MESSEAGE_KEY", b);

                startService(intent);
            }

        });
        //1.값을 가져온다.
        //2.클릭을 감지한다.
        //3.1번의 값을 다음 액티비티로 넘기다.

        btnSearchBook.setOnClickListener(btnOnClickListener);
        btnCommunity.setOnClickListener(btnOnClickListener);
        btnMyPage.setOnClickListener(btnOnClickListener);
        btnInterest.setOnClickListener(btnOnClickListener);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("User").document(LoginActivity.firebaseAuth.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> dataMap = new HashMap<>();
                        dataMap.putAll(document.getData());
                        userName = "" + dataMap.get("NickName");
                        Log.d("TAG", "No such document" + userName);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
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


    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem logout = menu.findItem(R.id.logout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                SharedPreferences sharedPreferences = getSharedPreferences("sFile", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", "");
                editor.putString("pass", "");
                editor.putBoolean("google", false);
                editor.commit();

                LoginActivity.firebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                return true;
            }
        });

        return true;
    }

    private void registerReceiver() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(new NetworkChangeReceiver(), intentFilter);
//            intentFilter.addAction(NetworkChangeReceiver.NETWORK_CHANGE_ACTION);
//            registerReceiver(internalNetworkChangeReceiver, intentFilter);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        stopService(intent);
    }
}
