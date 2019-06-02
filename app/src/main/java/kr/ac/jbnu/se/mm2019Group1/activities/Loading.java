package kr.ac.jbnu.se.mm2019Group1.activities;

import android.os.Handler;

public class Loading implements Runnable {
    Handler handler;

    public Loading(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {

        try {

            Thread.sleep(1000); // 실제 로딩시에는 로딩 작업을 처리
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(0); // 핸들러에 메세지를 던지면 스플레시가 종료
    }


}