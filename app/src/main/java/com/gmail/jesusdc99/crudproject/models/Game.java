package com.gmail.jesusdc99.crudproject.models;

import com.gmail.jesusdc99.crudproject.utils.Utils;

import java.util.Date;

public class Game {

    public Integer id;
    public String image;
    public String title;
    public String platform;
    public String developer;
    public String publisher;
    public String rating;
    public String releaseDate;
    public boolean nuevo;

    public Game() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public boolean setTitle(String title) {
        try{
            if(!title.equals("")){
                this.title = title;
                return true;
            }

        }
        catch (Exception e) { }
        return false;
    }

    public String getPlatform() {
        return platform;
    }

    public boolean setPlatform(String platform) {
        try{
            if(!platform.equals("")){
                this.platform = platform;
                return true;
            }
        }
        catch (Exception e) { }
        return false;
    }

    public String getDeveloper() {
        return developer;
    }

    public boolean setDeveloper(String developer) {
        try{
            if(!developer.equals("")){
                this.developer = developer;
                return true;
            }
        }
        catch (Exception e) { }
        return false;
    }

    public String getPublisher() {
        return publisher;
    }

    public boolean setPublisher(String publisher) {
        try{
            if(!publisher.equals("")){
                this.publisher = publisher;
                return true;
            }
        }
        catch (Exception e) { }
        return false;
    }

    public String getRating() {
        return rating;
    }

    public boolean setRating(String rating) {
        try{
            if(!rating.equals("") && Utils.validateNota(rating)){
                this.rating = rating;
                return true;
            }
        }
        catch (Exception e) { }
        return false;
    }

    public String getreleaseDate() {
        return releaseDate;
    }

    public boolean setReleaseDate(String releaseDate) {
        try{
            if(!releaseDate.equals("") && Utils.validateDate(releaseDate)){
                this.releaseDate = releaseDate;
                return true;
            }
        }
        catch (Exception e) { }
        return false;
    }

    public boolean getNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) { // Al ser booleano ya esta inicializado
        this.nuevo = nuevo;
    }
}
