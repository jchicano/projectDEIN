package com.gmail.jesusdc99.crudproject.presenters;

import com.gmail.jesusdc99.crudproject.interfaces.ListadoInterface;

public class ListadoPresenter implements ListadoInterface.Presenter {

    private ListadoInterface.View view;

    public ListadoPresenter(ListadoInterface.View view) {
        this.view = view;
    }

    @Override
    public void onClickAdd(){
        view.launchForm();
    }
}
