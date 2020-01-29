package com.gmail.jesusdc99.crudproject.interfaces;

import com.gmail.jesusdc99.crudproject.models.Game;

import java.util.ArrayList;

public interface ListadoInterface {

    // Metodos de la vista
    interface View {
        void launchForm(int id);
        void launchAbout();
        void launchBuscar();
        void initializeWidgets();
        void initializeWidgetsListeners();
        void initializeFloatingActionButton();
        //void getGamesList();
        void getGamesListWithFilter(String[] params);
    }

    // Metodos del presentador
    interface Presenter {
        void onClickAdd();
        void onClickSobreAppCRUD();
        void onClickBuscar();
        //ArrayList<Game> getAllGames();
        void onClickRecyclerView(int id);
        void initializeDatabase();
        ArrayList<Game> getGamesByCriteria(String title, String platform, String date);
    }

}
