package com.paysystem.app;

import com.paysystem.utils.Crud;
import com.paysystem.view.MainMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        new Crud();
//        AuthMenu authMenu = new AuthMenu();
//
//        authMenu.authMenuConsole();

        MainMenu mainMenu = new MainMenu();
        mainMenu.handleAuthenticate();


    }
}