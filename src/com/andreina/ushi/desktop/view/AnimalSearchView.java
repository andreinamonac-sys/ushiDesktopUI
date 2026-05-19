package com.andreina.ushi.desktop.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.desktop.model.AnimalTableModel;
import com.andreina.ushi.desktop.renderer.AnimalActionsEditor;
import com.andreina.ushi.desktop.renderer.AnimalActionsRenderer;
import com.andreina.ushi.desktop.renderer.UshiTableCellRenderer;
import com.andreina.ushi.model.AnimalDTO;

import java.util.List;

public class AnimalSearchView extends AbstractView {

    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    private JPanel searchTab;
    private JPanel searchPanel;

    private JTextField animalTF;
    private JTextField fechaNacimientoDesdeTF;
    private JTextField fechaNacimientoHastaTF;
    private JTextField fechaAltaDesdeTF;
    private JTextField fechaAltaHastaTF;
    private JTextField fechaBajaDesdeTF;
    private JTextField fechaBajaHastaTF;
    private JComboBox<String> sexoCombo;
    private JTextField granjaIdTF;
    private JTextField granjaNifTF;
    private JTextField padreExternoTF;
    private JTextField madreExternaTF;
    private JTextField madreInternaTF;

    private JButton btnBuscar;

    private JTable resultadosTable;
    private AnimalTableModel resultadosModel;
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public AnimalSearchView() {
    	super("Animales");
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UshiColors.C_GRIS_NIEBLA_CLARO);
        mainPanel = this;

        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        searchTab = new JPanel(new BorderLayout(0, 0));
        tabbedPane.addTab("Buscar animal", searchTab);

        searchPanel = new JPanel();
        searchPanel.setBackground(UshiColors.C_BLANCO_SUAVE);
        searchTab.add(searchPanel, BorderLayout.NORTH);

