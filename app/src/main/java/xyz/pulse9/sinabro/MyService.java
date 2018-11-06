package xyz.pulse9.sinabro;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {
    public MyService() {
    }

    private final String TAG = "jangmin";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(SettingsCheck() && inRoomCheck(remoteMessage.getData().get("roomname")))
        {
            String channelId = "channel";
            String channelName = "sinabro";

            NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
                notifManager.createNotificationChannel(mChannel);
            }
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId);

//
//            Intent intent1 = new Intent("fgsdgdfgg");
//            sendBroadcast(intent1);

            Intent notificationIntent = new Intent(getApplicationContext(), ConnectActivity.class);
            notificationIntent.putExtra("chatroomname", remoteMessage.getData().get("roomname"));
            notificationIntent.putExtra("receiverUID", remoteMessage.getData().get("senderUID"));
//            sendBroadcast(notificationIntent);

            ActivityManager am = (ActivityManager)getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            Log.d("JANGMIN", cn.getClassName());
//            ((Activity)getBaseContext()).finish();

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            int requestID = (int) System.currentTimeMillis();

            PendingIntent pendingIntent
                    = PendingIntent.getActivity(getApplicationContext()
                    , requestID
                    , notificationIntent
                    , PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder
                    .setSmallIcon(R.drawable.heart)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("body"))
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            notifManager.notify(0, mBuilder.build());
        }
    }

    private boolean SettingsCheck(){
        SharedPreferences pref = getSharedPreferences("sina_set", MODE_PRIVATE);
        return pref.getBoolean("notiChk", true);
    }
    private boolean inRoomCheck(String roomname)
    {
        Log.d("JANGMIN", "this is chat name :" + ConnectActivity.chatroomname);
//        SharedPreferences pref = getSharedPreferences("sina_set", MODE_PRIVATE);
//        String cur_room = pref.getString("cur_roomname", "");
        return !ConnectActivity.chatroomname.equals(roomname);
    }
}
