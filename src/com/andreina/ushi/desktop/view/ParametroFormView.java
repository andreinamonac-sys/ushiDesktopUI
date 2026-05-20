package com.andreina.ushi.desktop.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.andreina.ushi.model.ParametroDTO;

public class ParametroFormView extends AbstractView {

    private final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private JTextField fechaTextField;
    private JTextField valorTextField;
    private JTextField tagTextField;
    private JTextField animalTextField;
    private JTextField tipoParametroTextField;

    public ParametroFormView() {
        super("Alta parametro");
        initComponents();
    }

    /**
     * @wbp.parser.entryPoint
     */
    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(UshiColors.C_BLANCO_SUAVE);

        fechaTextField = field("dd/MM/yyyy HH:mm");
        valorTextField = field("Valor numerico. Ej. 38.4");
        tagTextField = field("ID del tag");
        animalTextField = field("ID del animal");
        tipoParametroTextField = field("ID del tipo de parametro");

        addRow(0, "Fecha", fechaTextField);
        addRow(1, "Valor", valorTextField);
        addRow(2, "Tag", tagTextField);
        addRow(3, "Animal", animalTextField);
        addRow(4, "Tipo parametro", tipoParametroTextField);
    }

    public ParametroDTO toParametroDTO() throws ParseException {
        ParametroDTO parametro = new ParametroDTO();
        parametro.setFechaHora(toDateTime(fechaTextField.getText()));
        parametro.setValorParametro(toDouble(valorTextField.getText()));
        parametro.setTagId(toLong(tagTextField.getText()));
        parametro.setAnimalId(toLong(animalTextField.getText()));
        parametro.setTipoParametroId(toLong(tipoParametroTextField.getText()));
        return parametro;
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

    private java.util.Date toDateTime(String value) throws ParseException {
        String text = trimToNull(value);
        return text == null ? null : dateTimeFormat.parse(text);
    }

    private Long toLong(String value) {
        String text = trimToNull(value);
        return text == null ? null : Long.valueOf(text);
    }

    private Double toDouble(String value) {
        String text = trimToNull(value);
        return text == null ? null : Double.valueOf(text.replace(',', '.'));
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
