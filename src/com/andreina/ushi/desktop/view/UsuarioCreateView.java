package com.andreina.ushi.desktop.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.andreina.ushi.model.Rol;

public class UsuarioCreateView extends AbstractView {

    private JPanel contentPanel;

    private JLabel rolLabel;
    private JComboBox<Rol> rolCombo;

    private JLabel dniNieLabel;
    private JTextField dniNieTF;

    private JLabel nombreLabel;
    private JTextField nombreTF;

    private JLabel apellido1Label;
    private JTextField apellido1TF;

    private JLabel apellido2Label;
    private JTextField apellido2TF;

    private JLabel telefonoLabel;
    private JTextField telefonoTF;

    private JLabel emailLabel;
    private JTextField emailTF;

    private JButton btnNuevoUsuario;
    private JButton btnCancelar;

    public UsuarioCreateView() {
        super("Alta usuario");
        initComponents();
        postInitialize();
    }

    /**
     * @wbp.parser.entryPoint
     */
    private void initComponents() {
        contentPanel = this;
        setLayout(new BorderLayout());
        setBackground(UshiColors.C_GRIS_NIEBLA_CLARO);
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        rolLabel = new JLabel("Seleccionar rol de usuario");
        rolCombo = new JComboBox<>();

        dniNieLabel = new JLabel("DNI/NIE");
        dniNieTF = new JTextField(20);

        nombreLabel = new JLabel("Nombre");
        nombreTF = new JTextField(20);

        apellido1Label = new JLabel("Apellido 1");
        apellido1TF = new JTextField(20);

        apellido2Label = new JLabel("Apellido 2");
        apellido2TF = new JTextField(20);

        telefonoLabel = new JLabel("Telefono");
        telefonoTF = new JTextField(20);

        emailLabel = new JLabel("Email");
        emailTF = new JTextField(20);

        btnNuevoUsuario = new JButton("Nuevo usuario");
        btnCancelar = new JButton("Cancelar");

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UshiColors.C_BLANCO_SUAVE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(UshiColors.C_GRIS_VERDOSO_OSCURO), "Alta usuario"));
        formPanel.setPreferredSize(new Dimension(460, 470));
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(formPanel);
        add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        formPanel.add(rolLabel, gbc);

        gbc.gridy = 1;
        rolCombo.setPreferredSize(new Dimension(230, 28));
        formPanel.add(rolCombo, gbc);

        addField(formPanel, nombreLabel, nombreTF, 0, 2);
        addField(formPanel, apellido1Label, apellido1TF, 1, 2);
        addField(formPanel, apellido2Label, apellido2TF, 2, 2);
        addField(formPanel, dniNieLabel, dniNieTF, 0, 4);
        addField(formPanel, telefonoLabel, telefonoTF, 1, 4);
        addWideField(formPanel, emailLabel, emailTF, 0, 6);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(btnNuevoUsuario);
        buttonsPanel.add(btnCancelar);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(26, 8, 8, 8);
        formPanel.add(buttonsPanel, gbc);
    }

    private void postInitialize() {
        // TODO: add listeners and validation here
    }

    public JFrame getFrame() {
        return (JFrame) SwingUtilities.getWindowAncestor(this);
    }

    public JComboBox<Rol> getRolCombo() {
        return rolCombo;
    }

    public JTextField getDniNieTF() {
        return dniNieTF;
    }

    public JTextField getNombreTF() {
        return nombreTF;
    }

    public JTextField getApellido1TF() {
        return apellido1TF;
    }

    public JTextField getApellido2TF() {
        return apellido2TF;
    }

    public JTextField getTelefonoTF() {
        return telefonoTF;
    }

    public JTextField getEmailTF() {
        return emailTF;
    }

    public JButton getBtnNuevoUsuario() {
        return btnNuevoUsuario;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    private void addField(JPanel panel, JLabel label, JTextField field, int column, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 2, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = column;
        gbc.gridy = row;
        panel.add(label, gbc);

        gbc.gridy = row + 1;
        field.setColumns(10);
        panel.add(field, gbc);
    }

    private void addWideField(JPanel panel, JLabel label, JTextField field, int column, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 2, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = column;
        gbc.gridy = row;
        gbc.gridwidth = 3;
        panel.add(label, gbc);

        gbc.gridy = row + 1;
        field.setColumns(26);
        panel.add(field, gbc);
    }
}
