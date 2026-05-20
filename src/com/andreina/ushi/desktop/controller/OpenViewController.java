package com.andreina.ushi.desktop.controller;

import com.andreina.ushi.desktop.MainWindow;

public abstract class OpenViewController extends AbstractController {

    protected final NavigationService navigationService;

    protected OpenViewController(MainWindow mainWindow) {
        this(new NavigationService(mainWindow));
    }

    protected OpenViewController(NavigationService navigationService) {
        this.navigationService = navigationService;
    }
}
