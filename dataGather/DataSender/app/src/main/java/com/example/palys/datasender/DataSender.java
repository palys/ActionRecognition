package com.example.palys.datasender;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DataSender extends ActionBarActivity {

    private Button enterPinButton;

    private Button testConnectionButton;

    private Button backgroundJobButton;

    private EditText pinText;

    private BluetoothSender sender;

    private IMUService imuService;

    private boolean serviceActive = false;

    private void onTestReceived() {
        logToast("Test ok");
    }

    private void onStartReceived() {
        if (imuService != null) {
            imuService.startGatheringData();
        }
    }

    private void onStopReceived() {
        if (imuService != null) {
            imuService.stopGatheringDataAndSend();
        }
    }

    public void onMessage(byte[] message) {
        String msg = new String(message);

        if ("test".equals(msg)) {
            onTestReceived();
        } else if("start".equals(msg)) {
            onStartReceived();
        } else if ("end".equals(msg)) {
            onStopReceived();
        }
    }

    private void logToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            imuService = ((IMUService.LocalBinder)service).getService();
            imuService.setSender(sender);

            logToast("Service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            imuService = null;

            logToast("Service disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sender);

        sender = new BluetoothSender(this);

        enterPinButton = (Button) findViewById(R.id.enterPinButton);
        testConnectionButton = (Button) findViewById(R.id.testConnectionButton);
        backgroundJobButton = (Button) findViewById(R.id.jobControllButton);
        pinText = (EditText) findViewById(R.id.pinText);

        attachListeners();
    }

    private void doBindService() {
        //startService?
        bindService(new Intent(DataSender.this, IMUService.class), connection, Context.BIND_AUTO_CREATE);
    }

    private void attachListeners() {
        attachPinButtonListener();
        attachTestConnectionButtonListener();
        attachBackgroundJobButtonListener();
    }

    private void attachBackgroundJobButtonListener() {
        backgroundJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (serviceActive) {
                    stopService(new Intent(DataSender.this, IMUService.class));
                    backgroundJobButton.setText("START BACKGROUND JOB");
                } else {
                    doBindService();
                    if (imuService != null) {
                        imuService.startGatheringData();
                        backgroundJobButton.setText("STOP BACKGROUND JOB");
                    } else {
                        logToast("Service is not bound");
                    }
                }
            }
        });
    }

    private void attachTestConnectionButtonListener() {
        testConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sender.send("TEST".getBytes());
            }
        });
    }

    private void attachPinButtonListener() {
        enterPinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinString = pinText.getText().toString();
                sender.enterPin(pinString);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_sender, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
