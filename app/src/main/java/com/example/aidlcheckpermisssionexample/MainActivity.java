package com.example.aidlcheckpermisssionexample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.aidlservice.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    IMyAidlInterface mService;

    private void log(String message) {
        Log.e("!!!MainActivity", message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log("onCreate");
        Intent serviceIntent = new Intent()
                .setComponent(new ComponentName(
                        "com.example.aidlservice",
                        "com.example.aidlservice.MyService"));

        startService(serviceIntent);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);


        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log("testClick");
                try {
                    mService.testCall();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            log("Service binded!");
            mService = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                mService.testCall();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
            log("Service unbinded!");
        }
    };

}
