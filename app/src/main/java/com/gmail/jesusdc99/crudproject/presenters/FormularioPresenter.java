package com.gmail.jesusdc99.crudproject.presenters;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.gmail.jesusdc99.crudproject.interfaces.FormularioInterface;
import com.gmail.jesusdc99.crudproject.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

public class FormularioPresenter implements FormularioInterface.Presenter {

    private FormularioInterface.View view;

    public FormularioPresenter(FormularioInterface.View view) {
        this.view = view;
    }

    @Override
    public void onClickGuardar() {
        if(view.isValidForm()) {
            view.launchListado();
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
}
