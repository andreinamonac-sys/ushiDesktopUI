package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;

public class OpenGranjaSearchController extends OpenViewController {

    public OpenGranjaSearchController(NavigationService navigationService) {
        super(navigationService);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigationService.openGranjas();
    }
}
