package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;

public class OpenEstadisticasController extends OpenViewController {

    public OpenEstadisticasController(NavigationService navigationService) {
        super(navigationService);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigationService.openEstadisticas();
    }
}
