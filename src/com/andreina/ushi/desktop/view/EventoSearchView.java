package com.andreina.ushi.desktop.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.andreina.ushi.dao.criteria.EventoCriteria;
import com.andreina.ushi.desktop.model.EventoTableModel;
import com.andreina.ushi.desktop.renderer.RowActionsEditor;
import com.andreina.ushi.desktop.renderer.RowActionsRenderer;
import com.andreina.ushi.desktop.renderer.UshiTableCellRenderer;
import com.andreina.ushi.model.EventoDTO;

public class EventoSearchView extends AbstractView {

    private JTextField idTextField;
    private JTextField animalTextField;
    private JTextField tipoTextField;
    private JTextField descripcionTextField;
    private JTextField fechaDesdeTextField;
    private JTextField fechaHastaTextField;
    private JTextField veterinarioTextField;
    private JButton btnBuscar;
    private JButton btnNuevo;
    private JButton btnLimpiar;
    private JTable eventosTable;
    private EventoTableModel tableModel;
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public EventoSearchView() {
        super("Eventos");
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
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        add(filterPanel, BorderLayout.NORTH);

        idTextField = new JTextField();
        animalTextField = new JTextField();
        tipoTextField = new JTextField();
        descripcionTextField = new JTextField();
        fechaDesdeTextField = new JTextField();
        fechaHastaTextField = new JTextField();
        veterinarioTextField = new JTextField();
        animalTextField.putClientProperty("JTextField.placeholderText", "ID o num. registro");
        animalTextField.setToolTipText("Acepta ID de animal o numero de registro");
        tipoTextField.putClientProperty("JTextField.placeholderText", "Texto del tipo");
        tipoTextField.setToolTipText("Busca por nombre del tipo de evento");
        veterinarioTextField.putClientProperty("JTextField.placeholderText", "Nombre o apellido");
        veterinarioTextField.setToolTipText("Busca por nombre o apellido del veterinario");
        btnBuscar = new JButton("Buscar evento");
        btnNuevo = new JButton("Nuevo evento");
        btnLimpiar = new JButton("Limpiar");

        addField(filterPanel, "ID", idTextField, 0);
        addField(filterPanel, "Animal", animalTextField, 1);
        addField(filterPanel, "Tipo", tipoTextField, 2);
        addField(filterPanel, "Descripcion", descripcionTextField, 3);
        addField(filterPanel, "Fecha desde", fechaDesdeTextField, 4);
        addField(filterPanel, "Fecha hasta", fechaHastaTextField, 5);
        addField(filterPanel, "Veterinario", veterinarioTextField, 6);

        GridBagConstraints gbcButtons = constraints(14, 1);
        filterPanel.add(btnBuscar, gbcButtons);
        GridBagConstraints gbcNew = constraints(15, 1);
        filterPanel.add(btnNuevo, gbcNew);
        GridBagConstraints gbcClear = constraints(16, 1);
        filterPanel.add(btnLimpiar, gbcClear);

        tableModel = new EventoTableModel();
        eventosTable = new JTable(tableModel);
        eventosTable.setDefaultRenderer(Object.class, new UshiTableCellRenderer());
        eventosTable.setRowHeight(28);
        eventosTable.getTableHeader().setBackground(UshiColors.C_VERDE_MENTA_LECHE);
        eventosTable.getTableHeader().setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        eventosTable.getColumnModel().getColumn(EventoTableModel.ACTIONS_COLUMN).setCellRenderer(new RowActionsRenderer());
        eventosTable.getColumnModel().getColumn(EventoTableModel.ACTIONS_COLUMN).setPreferredWidth(105);
        add(new JScrollPane(eventosTable), BorderLayout.CENTER);
    }

    public EventoCriteria getCriteria() throws ParseException {
        EventoCriteria criteria = new EventoCriteria();
        criteria.setId(toLong(idTextField.getText()));
        setAnimalCriteria(criteria, animalTextField.getText());
        criteria.setTipoEventoNombre(trimToNull(tipoTextField.getText()));
        criteria.setDescripcion(trimToNull(descripcionTextField.getText()));
        criteria.setFechaDesde(parseDate(fechaDesdeTextField.getText()));
        criteria.setFechaHasta(parseDate(fechaHastaTextField.getText()));
        criteria.setVeterinarioNombre(trimToNull(veterinarioTextField.getText()));
        return criteria;
    }

    public void clearFilters() {
        idTextField.setText("");
        animalTextField.setText("");
        tipoTextField.setText("");
        descripcionTextField.setText("");
        fechaDesdeTextField.setText("");
        fechaHastaTextField.setText("");
        veterinarioTextField.setText("");
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

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setModel(List<EventoDTO> eventos) {
        tableModel.setEventos(eventos);
    }

    public EventoDTO getEventoAt(int modelRow) {
        return tableModel.getEventoAt(modelRow);
    }

    public void setActionsHandler(RowActionsEditor.RowActionHandler handler) {
        eventosTable.getColumnModel().getColumn(EventoTableModel.ACTIONS_COLUMN)
                .setCellEditor(new RowActionsEditor(handler));
    }

    public void setActionsVisible(boolean visible) {
        javax.swing.table.TableColumn column = eventosTable.getColumnModel()
                .getColumn(EventoTableModel.ACTIONS_COLUMN);
        int width = visible ? 105 : 0;
        column.setMinWidth(width);
        column.setPreferredWidth(width);
        column.setMaxWidth(width);
        column.setResizable(visible);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Eventos", JOptionPane.ERROR_MESSAGE);
    }

    private void addField(JPanel panel, String label, JTextField field, int pairIndex) {
        panel.add(new JLabel(label), constraints(pairIndex * 2, 0));
        GridBagConstraints gbc = constraints(pairIndex * 2 + 1, 0);
        gbc.weightx = 1.0;
        field.setColumns(10);
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

    private Long toLong(String value) {
        String trimmed = trimToNull(value);
        return trimmed == null ? null : Long.valueOf(trimmed);
    }

    private void setAnimalCriteria(EventoCriteria criteria, String value) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            return;
        }
        if (isLong(trimmed)) {
            criteria.setAnimalId(Long.valueOf(trimmed));
        } else {
            criteria.setNumRegistro(trimmed);
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

    private java.util.Date parseDate(String value) throws ParseException {
        String trimmed = trimToNull(value);
        return trimmed == null ? null : dateFormat.parse(trimmed);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
