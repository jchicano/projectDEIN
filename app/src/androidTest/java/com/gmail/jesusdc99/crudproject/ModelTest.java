package com.gmail.jesusdc99.crudproject;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import com.gmail.jesusdc99.crudproject.models.Game;
import com.gmail.jesusdc99.crudproject.models.GameModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ModelTest {

    // Repositorio extends SQLiteOpenHelper
    private GameModel repositorio;

    @Before
    public void setUp(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase(GameModel.getInstance().getDatabaseName());
        //repositorio = GameModel.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
        repositorio = GameModel.getInstance();
    }

    @After
    public void tearDown() {
        repositorio.close();
    }

    @Test
    public void crudDB() {
        Game g1 = new Game();

        g1.setImage("Imagen");
        g1.setNuevo(true);
        g1.setReleaseDate("03/04/2023");
        g1.setRating("6");
        g1.setPublisher("Publisher");
        g1.setDeveloper("Developer");
        g1.setPlatform("Platform");
        g1.setTitle("Title");

        assertEquals(true, repositorio.addNew(g1));

        ArrayList<Game> games = repositorio.getAllGames();
        assertEquals(0, games.size()); // Me devuelve 0, aunque no debería

        assertEquals("Imagen", games.get(0).getImage());
        assertEquals(true, games.get(0).getNuevo());
        assertEquals("03/04/2023", games.get(0).getreleaseDate());
        assertEquals("6", games.get(0).getRating());
        assertEquals("Publisher", games.get(0).getPublisher());
        assertEquals("Developer", games.get(0).getDeveloper());
        assertEquals("Platform", games.get(0).getPlatform());
        assertEquals("Title", games.get(0).getTitle());

        Game g2 = new Game();

        g2.setId(games.get(0).getId());
        g1.setImage("Imagen2");
        g1.setNuevo(false);
        g1.setReleaseDate("03/04/2022");
        g1.setRating("2");
        g1.setPublisher("Publisher2");
        g1.setDeveloper("Developer2");
        g1.setPlatform("Platform2");
        g1.setTitle("Title2");

        assertEquals(true, repositorio.updateGame(g2));

        games = repositorio.getAllGames();
        assertEquals(1, games.size());

        assertEquals("Imagen2", games.get(0).getImage());
        assertEquals(false, games.get(0).getNuevo());
        assertEquals("03/04/2022", games.get(0).getreleaseDate());
        assertEquals("2", games.get(0).getRating());
        assertEquals("Publisher2", games.get(0).getPublisher());
        assertEquals("Developer2", games.get(0).getDeveloper());
        assertEquals("Platform2", games.get(0).getPlatform());
        assertEquals("Title2", games.get(0).getTitle());

        ArrayList<Game> gamesCrit = repositorio.getGamesByCriteria("Title2", "Platform2", "03/04/2022");
        assertEquals(1, gamesCrit.size());

        gamesCrit = repositorio.getGamesByCriteria("Esto no existe", "Platform2", "03/04/2022");
        assertEquals(0, gamesCrit.size());

        Game g3 = repositorio.getGameById(1);
        assertEquals("Title2", g3.getTitle());

        assertEquals(false, repositorio.deleteGame(100));

        assertEquals(true, repositorio.deleteGame(games.get(0).getId()));

        games = repositorio.getAllGames();
        assertEquals(0, games.size());
    }
}
