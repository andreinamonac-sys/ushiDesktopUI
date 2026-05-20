package com.andreina.ushi.desktop.controller;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.andreina.ushi.desktop.MainWindow;
import com.andreina.ushi.desktop.renderer.RowActionsEditor;
import com.andreina.ushi.desktop.view.UsuarioCreateView;
import com.andreina.ushi.desktop.view.UsuarioSearchView;
import com.andreina.ushi.model.UsuarioDTO;
import com.andreina.ushi.service.UsuarioService;
import com.andreina.ushi.service.impl.UsuarioServiceImpl;

public class UsuarioSearchController extends AbstractController implements RowActionsEditor.RowActionHandler {

    private final UsuarioSearchView view;
    private final UsuarioService usuarioService;

    public UsuarioSearchController(UsuarioSearchView view) {
        this.view = view;
        this.usuarioService = new UsuarioServiceImpl();
        this.view.getBtnBuscar().addActionListener(this);
        this.view.getBtnNuevo().addActionListener(this);
        this.view.setActionsHandler(this);
        buscar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnNuevo()) {
            UsuarioCreateView createView = new UsuarioCreateView();
            new UsuarioCreateController(createView);
            MainWindow.getInstance().setView(createView);
            return;
        }
        buscar();
    }

    private void buscar() {
        try {
            List<UsuarioDTO> usuarios = usuarioService.findByCriteria(view.getCriteria(), 0, 100);
            view.setModel(usuarios);
        } catch (Exception e) {
            view.showError("No se pudieron cargar los usuarios: " + e.getMessage());
        }
    }

    @Override
    public void editRow(int modelRow) {
        UsuarioDTO usuario = view.getUsuarioAt(modelRow);
        if (usuario == null) {
            return;
        }
        try {
            JTextField dni = new JTextField(text(usuario.getDniNie()));
            JTextField nombre = new JTextField(text(usuario.getNombre()));
            JTextField apellido1 = new JTextField(text(usuario.getApellido1()));
            JTextField apellido2 = new JTextField(text(usuario.getApellido2()));
            JTextField telefono = new JTextField(text(usuario.getTelefono()));
            JTextField email = new JTextField(text(usuario.getEmail()));
            JComboBox<String> rol = new JComboBox<String>(new String[] { "Administrador", "Veterinario", "Encargado", "Operario" });
            rol.setSelectedItem(text(usuario.getNombreRol()));
            JPanel panel = new JPanel(new java.awt.GridLayout(0, 2, 6, 6));
            panel.add(new JLabel("DNI/NIE"));
            panel.add(dni);
            panel.add(new JLabel("Nombre"));
            panel.add(nombre);
            panel.add(new JLabel("Apellido 1"));
            panel.add(apellido1);
            panel.add(new JLabel("Apellido 2"));
            panel.add(apellido2);
            panel.add(new JLabel("Telefono"));
            panel.add(telefono);
            panel.add(new JLabel("Email"));
            panel.add(email);
            panel.add(new JLabel("Rol"));
            panel.add(rol);
            int option = JOptionPane.showConfirmDialog(view, panel, "Modificar usuario", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            usuario.setDniNie(trimToNull(dni.getText()));
            usuario.setNombre(trimToNull(nombre.getText()));
            usuario.setApellido1(trimToNull(apellido1.getText()));
            usuario.setApellido2(trimToNull(apellido2.getText()));
            usuario.setTelefono(toLong(telefono.getText()));
            usuario.setEmail(trimToNull(email.getText()));
            usuario.setNombreRol((String) rol.getSelectedItem());
            usuario.setRolId(roleId((String) rol.getSelectedItem()));
            usuarioService.update(usuario);
            buscar();
        } catch (Exception e) {
            view.showError("No se pudo modificar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void deleteRow(int modelRow) {
        UsuarioDTO usuario = view.getUsuarioAt(modelRow);
        if (usuario == null || usuario.getId() == null) {
            return;
        }
        int option = JOptionPane.showConfirmDialog(view, "Quieres borrar el usuario " + usuario.getEmail() + "?",
                "Borrar usuario", JOptionPane.YES_NO_OPTION);
        if (option != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            usuarioService.delete(usuario.getId());
            buscar();
        } catch (Exception e) {
            view.showError("No se pudo borrar el usuario: " + e.getMessage());
        }
    }

    private Long roleId(String role) {
        if ("Veterinario".equalsIgnoreCase(role)) {
            return Long.valueOf(2L);
        }
        if ("Encargado".equalsIgnoreCase(role)) {
            return Long.valueOf(3L);
        }
        if ("Operario".equalsIgnoreCase(role)) {
            return Long.valueOf(4L);
        }
        return Long.valueOf(1L);
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

    private String text(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}
