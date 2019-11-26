package com.ryd.gyy.guolinstudy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ryd.gyy.guolinstudy.R;


/**
 * 而使用Glide，我们就完全不用担心图片内存浪费，甚至是内存溢出的问题。
 * 因为Glide从来都不会直接将图片的完整尺寸全部加载到内存中，而是用多少加载多少。
 * Glide会自动判断ImageView的大小，然后只将这么大的图片像素加载到内存当中，帮助我们节省内存开支。
 */
public class GlideActivity extends AppCompatActivity {

    private static final String TAG = "GlideActivity";

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        initView();
        initData();
    }

    private void initData() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.d("TAG", "Max memory is " + maxMemory + "KB");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.id.start_download, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;

    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    /**
     * @param view
     */
    public void loadImage(View view) {
        // 加载本地图片
        //点击“清除数据”之后，系统会删除data下对应的包名——》/sdcard/Android/data下对应的包名
        // 点击“清除缓存”之后，会删除cache目录——》/sdcard/Android/data/应用包名/cache/目录
        //Log.i(TAG, "getExternalCacheDir(): " + getExternalCacheDir());//storage/emulated/0/Android/data/com.ryd.gyy.guolinstudy/cache
        //Log.i(TAG, "getExternalCacheDir(): " + getExternalFilesDir("file").getPath());///storage/emulated/0/Android/data/com.ryd.gyy.guolinstudy/files/file

        //加载外部存储器的图片
//        File file = new File(getExternalCacheDir() + "/image.png");
//        Glide.with(this).load(file).into(imageView);

        // 加载应用资源
//        int resource = R.loading.ryd;
//        Glide.with(this).load(resource).into(imageView);
//
//        // 加载二进制流
//        byte[] image = getImageBytes();
//        Glide.with(this).load(image).into(imageView);

//        // 加载Uri对象
//        Bitmap bitmap = BitmapFactory.decodeResource(GlideActivity.this.getResources(), R.loading.ryd);
//        Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmap , null,null));
//        Glide.with(this).load(imageUri).into(imageView);
//

        Log.e(TAG, "loadImage begin ---------");
        //override()方法指定了一个图片的尺寸，也就是说，Glide现在只会将图片加载成1080*1920像素的尺寸，而不会管你的ImageView的大小是多少了
        //asGif:强制加载动态图片。既然指定了只允许加载动态图片，如果我们传入了一张静态图片的URL地址，就是显示报错的异常占位图
        //asBitmap:强制加载静态图片（如果是gif，那就是加载第一帧）在Glide 3中的语法是先load()再asBitmap()的，而在Glide 4中是先asBitmap()再load()的
        //placeholder占位图:在图片的加载过程中，我们先显示一张临时的图片，等图片加载出来了再替换成要加载的图片。
        //DiskCacheStrategy.NONE：禁用掉Glide的缓存功能，目的是为了显示占位符的效果
        //error异常占位图:运行异常时显示
        String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        String gifurl = "http://p1.pstatp.com/large/166200019850062839d3";
        Glide.with(this)
//                .asGif()
                .load(url)
                .override(1080, 1920)
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }


}




