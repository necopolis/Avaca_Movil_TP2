package com.avaca.avaca_tp_2;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkCallingOrSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1000);
        }
        // INSTANCIAMOS INTENT Y PASMOS EL SERVIS
        intent = new Intent(this, ServisMensajes.class);

        // ACCIONAMOS EL SERVICIO Y QUE TIENE EL HILO
        startService(intent);

    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(intent);
        Log.d("mensaje","Servicio Pausado");
    }

}