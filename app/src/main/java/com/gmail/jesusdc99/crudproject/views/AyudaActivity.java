package com.gmail.jesusdc99.crudproject.views;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.gmail.jesusdc99.crudproject.R;

public class AyudaActivity extends AppCompatActivity {

    private static final String TAG = "APPCRUD/Ayuda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Flecha atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String activity_from = getIntent().getStringExtra("activity_from");
        Log.d(TAG, "Abrimos desde actividad: " + activity_from);

        // Cargamos el WebView
        WebView webView = findViewById(R.id.ayuda_webView);
        webView.getSettings().setJavaScriptEnabled(true); // enable javascript

        if(activity_from.equals("listado")) {
            Log.d(TAG, "Abriendo ayuda de listado");
            webView.loadUrl("https://jchicano.github.io/AppCRUD/listado.html");
        }
        if(activity_from.equals("formulario")) {
            Log.d(TAG, "Abriendo ayuda de formulario");
            webView.loadUrl("https://jchicano.github.io/AppCRUD/formulario.html");
        }
        if(activity_from.equals("buscar")) {
            Log.d(TAG, "Abriendo ayuda de buscar");
            webView.loadUrl("https://jchicano.github.io/AppCRUD/buscar.html");
        }
    }

}
