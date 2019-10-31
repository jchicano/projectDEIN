package com.gmail.jesusdc99.crudproject.interfaces;

public interface ListadoInterface {

    // Metodos de la vista
    public interface View {
        void launchForm();
    }

    // Metodos del presentador
    public interface Presenter {
        void onClickAdd();
    }

}
