package com.gmail.jesusdc99.crudproject.interfaces;

public interface ListadoInterface {

    // Metodos de la vista
    interface View {
        void launchForm();
        void launchAbout();
        void launchBuscar();
        void initializeWidgets();
        void initializeWidgetsListeners();
        void initializeFloatingActionButton();
    }

    // Metodos del presentador
    interface Presenter {
        void onClickAdd();
        void onClickSobreAppCRUD();
        void onClickBuscar();
    }

}
