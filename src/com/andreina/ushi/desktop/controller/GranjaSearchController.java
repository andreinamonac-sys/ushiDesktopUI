package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.andreina.ushi.dao.criteria.GranjaCriteria;
import com.andreina.ushi.desktop.MainWindow;
import com.andreina.ushi.desktop.RolePermissions;
import com.andreina.ushi.desktop.renderer.RowActionsEditor;
import com.andreina.ushi.desktop.view.GranjaFormView;
import com.andreina.ushi.desktop.view.GranjaSearchView;
import com.andreina.ushi.model.GranjaDTO;
import com.andreina.ushi.model.LocalidadDTO;
import com.andreina.ushi.model.Provincia;
import com.andreina.ushi.service.GranjaService;
import com.andreina.ushi.service.LocalidadService;
import com.andreina.ushi.service.ProvinciaService;
import com.andreina.ushi.service.impl.GranjaServiceImpl;
import com.andreina.ushi.service.impl.LocalidadServiceImpl;
import com.andreina.ushi.service.impl.ProvinciaServiceImpl;

public class GranjaSearchController extends AbstractController implements RowActionsEditor.RowActionHandler {

    private final GranjaSearchView view;
    private final GranjaService granjaService;
    private final LocalidadService localidadService;
    private final ProvinciaService provinciaService;

