package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (isOnline(context))
        {
            Log.d("Network Available ", "Flag No 0");
        }
        else
        {
            Toast.makeText( context, "인터넷 연결 끊김!", Toast.LENGTH_SHORT ).show();
            Log.d("Network Available ", "Flag No 1");
            Intent i = new Intent(context, AlertDialogActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }

    }

//    private void makeDialog(){
//
//        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
//
//        alt_bld.setMessage("입력하신 정보로 브랜드 인증 요청하시겠습니까?")
//
//                .setCancelable(false)
//
//                .setPositiveButton("네",
//
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                // 네 클릭
//
//                            }
//
//                        })
//
//                .setNegativeButton("아니오",
//
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                // 아니오 클릭. dialog 닫기.
//
//                                dialog.cancel();
//
//                            }
//
//                        });
//
//        AlertDialog alert = alt_bld.create();
//
//
//
//        // 대화창 클릭시 뒷 배경 어두워지는 것 막기
//
//        //alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//
//
//
//        // 대화창 제목 설정
//
//        alert.setTitle("인증 요청");
//
//
//
//
//
//        // 대화창 배경 색  설정
//
//        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,62,79,92)));
//
//
//
//        alert.show();
//
//    }



//    private void sendInternalBroadcast(Context context, String status)
//    {
//        try
//        {
//            Intent intent = new Intent();
//            intent.putExtra("status", status);
//            intent.setAction(NETWORK_CHANGE_ACTION);
//            context.sendBroadcast(intent);
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//    }

    public boolean isOnline(Context context)
    {
        boolean isOnline = false;
        try
        {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            isOnline = (netInfo != null && netInfo.isConnected());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return isOnline;
    }
}
