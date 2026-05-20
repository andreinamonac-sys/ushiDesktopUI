package com.andreina.ushi.desktop.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.desktop.model.ParametroTableModel;
import com.andreina.ushi.desktop.model.TagTableModel;
import com.andreina.ushi.desktop.renderer.RowActionsEditor;
import com.andreina.ushi.desktop.renderer.RowActionsRenderer;
import com.andreina.ushi.desktop.renderer.UshiTableCellRenderer;
import com.andreina.ushi.model.ParametroDTO;
import com.andreina.ushi.model.Tag;

public class MonitorizacionView extends AbstractView {

    private JTextField animalTextField;
    private JTextField parametroTagTextField;
    private JTextField tagNumeroTextField;
    private JButton btnBuscarParametros;
    private JButton btnNuevoParametro;
    private JButton btnBuscarTagNumero;
    private JButton btnNuevoTag;
    private JButton btnTagsDisponibles;
    private JButton btnTagsIncidencias;
    private ParametroTableModel parametroTableModel;
    private TagTableModel tagTableModel;
    private JTable tagsTable;

    public MonitorizacionView() {
        super("Monitorizacion");
        initComponents();
    }

    /**
     * @wbp.parser.entryPoint
     */
    private void initComponents() {
        setLayout(new BorderLayout(0, 12));
        setBackground(UshiColors.C_GRIS_NIEBLA_CLARO);
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        JPanel parametrosPanel = new JPanel(new BorderLayout(0, 8));
        parametrosPanel.setBackground(UshiColors.C_GRIS_NIEBLA_CLARO);
        JPanel parametrosFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        parametrosFilterPanel.setBackground(UshiColors.C_BLANCO_SUAVE);
        parametrosFilterPanel.setBorder(BorderFactory.createLineBorder(UshiColors.C_GRIS_VERDOSO_BORDE));
        parametrosPanel.add(parametrosFilterPanel, BorderLayout.NORTH);

        parametrosFilterPanel.add(new JLabel("Animal"));
        animalTextField = new JTextField(10);
        animalTextField.putClientProperty("JTextField.placeholderText", "ID o num. registro");
        animalTextField.setToolTipText("Acepta ID de animal o numero de registro");
        parametrosFilterPanel.add(animalTextField);
        parametrosFilterPanel.add(new JLabel("Tag"));
        parametroTagTextField = new JTextField(10);
        parametrosFilterPanel.add(parametroTagTextField);

        btnBuscarParametros = new JButton("Buscar parametros");
        parametrosFilterPanel.add(btnBuscarParametros);
        btnNuevoParametro = new JButton("Nuevo parametro");
        parametrosFilterPanel.add(btnNuevoParametro);

        parametroTableModel = new ParametroTableModel();
        JTable parametrosTable = new JTable(parametroTableModel);
        parametrosTable.setDefaultRenderer(Object.class, new UshiTableCellRenderer());
        parametrosTable.setRowHeight(28);
        parametrosTable.getTableHeader().setBackground(UshiColors.C_VERDE_MENTA_LECHE);
        parametrosPanel.add(new JScrollPane(parametrosTable), BorderLayout.CENTER);
        tabbedPane.addTab("Parametros", parametrosPanel);

        JPanel tagsPanel = new JPanel(new BorderLayout(0, 8));
        tagsPanel.setBackground(UshiColors.C_GRIS_NIEBLA_CLARO);
        JPanel tagsFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        tagsFilterPanel.setBackground(UshiColors.C_BLANCO_SUAVE);
        tagsFilterPanel.setBorder(BorderFactory.createLineBorder(UshiColors.C_GRIS_VERDOSO_BORDE));
        tagsPanel.add(tagsFilterPanel, BorderLayout.NORTH);

        tagsFilterPanel.add(new JLabel("Numero"));
        tagNumeroTextField = new JTextField(12);
        tagsFilterPanel.add(tagNumeroTextField);
        btnBuscarTagNumero = new JButton("Buscar tag");
        tagsFilterPanel.add(btnBuscarTagNumero);
        btnNuevoTag = new JButton("Nuevo tag");
        tagsFilterPanel.add(btnNuevoTag);
        btnTagsDisponibles = new JButton("Disponibles");
        tagsFilterPanel.add(btnTagsDisponibles);
        btnTagsIncidencias = new JButton("Incidencias");
        tagsFilterPanel.add(btnTagsIncidencias);

        tagTableModel = new TagTableModel();
        tagsTable = new JTable(tagTableModel);
        tagsTable.setDefaultRenderer(Object.class, new UshiTableCellRenderer());
        tagsTable.setRowHeight(28);
        tagsTable.getTableHeader().setBackground(UshiColors.C_VERDE_MENTA_LECHE);
        tagsTable.getColumnModel().getColumn(TagTableModel.ACTIONS_COLUMN).setCellRenderer(new RowActionsRenderer());
        tagsTable.getColumnModel().getColumn(TagTableModel.ACTIONS_COLUMN).setPreferredWidth(105);
        tagsPanel.add(new JScrollPane(tagsTable), BorderLayout.CENTER);
        tabbedPane.addTab("Tags", tagsPanel);
    }

