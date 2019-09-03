package com.tourye.run.receiver;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class LocationAlarmReceiver extends BroadcastReceiver {
    //todo
    //创建Alarm并启动
    Intent alarmIntent ;
    PendingIntent pendingIntent ;
    AlarmManager alarmManager ;

    @Override
    public void onReceive(final Context context, Intent intent) {
        alarmIntent = new Intent(context,LocationAlarmReceiver.class);
        alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        // 每五秒唤醒一次
        long second = 60* 1000;
        // pendingIntent 为发送广播
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+second, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis()+second, pendingIntent);
        }

        //针对熄屏后cpu休眠导致的无法联网、定位失败问题,通过定期点亮屏幕实现联网,会导致cpu无法休眠耗电量增加,谨慎使用
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag")
        PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
//        PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.PARTIAL_WAKE_LOCK, "bright");
//        PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK , "bright");
        //点亮屏幕
        wl.acquire();
        //释放屏幕
        wl.release();
        //todo
        new Thread() {
            @Override
            public void run() {
                super.run();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String path = context.getExternalFilesDir("") + File.separator + "along";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                File location = new File(file, "thread.txt");
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(location, true);
                    StringBuffer stringBuffer = new StringBuffer();
                    Date date = new Date();
                    String format = simpleDateFormat.format(date);
                    stringBuffer.append("至少曾来过"+format+"\n");
                    fileOutputStream.write(stringBuffer.toString().getBytes());
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
