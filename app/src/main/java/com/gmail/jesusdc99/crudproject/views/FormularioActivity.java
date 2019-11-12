package com.gmail.jesusdc99.crudproject.views;

import android.os.Bundle;

import com.gmail.jesusdc99.crudproject.R;
import com.gmail.jesusdc99.crudproject.interfaces.FormularioInterface;
import com.gmail.jesusdc99.crudproject.presenters.FormularioPresenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FormularioActivity extends AppCompatActivity implements FormularioInterface.View {

    String TAG = "APPCRUD/Formulario";
    private FormularioInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = findViewById(R.id.toolbarFormulario);
        setSupportActionBar(toolbar);

        // Flecha atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Cargamos los elementos en el spinner
        Spinner spinner = findViewById(R.id.plataformaSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.plataformas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Cargamos el presentador
        presenter = new FormularioPresenter(this);

    }

    // Para añadir el boton de guardar en el toolbar lo inflamos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Metodo que lanza la actividad listado
    @Override
    public void launchListado() {
        Log.d(TAG, "Lanzando listado...");
        finish();
    }

    // Elegimos que hacer segun que boton del toolbar se ha pulsado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_save_game: // Cuando se pulsa el boton guardar
                Log.d(TAG, "Pulsando boton guardar juego...");
                presenter.onClickGuardar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
