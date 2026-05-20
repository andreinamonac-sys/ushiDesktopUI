package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;

public class OpenAnimalSearchController extends OpenViewController {

    public OpenAnimalSearchController(NavigationService navigationService) {
        super(navigationService);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigationService.openAnimalSearch();
    }
}
