package com.example.administrator.gpsdemo;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    private static final String TAG = MainActivity.class.getSimpleName();
//    private TextView info;
//    private LocationManager locationManager;
//    LocationListener locationListener;
//    private String locationProvider;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//        setContentView(R.layout.activity_main);
//        info = (TextView) findViewById(R.id.tv);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        listener();
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            getLocation();
//            //gps已打开
//        } else {
//            toggleGPS();
//            new Handler() {}.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    getLocation();
//                }
//            }, 2000);
//
//        }
//    }
//
//    private void listener() {
//        locationListener= new LocationListener() {
//            // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//            }
//
//            // Provider被enable时触发此函数，比如GPS被打开
//            @Override
//            public void onProviderEnabled(String provider) {
//                Log.e(TAG, provider);
//            }
//
//            // Provider被disable时触发此函数，比如GPS被关闭
//            @Override
//            public void onProviderDisabled(String provider) {
//                Log.e(TAG, provider);
//            }
//
//            // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
//            @Override
//            public void onLocationChanged(Location location) {
//                if (location != null) {
//                    Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
//                    updateText(location);
//                }
//            }
//        };
//    }
//
//    private void toggleGPS() {
//        Intent gpsIntent = new Intent();
//        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
//        gpsIntent.setData(Uri.parse("custom:3"));
//        try {
//            PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send();
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
//            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (location1 != null) {
//                updateText(location1);
//            }
//        }
//    }
//
//    private void getLocation() {
//        List<String> providers = locationManager.getProviders(true);
//        if (providers.contains(LocationManager.GPS_PROVIDER)) {
//            //如果是GPS
//            locationProvider = LocationManager.GPS_PROVIDER;
//            Log.d(TAG, "onCreate: gps=" + locationProvider);
//        }
////        else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
////            //如果是Network
////            locationProvider = LocationManager.NETWORK_PROVIDER;
////            Log.d(TAG, "onCreate: network=" + locationProvider);
////        }
//        else {
//            Log.d(TAG, "onCreate: 没有可用的位置提供器");
//            Toast.makeText(this,"没有可用的位置提供器",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Location location = locationManager.getLastKnownLocation(locationProvider);
////        locationManager.setTestProviderEnabled("gps",true);
//        if (location != null) {
//            updateText(location);
//        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
//        }
//
//    }
//
//    private void updateText(Location location) {
//        info.setText("纬度：" + location.getLatitude() + "\n" + "经度：" + location.getLongitude() + "\n" + "时间" + location.getTime()+"\n"+"方向"+location.getBearing()+"\n"+"海拔"+location.getAltitude()+"\n"+"速度"+location.getSpeed());
//    }
//
//
//
//
//
//
//    // 打开和关闭gps第二种方法
//    private void openGPSSettings() {
//        //获取GPS现在的状态（打开或是关闭状态）
//        boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER);
//        if (gpsEnabled) {
//            //关闭GPS
//            Settings.Secure.setLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER, false);
//        } else {
//            //打开GPS
//            Settings.Secure.setLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER, true);
//        }
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (locationManager != null) {
//            locationManager.removeUpdates(locationListener);
//        }
//    }




    private static final String TAG = MainActivity.class.getSimpleName();
    private double latitude = 0.0;
    private double longitude = 0.0;
    private TextView info;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.tv);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
            //gps已打开
        } else {
            toggleGPS();
            new Handler() {}.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getLocation();
                }
            }, 2000);

        }
    }

    private void toggleGPS() {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location1 != null) {
                latitude = location1.getLatitude(); // 经度
                longitude = location1.getLongitude(); // 纬度
            }
        }
    }

    private void getLocation() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            info.setText("纬度：" + latitude + "\n" + "经度：" + longitude);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        }

    }

    LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, provider);
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, provider);
        }

        // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
                latitude = location.getLatitude(); // 经度
                longitude = location.getLongitude(); // 纬度
                info.setText("纬度：" + latitude + "\n" + "经度：" + longitude);
            }
        }
    };



    // 打开和关闭gps第二种方法
    private void openGPSSettings() {
        //获取GPS现在的状态（打开或是关闭状态）
        boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER);
        if (gpsEnabled) {
            //关闭GPS
            Settings.Secure.setLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER, false);
        } else {
            //打开GPS
            Settings.Secure.setLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER, true);
        }
    }
}

