package com.ryd.gyy.guolinstudy.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.Util.UserUtil;

import static com.ryd.gyy.guolinstudy.Util.MainApplication.getGlobalContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button btn_demo;
    private TextView tv;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_test);//学习ConstraintLayout的布局
//        setContentView(R.layout.activity_constraint_guoshen);//仿照郭神用拖动的方法制作的布局


        initView();
    }


    private void initView() {
        tv = findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());

        btn_demo = (Button) findViewById(R.id.btn_demo);
        btn_demo.setOnClickListener(this);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_demo:
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
                UserUtil.showToast(getGlobalContext(), "我是toast", Toast.LENGTH_SHORT);
                break;
        }
    }


}
