package com.andreina.ushi.desktop.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.desktop.MainWindow;
import com.andreina.ushi.desktop.model.AlertaTableModel;
import com.andreina.ushi.desktop.renderer.AlertaActionsEditor;
import com.andreina.ushi.desktop.view.AnimalView;
import com.andreina.ushi.desktop.view.DashboardView;
import com.andreina.ushi.model.AlertaDTO;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.ParametroDTO;
import com.andreina.ushi.service.AlertaService;
import com.andreina.ushi.service.AnimalService;
import com.andreina.ushi.service.EventoService;
import com.andreina.ushi.service.ParametroService;
import com.andreina.ushi.service.impl.AlertaServiceImpl;
import com.andreina.ushi.service.impl.AnimalServiceImpl;
import com.andreina.ushi.service.impl.EventoServiceImpl;
import com.andreina.ushi.service.impl.ParametroServiceImpl;

public class DashboardController implements AlertaActionsEditor.AlertaActionHandler {

    private final DashboardView view;
    private final AlertaService alertaService;
    private final AnimalService animalService;
    private final EventoService eventoService;
    private final ParametroService parametroService;

    public DashboardController(DashboardView view) {
        this.view = view;
        this.alertaService = new AlertaServiceImpl();
        this.animalService = new AnimalServiceImpl();
        this.eventoService = new EventoServiceImpl();
        this.parametroService = new ParametroServiceImpl();
        this.view.setAlertaActionsHandler(this);
        this.view.getAlertasTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openAnimalFromAlert(e);
            }
        });
    }

    public void loadAlertas() {
        try {
            view.setAlertas(alertaService.findUltimas(10));
        } catch (Exception e) {
            view.setAlertas(new ArrayList<AlertaDTO>());
        }
    }

    @Override
    public void markAsRead(AlertaDTO alerta) {
        if (alerta == null || alerta.getId() == null) {
            return;
        }
        try {
            alertaService.marcarLeida(alerta.getId());
            loadAlertas();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(view, "No se pudo marcar la alerta como leida.",
                    "Alertas", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openAnimalFromAlert(MouseEvent e) {
        int row = view.getAlertasTable().rowAtPoint(e.getPoint());
        int column = view.getAlertasTable().columnAtPoint(e.getPoint());
        if (row < 0 || column == AlertaTableModel.ACTIONS_COLUMN) {
            return;
        }
        int modelRow = view.getAlertasTable().convertRowIndexToModel(row);
        AlertaDTO alerta = view.getAlertaAt(modelRow);
        if (alerta == null || alerta.getAnimalId() == null) {
            javax.swing.JOptionPane.showMessageDialog(view, "Esta alerta no tiene animal asociado.",
                    "Alertas", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            AnimalDTO animal = animalService.findById(alerta.getAnimalId());
            ParametroCriteria parametroCriteria = new ParametroCriteria();
            parametroCriteria.setAnimalId(alerta.getAnimalId());
            java.util.List<ParametroDTO> parametros = parametroService.findByCriteria(parametroCriteria, 1, 1000);
            MainWindow.getInstance().setView(new AnimalView(animal,
                    eventoService.findByAnimalId(alerta.getAnimalId()), parametros));
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(view, "No se pudo abrir el detalle del animal.",
                    "Alertas", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}
