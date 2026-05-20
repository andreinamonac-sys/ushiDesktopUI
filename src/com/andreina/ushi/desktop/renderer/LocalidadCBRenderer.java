package com.andreina.ushi.desktop.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.andreina.ushi.model.LocalidadDTO;

public class LocalidadCBRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, displayText(value), index, isSelected, cellHasFocus);
        return this;
    }

    private String displayText(Object value) {
        if (value instanceof LocalidadDTO) {
            LocalidadDTO localidad = (LocalidadDTO) value;
            String nombre = localidad.getNombre() == null ? "" : localidad.getNombre();
            String provincia = localidad.getProvinciaNombre() == null ? "" : " (" + localidad.getProvinciaNombre() + ")";
            return localidad.getId() == null ? nombre : nombre + provincia;
        }
        return "";
    }
}
