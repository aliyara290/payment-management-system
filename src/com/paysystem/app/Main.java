package com.paysystem.app;

import com.paysystem.utils.Crud;
import com.paysystem.view.AuthMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        AuthMenu authMenu = new AuthMenu();

        authMenu.authMenuConsole();
    }
}