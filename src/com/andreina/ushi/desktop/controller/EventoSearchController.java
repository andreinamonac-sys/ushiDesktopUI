package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.andreina.ushi.dao.criteria.EventoCriteria;
import com.andreina.ushi.desktop.MainWindow;
import com.andreina.ushi.desktop.RolePermissions;
import com.andreina.ushi.desktop.renderer.RowActionsEditor;
import com.andreina.ushi.desktop.view.EventoFormView;
import com.andreina.ushi.desktop.view.EventoSearchView;
import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.model.Results;
import com.andreina.ushi.service.EventoService;
import com.andreina.ushi.service.impl.EventoServiceImpl;

public class EventoSearchController extends AbstractController implements RowActionsEditor.RowActionHandler {

    private final EventoSearchView view;
    private final EventoService eventoService;
    private final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public EventoSearchController(EventoSearchView view) {
        this.view = view;
        this.eventoService = new EventoServiceImpl();
        this.view.getBtnBuscar().addActionListener(this);
        this.view.getBtnNuevo().addActionListener(this);
        this.view.getBtnLimpiar().addActionListener(this);
        this.view.setActionsHandler(this);
        this.view.setActionsVisible(canWriteEvents());
        this.view.setNewButtonVisible(canWriteEvents());
        buscar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnLimpiar()) {
            view.clearFilters();
        } else if (e.getSource() == view.getBtnNuevo()) {
            crearEvento();
            return;
        }
        buscar();
    }

    private void crearEvento() {
        if (!canWriteEvents()) {
            view.showError("Tu rol solo permite consultar eventos.");
            return;
        }
        EventoFormView form = new EventoFormView();
        int option = JOptionPane.showConfirmDialog(view, form, "Nuevo evento", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (option != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            eventoService.create(form.toEventoDTO());
            buscar();
        } catch (Exception e) {
            view.showError("No se pudo crear el evento: " + e.getMessage());
        }
    }

    private void buscar() {
        try {
            EventoCriteria criteria = view.getCriteria();
            if (!applyFarmScope(criteria)) {
                view.setModel(new java.util.ArrayList<EventoDTO>());
                return;
            }
            Results<EventoDTO> results = eventoService.findByCriteria(criteria, 1, 100);
            List<EventoDTO> eventos = results == null ? null : results.getPageResults();
            view.setModel(eventos);
        } catch (Exception e) {
            view.showError("No se pudieron cargar los eventos: " + e.getMessage());
        }
    }

    @Override
    public void editRow(int modelRow) {
        if (!canWriteEvents()) {
            view.showError("Tu rol solo permite consultar eventos.");
            return;
        }
        EventoDTO evento = view.getEventoAt(modelRow);
        if (evento == null) {
            return;
        }
        try {
            EventoDTO editable = eventoService.findById(evento.getId());
            if (editable == null) {
                editable = evento;
            }
            JTextField fecha = new JTextField(editable.getFechaDesde() == null ? "" : dateTimeFormat.format(editable.getFechaDesde()));
            JTextField tipo = new JTextField(text(editable.getNombreTipoEvento()));
            JTextField animal = new JTextField(text(editable.getAnimalNumRegistro()));
            JTextField veterinario = new JTextField(displayVeterinario(editable));
            JTextField descripcion = new JTextField(text(editable.getDescripcion()));
            JTextField diagnostico = new JTextField(text(editable.getValorDiagnostico()));
            JPanel panel = new JPanel(new java.awt.GridLayout(0, 2, 6, 6));
            panel.add(new JLabel("Fecha"));
            panel.add(fecha);
            panel.add(new JLabel("Tipo"));
            panel.add(tipo);
            panel.add(new JLabel("Animal"));
            panel.add(animal);
            panel.add(new JLabel("Veterinario"));
            panel.add(veterinario);
            panel.add(new JLabel("Descripcion"));
            panel.add(descripcion);
            panel.add(new JLabel("Diagnostico"));
            panel.add(diagnostico);
            int option = JOptionPane.showConfirmDialog(view, panel, "Modificar evento", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            editable.setFechaDesde(fecha.getText().trim().isEmpty() ? null : dateTimeFormat.parse(fecha.getText().trim()));
            editable.setTipoEventoId(resolveLongOrKeep(tipo.getText(), editable.getTipoEventoId()));
            editable.setAnimalId(resolveLongOrKeep(animal.getText(), editable.getAnimalId()));
            editable.setVeterinarioId(resolveLongOrKeep(veterinario.getText(), editable.getVeterinarioId()));
            editable.setDescripcion(trimToNull(descripcion.getText()));
            editable.setValorDiagnostico(trimToNull(diagnostico.getText()));
            eventoService.update(editable);
            buscar();
        } catch (Exception e) {
            view.showError("No se pudo modificar el evento: " + e.getMessage());
        }
    }

    @Override
    public void deleteRow(int modelRow) {
        if (!canWriteEvents()) {
            view.showError("Tu rol solo permite consultar eventos.");
            return;
        }
        EventoDTO evento = view.getEventoAt(modelRow);
        if (evento == null || evento.getId() == null) {
            return;
        }
        int option = JOptionPane.showConfirmDialog(view, "Quieres borrar el evento " + evento.getId() + "?",
                "Borrar evento", JOptionPane.YES_NO_OPTION);
        if (option != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            eventoService.anularEvento(evento.getId());
            buscar();
        } catch (Exception e) {
            view.showError("No se pudo borrar el evento: " + e.getMessage());
        }
    }

    private Long toLong(String value) {
        String text = trimToNull(value);
        return text == null ? null : Long.valueOf(text);
    }

    private Long resolveLongOrKeep(String value, Long currentValue) {
        String text = trimToNull(value);
        if (text == null) {
            return null;
        }
        try {
            return Long.valueOf(text);
        } catch (NumberFormatException e) {
            return currentValue;
        }
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String text(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    private String displayVeterinario(EventoDTO evento) {
        String nombre = text(evento.getNombreVeterinario()).trim();
        String apellido = text(evento.getApellidoVeterinario()).trim();
        String fullName = (nombre + " " + apellido).trim();
        return fullName.isEmpty() ? text(evento.getVeterinarioId()) : fullName;
    }

    private boolean canWriteEvents() {
        return RolePermissions.canWriteEvents(MainWindow.getInstance().getCurrentRoleName());
    }

    private boolean applyFarmScope(EventoCriteria criteria) {
        String role = MainWindow.getInstance().getCurrentRoleName();
        if (!RolePermissions.isFarmScoped(role)) {
            return true;
        }
        Long selectedGranjaId = MainWindow.getInstance().getSelectedGranjaId();
        if (selectedGranjaId == null) {
            view.showError("Tu usuario no tiene una granja asignada.");
            return false;
        }
        criteria.setGranjaId(selectedGranjaId);
        return true;
    }
}
