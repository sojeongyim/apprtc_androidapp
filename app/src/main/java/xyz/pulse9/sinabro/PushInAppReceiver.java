package xyz.pulse9.sinabro;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by ssoww on 2018-09-19.
 */

public class PushInAppReceiver extends BroadcastReceiver {

    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);

        //
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 9, 20, 11, 28, 0);
        //

        builder.setSmallIcon(R.drawable.login_logo).setTicker("SINABRO").setWhen(calendar.getInstance().getTimeInMillis()) //변경
                .setNumber(1).setContentTitle("화상채팅 알림").setContentText("화상채팅<>분전")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

        notificationmanager.notify(1, builder.build());
    }


}
