package com.gmail.jesusdc99.crudproject.interfaces;

import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;

public interface FormularioInterface {

    interface View {
        void launchListado();
        void initializeWidgets();
        void initializeWidgetsListeners();
        void loadSpinner();
        void launchDeleteAlert();
        boolean isValidForm();
    }

    interface Presenter {
        void onClickGuardar();
        void onClickEliminar();
        void cargarDesplegable();
        void addTextChangedListener(EditText input, final TextInputLayout layout, final boolean validarFecha, final boolean validarNota);
    }

}
