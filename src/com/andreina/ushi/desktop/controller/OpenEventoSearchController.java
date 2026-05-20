package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;

public class OpenEventoSearchController extends OpenViewController {

    public OpenEventoSearchController(NavigationService navigationService) {
        super(navigationService);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigationService.openEventos();
    }
}
