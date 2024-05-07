package com.example.mydragonballzapp;

public class Character {
    private String name;
    private String description;
    private String image;
    private String gender;
    private String race;

    public Character(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }


    public Character(String name, String description, String image, String gender, String race) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.gender = gender;
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
