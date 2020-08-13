package com.ryd.gyy.guolinstudy.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.View.ButtonSubclass;
import com.ryd.gyy.guolinstudy.View.CollapseView;
import com.ryd.gyy.guolinstudy.View.FlowLayout;
import com.ryd.gyy.guolinstudy.testjava.Singleton;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;

/**
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃ 神兽保佑
 * 　　　　┃　　　┃ 代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * add by GYY
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //Convert to WebP可以将png和jpeg无损压缩，可减小apk体积

    //学习位图
    ImageView poolImage;
    int resIndex;
    int[] resIds = {R.drawable.rodman, R.drawable.rodman2};
    Bitmap reuseBitmap;
    private LruCache<String, Bitmap> bitmapCache;


    private Button btn_demo;
    private TextView tv;

    private Context mcontext;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    //    自定义view
    CollapseView collapseView;

    FlowLayout mFlowLayout;

    private FlowLayout flowLayout;

    private ButtonSubclass mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext = MainActivity.this;
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_test);//学习ConstraintLayout的布局
//        setContentView(R.layout.activity_constraint_guoshen);//仿照郭神用拖动的方法制作的布局


        initView();
        initData();

        studyDesignModule();
        studyBitmap();
    }

    /***
     * 学习拉勾教育上的位图
     */
    private void studyBitmap() {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        //Bitmap 采样压缩 宽高都有，所以整体内存会缩小4倍
//        options.inSampleSize = 2;

        //RGB_565 一个像素占两个字节     一半
        //ARGB_8888 一个像素占4个字节

        //bitmap size is 2483776 420dpi ARGB_8888 drawable-xhdpi的屏幕密度是320 算下来是2,480,625
        //bitmap size is 4410000 420dpi ARGB_8888 drawable-hdpi的屏幕密度是240  没错
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rodman, options);
//        Log.i(TAG, "bitmap size is " + bitmap.getAllocationByteCount());


//        try {
//            InputStream is = getResources().getAssets().open("rodman.png");
//            Bitmap image = BitmapFactory.decodeStream(is);
//            //原始大小：1440000 即没有经过缩放的大小
//            Log.i(TAG, "image bitmap size is " + image.getAllocationByteCount());
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //改进内存抖动之后的代码
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        reuseBitmap = BitmapFactory.decodeResource(getResources(), resIds[0], options);


        //缓存机制
        int cacheSize = 20 * 1024 * 1024;  // 20M
        bitmapCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getAllocationByteCount();
            }
        };

    }


    public void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            bitmapCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String key) {
        return bitmapCache.get(key);
    }


    public void switchImage(View view) {
        poolImage.setImageBitmap(getBitmap());
    }

    /***
     * 之前会引起内存抖动的方法
     * @return
     */
