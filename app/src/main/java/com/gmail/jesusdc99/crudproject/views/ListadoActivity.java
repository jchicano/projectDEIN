package com.gmail.jesusdc99.crudproject.views;

import android.content.Context;
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
import android.widget.Toast;

public class ListadoActivity extends AppCompatActivity implements ListadoInterface.View {

    private static final String TAG = "APPCRUD/Listado";
    private ListadoInterface.Presenter presenter;
    private Context myContext;
    private FloatingActionButton addFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        Toolbar toolbar = findViewById(R.id.listado_toolbarListado);
        setSupportActionBar(toolbar);

        myContext = this;

        // Flecha atras
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Cargamos el presentador
        presenter = new ListadoPresenter(this);

        initializeWidgets();
        initializeWidgetsListeners();
    }

    @Override
    public void launchForm() {
        Log.d(TAG, "Lanzando formulario...");
        Intent intent = new Intent(ListadoActivity.this, FormularioActivity.class); // Comunicamos las 2 actividades
        startActivity(intent);
    }

    @Override
    public void launchBuscar() {
        Log.d(TAG, "Lanzando buscar...");
        Intent intent = new Intent(ListadoActivity.this, BuscarActivity.class); // Comunicamos las 2 actividades
        startActivity(intent);
    }

    @Override
    public void launchAbout() {
        Log.d(TAG, "Lanzando about...");
        Intent intent = new Intent(ListadoActivity.this, AboutActivity.class); // Comunicamos las 2 actividades
        startActivity(intent);
    }

    // Elegimos que hacer segun que boton del toolbar se ha pulsado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.listado_action_buscar: //  Cuando se pulsa la opcion buscar
                Log.d(TAG, "Pulsando opcion buscar...");
                presenter.onClickBuscar();
                return true;
            case R.id.listado_action_ordenar: // Cuando se pulsa la opcion ordenar
                Log.d(TAG, "Pulsando opcion ordenar...");
                Toast.makeText(myContext, "No implementado todavía", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.listado_action_configuracion: // Cuando se pulsa la opcion configuracion
                Log.d(TAG, "Pulsando opcion configuracion...");
                Toast.makeText(myContext, "No implementado todavía", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.listado_action_sobre_app: // Cuando se pulsa la opcion Sobre AppCRUD
                Log.d(TAG, "Pulsando opcion sobre AppCRUD...");
                presenter.onClickSobreAppCRUD();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void initializeWidgets() {
        addFloatingActionButton = findViewById(R.id.listado_listadoFB);
    }

    @Override
    public void initializeWidgetsListeners(){
        // Rellenar con futuros widgets
        initializeFloatingActionButton();
    }

    // Logica del boton flotante
    @Override
    public void initializeFloatingActionButton() {
        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Pulsando boton flotante...");
                presenter.onClickAdd();
            }
        });
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
