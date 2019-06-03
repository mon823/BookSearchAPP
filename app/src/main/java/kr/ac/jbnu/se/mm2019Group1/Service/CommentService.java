package kr.ac.jbnu.se.mm2019Group1.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.activities.LoginActivity;

public class CommentService extends Service {

    private String name;
    private NotificationManager notificationManager;
    private  FirebaseFirestore db;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("asdf1","asdf");

        db.collection("Community")
                .whereEqualTo("writerUid",name)
                .addSnapshotListener(MetadataChanges.INCLUDE,new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG1", "Listen failed.", e);
                            return;
                        }
                        Log.d("TAG1", "변경감지"+value);

                        List<Integer> cities = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("commentCount") != null) {
                                cities.add(Integer.parseInt(doc.get("commentCount").toString()));
                            }
                        }
                        for(int i=0;i<cities.size();i++) {
                            Log.d("TAG1", "좀 봐보자"+cities.get(i));
                        }
                        int tmp = cities.size();
                        if(value != null) {
                            notificationManager.notify(3, makeNotification("댓글!","댓글!"));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
                            }
                        }else{
                            Log.d("TAG1", "빈 변경감지"+value);
                        }

                    }
                });



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        name = LoginActivity.userUUID;
        notificationManager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        db = FirebaseFirestore.getInstance();



    }

    private Notification makeNotification(String Title,String Text) {
        // 알람바 변수
        Notification notification = null;

        // 알람바를 생성하기 위한 빌더 변수, 빌더 패턴
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, "default");

        // 빌더에 설정
        notifBuilder.setContentTitle(Title)
                .setContentInfo("Content Info")
                .setContentText(Text)
                .setCategory("Category")
                .setSmallIcon(R.drawable.ic_launcher);

        // 빌더를 통해 알람바 생성
        notification = notifBuilder.build();
        return notification;
    }

    @Override
    public void onDestroy() {

    }
}
