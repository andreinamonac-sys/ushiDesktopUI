package com.andreina.ushi.desktop.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.andreina.ushi.desktop.model.AnimalTableModel;

public class AnimalSearchView extends AbstractView {

    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    private JPanel searchTab;
    private JPanel searchPanel;

    private JTextField idTF;
    private JTextField numRegistroTF;
    private JTextField fechaDesdeTF;
    private JTextField fechaHastaTF;
    private JComboBox<String> sexoCombo;
    private JComboBox<String> granjaCombo;

    private JButton btnBuscar;

    private JTable resultadosTable;
    private AnimalTableModel resultadosModel;

    public AnimalSearchView() {
    	super("Animales")
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        mainPanel = this;

        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        searchTab = new JPanel(new BorderLayout(0, 0));
        tabbedPane.addTab("Buscar animal", searchTab);

        searchPanel = new JPanel();
        searchTab.add(searchPanel, BorderLayout.NORTH);

        GridBagLayout gbl_searchPanel = new GridBagLayout();
        gbl_searchPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gbl_searchPanel.rowHeights = new int[] { 0, 0, 0 };
        gbl_searchPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0 };
        gbl_searchPanel.rowWeights = new double[] { 0.0, 0.0, 0.0 };
        searchPanel.setLayout(gbl_searchPanel);

        // Labels row
        addLabel(searchPanel, 0, 0, "ID");
        addLabel(searchPanel, 2, 0, "N? de registro");
        addLabel(searchPanel, 4, 0, "Fecha desde");
        addLabel(searchPanel, 6, 0, "Fecha hasta");
        addLabel(searchPanel, 8, 0, "Sexo");
        addLabel(searchPanel, 10, 0, "Granja");

        // Fields row
        idTF = new JTextField();
        addField(searchPanel, 1, 1, idTF);

        numRegistroTF = new JTextField();
        addField(searchPanel, 3, 1, numRegistroTF);

        fechaDesdeTF = new JTextField();
        addField(searchPanel, 5, 1, fechaDesdeTF);

        fechaHastaTF = new JTextField();
        addField(searchPanel, 7, 1, fechaHastaTF);

        sexoCombo = new JComboBox<>(new String[] { "Macho", "Hembra" });
        addField(searchPanel, 9, 1, sexoCombo);

        granjaCombo = new JComboBox<>();
        addField(searchPanel, 11, 1, granjaCombo);

        // Button row
        btnBuscar = new JButton("Buscar");
        GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
        gbc_btnBuscar.insets = new Insets(6, 0, 0, 5);
        gbc_btnBuscar.gridx = 10;
        gbc_btnBuscar.gridy = 2;
        gbc_btnBuscar.anchor = GridBagConstraints.EAST;
        searchPanel.add(btnBuscar, gbc_btnBuscar);

        resultadosModel = new AnimalTableModel();
        resultadosTable = new JTable(resultadosModel);
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

    public AnimalCriteria getCriteria() {
    	AnimalCriteria criteria = new AnimalCriteria();
    	//TODO StringUtils.trimToNull() en los campos de texto
    	
    	criteria.setId(Long.valueOf(idTF.getText()));
    	
    	String numRegistro = numRegistroTF.getText();
    	
		
		return criteria;
    }
}
