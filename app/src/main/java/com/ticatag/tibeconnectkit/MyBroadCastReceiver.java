package com.ticatag.tibeconnectkit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.ticatag.tibeaconconnect.broadcast.StatusConnectBroadcastReceiver;

/**
 * Created by quentinlecam on 09/03/2017.
 *
 */
public class MyBroadCastReceiver extends StatusConnectBroadcastReceiver {


    private void playNotificationWithSound(Context context, String title, String message) {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(message);
        mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);

        //https://developer.android.com/guide/topics/ui/notifiers/notifications.html

        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    protected void receiveCommand(Context context, String macAddress, Status status) {
        playNotificationWithSound(context, macAddress, status.toString());
    }
}
