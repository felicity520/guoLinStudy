package com.ryd.gyy.guolinstudy.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ryd.gyy.guolinstudy.R;
import com.ryd.gyy.guolinstudy.Util.UserUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ryd.gyy.guolinstudy.Util.MainApplication.getGlobalContext;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //我们需要在声明变量的时候添加注解： 声明的属性不能用private或者static修饰，否则会报错！
    @BindView(R.id.texttest)
    TextView tv;


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);//一定要在setContentView之后

    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    //绑定监听事件:多个按钮使用{}拼接
    @OnClick({R.id.btn_demo, R.id.btn_demo1})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_demo:
//////                Toast toast = Toast.makeText(MainActivity.this, "提示用户，Toast一下", Toast.LENGTH_SHORT);
//////                LinearLayout toastView = (LinearLayout) toast.getView();//获取Toast的LinearLayout，注意需要是线性布局
//////                ImageView image = new ImageView(MainActivity.this);
//////                image.setImageResource(R.drawable.ryd2);//生成一个现实Logo的ImageView
//////                toastView.addView(image);//将ImageView加载到LinearLayout上面
//////                toast.setGravity(Gravity.CENTER_VERTICAL, 0, -50);
//////                toast.show();
//////                UserUtil.dialog("title","content",MainActivity.this);
//////                UserUtil.showSnackbar(getWindow().getDecorView());
//////                Log.e(TAG, "验证dialog的阻断---------------: ");
                UserUtil.showToast(getGlobalContext(), "我是toast", Toast.LENGTH_SHORT);
                tv.setText("hhhhhhhhhhhhh");
                break;
            case R.id.btn_demo1:
                UserUtil.showToast(getGlobalContext(), "我是toast111", Toast.LENGTH_SHORT);
                break;
            default:
                break;
        }

    }

}
