package com.ryd.gyy.guolinstudy.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ryd.gyy.guolinstudy.R;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 踩坑点：
 * 1.NeedsPermission和OnShowRationale、OnPermissionDenied、OnNeverAskAgain的注解权限要一致，否则不会回调提示方法。
 * 2.编译后调用，写完注释方法后先make project再运行
 * 3.安装插件，alt+insert可以快速创建注解方法。
 * 参考：https://www.jianshu.com/p/64e7334cde11
 */
@RuntimePermissions
public class PermissionsActivity extends BaseActivity {

    private static final String TAG = "PermissionsActivity";

    @BindView(R.id.button5)
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("权限测试界面");
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_permissions;
    }

    @Override
    void initData() {

    }

    @Override
    void initView() {

    }

    /**
     * @param view 绑定多个点击事件
     */
    @OnClick({R.id.button5})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.button5:
                // 调用带权限检查的 needPermissions 方法
                //*PermissionDispatcher.*WithCheck 方法对你原来的方法进行了包装，包了一层权限检查的代码，你需要做的就是把原来的方法调用改为 *PermissionDispatcher.*WithCheck。（类名 方法名）
                // 另外，在 onRequestPermissionsResult 中回调 *PermissionsDispatcher.onRequestPermissionsResult 方法。
                PermissionsActivityPermissionsDispatcher.needPermissionsWithPermissionCheck(this);
                break;
            default:
                break;
        }
    }


    /**
     * 可以在这个返回中获取到哪些权限被拒绝，进而在OnPermissionDenied中进行处理对应的申请或者提醒
     * log证明先执行这个方法再执行NeedsPermission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 代理权限处理到自动生成的方法：授权结果会保存在grantResults中
        if (permissions.length > 0) {
            for (int i = 0; i < permissions.length; i++) {
                Log.e(TAG, "requestCode: " + requestCode + "  grantResults[" + i + "]:" + grantResults[i] + "  permissions[" + i + "]:" + permissions[i]);
            }
        }
        PermissionsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /**
     * ⚠注意： 当用户给予权限时会执行该方法(用户同意之后，直接执行该方法)
     * -------------------------
     * 说明：调用此方法后会弹出一个系统弹框，让用户来选择
     * 1、用户都同意：就pass啦
     * 2、只要有拒绝（没有点不再提醒）就会调用OnPermissionDenied
     * 3、只要有拒绝（并且点不再提醒）就会调用showNeverAskForCamera（不管你点了几次）。意思就是不会再调用OnShowRationale向用户解释
     * -------------------------
     * 拒绝但没有不再提醒：（也就是用户有拒绝系统弹出的权限申请）
     * 1、有拒绝就会调用OnShowRationale（被拒绝的才会弹出来，允许的不会弹出来）
     * -------------------------
     * 举例：申请相机和录音权限
     * 相机点击拒绝，录音点拒绝且不再提醒。
     * 结果：调用OnPermissionDenied（优先级高）
     * 再次申请：拒绝的还是会执行OnShowRationale，同时也会执行OnNeverAskAgain
     */
    // 单个权限
    // @NeedsPermission(Manifest.permission.CAMERA)
    // 多个权限
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.CALL_PHONE})
    void needPermissions() {
        Log.e(TAG, "needPermissions: ----------------");
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    /**
     * 向用户说明为什么需要这些权限（可选）
     */
    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.CALL_PHONE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("应用将要申请录音权限和相机权限")
                .show();
    }


    /**
     * 用户拒绝授权回调（可选）
     */
    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.CALL_PHONE})
    void showDeniedForCamera() {
        Toast.makeText(this, R.string.permission_camera_denied, Toast.LENGTH_SHORT).show();
    }

    /**
     * 用户勾选了“不再提醒”时调用（可选）
     */
    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.CALL_PHONE})
    void showNeverAskForCamera() {
        Log.e(TAG, "showNeverAskForCamera: --------------");
        Toast.makeText(this, R.string.permission_camera_neverask, Toast.LENGTH_SHORT).show();
    }

}
