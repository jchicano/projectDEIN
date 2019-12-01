package com.gmail.jesusdc99.crudproject.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import com.gmail.jesusdc99.crudproject.R;
import com.gmail.jesusdc99.crudproject.interfaces.FormularioInterface;
import com.gmail.jesusdc99.crudproject.presenters.FormularioPresenter;
import com.gmail.jesusdc99.crudproject.utils.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class FormularioActivity extends AppCompatActivity implements FormularioInterface.View {

    private static final String TAG = "APPCRUD/Formulario";
    private FormularioInterface.Presenter presenter;
    private TextInputEditText tituloTextInputEditText, desarrolladorTextInputEditText, distribuidorTextInputEditText, notaTextInputEditText, fechaTextInputEditText;
    private TextInputLayout tituloTextInputLayout, desarrolladorTextInputLayout, distribuidorTextInputLayout, notaTextInputLayout, fechaTextInputLayout;
    private Button subirImagenButton, addPlataformaButton, selectFechaButton;
    private ArrayAdapter<String> adapter;
    private Spinner plataformaSpinner;
    private Context myContext;
    private Integer idGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = findViewById(R.id.formulario_toolbarFormulario);
        setSupportActionBar(toolbar);

        // Contexto de la Actividad para utilizarlo en las subclases
        myContext = this;

        // Flecha atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Cargamos el presentador
        presenter = new FormularioPresenter(this);

        initializeWidgets();
        initializeWidgetsListeners();

        // Oculto teclado
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tituloTextInputEditText.getWindowToken(), 0);

        idGame = getGameIDFromRV();
        Log.d(TAG, "ID del juego recibido: " + idGame);

        // Codigo temporal para comprobar el ID recibido
        TextView temporalTV = findViewById(R.id.formulario_IDTemporalTextView);
        temporalTV.setText("ID del juego: " + idGame);
    }

    // Para añadir el boton de guardar en el toolbar lo inflamos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Metodo que lanza la actividad listado, se llama desde onClickGuardar
    @Override
    public void launchListado() {
        Snackbar.make(findViewById(android.R.id.content), R.string.game_saved, Snackbar.LENGTH_LONG).show();
        finish();
    }

    @Override
    public boolean isValidForm() {
        if(tituloTextInputLayout.getError() == "" && desarrolladorTextInputLayout.getError() == "" && distribuidorTextInputLayout.getError() == "" && notaTextInputLayout.getError() == "" && fechaTextInputLayout.getError() == "") {
            return true;
        }
        Snackbar.make(findViewById(android.R.id.content), R.string.complete_all_fields, Snackbar.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void launchDeleteAlert() {
        // Recuperacion de la vista del AlertDialog a partir del layout de la Actividad
        LayoutInflater layoutActivity = LayoutInflater.from(myContext);
        View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog_delete_game, null);

        // Definicion del AlertDialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

        // Asignacion del AlertDialog a su vista
        alertDialog.setView(viewAlertDialog);

        // Recuperacion del EditText del AlertDialog
        final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);

        // Configuracion del AlertDialog
        alertDialog
            .setCancelable(false)
            // Boton Añadir
            .setPositiveButton(getResources().getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            Toast.makeText(myContext, R.string.game_deleted, Toast.LENGTH_SHORT).show();
                            launchListado();
                        }
                    })
            // Boton Cancelar
            .setNegativeButton(getResources().getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            dialogBox.cancel();
                        }
                    })
            .create()
            .show();
    }

    // Elegimos que hacer segun que boton del toolbar se ha pulsado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.formulario_action_save_game: // Cuando se pulsa el boton guardar
                Log.d(TAG, "Pulsando boton guardar juego...");
                presenter.onClickGuardar();
                return true;
            case R.id.formulario_action_delete_game: // Cuando se pulsa el boton eliminar
                Log.d(TAG, "Pulsando boton eliminar juego...");
                presenter.onClickEliminar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void initializeWidgets() {
        subirImagenButton = findViewById(R.id.formulario_subirImagenButton);
        tituloTextInputEditText = findViewById(R.id.formulario_tituloTextInputEditText);
        tituloTextInputLayout = findViewById(R.id.formulario_tituloTextInputLayout);
        desarrolladorTextInputEditText = findViewById(R.id.formulario_desarrolladorTextInputEditText);
        desarrolladorTextInputLayout = findViewById(R.id.formulario_desarrolladorTextInputLayout);
        distribuidorTextInputEditText = findViewById(R.id.formulario_distribuidorTextInputEditText);
        distribuidorTextInputLayout = findViewById(R.id.formulario_distribuidorTextInputLayout);
        notaTextInputEditText = findViewById(R.id.formulario_notaTextInputEditText);
        notaTextInputLayout = findViewById(R.id.formulario_notaTextInputLayout);
        fechaTextInputEditText = findViewById(R.id.formulario_fechaLanzamientoTextInputEditText);
        fechaTextInputLayout = findViewById(R.id.formulario_fechaLanzamientoTextInputLayout);
        plataformaSpinner = findViewById(R.id.formulario_plataformaSpinner);

        subirImagenButton = findViewById(R.id.formulario_subirImagenButton);
        addPlataformaButton = findViewById(R.id.formulario_addPlataformaButton);
        selectFechaButton = findViewById(R.id.formulario_selectFechaButton);

        Utils.getDatePicker(selectFechaButton, fechaTextInputEditText, myContext);
    }

    @Override
    public void initializeWidgetsListeners() {
        presenter.addTextChangedListener(tituloTextInputEditText, tituloTextInputLayout, false, false);
        presenter.addTextChangedListener(desarrolladorTextInputEditText, desarrolladorTextInputLayout, false, false);
        presenter.addTextChangedListener(distribuidorTextInputEditText, distribuidorTextInputLayout, false, false);
        presenter.addTextChangedListener(notaTextInputEditText, notaTextInputLayout, false, true);
        presenter.cargarDesplegable(); // loadSpinner()
        presenter.addTextChangedListener(fechaTextInputEditText, fechaTextInputLayout, true, false);
    }

    @Override
    public void loadSpinner(){
        // Definicion de la lista de opciones
        ArrayList<String> items = new ArrayList<String>();
        items.add("PC");
        items.add("PS4");
        items.add("Xbox One");
        items.add("Switch");

        // Definicion del Adaptador que contiene la lista de opciones
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // Definicion del Spinner
        plataformaSpinner.setAdapter(adapter);

        // Definicion de la accion del boton
        addPlataformaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // Recuperacion de la vista del AlertDialog a partir del layout de la Actividad
            LayoutInflater layoutActivity = LayoutInflater.from(myContext);
            View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog_add_platform, null);


            // Definicion del AlertDialog
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

            // Asignacion del AlertDialog a su vista
            alertDialog.setView(viewAlertDialog);

            // Recuperacion del EditText del AlertDialog
            final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);

            // Configuracion del AlertDialog
            alertDialog
                .setCancelable(false)
                // Boton Añadir
                .setPositiveButton(getResources().getString(R.string.add),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                if(dialogInput.length() > 0){
                                    adapter.add(dialogInput.getText().toString());
                                    plataformaSpinner.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                    Toast.makeText(myContext, R.string.item_added, Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(myContext, R.string.action_cancelled, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                // Boton Cancelar
                .setNegativeButton(getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                                Toast.makeText(myContext, R.string.action_cancelled, Toast.LENGTH_SHORT).show();
                            }
                        })
                .create()
                .show();
            }
        });
    }

    public int getGameIDFromRV() {
        // Recibo parametro desde el bundle
        // https://stackoverflow.com/a/3913720/10387022
        // Opcion alternativa https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null)
            value = b.getInt("id_game");
        return value;
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
