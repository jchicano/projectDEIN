package com.gmail.jesusdc99.crudproject.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import com.gmail.jesusdc99.crudproject.R;
import com.gmail.jesusdc99.crudproject.interfaces.FormularioInterface;
import com.gmail.jesusdc99.crudproject.models.Game;
import com.gmail.jesusdc99.crudproject.models.GameModel;
import com.gmail.jesusdc99.crudproject.presenters.FormularioPresenter;
import com.gmail.jesusdc99.crudproject.utils.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import android.util.Base64;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FormularioActivity extends AppCompatActivity implements FormularioInterface.View {

    private static final String TAG = "APPCRUD/Formulario";
    private FormularioInterface.Presenter presenter;
    private TextInputEditText tituloTextInputEditText, desarrolladorTextInputEditText, distribuidorTextInputEditText, notaTextInputEditText, fechaTextInputEditText;
    private TextInputLayout tituloTextInputLayout, desarrolladorTextInputLayout, distribuidorTextInputLayout, notaTextInputLayout, fechaTextInputLayout;
    private Button subirImagenButton, addPlataformaButton, selectFechaButton;
    private ImageView caratulaImageView;
    private ArrayAdapter<String> adapter;
    private Spinner plataformaSpinner;
    private Switch nuevoSwitch;
    private Context myContext;
    private Integer idGame;
    final private int CODE_READ_EXTERNAL_STORAGE_PERMISSION = 123;
    private static final int REQUEST_SELECT_IMAGE = 201;
    private ConstraintLayout constraintLayoutFormularioActivity;

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

        // Recuperamos el Layout donde mostrar el Snackbar con las notificaciones
        constraintLayoutFormularioActivity = findViewById(R.id.formulario_ConstaintLayout);

        initializeWidgets();
        initializeWidgetsListeners();
        fillFormInputWithReceivedData();

        // Oculto teclado
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tituloTextInputEditText.getWindowToken(), 0);
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
        Snackbar.make(findViewById(android.R.id.content), myContext.getResources().getString(R.string.game_saved), Snackbar.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void launchAyuda() {
        Intent intent = new Intent(getBaseContext(), AyudaActivity.class);
        intent.putExtra("activity_from", "formulario");
        startActivity(intent);
    }

    @Override
    public boolean isValidForm() {
        if(tituloTextInputLayout.getError() == null && desarrolladorTextInputLayout.getError() == null && distribuidorTextInputLayout.getError() == null && notaTextInputLayout.getError() == null && fechaTextInputLayout.getError() == null) {
            return true;
        }
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
                            if(presenter.deleteGame(idGame)){
                                Toast.makeText(myContext, R.string.game_deleted, Toast.LENGTH_SHORT).show();
                                launchListado();
                            }
                            else {
                                Toast.makeText(myContext, R.string.game_deleted_error, Toast.LENGTH_SHORT).show();
                            }

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
                Game g = new Game();
                g.setId(idGame);
                g.setTitle(tituloTextInputEditText.getText().toString());
                g.setPlatform(plataformaSpinner.getSelectedItem().toString());
                g.setDeveloper(desarrolladorTextInputEditText.getText().toString());
                g.setPublisher(distribuidorTextInputEditText.getText().toString());
                g.setReleaseDate(fechaTextInputEditText.getText().toString());
                g.setRating(notaTextInputEditText.getText().toString());
                // Convierto el BitMap a Base64
                try{
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ((BitmapDrawable) caratulaImageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    g.setImage(encoded);
                }
                catch (Exception e) {
                    Log.d(TAG, "No hay imagen, seleccionando una por defecto...");
                }
                presenter.onClickGuardar(g, myContext);
                return true;
            case R.id.formulario_action_delete_game: // Cuando se pulsa el boton eliminar
                Log.d(TAG, "Pulsando boton eliminar juego...");
                presenter.onClickEliminar();
                return true;
            case R.id.formulario_action_ayuda: //  Cuando se pulsa la opcion buscar
                Log.d(TAG, "Pulsando opcion ayuda...");
                presenter.onClickAyuda();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void initializeWidgets() {
        caratulaImageView = findViewById(R.id.formulario_caratulaImageView);
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
        nuevoSwitch = findViewById(R.id.formulario_nuevoSwitch);

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

        subirImagenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onclickButtonUploadImage(myContext);
            }
        });

        this.tituloTextInputLayout.setError(myContext.getResources().getString(R.string.obligatory_field));
        this.tituloTextInputLayout.setErrorEnabled(true);
        this.desarrolladorTextInputLayout.setError(myContext.getResources().getString(R.string.obligatory_field));
        this.desarrolladorTextInputLayout.setErrorEnabled(true);
        this.distribuidorTextInputLayout.setError(myContext.getResources().getString(R.string.obligatory_field));
        this.distribuidorTextInputLayout.setErrorEnabled(true);
        this.notaTextInputLayout.setError(myContext.getResources().getString(R.string.obligatory_field));
        this.notaTextInputLayout.setErrorEnabled(true);
        this.fechaTextInputLayout.setError(myContext.getResources().getString(R.string.obligatory_field));
        this.fechaTextInputLayout.setErrorEnabled(true);
    }

    @Override
    public void loadSpinner(){
        // Definicion de la lista de opciones
        ArrayList<String> items = GameModel.getPlatformsList(false);

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

    @Override
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

    @Override
    public void fillFormInputWithReceivedData() {
        idGame = getGameIDFromRV();
        Log.d(TAG, "ID del juego recibido: " + idGame);

        Game juego = GameModel.getInstance().getGameById(idGame);
        tituloTextInputEditText.setText(juego.getTitle());
        desarrolladorTextInputEditText.setText(juego.getDeveloper());
        distribuidorTextInputEditText.setText(juego.getPublisher());
        notaTextInputEditText.setText(juego.getRating());

        int spinnerPosition = adapter.getPosition(juego.getPlatform());
        plataformaSpinner.setSelection(spinnerPosition);

        fechaTextInputEditText.setText(juego.getreleaseDate());
        nuevoSwitch.setChecked(juego.getNuevo());

        if(juego.getImage() != null) {
            byte[] decodedString = Base64.decode(juego.getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            caratulaImageView.setImageBitmap(decodedByte);
        }
    }

    @Override
    public void requestPermission() {
        // Pedimos permisos al usuario
        ActivityCompat.requestPermissions(FormularioActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_READ_EXTERNAL_STORAGE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_READ_EXTERNAL_STORAGE_PERMISSION:
                presenter.resultPermission(grantResults[0], myContext);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void launchGallery(){
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.choose_picture)),
                REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (REQUEST_SELECT_IMAGE):
                Bitmap bmCaratula = presenter.manageRequestForImage(requestCode, resultCode, data, myContext);
                if(!bmCaratula.equals(null)){
                    // Se carga el Bitmap en el ImageView
                    caratulaImageView.setImageBitmap(bmCaratula);
                }
                break;
        }
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public void showSnackbar(String msg) {
        Snackbar.make(constraintLayoutFormularioActivity, msg, Snackbar.LENGTH_SHORT).show();
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
