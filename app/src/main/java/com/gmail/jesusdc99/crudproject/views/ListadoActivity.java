package com.gmail.jesusdc99.crudproject.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gmail.jesusdc99.crudproject.R;
import com.gmail.jesusdc99.crudproject.interfaces.ListadoInterface;
import com.gmail.jesusdc99.crudproject.models.Game;
import com.gmail.jesusdc99.crudproject.presenters.ListadoPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ListadoActivity extends AppCompatActivity implements ListadoInterface.View {

    private static final String TAG = "APPCRUD/Listado";
    private ListadoInterface.Presenter presenter;
    private Context myContext;
    private FloatingActionButton addFloatingActionButton;
    private RecyclerView listadoRecyclerView;
    private GameAdapter adaptador;
    private ArrayList<Game> gamesList;

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
    public void launchForm(int id) {
        if(id == -1) {
            Log.d(TAG, "Lanzando formulario...");
            Intent intent = new Intent(ListadoActivity.this, FormularioActivity.class); // Comunicamos las 2 actividades
            startActivity(intent);
        }
        else {
            Log.d(TAG, "Lanzando formulario desde RV...");
            Intent intent = new Intent(ListadoActivity.this, FormularioActivity.class);
            //Probando a partir de aqui
            Bundle b = new Bundle();
            b.putInt("id_game", id); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            //Fin probando
            startActivity(intent);
        }
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
        listadoRecyclerView = findViewById(R.id.listado_listadoRecyclerView);

        // Crea el Adaptador con los datos de la lista anterior
        gamesList = presenter.getAllGames();
        adaptador = new GameAdapter(gamesList);

        // Asocia el Adaptador al RecyclerView
        listadoRecyclerView.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        listadoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Relaciono la accion swipe al RV
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(listadoRecyclerView);
    }

    @Override
    public void initializeWidgetsListeners(){
        // Rellenar con futuros widgets
        initializeFloatingActionButton();
        // Asocia el elemento de la lista con una accion al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Accion al pulsar el elemento
                int position = listadoRecyclerView.getChildAdapterPosition(v);
                Log.d(TAG, "Click RV: " + gamesList.get(position).getId());
                presenter.onClickRecyclerView(gamesList.get(position).getId());
            }
        });
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

    // Swipe para el RV
    // https://www.youtube.com/watch?v=M1XEqqo6Ktg
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int longitud = gamesList.size();
            gamesList.remove(viewHolder.getAdapterPosition());
            adaptador.notifyDataSetChanged();
            if(gamesList.size() < longitud) Toast.makeText(myContext, "Juego eliminado", Toast.LENGTH_SHORT).show();
            else Toast.makeText(myContext, "Error al eliminar juego", Toast.LENGTH_SHORT).show();
        }
    };

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
