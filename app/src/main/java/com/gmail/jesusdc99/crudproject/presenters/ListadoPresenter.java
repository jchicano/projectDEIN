package com.gmail.jesusdc99.crudproject.presenters;

import com.gmail.jesusdc99.crudproject.interfaces.ListadoInterface;
import com.gmail.jesusdc99.crudproject.models.Game;
import com.gmail.jesusdc99.crudproject.models.GameModel;

import java.util.ArrayList;

public class ListadoPresenter implements ListadoInterface.Presenter {

    private ListadoInterface.View view;
    private GameModel game;

    public ListadoPresenter(ListadoInterface.View view) {
        this.view = view;
        this.game = new GameModel();
    }

    @Override
    public void onClickAdd() {
        view.launchForm(-1);
    }

    @Override
    public void onClickSobreAppCRUD() {
        view.launchAbout();
    }

    @Override
    public void onClickBuscar() {
        view.launchBuscar();
    }

    @Override
    public ArrayList<Game> getAllGames() {
        return game.getAllGames();
    }

    @Override
    public void onClickRecyclerView(int id) {
        view.launchForm(id);
    }
}
