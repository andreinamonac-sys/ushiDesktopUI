package com.andreina.ushi.desktop.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import com.andreina.ushi.model.Rol;

public class UsuarioCreateView extends View {

    private JFrame frame;
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
        initComponents();
        postInitialize();
    }

    private void initComponents() {
        frame = new JFrame();
        frame.setTitle("Alta usuario");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPanel = this;

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
        btnCancelar.addActionListener(e -> frame.dispose());

        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(rolLabel)
                    .addComponent(rolCombo, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(dniNieLabel)
                            .addComponent(nombreLabel)
                            .addComponent(apellido1Label)
                            .addComponent(apellido2Label)
                            .addComponent(telefonoLabel)
                            .addComponent(emailLabel))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(dniNieTF)
                            .addComponent(nombreTF)
                            .addComponent(apellido1TF)
                            .addComponent(apellido2TF)
                            .addComponent(telefonoTF)
                            .addComponent(emailTF))))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnNuevoUsuario)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnCancelar))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(rolLabel)
                .addComponent(rolCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(dniNieLabel)
                    .addComponent(dniNieTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreLabel)
                    .addComponent(nombreTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(apellido1Label)
                    .addComponent(apellido1TF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(apellido2Label)
                    .addComponent(apellido2TF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(telefonoLabel)
                    .addComponent(telefonoTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel)
                    .addComponent(emailTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoUsuario)
                    .addComponent(btnCancelar))
        );

        frame.setContentPane(contentPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void postInitialize() {
        // TODO: add listeners and validation here
    }

    public JFrame getFrame() {
        return frame;
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
}
