package com.andreina.ushi.desktop.controller;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.andreina.ushi.desktop.MainWindow;
import com.andreina.ushi.desktop.model.UsuarioFormModel;
import com.andreina.ushi.desktop.renderer.RolCBRenderer;
import com.andreina.ushi.desktop.view.UsuarioCreateView;
import com.andreina.ushi.desktop.view.UsuarioSearchView;
import com.andreina.ushi.model.Rol;
import com.andreina.ushi.model.UsuarioDTO;
import com.andreina.ushi.service.RolService;
import com.andreina.ushi.service.UsuarioService;
import com.andreina.ushi.service.impl.RolServiceImpl;
import com.andreina.ushi.service.impl.UsuarioServiceImpl;

public class UsuarioCreateController {

	private final UsuarioCreateView view;
	private final RolService rolService;
	private final UsuarioService usuarioService;

	public UsuarioCreateController(UsuarioCreateView view) {
		this.view = view;
		this.rolService = new RolServiceImpl();
		this.usuarioService = new UsuarioServiceImpl();
		init();
	}

	private void init() {
		view.getRolCombo().setRenderer(new RolCBRenderer());
		loadRoles();

		view.getBtnNuevoUsuario().addActionListener(e -> crearUsuario());
		view.getBtnCancelar().addActionListener(e -> cancelar());
	}

	private void loadRoles() {
		DefaultComboBoxModel<Rol> model = new DefaultComboBoxModel<>();
		Rol placeholder = new Rol();
		placeholder.setId(null);
		placeholder.setNombre("Seleccionar");
		model.addElement(placeholder);

		try {
			List<Rol> roles = rolService.findAll();
			if (roles != null) {
				for (Rol r : roles) {
					model.addElement(r);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "No se pudieron cargar los roles: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		view.getRolCombo().setModel(model);
	}

	private void crearUsuario() {
		UsuarioFormModel form = new UsuarioFormModel();
		form.setDniNie(trimToNull(view.getDniNieTF().getText()));
		form.setNombre(trimToNull(view.getNombreTF().getText()));
		form.setApellido1(trimToNull(view.getApellido1TF().getText()));
		form.setApellido2(trimToNull(view.getApellido2TF().getText()));
		form.setTelefono(trimToNull(view.getTelefonoTF().getText()));
		form.setEmail(trimToNull(view.getEmailTF().getText()));
		form.setRol((Rol) view.getRolCombo().getSelectedItem());

		if (form.getRol() == null || form.getRol().getId() == null) {
			JOptionPane.showMessageDialog(view, "Selecciona un rol.", "Validacion",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			UsuarioDTO usuario = form.toUsuarioDTO("pass123");
			Long id = usuarioService.registrar(usuario);
			if (id != null) {
				JOptionPane.showMessageDialog(view, "Usuario creado con ID: " + id, "OK",
						JOptionPane.INFORMATION_MESSAGE);
				limpiar();
				volverAUsuarios();
			} else {
				JOptionPane.showMessageDialog(view, "No se pudo crear el usuario.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "No se pudo crear el usuario: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			limpiar();
		}
	}

	private void cancelar() {
		limpiar();
		volverAUsuarios();
	}

	private void limpiar() {
		view.getDniNieTF().setText("");
		view.getNombreTF().setText("");
		view.getApellido1TF().setText("");
		view.getApellido2TF().setText("");
		view.getTelefonoTF().setText("");
		view.getEmailTF().setText("");
		view.getRolCombo().setSelectedIndex(0);
	}

	private void volverAUsuarios() {
		UsuarioSearchView searchView = new UsuarioSearchView();
		new UsuarioSearchController(searchView);
		MainWindow.getInstance().setView(searchView);
	}

	private String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}
}
