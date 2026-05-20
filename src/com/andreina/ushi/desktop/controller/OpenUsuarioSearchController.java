package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;

public class OpenUsuarioSearchController extends OpenViewController {

    public OpenUsuarioSearchController(NavigationService navigationService) {
        super(navigationService);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigationService.openUsuarios();
    }
}
