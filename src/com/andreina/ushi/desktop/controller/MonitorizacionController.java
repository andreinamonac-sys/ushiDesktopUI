package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.andreina.ushi.desktop.MainWindow;
import com.andreina.ushi.desktop.RolePermissions;
import com.andreina.ushi.desktop.renderer.RowActionsEditor;
import com.andreina.ushi.desktop.view.MonitorizacionView;
import com.andreina.ushi.desktop.view.ParametroFormView;
import com.andreina.ushi.desktop.view.TagFormView;
import com.andreina.ushi.model.Tag;
import com.andreina.ushi.service.ParametroService;
import com.andreina.ushi.service.TagService;
import com.andreina.ushi.service.impl.ParametroServiceImpl;
import com.andreina.ushi.service.impl.TagServiceImpl;

public class MonitorizacionController extends AbstractController implements RowActionsEditor.RowActionHandler {

    private final MonitorizacionView view;
    private final ParametroService parametroService;
    private final TagService tagService;
    private final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public MonitorizacionController(MonitorizacionView view) {
        this.view = view;
        this.parametroService = new ParametroServiceImpl();
        this.tagService = new TagServiceImpl();
        this.view.getBtnBuscarParametros().addActionListener(this);
        this.view.getBtnNuevoParametro().addActionListener(this);
        this.view.getBtnBuscarTagNumero().addActionListener(this);
        this.view.getBtnNuevoTag().addActionListener(this);
        this.view.getBtnTagsDisponibles().addActionListener(this);
        this.view.getBtnTagsIncidencias().addActionListener(this);
        this.view.setTagActionsHandler(this);
        this.view.setTagActionsVisible(canWriteTags());
        this.view.setNewButtonsVisible(canWriteTags());
        cargarParametros();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnTagsDisponibles()) {
            cargarTagsDisponibles();
        } else if (e.getSource() == view.getBtnTagsIncidencias()) {
            cargarTagsConIncidencias();
        } else if (e.getSource() == view.getBtnNuevoParametro()) {
            crearParametro();
        } else if (e.getSource() == view.getBtnNuevoTag()) {
            crearTag();
        } else if (e.getSource() == view.getBtnBuscarTagNumero()) {
            buscarTagPorNumero();
        } else {
            cargarParametros();
        }
    }

    private void crearParametro() {
        if (!canWriteTags()) {
            view.showError("Tu rol solo permite consultar parametros.");
            return;
        }
        ParametroFormView form = new ParametroFormView();
        int option = JOptionPane.showConfirmDialog(view, form, "Nuevo parametro", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (option != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            parametroService.create(form.toParametroDTO());
            cargarParametros();
        } catch (Exception e) {
            view.showError("No se pudo crear el parametro: " + e.getMessage());
        }
    }

    private void crearTag() {
        if (!canWriteTags()) {
            view.showError("Tu rol solo permite consultar tags.");
            return;
        }
        TagFormView form = new TagFormView();
        int option = JOptionPane.showConfirmDialog(view, form, "Nuevo tag", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (option != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            tagService.create(form.toTag());
            cargarTagsDisponibles();
        } catch (Exception e) {
            view.showError("No se pudo crear el tag: " + e.getMessage());
        }
    }

    private void cargarParametros() {
        try {
            view.setParametros(parametroService.findByCriteria(view.getCriteria(), 0, 100));
        } catch (Exception e) {
            view.showError("No se pudieron cargar los parametros: " + e.getMessage());
        }
    }

    private void cargarTagsDisponibles() {
        try {
            view.setTags(tagService.findDisponible());
        } catch (Exception e) {
            view.showError("No se pudieron cargar los tags disponibles: " + e.getMessage());
        }
    }

    private void cargarTagsConIncidencias() {
        try {
            view.setTags(tagService.findConIncidencias());
        } catch (Exception e) {
            view.showError("No se pudieron cargar los tags con incidencias: " + e.getMessage());
        }
    }

    private void buscarTagPorNumero() {
        try {
            java.util.List<Tag> tags = new java.util.ArrayList<Tag>();
            Tag tag = tagService.findByNumero(view.getTagNumero());
            if (tag != null) {
                tags.add(tag);
            }
            view.setTags(tags);
        } catch (Exception e) {
            view.showError("No se pudo buscar el tag: " + e.getMessage());
        }
    }

    @Override
    public void editRow(int modelRow) {
        if (!canWriteTags()) {
            view.showError("Tu rol solo permite consultar tags.");
            return;
        }
        Tag tag = view.getTagAt(modelRow);
        if (tag == null) {
            return;
        }
        try {
            Tag editable = tagService.findById(tag.getId());
            if (editable == null) {
                editable = tag;
            }
            JTextField numero = new JTextField(text(editable.getNumero()));
            JTextField tipo = new JTextField(text(editable.getTipo()));
            JTextField version = new JTextField(text(editable.getVersionSoftware()));
            JTextField ultima = new JTextField(editable.getUltimaActualizacion() == null ? "" : dateTimeFormat.format(editable.getUltimaActualizacion()));
            JTextField incidencias = new JTextField(text(editable.getIncidencias()));
            JPanel panel = new JPanel(new java.awt.GridLayout(0, 2, 6, 6));
            panel.add(new JLabel("Numero"));
            panel.add(numero);
            panel.add(new JLabel("Tipo"));
            panel.add(tipo);
            panel.add(new JLabel("Version"));
            panel.add(version);
            panel.add(new JLabel("Ultima actualizacion"));
            panel.add(ultima);
            panel.add(new JLabel("Incidencias"));
            panel.add(incidencias);
            int option = JOptionPane.showConfirmDialog(view, panel, "Modificar tag", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            editable.setNumero(trimToNull(numero.getText()));
            editable.setTipo(trimToNull(tipo.getText()));
            editable.setVersionSoftware(trimToNull(version.getText()));
            editable.setUltimaActualizacion(trimToNull(ultima.getText()) == null ? null : dateTimeFormat.parse(ultima.getText().trim()));
            editable.setIncidencias(trimToNull(incidencias.getText()));
            tagService.update(editable);
            cargarTagsDisponibles();
        } catch (Exception e) {
            view.showError("No se pudo modificar el tag: " + e.getMessage());
        }
    }

    @Override
    public void deleteRow(int modelRow) {
        if (!canWriteTags()) {
            view.showError("Tu rol solo permite consultar tags.");
            return;
        }
        Tag tag = view.getTagAt(modelRow);
        if (tag == null || tag.getId() == null) {
            return;
        }
        int option = JOptionPane.showConfirmDialog(view, "Quieres borrar el tag " + tag.getNumero() + "?",
                "Borrar tag", JOptionPane.YES_NO_OPTION);
        if (option != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            tagService.delete(tag.getId());
            cargarTagsDisponibles();
        } catch (Exception e) {
            view.showError("No se pudo borrar el tag: " + e.getMessage());
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

    private boolean canWriteTags() {
        return RolePermissions.canWriteTags(MainWindow.getInstance().getCurrentRoleName());
    }
}
