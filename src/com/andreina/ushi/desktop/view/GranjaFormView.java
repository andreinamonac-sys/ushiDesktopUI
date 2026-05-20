package com.andreina.ushi.desktop.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.andreina.ushi.model.GranjaDTO;

public class GranjaFormView extends AbstractView {

    private JTextField nifTextField;
    private JTextField calleTextField;
    private JTextField numeroTextField;
    private JTextField cpTextField;
    private JTextField encargadoTextField;

    public GranjaFormView() {
        super("Alta granja");
        initComponents();
    }

    /**
     * @wbp.parser.entryPoint
     */
    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(UshiColors.C_BLANCO_SUAVE);

        nifTextField = field("Ej. B12345678");
        calleTextField = field("Nombre de la calle");
        numeroTextField = field("Numero de portal");
        cpTextField = field("Codigo postal");
        encargadoTextField = field("ID del encargado opcional");

        addRow(0, "NIF", nifTextField);
        addRow(1, "Calle", calleTextField);
        addRow(2, "Numero", numeroTextField);
        addRow(3, "CP", cpTextField);
        addRow(4, "Encargado", encargadoTextField);
    }

    public GranjaDTO toGranjaDTO() {
        GranjaDTO granja = new GranjaDTO();
        granja.setNif(trimToNull(nifTextField.getText()));
        granja.setCalle(trimToNull(calleTextField.getText()));
        granja.setNumero(toInteger(numeroTextField.getText()));
        granja.setCp(toLong(cpTextField.getText()));
        granja.setUsuarioId(toLong(encargadoTextField.getText()));
        return granja;
    }

    private JTextField field(String placeholder) {
        JTextField field = new JTextField(20);
        field.putClientProperty("JTextField.placeholderText", placeholder);
        return field;
    }

    private void addRow(int row, String label, java.awt.Component field) {
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = row;
        gbcLabel.insets = new Insets(5, 5, 5, 8);
        gbcLabel.anchor = GridBagConstraints.WEST;
        add(new JLabel(label), gbcLabel);

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.gridx = 1;
        gbcField.gridy = row;
        gbcField.insets = new Insets(5, 5, 5, 5);
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.weightx = 1.0;
        add(field, gbcField);
    }

    private Long toLong(String value) {
        String text = trimToNull(value);
        return text == null ? null : Long.valueOf(text);
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
}
