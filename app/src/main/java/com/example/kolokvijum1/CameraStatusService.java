package com.example.kolokvijum1;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class CameraStatusService extends Service {

    public static final String AKCIJA_KAMERA_DOZVOLJENA = "com.example.kolokvijum1.KAMERA_DOZVOLJENA";
    private static final long PERIODA_PROVERE = 60_000;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable zadatakProvere;

    @Override
    public void onCreate() {
        super.onCreate();
        zadatakProvere = new Runnable() {
            @Override
            public void run() {
                proveriDozvoluKamere();
                handler.postDelayed(this, PERIODA_PROVERE);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(zadatakProvere);
        return START_STICKY;
    }

    private void proveriDozvoluKamere() {
        boolean dozvoljeno = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        if (dozvoljeno) {
            Intent obavestenje = new Intent(AKCIJA_KAMERA_DOZVOLJENA);
            LocalBroadcastManager.getInstance(this).sendBroadcast(obavestenje);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(zadatakProvere);
    }
}
