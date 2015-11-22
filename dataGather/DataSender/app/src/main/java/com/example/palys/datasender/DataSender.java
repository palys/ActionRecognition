package com.example.palys.datasender;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class DataSender extends ActionBarActivity {

    private Button enterPinButton;

    private Button testConnectionButton;

    private Button backgroundJobButton;

    private EditText pinText;

    private BluetoothSender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sender);

        sender = new BluetoothSender();

        enterPinButton = (Button) findViewById(R.id.enterPinButton);
        testConnectionButton = (Button) findViewById(R.id.testConnectionButton);
        backgroundJobButton = (Button) findViewById(R.id.jobControllButton);
        pinText = (EditText) findViewById(R.id.pinText);

        attachListeners();
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
                //TODO
            }
        });
    }

    private void attachTestConnectionButtonListener() {
        testConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
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
