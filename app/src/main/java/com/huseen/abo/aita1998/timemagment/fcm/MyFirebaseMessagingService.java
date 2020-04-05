package com.huseen.abo.aita1998.timemagment.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.huseen.abo.aita1998.timemagment.R;
import com.huseen.abo.aita1998.timemagment.splash.SplashActivity;


import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "<<<<FCM";
    private final static AtomicInteger c = new AtomicInteger(0);


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {

//            if (AppSharedData.isUserLogin() && AppSharedData.getUserData() != null) {
//                AppSharedData.setBadgeCount(AppSharedData.getUserData().getUser().getId(),
//                        AppSharedData.getBadgeCount(AppSharedData.getUserData().getUser().getId()) + 1);
//                ShortcutBadger.applyCount(this, AppSharedData.getBadgeCount(AppSharedData.getUserData().getUser().getId()));
//            }

            Intent intent = new Intent(this, SplashActivity.class);
//            intent.putExtra(FROM_WHERE, FROM_NOTIFICATION);
            String title = getString(R.string.app_name);
            String body = "new Notification";
            Map<String, String> objectMap = remoteMessage.getData();
            Log.e("newNotification", "Data == >" + remoteMessage.getData().get("title"));
            if (objectMap != null) {
                sendChatNotification(remoteMessage.getData().get("type"), remoteMessage.getData().get("title"), remoteMessage.getData().get("message"), "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendChatNotification(String type, String title, String message, String receiverUid) {
        Intent intent;
        intent = new Intent(this, SplashActivity.class);

        int id = getID();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, id, intent,
                PendingIntent.FLAG_ONE_SHOT);
        send(pendingIntent, title, message, id);
    }


    private void send(PendingIntent pendingIntent, String title, String message, int id) {
        int notificationNumber = 0;

        NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifyManager.getNotificationChannel(String.valueOf(id));
            if (mChannel == null) {
                mChannel = new NotificationChannel(String.valueOf(id), title, importance);
                mChannel.setDescription(message);
                mChannel.enableVibration(true);
                mChannel.setShowBadge(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifyManager.createNotificationChannel(mChannel);

            }
        }
        builder = new NotificationCompat.Builder(this, String.valueOf(id));
        String GROUP_KEY_WORK = "HiWAY";
        builder.setContentTitle(title)  // required
                .setSmallIcon(R.mipmap.ic_launcher) // required
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(title + " : " + message)
                .setContentText(message)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        builder.setNumber(notificationNumber)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
//        notifyManager.deleteNotificationChannel(String.valueOf(id));
        notifyManager.notify(id, builder.build());
    }

    /**
     * generate message id
     *
     * @return
     */
    private int getID() {
        return c.incrementAndGet();
    }

}
