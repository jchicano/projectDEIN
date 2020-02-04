package com.gmail.jesusdc99.crudproject.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.gmail.jesusdc99.crudproject.interfaces.BuscarInterface;
import com.gmail.jesusdc99.crudproject.models.GameModel;
import com.gmail.jesusdc99.crudproject.presenters.BuscarPresenter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.gmail.jesusdc99.crudproject.R;
import com.gmail.jesusdc99.crudproject.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

public class BuscarActivity extends AppCompatActivity implements BuscarInterface.View {

    private static final String TAG = "APPCRUD/Buscar";
    private Context myContext;
    private BuscarPresenter presenter;
    TextInputLayout tituloTextInputLayout, fechaLanzamientoTextInputLayout;
    TextInputEditText tituloTextInputEditText, fechaLanzamientoTextInputEditText;
    private ArrayAdapter<String> adapter;
    Spinner plataformaSpinner;
    Button selectFechaButton, buscarAhoraButton;
    TextView fechaLanzamientoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myContext = this;

        // Flecha atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Cargamos el presentador
        presenter = new BuscarPresenter(this);

        initializeWidgets();
        initializeWidgetsListeners();
    }

    @Override
    public void initializeWidgets() {
        tituloTextInputLayout = findViewById(R.id.buscar_tituloTextInputLayout);
        tituloTextInputEditText = findViewById(R.id.buscar_tituloTextInputEditText);
        plataformaSpinner = findViewById(R.id.buscar_plataformaSpinner);
        loadSpinner();
        fechaLanzamientoTextView = findViewById(R.id.buscar_fechaLanzamientoTextView);
        selectFechaButton = findViewById(R.id.buscar_selectFechaButton);
        Utils.getDatePicker(selectFechaButton, fechaLanzamientoTextView, myContext);
        buscarAhoraButton = findViewById(R.id.buscar_buscarAhoraButton);
    }

    @Override
    public void initializeWidgetsListeners() {
        // Click en boton buscar ahora
        buscarAhoraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(myContext, "Búsqueda realizada", Toast.LENGTH_SHORT).show();
                //finish();
                // Si el EditText no está vacío recogemos el resultado.
                // Guardamos el texto del EditText en un String.
                String tituloSafe = "%";
                String fechaSafe = "%";
                if(tituloTextInputEditText.getText().length() != 0) {
                    tituloSafe = tituloTextInputEditText.getText().toString();
                    Log.d(TAG, "=================");
                    Log.d(TAG, "Titulo no esta vacio");
                }
                try {
                    if(!fechaLanzamientoTextInputEditText.getText().equals("")) {
                        fechaSafe = fechaLanzamientoTextInputEditText.getText().toString();
                    }
                }
                catch (Exception e) {
                    //Log.d(TAG, e.toString());
                    Log.d(TAG, "El campo fecha esta vacio");
                }

                String resultado = tituloSafe+";;;"+plataformaSpinner.getSelectedItem().toString()+";;;"+fechaSafe;
                // Recogemos el intent que ha llamado a esta actividad.
                Intent i = getIntent();
                // Le metemos el resultado que queremos mandar a la
                // actividad principal.
                i.putExtra("RESULTADO", resultado);
                // Establecemos el resultado, y volvemos a la actividad
                // principal. La variable que introducimos en primer lugar
                // "RESULT_OK" es de la propia actividad, no tenemos que
                // declararla nosotros.
                setResult(RESULT_OK, i);

                // Finalizamos la Activity para volver a la anterior
                finish();
            }
                    // Si no tenía nada escrito el EditText lo avisamos.
                    //Toast.makeText(myContext, "No se ha introducido nada en el campo de texto", Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public void loadSpinner(){
        // Definicion de la lista de opciones
        ArrayList<String> items = GameModel.getPlatformsList(true);

        // Definicion del Adaptador que contiene la lista de opciones
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // Definicion del Spinner
        plataformaSpinner.setAdapter(adapter);
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
