package com.andreina.ushi.desktop.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.andreina.ushi.model.EventoDTO;

public class EventoFormView extends AbstractView {

    private final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private JTextField fechaTextField;
    private JTextField tipoEventoTextField;
    private JTextField animalTextField;
    private JTextField veterinarioTextField;
    private JTextField descripcionTextField;
    private JTextField diagnosticoTextField;
    private JTextField criaTextField;
    private JTextField semillaTextField;
    private JTextField dosisTextField;

    public EventoFormView() {
        super("Alta evento");
        initComponents();
    }

    /**
     * @wbp.parser.entryPoint
     */
    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(UshiColors.C_BLANCO_SUAVE);

        fechaTextField = field("dd/MM/yyyy HH:mm");
        tipoEventoTextField = field("ID del tipo de evento");
        animalTextField = field("ID del animal");
        veterinarioTextField = field("ID del veterinario opcional");
        descripcionTextField = field("Descripcion del evento");
        diagnosticoTextField = field("P, N u otro valor opcional");
        criaTextField = field("ID de cria opcional");
        semillaTextField = field("ID de semilla opcional");
        dosisTextField = field("ID de dosis opcional");

        addRow(0, "Fecha", fechaTextField);
        addRow(1, "Tipo evento", tipoEventoTextField);
        addRow(2, "Animal", animalTextField);
        addRow(3, "Veterinario", veterinarioTextField);
        addRow(4, "Descripcion", descripcionTextField);
        addRow(5, "Diagnostico", diagnosticoTextField);
        addRow(6, "Cria", criaTextField);
        addRow(7, "Semilla", semillaTextField);
        addRow(8, "Dosis", dosisTextField);
    }

    public EventoDTO toEventoDTO() throws ParseException {
        EventoDTO evento = new EventoDTO();
        evento.setFechaDesde(toDateTime(fechaTextField.getText()));
        evento.setTipoEventoId(toLong(tipoEventoTextField.getText()));
        evento.setAnimalId(toLong(animalTextField.getText()));
        evento.setVeterinarioId(toLong(veterinarioTextField.getText()));
        evento.setDescripcion(trimToNull(descripcionTextField.getText()));
        evento.setValorDiagnostico(trimToNull(diagnosticoTextField.getText()));
        evento.setCriaId(toLong(criaTextField.getText()));
        evento.setSemillaId(toLong(semillaTextField.getText()));
        evento.setDosisId(toLong(dosisTextField.getText()));
        return evento;
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

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
