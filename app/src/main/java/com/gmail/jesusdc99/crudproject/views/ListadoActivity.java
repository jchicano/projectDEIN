package com.gmail.jesusdc99.crudproject.views;

import android.content.Intent;
import android.os.Bundle;

import com.gmail.jesusdc99.crudproject.R;
import com.gmail.jesusdc99.crudproject.interfaces.ListadoInterface;
import com.gmail.jesusdc99.crudproject.presenters.ListadoPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class ListadoActivity extends AppCompatActivity implements ListadoInterface.View {

    String TAG = "APPCRUD/Listado";
    private ListadoInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        Toolbar toolbar = findViewById(R.id.toolbarListado);
        setSupportActionBar(toolbar);

        // Flecha atras
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Cargamos el presentador
        presenter = new ListadoPresenter(this);

        // Logica del boton flotante
        FloatingActionButton fab = findViewById(R.id.listadoFB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Pulsando boton flotante...");
                presenter.onClickAdd();
            }
        });
    }

    @Override
    public void launchForm() {
        Log.d(TAG, "Lanzando formulario...");
        Intent intent = new Intent(ListadoActivity.this, FormularioActivity.class); // Comunicamos las 2 actividades
        startActivity(intent);
    }

    // Elegimos que hacer segun que boton del toolbar se ha pulsado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Se modificara mas alante con el menu
            /*case android.R.id.home:
                Log.d(TAG, "Pulsando boton atras...");
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Añadimos los 3 puntos al toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_listado, menu);
        return true;
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
