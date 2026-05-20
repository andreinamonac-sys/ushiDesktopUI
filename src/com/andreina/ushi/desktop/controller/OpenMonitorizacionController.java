package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;

public class OpenMonitorizacionController extends OpenViewController {

    public OpenMonitorizacionController(NavigationService navigationService) {
        super(navigationService);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigationService.openMonitorizacion();
    }
}
