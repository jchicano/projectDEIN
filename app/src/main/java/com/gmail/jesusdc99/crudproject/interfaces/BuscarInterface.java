package com.gmail.jesusdc99.crudproject.interfaces;

public interface BuscarInterface {

    // Metodos de la vista
    interface View {
        void initializeWidgets();
        void initializeWidgetsListeners();
        void loadSpinner();
        void launchAyuda();
    }

    // Metodos del presentador
    interface Presenter {
        void onClickAyuda();
    }

}
