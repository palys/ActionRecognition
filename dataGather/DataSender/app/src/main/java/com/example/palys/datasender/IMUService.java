package com.example.palys.datasender;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

public class IMUService extends Service implements SensorEventListener {

    private DataHolder dataHolder;

    private BluetoothSender sender;

    private SensorManager sensorManager;

    private Sensor accelerometer;

    private Sensor gyroscope;

    private boolean active = false;

    private boolean gyroscopeAvailable = false;

    private boolean wasAccPrevious = false;

    private Vector3 previous;

    private long previousTime = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (active) {

            long currentTime = System.currentTimeMillis();
            if (!gyroscopeAvailable) {
                DataFrame df = new DataFrame(currentTime, new Vector3(event.values));
                dataHolder.add(df);
            } else {
                if (event.sensor.equals(accelerometer)) {

                    if (previous == null) {

                        previous = new Vector3(event.values);
                        wasAccPrevious = true;
                        previousTime = System.currentTimeMillis();

                    } else {
                        if (wasAccPrevious) {
                            previous = new Vector3(event.values);
                            previousTime = System.currentTimeMillis();
                        } else {
                            DataFrame df = new DataFrame(previousTime, new Vector3(event.values), previous);
                            previous = null;
                            dataHolder.add(df);
                        }
                    }
                } else {//gyroscope
                    if (previous == null) {
                        previous = new Vector3(event.values);
                        wasAccPrevious = false;
                        previousTime = System.currentTimeMillis();
                    } else {
                        if (!wasAccPrevious) {
                            previous = new Vector3(event.values);
                            previousTime = System.currentTimeMillis();
                        } else {
                            DataFrame df = new DataFrame(previousTime, previous, new Vector3(event.values));
                            previous = null;
                            dataHolder.add(df);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class LocalBinder extends Binder {

        IMUService getService() {
            return IMUService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    public BluetoothSender getSender() {
        return sender;
    }

    public void setSender(BluetoothSender sender) {
        this.sender = sender;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        dataHolder = new DataHolder();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
            gyroscopeAvailable = true;
        }
    }

    @Override
    public void onDestroy() {

    }

    public void startGatheringData() {
        active = true;
        dataHolder.clear();
    }

    public void stopGatheringData() {
        active = false;
    }

    public void sendData() {
        sender.send(dataHolder.data());
    }

    public void stopGatheringDataAndSend() {
        stopGatheringData();
        sendData();
    }
}
