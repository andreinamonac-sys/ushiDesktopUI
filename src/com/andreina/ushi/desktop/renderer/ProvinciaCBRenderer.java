package com.andreina.ushi.desktop.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.andreina.ushi.model.Provincia;

public class ProvinciaCBRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, displayText(value), index, isSelected, cellHasFocus);
        return this;
    }

    private String displayText(Object value) {
        if (value instanceof Provincia) {
            Provincia provincia = (Provincia) value;
            return provincia.getNombre() == null ? "" : provincia.getNombre();
        }
        return "";
    }
}
