package com.gmail.jesusdc99.crudproject;

import com.gmail.jesusdc99.crudproject.models.Game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntidadDatosTest {
    Game g = new Game();

    @Test
    public void entidad_propiedades() {
        // Setters
        assertEquals(g.setDeveloper("Dev"), true);
        assertEquals(g.setDeveloper(""), false);
        assertEquals(g.setPlatform("Plat"), true);
        assertEquals(g.setPlatform(""), false);
        assertEquals(g.setPublisher("Pub"), true);
        assertEquals(g.setPublisher(""), false);
        assertEquals(g.setRating("4"), true);
        assertEquals(g.setRating(""), false);
        assertEquals(g.setReleaseDate("29/02/2020"), true);
        assertEquals(g.setReleaseDate("29/02/2021"), false);
        assertEquals(g.setReleaseDate("30/02/2080"), false);
        assertEquals(g.setReleaseDate("Fecha"), false);
        assertEquals(g.setReleaseDate(""), false);
        assertEquals(g.setTitle("Title"), true);
        assertEquals(g.setTitle(""), false);
        assertEquals(g.setId(2), true);
        assertEquals(g.setImage("Ima"), true);
        assertEquals(g.setImage(""), false);

        // Getters
        assertEquals("Dev", g.getDeveloper());
        assertEquals("Plat", g.getPlatform());
        assertEquals("Pub", g.getPublisher());
        assertEquals("4", g.getRating());
        assertEquals("29/02/2020", g.getreleaseDate());
        assertEquals("Title", g.getTitle());
        assertEquals("Ima", g.getImage());
    }

}
