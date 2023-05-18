package cc.heiz.ulna;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CreateNotification {
    public static Notification notification;
    public static final String CHANNEL_ID = "UlnaChannel";

    public static void createNotification(Context ctx, WebRadio radio, boolean is_playing) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(ctx);
        Bitmap bImage = BitmapFactory.decodeResource(ctx.getResources(), radio.image);

        notification = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.drawable.music_note)
                .setContentTitle(radio.title)
                .setContentText(is_playing ? "Playing" : "Paused")
                .setLargeIcon(bImage)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();


        notification.contentIntent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, BlankActivity.class).setAction(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), PendingIntent.FLAG_IMMUTABLE);


        if (ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        manager.notify(1, notification);

    }

    public static void clear(Context ctx) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(ctx);
        manager.cancelAll();
    }
}
