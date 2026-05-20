package com.andreina.ushi.desktop.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.andreina.ushi.dao.criteria.GranjaCriteria;
import com.andreina.ushi.desktop.model.GranjaTableModel;
import com.andreina.ushi.desktop.renderer.RowActionsEditor;
import com.andreina.ushi.desktop.renderer.RowActionsRenderer;
import com.andreina.ushi.desktop.renderer.UshiTableCellRenderer;
import com.andreina.ushi.model.GranjaDTO;

public class GranjaSearchView extends AbstractView {

    private JTextField idTextField;
    private JTextField nifTextField;
    private JTextField calleTextField;
    private JTextField numeroTextField;
    private JTextField cpTextField;
    private JComboBox<String> localidadComboBox;
    private JComboBox<String> provinciaComboBox;
    private JTextField encargadoTextField;
    private JButton btnBuscar;
    private JButton btnNuevo;
    private JButton btnMostrarTodas;
    private JTable granjasTable;
    private GranjaTableModel tableModel;

    public GranjaSearchView() {
        super("Granjas");
        initComponents();
    }

    /**
     * @wbp.parser.entryPoint
     */
    private void initComponents() {
        setLayout(new BorderLayout(0, 12));
        setBackground(UshiColors.C_GRIS_NIEBLA_CLARO);
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBackground(UshiColors.C_BLANCO_SUAVE);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UshiColors.C_GRIS_VERDOSO_BORDE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        add(filterPanel, BorderLayout.NORTH);

        idTextField = new JTextField(10);
        nifTextField = new JTextField(10);
        calleTextField = new JTextField(10);
        numeroTextField = new JTextField(10);
        cpTextField = new JTextField(10);
        localidadComboBox = new JComboBox<String>();
        provinciaComboBox = new JComboBox<String>();
        encargadoTextField = new JTextField(10);

        addPair(filterPanel, "ID", idTextField, 0, 0);
        addPair(filterPanel, "NIF", nifTextField, 1, 0);
        addPair(filterPanel, "Calle", calleTextField, 2, 0);
        addPair(filterPanel, "Numero", numeroTextField, 3, 0);
        addPair(filterPanel, "CP", cpTextField, 0, 1);
        addPair(filterPanel, "Localidad", localidadComboBox, 1, 1);
        addPair(filterPanel, "Provincia", provinciaComboBox, 2, 1);
        addPair(filterPanel, "Encargado", encargadoTextField, 3, 1);

        btnBuscar = new JButton("Buscar granja");
        filterPanel.add(btnBuscar, constraints(6, 2));
        btnNuevo = new JButton("Nueva granja");
        filterPanel.add(btnNuevo, constraints(7, 2));
        btnMostrarTodas = new JButton("Todas");
        filterPanel.add(btnMostrarTodas, constraints(8, 2));

        tableModel = new GranjaTableModel();
        granjasTable = new JTable(tableModel);
        granjasTable.setDefaultRenderer(Object.class, new UshiTableCellRenderer());
        granjasTable.setRowHeight(28);
        granjasTable.getTableHeader().setBackground(UshiColors.C_VERDE_MENTA_LECHE);
        granjasTable.getColumnModel().getColumn(GranjaTableModel.ACTIONS_COLUMN).setCellRenderer(new RowActionsRenderer());
        granjasTable.getColumnModel().getColumn(GranjaTableModel.ACTIONS_COLUMN).setPreferredWidth(105);
        add(new JScrollPane(granjasTable), BorderLayout.CENTER);
    }

    public String getNif() {
        String text = nifTextField.getText();
        return text == null || text.trim().isEmpty() ? null : text.trim();
    }

    public GranjaCriteria getCriteria() {
        GranjaCriteria criteria = new GranjaCriteria();
        criteria.setId(toLong(idTextField.getText()));
        criteria.setNif(trimToNull(nifTextField.getText()));
        criteria.setCalle(trimToNull(calleTextField.getText()));
        criteria.setNumero(toInteger(numeroTextField.getText()));
        criteria.setCp(toInteger(cpTextField.getText()));
        criteria.setLocalidad(trimToNull((String) localidadComboBox.getSelectedItem()));
        criteria.setProvincia(trimToNull((String) provinciaComboBox.getSelectedItem()));
        criteria.setEncargado(trimToNull(encargadoTextField.getText()));
        return criteria;
    }

    public void clearFilters() {
        idTextField.setText("");
        nifTextField.setText("");
        calleTextField.setText("");
        numeroTextField.setText("");
        cpTextField.setText("");
        localidadComboBox.setSelectedIndex(localidadComboBox.getItemCount() > 0 ? 0 : -1);
        provinciaComboBox.setSelectedIndex(provinciaComboBox.getItemCount() > 0 ? 0 : -1);
        encargadoTextField.setText("");
    }

    public JComboBox<String> getLocalidadComboBox() {
        return localidadComboBox;
    }

    public JComboBox<String> getProvinciaComboBox() {
        return provinciaComboBox;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnNuevo() {
        return btnNuevo;
    }

    public void setNewButtonVisible(boolean visible) {
        btnNuevo.setVisible(visible);
    }

    public JButton getBtnMostrarTodas() {
        return btnMostrarTodas;
    }

    public void setModel(List<GranjaDTO> granjas) {
        tableModel.setGranjas(granjas);
    }

    public GranjaDTO getGranjaAt(int modelRow) {
        return tableModel.getGranjaAt(modelRow);
    }

    public void setActionsHandler(RowActionsEditor.RowActionHandler handler) {
        granjasTable.getColumnModel().getColumn(GranjaTableModel.ACTIONS_COLUMN)
                .setCellEditor(new RowActionsEditor(handler));
    }

    public void setActionsVisible(boolean visible) {
        javax.swing.table.TableColumn column = granjasTable.getColumnModel()
                .getColumn(GranjaTableModel.ACTIONS_COLUMN);
        int width = visible ? 105 : 0;
        column.setMinWidth(width);
        column.setPreferredWidth(width);
        column.setMaxWidth(width);
        column.setResizable(visible);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Granjas", JOptionPane.ERROR_MESSAGE);
    }

    private void addPair(JPanel panel, String label, java.awt.Component field, int pairIndex, int row) {
        panel.add(new JLabel(label), constraints(pairIndex * 2, row));
        GridBagConstraints gbc = constraints(pairIndex * 2 + 1, row);
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private GridBagConstraints constraints(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private Long toLong(String value) {
        String text = trimToNull(value);
        return text == null ? null : Long.valueOf(text);
    }

    private Integer toInteger(String value) {
        String text = trimToNull(value);
        return text == null ? null : Integer.valueOf(text);
    }

}
