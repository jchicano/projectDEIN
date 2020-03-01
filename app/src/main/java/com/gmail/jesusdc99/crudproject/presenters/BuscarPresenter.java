package com.gmail.jesusdc99.crudproject.presenters;

import com.gmail.jesusdc99.crudproject.interfaces.BuscarInterface;

public class BuscarPresenter implements BuscarInterface.Presenter{
    private BuscarInterface.View view;

    public BuscarPresenter(BuscarInterface.View view) {
        this.view = view;
    }

    @Override
    public void onClickAyuda() { view.launchAyuda(); }
}