    public ParametroCriteria getCriteria() {
        ParametroCriteria criteria = new ParametroCriteria();
        setAnimalCriteria(criteria, animalTextField.getText());
        criteria.setTagId(toLong(parametroTagTextField.getText()));
        return criteria;
    }

    public JButton getBtnBuscarParametros() {
        return btnBuscarParametros;
    }

    public JButton getBtnNuevoParametro() {
        return btnNuevoParametro;
    }

    public JButton getBtnBuscarTagNumero() {
        return btnBuscarTagNumero;
    }

    public JButton getBtnNuevoTag() {
        return btnNuevoTag;
    }

    public void setNewButtonsVisible(boolean visible) {
        btnNuevoParametro.setVisible(visible);
        btnNuevoTag.setVisible(visible);
    }

    public JButton getBtnTagsDisponibles() {
        return btnTagsDisponibles;
    }

    public JButton getBtnTagsIncidencias() {
        return btnTagsIncidencias;
    }

    public String getTagNumero() {
        if (tagNumeroTextField == null || tagNumeroTextField.getText() == null) {
            return null;
        }
        String trimmed = tagNumeroTextField.getText().trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public void setParametros(List<ParametroDTO> parametros) {
        parametroTableModel.setParametros(parametros);
    }

    public void setTags(List<Tag> tags) {
        tagTableModel.setTags(tags);
    }

    public Tag getTagAt(int modelRow) {
        return tagTableModel.getTagAt(modelRow);
    }

    public void setTagActionsHandler(RowActionsEditor.RowActionHandler handler) {
        tagsTable.getColumnModel().getColumn(TagTableModel.ACTIONS_COLUMN)
                .setCellEditor(new RowActionsEditor(handler));
    }

    public void setTagActionsVisible(boolean visible) {
        javax.swing.table.TableColumn column = tagsTable.getColumnModel()
                .getColumn(TagTableModel.ACTIONS_COLUMN);
        int width = visible ? 105 : 0;
        column.setMinWidth(width);
        column.setPreferredWidth(width);
        column.setMaxWidth(width);
        column.setResizable(visible);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Monitorizacion", JOptionPane.ERROR_MESSAGE);
    }

    private Long toLong(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Long.valueOf(value.trim());
    }

    private void setAnimalCriteria(ParametroCriteria criteria, String value) {
        if (value == null || value.trim().isEmpty()) {
            return;
        }
        String trimmed = value.trim();
        if (isLong(trimmed)) {
            criteria.setAnimalId(Long.valueOf(trimmed));
        } else {
            criteria.setAnimalNumRegistro(trimmed);
        }
    }

    private boolean isLong(String value) {
        try {
            Long.valueOf(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
