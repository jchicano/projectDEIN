package com.gmail.jesusdc99.crudproject.interfaces;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;

public interface FormularioInterface {

    interface View {
        void launchListado();
        void initializeWidgets();
        void initializeWidgetsListeners();
        void loadSpinner();
        void launchDeleteAlert();
        boolean isValidForm();
        int getGameIDFromRV();
        void requestPermission();
        void launchGallery();
        void showSnackbar(String msg);
    }

    interface Presenter {
        void onClickGuardar();
        void onClickEliminar();
        void cargarDesplegable();
        void addTextChangedListener(EditText input, final TextInputLayout layout, final boolean validarFecha, final boolean validarNota);
        void onclickButtonUploadImage(Context contextCompat);
        void resultPermission(int result, Context context);
        Bitmap manageRequestForImage(int requestCode, int resultCode, Intent data, Context context);
    }

}
