
package com.andreina.ushi.desktop.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.andreina.ushi.model.AnimalDTO;

public class AnimalCBRenderer extends DefaultListCellRenderer {

    public AnimalCBRenderer() {
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        AnimalDTO animal = (AnimalDTO) value;
        if (animal != null) {
            String label = animal.getNumRegistro();
            if (label == null || label.isEmpty()) {
                label = animal.getId() != null ? animal.getId().toString() : "";
            }
            setText(label);
        } else {
            setText("Seleccionar");
        }
        return this;
    }
}
