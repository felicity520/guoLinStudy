package com.ryd.gyy.guolinstudy.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineDottedLineType;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.ryd.gyy.guolinstudy.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 注意：手机的GPS功能必须要用户主动去开启（设置里）
 */
public class LocationActivity extends AppCompatActivity {

    private static final String TAG = "LocationActivity";

    private boolean isFirstLocate = true;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文第四步的说明

    private TextView positionText;
    private MapView mapView;
    private BaiduMap baiduMap;//将地图显示出来

    private BitmapDescriptor mGreenTexture = BitmapDescriptorFactory.fromAsset("Icon_road_green_arrow.png");//纹理样式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext()); //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);//注册监听函数。Android定位SDK自V7.2版本起，对外提供了Abstract类型的监听接口BDAbstractLocationListener，用于实现定位监听。原有BDLocationListener暂时保留，推荐开发者升级到Abstract类型的新监听接口使用，该接口会异步获取定位结果
        SDKInitializer.initialize(getApplicationContext());//最好用全局的
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
        initLocation();
        mLocationClient.start();
        drawPolyline();
    }

    /**
     * 绘制折线，为了验证可以虚线绘制，也可以实线绘制
     */
    private void drawPolyline() {
        //构建折线点坐标
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(39.965,116.404));
        points.add(new LatLng(39.925,116.454));
        points.add(new LatLng(39.955,116.494));
        points.add(new LatLng(39.905,116.554));
        points.add(new LatLng(39.965,116.604));
        points.add(new LatLng(39.925,116.645));
        points.add(new LatLng(39.955,116.704));

        //设置折线的属性
        OverlayOptions mOverlayOptions = new PolylineOptions()
                .width(30)
                .visible(true)
                .customTexture(BitmapDescriptorFactory.fromResourceWithDpi(R.drawable.icon_road_green_arrow, 480))
                .points(points);
        //在地图上绘制折线
        //mPloyline 折线对象
        Overlay mPolyline = baiduMap.addOverlay(mOverlayOptions);
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(2000);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);//可选，设置是否使用gps，默认false。使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setIsNeedAddress(true);//需要获取位置地址的时候，必须加上这句，否则无法获取
        option.setIsNeedLocationDescribe(true);//如果开发者需要获得当前点的位置信息描述，此处必须为true
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时必须调用mMapView. onResume ()
        mapView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();//在activity执行onDestroy时必须调用mMapView.onDestroy()
        mLocationClient.stop();//在程序stop的时候停止定位，否则会在后台不停的定位，消耗电量
        mLocationClient.stopIndoorMode();//关闭室内定位模式
        baiduMap.setMyLocationEnabled(false);//关闭地图的定位
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
        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);//开启地图的定位图层,定位的时候要用
    }


    /**
     * 将地图移动到我的位置
     *
     * @param location 当前的位置
     */
    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            //移动到我的位置只需要在第一次定位的时候调用，所以用isFirstLocate做退出
            Toast.makeText(this, "nav to " + location.getAddrStr(), Toast.LENGTH_SHORT).show();
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(18f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        //在地图上需要实时显示我的位置光标。所以要写在if外面
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.accuracy(location.getRadius());
        locationBuilder.direction(location.getDirection());
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);


        //自定义内容：方向箭头的自定义，可有可无
//        MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true,
//                BitmapDescriptorFactory.fromResource(R.drawable.red01), 0xAAFFFF88, 0xAA00FF00);
//        baiduMap.setMyLocationConfiguration(mLocationConfiguration);
    }

    /*
    之前的监听方式：百度现在已经不推荐使用了
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

        }
    }
    */

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            /*
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度:").append(location.getLatitude()).append("\n");
            currentPosition.append("经度:").append(location.getLongitude()).append("\n");
            //获取地址信息一定要用到网络
            currentPosition.append("国家:").append(location.getCountry()).append("\n");
            currentPosition.append("省:").append(location.getProvince()).append("\n");
            currentPosition.append("市:").append(location.getCity()).append("\n");
            currentPosition.append("区:").append(location.getDistrict()).append("\n");
            currentPosition.append("街:").append(location.getStreet()).append("\n");
            currentPosition.append("位置信息描述:").append(location.getLocationDescribe()).append("\n");

            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            Log.i(TAG, "location.getFloor(): " + location.getFloor());
            if (location.getFloor() != null) {
                // 当前支持高精度室内定位
                String buildingID = location.getBuildingID();// 百度内部建筑物ID
                String buildingName = location.getBuildingName();// 百度内部建筑物缩写
                String floor = location.getFloor();// 室内定位的楼层信息，如 f1,f2,b1,b2
                currentPosition.append("buildingID:").append(buildingID).append("\n");
                currentPosition.append("buildingName:").append(buildingName).append("\n");
                currentPosition.append("floor:").append(floor).append("\n");
                mLocationClient.startIndoorMode();// 开启室内定位模式（重复调用也没问题），开启后，定位SDK会融合各种定位信息（GPS,WI-FI，蓝牙，传感器等）连续平滑的输出定位结果；
            }

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            String coorType = location.getCoorType();   //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            currentPosition.append("定位方式:");
            int errorCode = location.getLocType(); //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            Log.e(TAG, "onReceiveLocation: errorCode：" + errorCode);
            if (errorCode == 61) {
                currentPosition.append("GPS");
            } else if (errorCode == 161) {
                currentPosition.append("网络");
            }
            positionText.setText(currentPosition);
            */


            //为了测试折线，暂时先不移动到我的位置
//            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                navigateTo(location);
//            }

        }
    }


}
