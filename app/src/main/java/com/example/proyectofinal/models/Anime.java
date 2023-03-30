package com.example.proyectofinal.models;

public class Anime {

    private int id;
    private String name;
    private int current;
    private int episode;
    private String description;
    private String urlImage;
    private int idUser;

    public Anime() {
    }

    public Anime(int id, String name, int current, int episode, String description, String urlImage, int idUser) {
        this.id = id;
        this.name = name;
        this.current = current;
        this.episode = episode;
        this.description = description;
        this.urlImage = urlImage;
        this.idUser = idUser;
    }

    public Anime(String name, int current, int episode, String description, String url, int idUser) {
        this.name = name;
        this.current = current;
        this.episode = episode;
        this.description = description;
        this.urlImage = url;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }


}
