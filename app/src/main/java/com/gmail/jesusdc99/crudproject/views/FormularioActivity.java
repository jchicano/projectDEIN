package com.gmail.jesusdc99.crudproject.views;

import android.os.Bundle;

import com.gmail.jesusdc99.crudproject.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FormularioActivity extends AppCompatActivity {

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

    }

    // Para añadir el boton de guardar en el toolbar lo inflamos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.formulario_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Cuando se pulsa el boton guardar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if(item.getItemId() == R.id.action_save_game){
            // De momento redirigimos a ListadoActivity
            return super.onOptionsItemSelected(item); // TODO
        }
        else return super.onOptionsItemSelected(item);
    }

}
