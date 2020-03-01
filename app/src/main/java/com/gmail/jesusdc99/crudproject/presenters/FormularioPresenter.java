package com.gmail.jesusdc99.crudproject.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.gmail.jesusdc99.crudproject.R;
import com.gmail.jesusdc99.crudproject.interfaces.FormularioInterface;
import com.gmail.jesusdc99.crudproject.models.Game;
import com.gmail.jesusdc99.crudproject.models.GameModel;
import com.gmail.jesusdc99.crudproject.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class FormularioPresenter implements FormularioInterface.Presenter {

    private FormularioInterface.View view;
    private GameModel model;

    public FormularioPresenter(FormularioInterface.View view) {
        this.view = view;
        model = GameModel.getInstance();
    }

    @Override
    public void onClickGuardar(Game game, Context context) {
        if(view.isValidForm()) {
            if(game.getId() == -1){
                model.addNew(game);
            }
            else {
                if(model.updateGame(game)){
                    Toast.makeText(context,context.getResources().getString(R.string.game_updated),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,context.getResources().getString(R.string.game_updated_error),Toast.LENGTH_SHORT).show();
                }
            }
            view.launchListado();
        }
        else {
            view.showSnackbar(context.getString(R.string.complete_all_fields));
        }
    }

    @Override
    public void onClickEliminar() {
        view.launchDeleteAlert();
    }

    @Override
    public void cargarDesplegable() {
        view.loadSpinner();
    }

    @Override
    public void onClickAyuda() { view.launchAyuda(); }

    @Override
    public void addTextChangedListener(EditText input, final TextInputLayout layout, final boolean validarFecha, final boolean validarNota) {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(validarFecha) {
                    if(!Utils.validateDate(s)) {
                        layout.setError("Formato incorrecto");
                        layout.setErrorEnabled(true);
                    }
                    else{
                        layout.setError(null);
                        layout.setErrorEnabled(false);
                    }
                }
                else if(validarNota) {
                    if(!Utils.validateNota(s)) {
                        layout.setError("Valor incorrecto");
                        layout.setErrorEnabled(true);
                    }
                    else{
                        layout.setError(null);
                        layout.setErrorEnabled(false);
                    }
                }
                else if(s.length() == 0) {
                    layout.setError("Campo obligatorio");
                    layout.setErrorEnabled(true);
                }
                else{
                    layout.setError(null);
                    layout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public void onclickButtonUploadImage(Context myContext) {
        int readPermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            view.requestPermission();
        }
        else{
            view.launchGallery();
        }
    }

    @Override
    public void resultPermission(int result, Context context) {
        if (result == PackageManager.PERMISSION_GRANTED) {
            // Permiso aceptado
            Log.d("TEST/", "Permiso aceptado");
            view.launchGallery();
        } else {
            // Permiso rechazado
            Log.d("TEST/", "Permiso rechazado");
            view.showSnackbar(context.getString(R.string.permissions_needed));
        }
    }

    @Override
    public Bitmap manageRequestForImage(int requestCode, int resultCode, Intent data, Context context) {
        Bitmap bmpCompressed = null;
        if (resultCode == Activity.RESULT_OK) {
            // Se carga la imagen desde un objeto Bitmap
            Uri selectedImage = data.getData();
            String selectedPath = selectedImage.getPath();

            if (selectedPath != null) {
                // Se leen los bytes de la imagen
                InputStream imageStream = null;
                try {
                    imageStream = context.getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // Se transformam los bytes de la imagen a un Bitmap
                Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                // Comprimo la imagen bajandole la resolucion
                // https://stackoverflow.com/a/28425042/10387022
                int nh = (int) ( bmp.getHeight() * (180.0 / bmp.getWidth()) );
                bmpCompressed = Bitmap.createScaledBitmap(bmp, 180, nh, true);
            }
        }
        return bmpCompressed;
    }

    @Override
    public boolean deleteGame(int id) {
        return model.deleteGame(id);
    }
}
