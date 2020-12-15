package com.ryd.gyy.guolinstudy.Activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.ryd.gyy.guolinstudy.R;

import java.io.File;
import java.io.IOException;

/**
 * 发起通知
 */
public class NotificationTest extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "NotificationTest";

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    private Uri imageUri;
    private ImageView picture;

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

//        test();
        initView();

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
        gotoMedia();

    }

    /**
     * 测试结果：带有空格的字符串转换为数字时会报错。
     */
    private void test() {
        String Close_TIME_Hour = "15";//之前
        Log.d("ggg", "\n\r" + "关机时间 ：" + Close_TIME_Hour);
        int i = Integer.parseInt(Close_TIME_Hour);
        Log.e(TAG, "test:  i" + i);

        String Close_TIME_Hour1 = " 15";//之前
        Log.d("ggg", "\n\r" + "关机时间Close_TIME_Hour1 ：" + Close_TIME_Hour1);
        int i1 = Integer.parseInt(Close_TIME_Hour1);
        Log.e(TAG, "test:  i1" + i1);
    }


    private void initView() {
        picture = (ImageView) findViewById(R.id.picture);

//        播放音频
        Button play = (Button) findViewById(R.id.play);
        Button pause = (Button) findViewById(R.id.pause);
        Button stop = (Button) findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);

//        播放视频
        videoView = (VideoView) findViewById(R.id.video_view);
        Button playVideo = (Button) findViewById(R.id.playVideo);
        Button pauseVideo = (Button) findViewById(R.id.pauseVideo);
        Button replayVideo = (Button) findViewById(R.id.replayVideo);
        playVideo.setOnClickListener(this);
        pauseVideo.setOnClickListener(this);
        replayVideo.setOnClickListener(this);
    }


    /**
     * @param view 发布一个聊天的消息通知
     *             点击之后取消的两种方式：setAutoCancel(true)
     *             manager.cancel(1);传入设置的id
     */
    public void sendChatMsg(View view) {
        Intent intent1 = new Intent(this, NotificationActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent1, 0);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);
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
        //SmallIcon指在状态栏显示的图，LargeIcon是右侧显示的大图。setSound播放一段音频文件。setVibrate设置振动，单位毫秒，下标0表示静音时长，1表示振动时长，1表示静音，以此类推。
//        setLights设置LED等一闪一闪的。setDefaults直接用手机的默认设置。setStyle设置大长图和长文本。setPriority设置通知的重要程度。
        Notification notification = new NotificationCompat.Builder(this, "chat")
//                .setAutoCancel(true)
                .setContentTitle("收到一条聊天消息")
                .setContentText("今天中午吃什么？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.red01)
                .setContentIntent(pi)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.google01))
                .setAutoCancel(true)
                .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
                .setVibrate(new long[]{0, 1000, 1000, 1000})
                .setLights(Color.GREEN, 1000, 1000)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.big_image)))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();
        manager.notify(1, notification);
    }


    /**
     * NotificationCompat是适配7.1以下的，之前是Notification.Builder(this)
     *
     * @param view 发布一个订阅的通知
     *             长按显示2条未读数量
     *             <p>
     *             大图小图说明：
     *             Android从5.0系统开始，对于通知栏图标的设计进行了修改。现在Google要求，所有应用程序的通知栏图标(小图)，应该只使用alpha图层（不要带颜色）来进行绘制，而不应该包括RGB图层。
     *             系统给右下角的这个小圆圈默认是设置成灰色的，使用setColor设置成和大图标一个颜色，整体就好看了。
     *             <p>
     *             8.0:
     *             小图还是要用（不带颜色的），然后设置颜色
     */
    public void sendSubscribeMsg(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "subscribe")
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ryd2)
                .setColor(Color.parseColor("#7CB342"))
                .setNumber(2)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ryd))
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

    /**
     * @param view 拍照，注意，不要忘了View view否则会报错
     */
    public void takePhone(View view) {
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        Log.e(TAG, "outputImage.getPath(): " + outputImage.getPath());//  /storage/emulated/0/Android/data/com.ryd.gyy.guolinstudy/cache/output_image.jpg
        Log.e(TAG, "Environment.getExternalStorageDirectory(): " + Environment.getExternalStorageDirectory());//  /storage/emulated/0
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);//android 7.1以下，使用本地真实路径是不安全的。不推荐。
        } else {
            //FileProvider是特殊的内容提供器，使用了内容提供器类似的机制来保护数据，可以选择性的封装uri给外部，从而提高了安全性
//            继承自ContentProvider，使用时需要在清单文件中注册。
            imageUri = FileProvider.getUriForFile(NotificationTest.this, "com.ryd.gyy.guolinstudy.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片:被封装过的uri,所以需要处理
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片：返回的是真实的uri
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * @param view 从相册中选择
     *             需要读取存储的权限
     */
    public void chooseFromAlbum(View view) {
        if (ContextCompat.checkSelfPermission(NotificationTest.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NotificationTest.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");//必须要设置Type。否则会找不到类
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);//  content://com.android.providers.media.documents/document/image%3A2014771
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);//image:2014771
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id：2014771
                String selection = MediaStore.Images.Media._ID + "=" + id;//拿到这张图对应的where条件
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);//查询拿到图片的url
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片url路径显示图片
    }


    /**
     * @param data 返回的一个在图片数据库中的uri，需要通过这个uri去查询Media数据获取图片
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * @param uri
     * @param selection
     * @return 返回的一个在图片数据库中的uri
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * @param imagePath 通过这个uri去查询Media数据获取图片
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//这里使用第三方的图片压缩框架先压缩图片，再进行显示，否则图片的太小，太消耗内存。
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start(); // 开始播放
                }
                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause(); // 暂停播放
                }
                break;
            case R.id.stop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset(); // 将其重置到刚创建的状态，调用类创建的那个时候
                    initMediaPlayer();
                }
                break;
            case R.id.playVideo:
                if (!videoView.isPlaying()) {
                    videoView.start(); // 开始播放
                }
                break;
            case R.id.pauseVideo:
                if (videoView.isPlaying()) {
                    videoView.pause(); // 暂停播放
                }
                break;
            case R.id.replayVideo:
                if (videoView.isPlaying()) {
                    videoView.resume(); // 重新播放
                }
                break;
            default:
                break;
        }
    }


    private void gotoMedia() {
        if (ContextCompat.checkSelfPermission(NotificationTest.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NotificationTest.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        } else {
            initMediaPlayer(); // 初始化MediaPlayer
            initVideoPath(); // 初始化VideoPlayer
        }
    }

    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
            mediaPlayer.setDataSource(file.getPath()); // 指定音频文件的路径
            mediaPlayer.prepare(); // 让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(), "movie.mp4");
        videoView.setVideoPath(file.getPath()); // 指定视频文件的路径
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (videoView != null) {
            videoView.suspend();
        }
    }


}
