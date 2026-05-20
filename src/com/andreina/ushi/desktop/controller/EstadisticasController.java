package com.andreina.ushi.desktop.controller;

import com.andreina.ushi.dao.criteria.EventoCriteria;
import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.desktop.MainWindow;
import com.andreina.ushi.desktop.RolePermissions;
import com.andreina.ushi.desktop.view.EstadisticasView;
import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.model.ParametroDTO;
import com.andreina.ushi.model.Results;
import com.andreina.ushi.service.EventoService;
import com.andreina.ushi.service.ParametroService;
import com.andreina.ushi.service.impl.EventoServiceImpl;
import com.andreina.ushi.service.impl.ParametroServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class EstadisticasController {

    private static final int MAX_ROWS = 10000;

    private final EstadisticasView view;
    private final EventoService eventoService;
    private final ParametroService parametroService;

    public EstadisticasController(EstadisticasView view) {
        this.view = view;
        this.eventoService = new EventoServiceImpl();
        this.parametroService = new ParametroServiceImpl();
        load();
    }

    private void load() {
        try {
            EventoCriteria eventoCriteria = new EventoCriteria();
            ParametroCriteria parametroCriteria = new ParametroCriteria();
            Long selectedFarmId = MainWindow.getInstance().getSelectedGranjaId();
            if (RolePermissions.isFarmScoped(MainWindow.getInstance().getCurrentRoleName())) {
                if (selectedFarmId == null) {
                    view.setStatistics(new ArrayList<EventoDTO>(), new ArrayList<ParametroDTO>());
                    view.showError("Tu usuario no tiene una granja asignada.");
                    return;
                }
                eventoCriteria.setGranjaId(selectedFarmId);
                parametroCriteria.setGranjaId(selectedFarmId);
            }
            Results<EventoDTO> eventos = eventoService.findByCriteria(eventoCriteria, 1, MAX_ROWS);
            List<ParametroDTO> parametros = parametroService.findByCriteria(parametroCriteria, 1, MAX_ROWS);
            view.setStatistics(eventos == null ? new ArrayList<EventoDTO>() : eventos.getPageResults(), parametros);
        } catch (Exception e) {
            view.showError("No se pudieron cargar las estadisticas: " + e.getMessage());
        }
    }
}
