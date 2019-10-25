package com.gmail.jesusdc99.crudproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    String TAG = "APPCRUD/Splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Final para que funcione dentro del Handler
        final Intent intent = new Intent(this, ListadoActivity.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //No se debe hacer asi, sacarlo fuera del handler
                startActivity(intent);
                finish();
            }
        }, 5000);   //5 seconds

    }

    /*******************************************/
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"Ejecutando onStart...");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"Ejecutando onRestart...");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"Ejecutando onResume...");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"Ejecutando onPause...");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"Ejecutando onStop...");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"Ejecutando onDestroy...");
    }
}
