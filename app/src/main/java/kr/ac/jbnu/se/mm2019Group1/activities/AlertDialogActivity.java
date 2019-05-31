package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.provider.Settings;


// ALERT DIALOG
// Sources : http://techblogon.com/alert-dialog-with-edittext-in-android-example-with-source-code/

public class AlertDialogActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("인터넷 문제")
                .setMessage("연결 하시겠습니까?\n(취소시 어플 종료)")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);

                        startActivityForResult(intent, 0);

                        dialog.cancel();
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        finish();
                        finishAffinity();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}