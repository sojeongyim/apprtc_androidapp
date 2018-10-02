package xyz.pulse9.sinabro;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {
    public MyService() {
    }


    private final String TAG = "jangmin";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String channelId = "channel";
        String channelName = "sinabro";

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notifManager.createNotificationChannel(mChannel);
        }
//  List of Running activity
//        ActivityManager activity_manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> task_info = activity_manager.getRunningTasks(9999);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId);
        Intent notificationIntent = new Intent(getApplicationContext(), ConnectActivity.class);
        notificationIntent.putExtra("chatroomname", remoteMessage.getData().get("roomname"));
        notificationIntent.putExtra("receiveruid", remoteMessage.getData().get("receiveruid"));

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

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
