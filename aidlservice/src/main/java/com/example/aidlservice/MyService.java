package com.example.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {
    private void log(String message) {
        Log.e("!!!MainService", message);
    }

    @Override
    public IBinder onBind(Intent intent) {
        log("onBind" + getPackageManager().getNameForUid(Binder.getCallingUid()));
        return mBinder;
    }

    private final IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public void testCall() throws RemoteException {
            log("testCall " + getPackageManager().getNameForUid(Binder.getCallingUid()));
            // here we can check what package calling us
        }
    };
}
