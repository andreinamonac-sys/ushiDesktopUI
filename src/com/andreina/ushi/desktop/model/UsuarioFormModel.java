package com.andreina.ushi.desktop.model;

import com.andreina.ushi.model.Rol;
import com.andreina.ushi.model.UsuarioDTO;

public class UsuarioFormModel {

	private String dniNie;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String telefono;
	private String email;
	private Rol rol;

	public String getDniNie() {
		return dniNie;
	}

	public void setDniNie(String dniNie) {
		this.dniNie = dniNie;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public UsuarioDTO toUsuarioDTO(String password) {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setDniNie(dniNie);
		usuario.setNombre(nombre);
		usuario.setApellido1(apellido1);
		usuario.setApellido2(apellido2);
		usuario.setTelefono(parseTelefono(telefono));
		usuario.setEmail(email);
		usuario.setPassword(password);
		if (rol != null) {
			usuario.setRolId(rol.getId());
		}
		return usuario;
	}

	private Long parseTelefono(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		if (trimmed.isEmpty()) {
			return null;
		}
		try {
			return Long.parseLong(trimmed);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
