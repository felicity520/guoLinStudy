package com.ryd.gyy.guolinstudy.Activity;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.ryd.gyy.guolinstudy.R;

public class NotificationTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            //渠道ID、渠道名称以及重要等级
            //渠道ID可以随便定义，只要保证全局唯一性就可以。
            // 渠道名称是给用户看的，需要能够表达清楚这个渠道的用途，----在应用通知里面可以看到----
            // 重要等级的不同则会决定通知的不同行为，当然这里只是初始状态下的重要等级，用户可以随时手动更改某个渠道的重要等级，App是无法干预的。
            //IMPORTANCE_HIGH/IMPORTANCE_DEFAULT/IMPORTANCE_LOW/IMPORTANCE_MIN ----用户可以在设置中更改----
            createNotificationChannel(channelId, channelName, importance);

        }


    }


    /**
     * @param view 发布一个聊天的消息通知
     */
    public void sendChatMsg(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //判断应用是否有打开聊天消息的通道，如果没有跳转到设置提示用户打开
        NotificationChannel channel = manager.getNotificationChannel("chat");
        if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
            Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
            startActivity(intent);
            Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
        }
        //这里的channelId和上面的保持一致
        //SmallIcon指在状态栏显示的图，LargeIcon是右侧显示的大图
        Notification notification = new NotificationCompat.Builder(this, "chat")
                .setContentTitle("收到一条聊天消息")
                .setContentText("今天中午吃什么？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.red01)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.google01))
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);
    }


    /**
     * @param view 发布一个订阅的通知
     *             长按显示2条未读数量
     */
    public void sendSubscribeMsg(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "subscribe")
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.red01)
                .setNumber(2)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.google01))
                .setAutoCancel(true)
                .build();
        manager.notify(2, notification);
    }


    /**
     * @param view 删除Chat ID，会在应用通知显示已删除内容，如果有再重新创建，之前删除的内容就不会显示了
     */
    public void deleteChatID(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.deleteNotificationChannel("chat");
    }


    /**
     * 保证在通知弹出之前调用
     */
    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true);//允许这个渠道下的通知显示角标，默认打开的不设置也行
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }


}
