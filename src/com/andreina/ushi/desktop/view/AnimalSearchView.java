package com.andreina.ushi.desktop.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

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
import com.toedter.calendar.JDateChooser;

import java.util.List;

public class AnimalSearchView extends AbstractView {

    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    private JPanel searchTab;
    private JPanel searchPanel;

    private JTextField animalTF;
    private JDateChooser fechaNacimientoDesdeChooser;
    private JDateChooser fechaNacimientoHastaChooser;
    private JDateChooser fechaAltaDesdeChooser;
    private JDateChooser fechaAltaHastaChooser;
    private JDateChooser fechaBajaDesdeChooser;
    private JDateChooser fechaBajaHastaChooser;
    private JComboBox<String> sexoCombo;
    private JTextField granjaTF;
    private JTextField padreExternoTF;
    private JTextField madreExternaTF;
    private JTextField madreInternaTF;

    private JButton btnBuscar;
    private JButton btnNuevo;

    private JTable resultadosTable;
    private AnimalTableModel resultadosModel;

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
        gbl_searchPanel.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_searchPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0 };
        gbl_searchPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
        searchPanel.setLayout(gbl_searchPanel);

        animalTF = new JTextField();
        fechaNacimientoDesdeChooser = createDateChooser();
        fechaNacimientoHastaChooser = createDateChooser();
        fechaAltaDesdeChooser = createDateChooser();
        fechaAltaHastaChooser = createDateChooser();
        fechaBajaDesdeChooser = createDateChooser();
        fechaBajaHastaChooser = createDateChooser();
        sexoCombo = new JComboBox<>(new String[] { "", "Macho", "Hembra" });
        granjaTF = new JTextField();
        padreExternoTF = new JTextField();
        madreExternaTF = new JTextField();
        madreInternaTF = new JTextField();
        animalTF.putClientProperty("JTextField.placeholderText", "ID o num. registro");
        animalTF.setToolTipText("Acepta ID de animal o numero de registro");
        granjaTF.putClientProperty("JTextField.placeholderText", "ID o NIF");
        granjaTF.setToolTipText("Acepta ID de granja o NIF");
        madreInternaTF.putClientProperty("JTextField.placeholderText", "ID o num. registro");
        madreInternaTF.setToolTipText("Acepta ID de madre interna o numero de registro");

        addLabel(searchPanel, 0, 0, "Animal");
        addField(searchPanel, 1, 0, animalTF);
        addLabel(searchPanel, 2, 0, "Sexo");
        addField(searchPanel, 3, 0, sexoCombo);
        addLabel(searchPanel, 4, 0, "Granja");
        addField(searchPanel, 5, 0, granjaTF);
        addLabel(searchPanel, 6, 0, "Padre externo");
        addField(searchPanel, 7, 0, padreExternoTF);

        addLabel(searchPanel, 0, 1, "Madre externa");
        addField(searchPanel, 1, 1, madreExternaTF);
        addLabel(searchPanel, 2, 1, "Madre interna");
        addField(searchPanel, 3, 1, madreInternaTF);
        addLabel(searchPanel, 4, 1, "Nacimiento desde");
        addField(searchPanel, 5, 1, fechaNacimientoDesdeChooser);
        addLabel(searchPanel, 6, 1, "Nacimiento hasta");
        addField(searchPanel, 7, 1, fechaNacimientoHastaChooser);

        addLabel(searchPanel, 0, 2, "Alta desde");
        addField(searchPanel, 1, 2, fechaAltaDesdeChooser);
        addLabel(searchPanel, 2, 2, "Alta hasta");
        addField(searchPanel, 3, 2, fechaAltaHastaChooser);
        addLabel(searchPanel, 4, 2, "Baja desde");
        addField(searchPanel, 5, 2, fechaBajaDesdeChooser);
        addLabel(searchPanel, 6, 2, "Baja hasta");
        addField(searchPanel, 7, 2, fechaBajaHastaChooser);

        // Button row
        btnBuscar = new JButton("Buscar animal");
        GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
        gbc_btnBuscar.insets = new Insets(6, 0, 0, 5);
        gbc_btnBuscar.gridx = 6;
        gbc_btnBuscar.gridy = 3;
        gbc_btnBuscar.anchor = GridBagConstraints.EAST;
        searchPanel.add(btnBuscar, gbc_btnBuscar);
        btnNuevo = new JButton("Nuevo animal");
        GridBagConstraints gbcBtnNuevo = new GridBagConstraints();
        gbcBtnNuevo.insets = new Insets(6, 0, 0, 5);
        gbcBtnNuevo.gridx = 7;
        gbcBtnNuevo.gridy = 3;
        gbcBtnNuevo.anchor = GridBagConstraints.EAST;
        searchPanel.add(btnNuevo, gbcBtnNuevo);

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
        criteria.setFechaNacimientoDesde(fechaNacimientoDesdeChooser.getDate());
        criteria.setFechaNacimientoHasta(fechaNacimientoHastaChooser.getDate());
        criteria.setFechaAltaDesde(fechaAltaDesdeChooser.getDate());
        criteria.setFechaAltaHasta(endOfDay(fechaAltaHastaChooser.getDate()));
        criteria.setFechaBajaDesde(fechaBajaDesdeChooser.getDate());
        criteria.setFechaBajaHasta(endOfDay(fechaBajaHastaChooser.getDate()));
        setGranjaCriteria(criteria, granjaTF.getText());
        criteria.setPadreExternoId(toLong(padreExternoTF.getText()));
        criteria.setMadreExternaId(toLong(madreExternaTF.getText()));
        setMadreInternaCriteria(criteria, madreInternaTF.getText());

		return criteria;
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

    private JDateChooser createDateChooser() {
        JDateChooser chooser = new JDateChooser();
        chooser.setDateFormatString("dd/MM/yyyy");
        return chooser;
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

    private void setGranjaCriteria(AnimalCriteria criteria, String value) {
        String text = trimToNull(value);
        if (text == null) {
            return;
        }
        if (isLong(text)) {
            criteria.setGranjaId(Long.valueOf(text));
        } else {
            criteria.setGranjaNif(text);
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

    private Date endOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
