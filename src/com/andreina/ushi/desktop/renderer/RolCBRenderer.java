package com.andreina.ushi.desktop.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.andreina.ushi.model.Rol;

public class RolCBRenderer extends DefaultListCellRenderer {

	public RolCBRenderer() {
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		Rol rol = (Rol) value;
		if (rol != null) {
			this.setText(rol.getNombre());
		} else {
			this.setText("Seleccionar");
		}
		return this;
	}
}
