package com.ryd.gyy.guolinstudy.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ryd.gyy.guolinstudy.R;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LocationActivity extends AppCompatActivity {

    private static final String TAG = "LocationActivity";

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文第四步的说明

    private TextView positionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext()); //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);//注册监听函数。Android定位SDK自V7.2版本起，对外提供了Abstract类型的监听接口BDAbstractLocationListener，用于实现定位监听。原有BDLocationListener暂时保留，推荐开发者升级到Abstract类型的新监听接口使用，该接口会异步获取定位结果
        //上面的代码必须写在setContentView之前
        setContentView(R.layout.activity_location);

        initView();
        initpermission();
    }

    /**
     * 这里将所有权限添加到一个list集合，一次性申请，值得学习
     * 注意：同一个权限组的申请其一就可以了。危险权限要动态申请。
     */
    private void initpermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            //将list转换成数组
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(LocationActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        mLocationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void initView() {
        positionText = (TextView) findViewById(R.id.positionText);
    }


    /*
    之前的监听方式
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

        }
    }
    */

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度:").append(location.getLatitude()).append("\n");
            currentPosition.append("经度:").append(location.getLongitude()).append("\n");

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            String coorType = location.getCoorType();   //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            currentPosition.append("定位方式:");
            int errorCode = location.getLocType(); //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            Log.e(TAG, "onReceiveLocation: errorCode" + errorCode );
            if (errorCode == BDLocation.TypeGpsLocation) {
                currentPosition.append("GPS");
            } else {
                currentPosition.append("网络");
            }
            positionText.setText(currentPosition);
        }
    }


}