//    private Bitmap getBitmap() {
//        return BitmapFactory.decodeResource(getResources(), resIds[resIndex++ % 2]);
//    }

    /***
     * 改进后的,查看内存分析工具Profiler下面不会有删除形状的垃圾桶，那就是没有触发内存抖动
     * @return
     */
    private Bitmap getBitmap() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), resIds[resIndex % 2], options);
        if (canUseForInBitmap(reuseBitmap, options)) {
            Log.e(TAG, "reuseBitmap is reusable");
            //Options.inMutable 置为 true，这里如果不置为 true 的话，BitmapFactory 将不会重复利用 Bitmap 内存
            options.inMutable = true;
            options.inBitmap = reuseBitmap;
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(getResources(), resIds[resIndex++ % 2], options);
    }

    public static boolean canUseForInBitmap(Bitmap candidate, BitmapFactory.Options targetOptions) {
        int width = targetOptions.outWidth / Math.max(targetOptions.inSampleSize, 1);
        int height = targetOptions.outHeight / Math.max(targetOptions.inSampleSize, 1);
        int byteCount = width * height * getBytesPerPixel(candidate.getConfig());

        Log.e(TAG, "reuseBitmap byteCount: " + byteCount);
        Log.e(TAG, "reuseBitmap candidate.getAllocationByteCount(): " + candidate.getAllocationByteCount());
        //在 Android 4.4 版本之前，只能重用相同大小的 Bitmap 内存区域；
        //4.4 之后你可以重用任何 Bitmap 的内存区域，只要这块内存比将要分配内存的 bitmap 大就可以。
        //     新内存     <  可以复用的内存
        return byteCount <= candidate.getAllocationByteCount();
    }


    private static int getBytesPerPixel(Bitmap.Config config) {
        int bytesPerPixel;
        switch (config) {
            case ALPHA_8:
                bytesPerPixel = 1;
                break;
            case RGB_565:
            case ARGB_4444:
                bytesPerPixel = 2;
                break;
            default:
                bytesPerPixel = 4;
                break;
        }
        return bytesPerPixel;
    }

    /**
     * 学习设计者模式
     */
    private void studyDesignModule() {
        Singleton.method();//单例模式，全局有效


        //建造者模式
    }

    private void initData() {

//        自定义view第一个例子
        collapseView.setNumber("1");
        collapseView.setTitle("大傻子周正亮");
//      这里要传入图片对应的xml文件才可以
        collapseView.setContent(R.layout.nav_header);

//        自定义view第二个例子，不能直接使用activity_main中的TextView。所以直接new一个TextView，并且设置参数
        TextView testText = new TextView(mcontext);
        testText.setText("我是测试代码hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        mFlowLayout.addView(testText, params);

        //        自定义view学习-
        collapseView.setNumber("1");

        collapseView.setTitle("第一张图");
        collapseView.setContent(R.layout.nav_header);

//        自定义view学习二

        flowLayout = (FlowLayout) findViewById(R.id.flowLayout);

        flowLayout = (FlowLayout) findViewById(R.id.flowLayout1);

        List<String> lable = new ArrayList<>();
        lable.add("经济");
        lable.add("娱乐");
        lable.add("八卦");
        lable.add("小道消息");
        lable.add("政治中心");
        lable.add("彩票");
        lable.add("情感");
        //设置标签
        flowLayout.setLables(lable, true);

//自定义view的ButtonSubclass
        mButton = (ButtonSubclass) findViewById(R.id.button);
    }

    private void initView() {
//        tv = findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());


//        自定义view
        collapseView = (CollapseView) findViewById(R.id.collapseView);
//        ((ViewGroup) mFlowLayout.getParent()).removeView(mFlowLayout);
        mFlowLayout = (FlowLayout) findViewById(R.id.flowLayout);
//        说明：这里直接将activity_main中的TextView加载到MyFlowLayout会报错
//        报错内容:“The specified child already has a parent. You must call removeView"
//        报错原因:因为activity_main中的TextView已经有一个父View了，重复添加子View会报错
//        参考博客：https://www.jianshu.com/p/2146ace8a244
//        mFlowLayout.addView(tv, tv.getLayoutParams());


    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public void test() {
        //                Toast toast = Toast.makeText(MainActivity.this, "提示用户，Toast一下", Toast.LENGTH_SHORT);
//                LinearLayout toastView = (LinearLayout) toast.getView();//获取Toast的LinearLayout，注意需要是线性布局
//                ImageView image = new ImageView(MainActivity.this);
//                image.setImageResource(R.loading.ryd2);//生成一个现实Logo的ImageView
//                toastView.addView(image);//将ImageView加载到LinearLayout上面
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, -50);
//                toast.show();
//                UserUtil.dialog("title","content",MainActivity.this);
//                UserUtil.showSnackbar(getWindow().getDecorView());
//                Log.e(TAG, "验证dialog的阻断---------------: ");
//        UserUtil.showToast(getGlobalContext(), "我是toast", Toast.LENGTH_SHORT);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("---> MainActivity中调用dispatchTouchEvent()--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("---> MainActivity中调用dispatchTouchEvent()--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("---> MainActivity中调用dispatchTouchEvent()--->ACTION_UP");
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("---> MainActivity中调用onTouchEvent()--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("---> MainActivity中调用onTouchEvent()--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("---> MainActivity中调用onTouchEvent()--->ACTION_UP");
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }


}

