package com.example.fypcalisthenicsapp.main.models;

public class MainMenuModel {

    String menuName;
    int menuImage;


    public MainMenuModel(String menuName, int menuImage) {
        this.menuName = menuName;
        this.menuImage = menuImage;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getMenuImage() {
        return menuImage;
    }
}
