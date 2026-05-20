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

import com.andreina.ushi.dao.criteria.UsuarioCriteria;
import com.andreina.ushi.desktop.model.UsuarioTableModel;
import com.andreina.ushi.desktop.renderer.RowActionsEditor;
import com.andreina.ushi.desktop.renderer.RowActionsRenderer;
import com.andreina.ushi.desktop.renderer.UshiTableCellRenderer;
import com.andreina.ushi.model.UsuarioDTO;

public class UsuarioSearchView extends AbstractView {

    private JTextField nombreTextField;
    private JTextField apellido1TextField;
    private JTextField apellido2TextField;
    private JTextField emailTextField;
    private JTextField dniTextField;
    private JComboBox<String> rolComboBox;
    private JButton btnBuscar;
    private JButton btnNuevo;
    private JTable usuariosTable;
    private UsuarioTableModel tableModel;

    public UsuarioSearchView() {
        super("Usuarios");
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
        filterPanel.setBorder(BorderFactory.createLineBorder(UshiColors.C_GRIS_VERDOSO_BORDE));
        add(filterPanel, BorderLayout.NORTH);

        nombreTextField = new JTextField(12);
        apellido1TextField = new JTextField(12);
        apellido2TextField = new JTextField(12);
        emailTextField = new JTextField(12);
        dniTextField = new JTextField(12);
        rolComboBox = new JComboBox<String>(new String[] { "", "Administrador", "Veterinario", "Encargado", "Operario" });
        btnBuscar = new JButton("Buscar usuario");
        btnNuevo = new JButton("Nuevo usuario");

        addPair(filterPanel, "Nombre", nombreTextField, 0, 0);
        addPair(filterPanel, "Primer apellido", apellido1TextField, 1, 0);
        addPair(filterPanel, "Segundo apellido", apellido2TextField, 2, 0);
        addPair(filterPanel, "Email", emailTextField, 0, 1);
        addPair(filterPanel, "DNI/NIE", dniTextField, 1, 1);
        addPair(filterPanel, "Rol", rolComboBox, 2, 1);
        filterPanel.add(btnBuscar, constraints(4, 2));
        filterPanel.add(btnNuevo, constraints(5, 2));

        tableModel = new UsuarioTableModel();
        usuariosTable = new JTable(tableModel);
        usuariosTable.setDefaultRenderer(Object.class, new UshiTableCellRenderer());
        usuariosTable.setRowHeight(28);
        usuariosTable.getTableHeader().setBackground(UshiColors.C_VERDE_MENTA_LECHE);
        usuariosTable.getColumnModel().getColumn(UsuarioTableModel.ACTIONS_COLUMN).setCellRenderer(new RowActionsRenderer());
        usuariosTable.getColumnModel().getColumn(UsuarioTableModel.ACTIONS_COLUMN).setPreferredWidth(105);
        add(new JScrollPane(usuariosTable), BorderLayout.CENTER);
    }

    public UsuarioCriteria getCriteria() {
        UsuarioCriteria criteria = new UsuarioCriteria();
        criteria.setNombre(trimToNull(nombreTextField.getText()));
        criteria.setApellido1(trimToNull(apellido1TextField.getText()));
        criteria.setApellido2(trimToNull(apellido2TextField.getText()));
        criteria.setEmail(trimToNull(emailTextField.getText()));
        criteria.setDniNie(trimToNull(dniTextField.getText()));
        criteria.setRolNombre(trimToNull((String) rolComboBox.getSelectedItem()));
        return criteria;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnNuevo() {
        return btnNuevo;
    }

    public void setModel(List<UsuarioDTO> usuarios) {
        tableModel.setUsuarios(usuarios);
    }

    public UsuarioDTO getUsuarioAt(int modelRow) {
        return tableModel.getUsuarioAt(modelRow);
    }

    public void setActionsHandler(RowActionsEditor.RowActionHandler handler) {
        usuariosTable.getColumnModel().getColumn(UsuarioTableModel.ACTIONS_COLUMN)
                .setCellEditor(new RowActionsEditor(handler));
    }

    public void setActionsVisible(boolean visible) {
        javax.swing.table.TableColumn column = usuariosTable.getColumnModel()
                .getColumn(UsuarioTableModel.ACTIONS_COLUMN);
        int width = visible ? 105 : 0;
        column.setMinWidth(width);
        column.setPreferredWidth(width);
        column.setMaxWidth(width);
        column.setResizable(visible);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Usuarios", JOptionPane.ERROR_MESSAGE);
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
        gbc.insets = new Insets(8, 6, 8, 6);
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
}
