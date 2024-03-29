package com.classtune.classtuneuni.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;


public class MyApplication extends Application {
    //DatabaseHandler databaseHandler;
    //private SQLiteDatabase mDatabase;
    private static MyApplication mInstance;
    private static Context context;
    //ApiInterface apiInterface;

    private static PendingIntent pendingIntent;
    private static Intent intent1;


    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static void setmInstance(MyApplication mInstance) {
        MyApplication.mInstance = mInstance;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //DBManager.initializeInstance();
        mInstance = this;
        context = this;
        MultiDex.install(this);
//        Thread.setDefaultUncaughtExceptionHandler(new LocalFileUncaughtExceptionHandler(this,
//                Thread.getDefaultUncaughtExceptionHandler()));
    }

    public void dbInitialize() {
        //DBManager.initializeInstance();
        // databaseHandler = new DatabaseHandler();
        //mDatabase = databaseHandler.getWritableDatabase();
    }


    public static void sendMyNotification(final String subject, final String message, final String type, final String id, final String target) {

        ActivityManager activityManager = (ActivityManager) mInstance.getSystemService(ACTIVITY_SERVICE);
// The first in the list of RunningTasks is always the foreground task.
        ActivityManager.RunningTaskInfo foregroundTaskInfo = activityManager.getRunningTasks(1).get(0);
        //Log.e(TAG, "foregroundTaskInfo: " + foregroundTaskInfo);

        String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();
        String topActivityRun = foregroundTaskInfo.topActivity.toString();
        String name = foregroundTaskPackageName + "/" + topActivityRun;

//        if (topActivityRun.equals("ComponentInfo{com.classtune.classtuneuni/com.classtune.classtuneuni.activity.MainActivity}"))
////            HomeFragment.openDialog();
//        {
//
//
//            final Intent intent = new Intent("target_url_token");
//            final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(MyApplication.getInstance());
//            intent.putExtra("subject", subject);
//            intent.putExtra("message", message);
//            intent.putExtra("target_type", type);
//            intent.putExtra("target_id", id);
//            intent.putExtra("target_view", target);
//            broadcastManager.sendBroadcast(intent);
//
//
//        } else {

            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) mInstance
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Intent notificationIntent = new Intent(mInstance, MainActivity.class);
            notificationIntent.putExtra("subject", subject);
            notificationIntent.putExtra("message", message);
            notificationIntent.putExtra("target_type", type);
            notificationIntent.putExtra("target_id", id);
            notificationIntent.putExtra("target_view", target);
            PendingIntent pendingNotifyIntent = PendingIntent.getActivity(mInstance, 1111, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(mInstance)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setColor(ContextCompat.getColor(context, R.color.appColor))
                    .setContentTitle(subject)
                    .setContentText(message)
                    .setOngoing(false)
                    //.setSound(true)
                    .setAutoCancel(true).setWhen(when)
                    .setContentIntent(pendingNotifyIntent)
                    .setVibrate(new long[]{3000, 3000});

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "edoozz", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.WHITE);
                notificationChannel.enableVibration(true);
//            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert notificationManager != null;
                mNotifyBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            notificationManager.notify(0, mNotifyBuilder.build());
       // }

    }

}

