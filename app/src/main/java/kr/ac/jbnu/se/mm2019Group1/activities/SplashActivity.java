package kr.ac.jbnu.se.mm2019Group1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class SplashActivity extends Activity {

    Handler handler; // 헨들러 선언
    Loading loader; // 로더 클래스 선언



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TedPermission.with(this)
//                .setPermissionListener(permissionlistener)
//                .setRationaleMessage("현위치를 가져오기 위해서 위치 정보 권한이 필요합니다.")
//                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있어요.")
//                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
//                .check();

        init();

        loader = new Loading(handler);
        new Thread(loader).start();

//        Handler handler = new Handler();
//        handler.postDelayed(new splashhandler(), 3000); // 1초 후에 hd handler 실행  3000ms = 3초
    }

    private class splashhandler implements Runnable {
        public void run() {
            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void init(){

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
//                Toast.makeText(getApplicationContext(), "Loading Complete",Toast.LENGTH_LONG).show();
                finish();
            }
        };
    }


    @Override
    public void onBackPressed() {

    }
}


