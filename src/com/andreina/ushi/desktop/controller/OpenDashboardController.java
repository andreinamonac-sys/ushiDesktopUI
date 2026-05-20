package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;

public class OpenDashboardController extends OpenViewController {

    public OpenDashboardController(NavigationService navigationService) {
        super(navigationService);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigationService.openDashboard();
    }
}
