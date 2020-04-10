package com.ryd.gyy.guolinstudy.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.ryd.gyy.guolinstudy.Activity.DownLoadActivity;
import com.ryd.gyy.guolinstudy.Interface.DownloadListener;
import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.Thread.DownloadTask;

import java.io.File;

public class DownloadService extends Service {

    private static final String TAG = "DownloadService";

    public DownloadTask downloadTask;
    public String downloadUrl;

    public DownloadListener listener = new DownloadListener() {

        /**
         * @param progress 这里每下载完成1%都会弹窗通知（getNotification）用户，开始下载的时候也弹窗提醒，因为用的是
         *  同一个Notification，实际中最好不要用弹窗，所以改用了IMPORTANCE_DEFAULT，并且关闭声音(setSound)。
         */
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            //下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success", -1));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            //下载失败时将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Failed", -1));
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(DownloadService.this, "Download Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Download Canceled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Download Error", Toast.LENGTH_SHORT).show();
        }
    };

    public DownloadService() {
    }

    public DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public Notification getNotification(String title, int progress) {
        //注意：8.0产生Notification之前需要先设置Notification的channel_ID,否则报错invalid channel for service notification
        String CHANNEL_ID = "download";
        String CHANNEL_NAME = "下载通知";
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setSound(null, null);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(this, DownLoadActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //因为channelId是8.0之后才有的，所以这里要判断
            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(this);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setSound(null);
        builder.setContentTitle(title);
        if (progress >= 0) {
            //当progress大于或者等于0时才需显示下载进度,100是总进度
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }


    /**
     * 注意：必须要加上public，不加或者private都是不行的
     */
    public class DownloadBinder extends Binder {

        public void startDownload(String url) {
            if (downloadTask == null) {
            }
            downloadUrl = url;
            downloadTask = new DownloadTask(listener);
            downloadTask.execute(downloadUrl);

            //startForeground是开启前台服务。
            startForeground(1, getNotification("Downloading...", 0));
            Toast.makeText(DownloadService.this, "Download ...", Toast.LENGTH_SHORT).show();
        }

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            }
            if (downloadUrl != null) {
                //取消下载时需将文件删除，并将通知关闭
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory + fileName);
                if (file.exists()) {
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

