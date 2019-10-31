package com.gmail.jesusdc99.crudproject.presenters;

import com.gmail.jesusdc99.crudproject.interfaces.FormularioInterface;

public class FormularioPresenter implements FormularioInterface.Presenter {

    private FormularioInterface.View view;

    public FormularioPresenter(FormularioInterface.View view) {
        this.view = view;
    }

    @Override
    public void onClickGuardar() {
        view.launchListado();
    }
}