        GridBagLayout gbl_searchPanel = new GridBagLayout();
        gbl_searchPanel.columnWidths = new int[] { 0, 130, 0, 130, 0, 130, 0, 130 };
        gbl_searchPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_searchPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0 };
        gbl_searchPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
        searchPanel.setLayout(gbl_searchPanel);

        animalTF = new JTextField();
        fechaNacimientoDesdeTF = new JTextField();
        fechaNacimientoHastaTF = new JTextField();
        fechaAltaDesdeTF = new JTextField();
        fechaAltaHastaTF = new JTextField();
        fechaBajaDesdeTF = new JTextField();
        fechaBajaHastaTF = new JTextField();
        sexoCombo = new JComboBox<>(new String[] { "", "Macho", "Hembra" });
        granjaIdTF = new JTextField();
        granjaNifTF = new JTextField();
        padreExternoTF = new JTextField();
        madreExternaTF = new JTextField();
        madreInternaTF = new JTextField();
        animalTF.putClientProperty("JTextField.placeholderText", "ID o num. registro");
        animalTF.setToolTipText("Acepta ID de animal o numero de registro");
        madreInternaTF.putClientProperty("JTextField.placeholderText", "ID o num. registro");
        madreInternaTF.setToolTipText("Acepta ID de madre interna o numero de registro");

        addLabel(searchPanel, 0, 0, "Animal");
        addField(searchPanel, 1, 0, animalTF);
        addLabel(searchPanel, 4, 0, "Sexo");
        addField(searchPanel, 5, 0, sexoCombo);
        addLabel(searchPanel, 6, 0, "Nacimiento desde");
        addField(searchPanel, 7, 0, fechaNacimientoDesdeTF);

        addLabel(searchPanel, 0, 1, "Nacimiento hasta");
        addField(searchPanel, 1, 1, fechaNacimientoHastaTF);
        addLabel(searchPanel, 2, 1, "Alta desde");
        addField(searchPanel, 3, 1, fechaAltaDesdeTF);
        addLabel(searchPanel, 4, 1, "Alta hasta");
        addField(searchPanel, 5, 1, fechaAltaHastaTF);
        addLabel(searchPanel, 6, 1, "Baja desde");
        addField(searchPanel, 7, 1, fechaBajaDesdeTF);

        addLabel(searchPanel, 0, 2, "Baja hasta");
        addField(searchPanel, 1, 2, fechaBajaHastaTF);
        addLabel(searchPanel, 2, 2, "Granja ID");
        addField(searchPanel, 3, 2, granjaIdTF);
        addLabel(searchPanel, 4, 2, "Granja NIF");
        addField(searchPanel, 5, 2, granjaNifTF);
        addLabel(searchPanel, 6, 2, "Padre externo");
        addField(searchPanel, 7, 2, padreExternoTF);

        addLabel(searchPanel, 0, 3, "Madre externa");
        addField(searchPanel, 1, 3, madreExternaTF);
        addLabel(searchPanel, 2, 3, "Madre interna");
        addField(searchPanel, 3, 3, madreInternaTF);

        // Button row
        btnBuscar = new JButton("Buscar");
        GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
        gbc_btnBuscar.insets = new Insets(6, 0, 0, 5);
        gbc_btnBuscar.gridx = 7;
        gbc_btnBuscar.gridy = 4;
        gbc_btnBuscar.anchor = GridBagConstraints.EAST;
        searchPanel.add(btnBuscar, gbc_btnBuscar);

        resultadosModel = new AnimalTableModel();
        resultadosTable = new JTable(resultadosModel);
        resultadosTable.setDefaultRenderer(Object.class, new UshiTableCellRenderer());
        resultadosTable.setRowHeight(28);
        resultadosTable.getTableHeader().setBackground(UshiColors.C_VERDE_MENTA_LECHE);
        resultadosTable.getTableHeader().setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        resultadosTable.getColumnModel().getColumn(AnimalTableModel.ACTIONS_COLUMN)
                .setCellRenderer(new AnimalActionsRenderer());
        resultadosTable.getColumnModel().getColumn(AnimalTableModel.ACTIONS_COLUMN).setPreferredWidth(105);
        JScrollPane scrollPane = new JScrollPane(resultadosTable);
        searchTab.add(scrollPane, BorderLayout.CENTER);
    }

    private void addLabel(JPanel panel, int x, int y, String text) {
        JLabel label = new JLabel(text);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(label, gbc);
    }

    private void addField(JPanel panel, int x, int y, java.awt.Component field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(field, gbc);
    }

    public AnimalCriteria getCriteria() throws ParseException {
    	AnimalCriteria criteria = new AnimalCriteria();

        setAnimalCriteria(criteria, animalTF.getText());
        criteria.setSexoDescripcion(trimToNull((String) sexoCombo.getSelectedItem()));
        criteria.setFechaNacimientoDesde(parseDate(fechaNacimientoDesdeTF.getText()));
        criteria.setFechaNacimientoHasta(parseDate(fechaNacimientoHastaTF.getText()));
        criteria.setFechaAltaDesde(parseDateTime(fechaAltaDesdeTF.getText()));
        criteria.setFechaAltaHasta(parseDateTime(fechaAltaHastaTF.getText()));
        criteria.setFechaBajaDesde(parseDateTime(fechaBajaDesdeTF.getText()));
        criteria.setFechaBajaHasta(parseDateTime(fechaBajaHastaTF.getText()));
        criteria.setGranjaId(toLong(granjaIdTF.getText()));
        criteria.setGranjaNif(trimToNull(granjaNifTF.getText()));
        criteria.setPadreExternoId(toLong(padreExternoTF.getText()));
        criteria.setMadreExternaId(toLong(madreExternaTF.getText()));
        setMadreInternaCriteria(criteria, madreInternaTF.getText());

		return criteria;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setModel(List<AnimalDTO> animales) {
        resultadosModel.setAnimales(animales);
    }

    public JTable getResultadosTable() {
        return resultadosTable;
    }

    public AnimalDTO getAnimalAt(int modelRow) {
        return resultadosModel.getAnimalAt(modelRow);
    }

    public void setAnimalActionsHandler(AnimalActionsEditor.AnimalActionHandler handler) {
        resultadosTable.getColumnModel().getColumn(AnimalTableModel.ACTIONS_COLUMN)
                .setCellEditor(new AnimalActionsEditor(handler));
    }

    public void setAnimalActionsVisible(boolean visible) {
        javax.swing.table.TableColumn column = resultadosTable.getColumnModel()
                .getColumn(AnimalTableModel.ACTIONS_COLUMN);
        int width = visible ? 105 : 0;
        column.setMinWidth(width);
        column.setPreferredWidth(width);
        column.setMaxWidth(width);
        column.setResizable(visible);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Animales", JOptionPane.ERROR_MESSAGE);
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

    private void setAnimalCriteria(AnimalCriteria criteria, String value) {
        String text = trimToNull(value);
        if (text == null) {
            return;
        }
        if (isLong(text)) {
            criteria.setId(Long.valueOf(text));
        } else {
            criteria.setNumRegistro(text);
        }
    }

    private void setMadreInternaCriteria(AnimalCriteria criteria, String value) {
        String text = trimToNull(value);
        if (text == null) {
            return;
        }
        if (isLong(text)) {
            criteria.setMadreInternaId(Long.valueOf(text));
        } else {
            criteria.setNumRegistroMadreInterna(text);
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
        String text = trimToNull(value);
        return text == null ? null : dateFormat.parse(text);
    }

    private java.util.Date parseDateTime(String value) throws ParseException {
        String text = trimToNull(value);
        return text == null ? null : dateTimeFormat.parse(text);
    }
}
