package com.ryd.gyy.guolinstudy.Activity;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.View.CollapseView;
import com.ryd.gyy.guolinstudy.View.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btn_demo;
    private TextView tv;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    //    自定义view
    CollapseView collapseView;

    private FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_test);//学习ConstraintLayout的布局
//        setContentView(R.layout.activity_constraint_guoshen);//仿照郭神用拖动的方法制作的布局


        initView();
        initData();
    }

    private void initData() {
//        自定义view学习-
        collapseView.setNumber("1");
        collapseView.setTitle("第一张图");
        collapseView.setContent(R.layout.nav_header);

//        自定义view学习二
        flowLayout = (FlowLayout) findViewById(R.id.flowLayout);
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
    }


    private void initView() {
//        tv = findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());


//        自定义view
        collapseView = (CollapseView) findViewById(R.id.collapseView);
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


}
