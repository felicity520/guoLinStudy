package com.ryd.gyy.guolinstudy.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

/**
 * Dialog：当提示信息是至关重要的，并且必须要由用户做出决定才能继续的时候，使用Dialog。
 * （它会阻止用户原本正在进行的操作，必须停止下来对Dialog进行处理，不是指程序）
 * Toast：当提示信息只是告知用户某个事情发生了，用户不需要对这个事情做出响应的时候，使用Toast。
 * Snackbar：以上两者之外的任何其他场景，Snackbar可能会是你最好的选择。
 * （Snackbar不会阻断用户现有的操作，是有时间限制的，无法获取到焦点）
 */
public class UserUtil {

    private static final String TAG = "UserUtil";

    private static Toast mToast;

    public static void dialog(String title, String str, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        builder.setTitle(title); //设置标题
        builder.setMessage(str); //设置内容
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.dismiss(); //关闭dialog
            }
        });
        builder.show();
    }

    /**
     * 显示一个toast提示
     * <p>
     * 9.0以下：防止用户多次点击一直弹出toast。这里toast只会持续一次Toast显示的时长（倒计时配合toast显示倒计时长，用toast不合适，适合用TextView做倒计时）
     * 9.0以上google源码有变，参考https://blog.csdn.net/qq_36486247/article/details/102753801
     *
     * @param context  注意：这里要使用全局的context，否则可能会内存泄漏
     * @param text     toast字符串
     * @param duration toast显示时间
     */
    public static void showToast(Context context, String text, int duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Toast.makeText(context, text, duration).show();
        } else {
            if (mToast == null) {
                mToast = Toast.makeText(context, text, duration);
            } else {
                mToast.setText(text);
                mToast.setDuration(duration);
            }
            mToast.show();
        }
    }


    /**
     * Undo:撤销
     *
     * @param view
     */
    public static void showSnackbar(View view) {
        Snackbar.make(view, "data deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "onClick: 用户点击了撤销");
                    }
                })
                .show();
    }

}
