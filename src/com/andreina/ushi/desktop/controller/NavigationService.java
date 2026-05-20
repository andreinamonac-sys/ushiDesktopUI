package com.andreina.ushi.desktop.controller;

import com.andreina.ushi.desktop.MainWindow;
import com.andreina.ushi.desktop.RolePermissions;
import com.andreina.ushi.desktop.view.AnimalSearchView;
import com.andreina.ushi.desktop.view.DashboardView;
import com.andreina.ushi.desktop.view.EstadisticasView;
import com.andreina.ushi.desktop.view.EventoSearchView;
import com.andreina.ushi.desktop.view.GranjaSearchView;
import com.andreina.ushi.desktop.view.MonitorizacionView;
import com.andreina.ushi.desktop.view.UsuarioSearchView;

public class NavigationService {

    private final MainWindow mainWindow;

    public NavigationService(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void openDashboard() {
        DashboardView view = new DashboardView();
        view.setSummary(mainWindow.getCurrentUserLabel(), mainWindow.getCurrentRoleLabel(),
                mainWindow.getPermittedViewsLabel());
        DashboardController controller = new DashboardController(view);
        controller.loadAlertas();
        mainWindow.setView(view);
    }

    public void openAnimalSearch() {
        if (!RolePermissions.canOpenAnimals(mainWindow.getCurrentRoleName())) {
            mainWindow.showAccessDenied();
            return;
        }
        AnimalSearchView view = new AnimalSearchView();
        new AnimalSearchController(view);
        mainWindow.setView(view);
    }

    public void openEventos() {
        if (!RolePermissions.canOpenEvents(mainWindow.getCurrentRoleName())) {
            mainWindow.showAccessDenied();
            return;
        }
        EventoSearchView view = new EventoSearchView();
        new EventoSearchController(view);
        mainWindow.setView(view);
    }

    public void openGranjas() {
        if (!RolePermissions.canOpenFarms(mainWindow.getCurrentRoleName())) {
            mainWindow.showAccessDenied();
            return;
        }
        GranjaSearchView view = new GranjaSearchView();
        new GranjaSearchController(view);
        mainWindow.setView(view);
    }

    public void openMonitorizacion() {
        if (!RolePermissions.canOpenMonitoring(mainWindow.getCurrentRoleName())) {
            mainWindow.showAccessDenied();
            return;
        }
        MonitorizacionView view = new MonitorizacionView();
        new MonitorizacionController(view);
        mainWindow.setView(view);
    }

    public void openEstadisticas() {
        if (!RolePermissions.canOpenStatistics(mainWindow.getCurrentRoleName())) {
            mainWindow.showAccessDenied();
            return;
        }
        EstadisticasView view = new EstadisticasView();
        new EstadisticasController(view);
        mainWindow.setView(view);
    }

    public void openUsuarios() {
        if (!RolePermissions.canOpenUsers(mainWindow.getCurrentRoleName())) {
            mainWindow.showAccessDenied();
            return;
        }
        UsuarioSearchView view = new UsuarioSearchView();
        new UsuarioSearchController(view);
        mainWindow.setView(view);
    }
}