    public GranjaSearchController(GranjaSearchView view) {
        this.view = view;
        this.granjaService = new GranjaServiceImpl();
        this.localidadService = new LocalidadServiceImpl();
        this.provinciaService = new ProvinciaServiceImpl();
        this.view.getBtnBuscar().addActionListener(this);
        this.view.getBtnNuevo().addActionListener(this);
        this.view.getBtnMostrarTodas().addActionListener(this);
        this.view.setActionsHandler(this);
        this.view.setActionsVisible(canWriteFarms());
        this.view.setNewButtonVisible(canWriteFarms());
        cargarCombos();
        mostrarTodas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnBuscar()) {
            buscar();
        } else if (e.getSource() == view.getBtnNuevo()) {
            crearGranja();
        } else {
            view.clearFilters();
            mostrarTodas();
        }
    }

    private void crearGranja() {
        if (!canWriteFarms()) {
            view.showError("Tu rol solo permite consultar granjas.");
            return;
        }
        GranjaFormView form = new GranjaFormView();
        int option = JOptionPane.showConfirmDialog(view, form, "Nueva granja", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (option != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            granjaService.create(form.toGranjaDTO());
            mostrarTodas();
        } catch (Exception e) {
            view.showError("No se pudo crear la granja: " + e.getMessage());
        }
    }

    private void buscar() {
        try {
            GranjaCriteria criteria = view.getCriteria();
            if (!applyFarmScope(criteria)) {
                view.setModel(new ArrayList<GranjaDTO>());
                return;
            }
            view.setModel(granjaService.findByCriteria(criteria));
        } catch (Exception e) {
            view.showError("No se pudo buscar la granja: " + e.getMessage());
        }
    }

    private void mostrarTodas() {
        try {
            if (RolePermissions.isFarmScoped(MainWindow.getInstance().getCurrentRoleName())) {
                List<GranjaDTO> granjas = new ArrayList<GranjaDTO>();
                Long selectedGranjaId = MainWindow.getInstance().getSelectedGranjaId();
                if (selectedGranjaId != null) {
                    GranjaDTO granja = granjaService.findById(selectedGranjaId);
                    if (granja != null) {
                        granjas.add(granja);
                    }
                }
                view.setModel(granjas);
                return;
            }
            view.setModel(granjaService.findAll());
        } catch (Exception e) {
            view.showError("No se pudieron cargar las granjas: " + e.getMessage());
        }
    }

    private void cargarCombos() {
        cargarLocalidades();
        cargarProvincias();
    }

    private void cargarLocalidades() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        model.addElement("");
        try {
            List<LocalidadDTO> localidades = localidadService.findAll();
            Set<String> nombres = new LinkedHashSet<String>();
            if (localidades != null) {
                for (LocalidadDTO localidad : localidades) {
                    if (localidad.getNombre() != null && !localidad.getNombre().trim().isEmpty()) {
                        nombres.add(localidad.getNombre().trim());
                    }
                }
                for (String nombre : nombres) {
                    model.addElement(nombre);
                }
            }
        } catch (Exception e) {
            view.showError("No se pudieron cargar las localidades: " + e.getMessage());
        }
        view.getLocalidadComboBox().setModel(model);
    }

    private void cargarProvincias() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        model.addElement("");
        try {
            List<Provincia> provincias = provinciaService.findAll();
            if (provincias != null) {
                for (Provincia provincia : provincias) {
                    if (provincia.getNombre() != null && !provincia.getNombre().trim().isEmpty()) {
                        model.addElement(provincia.getNombre().trim());
                    }
                }
            }
        } catch (Exception e) {
            view.showError("No se pudieron cargar las provincias: " + e.getMessage());
        }
        view.getProvinciaComboBox().setModel(model);
    }

    @Override
    public void editRow(int modelRow) {
        if (!canWriteFarms()) {
            view.showError("Tu rol solo permite consultar granjas.");
            return;
        }
        GranjaDTO granja = view.getGranjaAt(modelRow);
        if (granja == null) {
            return;
        }
        try {
            GranjaDTO editable = granjaService.findById(granja.getId());
            if (editable == null) {
                editable = granja;
            }
            JTextField nif = new JTextField(text(editable.getNif()));
            JTextField calle = new JTextField(text(editable.getCalle()));
            JTextField numero = new JTextField(text(editable.getNumero()));
            JTextField cp = new JTextField(text(editable.getCp()));
            JTextField encargado = new JTextField(text(editable.getUsuarioNombre()));
            JPanel panel = new JPanel(new java.awt.GridLayout(0, 2, 6, 6));
            panel.add(new JLabel("NIF"));
            panel.add(nif);
            panel.add(new JLabel("Calle"));
            panel.add(calle);
            panel.add(new JLabel("Numero"));
            panel.add(numero);
            panel.add(new JLabel("CP"));
            panel.add(cp);
            panel.add(new JLabel("Encargado"));
            panel.add(encargado);
            int option = JOptionPane.showConfirmDialog(view, panel, "Modificar granja", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            editable.setNif(trimToNull(nif.getText()));
            editable.setCalle(trimToNull(calle.getText()));
            editable.setNumero(toInteger(numero.getText()));
            editable.setCp(toLong(cp.getText()));
            editable.setUsuarioId(resolveLongOrKeep(encargado.getText(), editable.getUsuarioId()));
            granjaService.update(editable);
            mostrarTodas();
        } catch (Exception e) {
            view.showError("No se pudo modificar la granja: " + e.getMessage());
        }
    }

    @Override
    public void deleteRow(int modelRow) {
        if (!canWriteFarms()) {
            view.showError("Tu rol solo permite consultar granjas.");
            return;
        }
        GranjaDTO granja = view.getGranjaAt(modelRow);
        if (granja == null || granja.getId() == null) {
            return;
        }
        int option = JOptionPane.showConfirmDialog(view, "Quieres borrar la granja " + granja.getNif() + "?",
                "Borrar granja", JOptionPane.YES_NO_OPTION);
        if (option != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            granjaService.delete(granja.getId());
            mostrarTodas();
        } catch (Exception e) {
            view.showError("No se pudo borrar la granja: " + e.getMessage());
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

    private Integer toInteger(String value) {
        String text = trimToNull(value);
        return text == null ? null : Integer.valueOf(text);
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

    private boolean canSeeFarm(GranjaDTO granja) {
        if (!RolePermissions.isFarmScoped(MainWindow.getInstance().getCurrentRoleName())) {
            return true;
        }
        Long selectedGranjaId = MainWindow.getInstance().getSelectedGranjaId();
        return selectedGranjaId != null && selectedGranjaId.equals(granja.getId());
    }

    private boolean canWriteFarms() {
        return RolePermissions.canWriteFarms(MainWindow.getInstance().getCurrentRoleName());
    }

    private boolean applyFarmScope(GranjaCriteria criteria) {
        if (!RolePermissions.isFarmScoped(MainWindow.getInstance().getCurrentRoleName())) {
            return true;
        }
        Long selectedGranjaId = MainWindow.getInstance().getSelectedGranjaId();
        if (selectedGranjaId == null) {
            view.showError("Tu usuario no tiene una granja asignada.");
            return false;
        }
        criteria.setId(selectedGranjaId);
        return true;
    }
}
