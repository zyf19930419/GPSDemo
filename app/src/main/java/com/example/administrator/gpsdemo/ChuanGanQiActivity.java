package com.example.administrator.gpsdemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.gpsdemo.bean.AccelerationBean;
import com.example.administrator.gpsdemo.bean.LightBean;
import com.example.administrator.gpsdemo.utils.JsonUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * 传感器
 */
public class ChuanGanQiActivity extends AppCompatActivity {
    private  SensorManager mSensorManager;
    private  Sensor mAccelerometer,mLightSensor;
    private Button jiasudu,guangxian,wendu,shidu;
    private TextView sensortext,mTvInfo;
    private float mGravity=9.80665F;
    private List<AccelerationBean> accelerationBeanList=new ArrayList<>();
    private List<LightBean> lightBeanList=new ArrayList<>();
    private File file;
    private SensorEventListener listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {



            Log.e("linc", "value size: " + sensorEvent.values.length);
            float xValue = sensorEvent.values[0];// Acceleration minus Gx on the x-axis
            float yValue = sensorEvent.values[1];//Acceleration minus Gy on the y-axis
            float zValue = sensorEvent.values[2];//Acceleration minus Gz on the z-axis

            switch (sensorEvent.sensor.getType()){
//                加速度传感器
                case Sensor.TYPE_ACCELEROMETER:
                    AccelerationBean accelerationBean=new AccelerationBean(xValue,yValue,zValue);
//                    accelerationBeanList.add(accelerationBean);
                    writeTxtToFile(JsonUtil.obj2json(accelerationBean));
                    break;
//                    光线传感器
                case Sensor.TYPE_LIGHT:
                    LightBean lightBean=new LightBean(xValue);
                    writeTxtToFile(JsonUtil.obj2json(lightBean));
                    break;
//                    温度传感器
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    break;
//                    湿度传感器
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                    break;
            }

//            if (1==sensorEvent.sensor.getType()) {
//                mTvInfo.setText("x轴： " + xValue + " \n y轴： " + yValue + " \n z轴： " + zValue);
//                if (xValue > mGravity) {
//                    mTvInfo.append("\n重力指向设备左边");
//                } else if (xValue < -mGravity) {
//                    mTvInfo.append("\n重力指向设备右边");
//                } else if (yValue > mGravity) {
//                    mTvInfo.append("\n重力指向设备下边");
//                } else if (yValue < -mGravity) {
//                    mTvInfo.append("\n重力指向设备上边");
//                } else if (zValue > mGravity) {
//                    mTvInfo.append("\n屏幕朝上");
//                } else if (zValue < -mGravity) {
//                    mTvInfo.append("\n屏幕朝下");
//                }
//            }else if(5==sensorEvent.sensor.getType()){
//                fileName="light.json";
//                StringBuilder stringBuilder=new StringBuilder();
//                stringBuilder.append("光线强度： " + xValue );
//                writeTxtToFile(stringBuilder.toString(),filePath,fileName);
//                mTvInfo.setText("光线强度："+xValue);
//            }else if(7==sensorEvent.sensor.getType()){
//                   /*温度传感器返回当前的温度，单位是摄氏度（°C）。*/
//                mTvInfo.setText("设备的温度："+xValue);
//            }else if(12==sensorEvent.sensor.getType()){
//                mTvInfo.setText("周围空气相对湿度："+xValue);
//            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuan_gan_qi);
        jiasudu=findViewById(R.id.jiasudu);
        guangxian=findViewById(R.id.guangxian);
        wendu=findViewById(R.id.wendu);
        shidu=findViewById(R.id.shidu);
        mTvInfo=findViewById(R.id.content);
        sensortext=findViewById(R.id.sensor);
        String filePath = "/sdcard/Test/";
        file=CreateFile.makeFilePath(filePath,"test.json");
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor:sensorList){
            sensortext.append(sensor.getName()+"\n");
        }



        jiasudu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSensor(Sensor.TYPE_ACCELEROMETER);
            }
        });

        guangxian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSensor(Sensor.TYPE_LIGHT);
            }
        });

        wendu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            }
        });

        shidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            }
        });
    }



    // 将字符串写入到文本文件中
    public void writeTxtToFile(String strcontent) {
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    // 生成文件
//    public File makeFilePath(String filePath, String fileName) {
//        File file = null;
//        makeRootDirectory(filePath);
//        try {
//            file = new File(filePath + fileName);
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return file;
//    }
//
//    // 生成文件夹
//    public static void makeRootDirectory(String filePath) {
//        File file = null;
//        try {
//            file = new File(filePath);
//            if (!file.exists()) {
//                file.mkdir();
//            }
//        } catch (Exception e) {
//            Log.i("error:", e + "");
//        }
//    }


    private void setSensor(int sensor) {
        mTvInfo.setText("");
        mSensorManager.unregisterListener(listener);
        mAccelerometer = mSensorManager.getDefaultSensor(sensor);
        mSensorManager.registerListener(listener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (null!=mSensorManager) {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(listener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

            mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            mSensorManager.registerListener(listener, mLightSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(listener);
    }

}
