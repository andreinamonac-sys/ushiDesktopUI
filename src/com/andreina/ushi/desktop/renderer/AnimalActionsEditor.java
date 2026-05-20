package com.andreina.ushi.desktop.renderer;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.andreina.ushi.desktop.view.UshiColors;
import com.andreina.ushi.model.AnimalDTO;

public class AnimalActionsEditor extends AbstractCellEditor implements TableCellEditor {

    public interface AnimalActionHandler {
        void editAnimal(AnimalDTO animal);
        void deleteAnimal(AnimalDTO animal);
    }

    private final JPanel panel;
    private final JButton editButton;
    private final JButton deleteButton;
    private final AnimalActionHandler handler;
    private AnimalDTO animal;

    public AnimalActionsEditor(AnimalActionHandler handler) {
        this.handler = handler;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        panel.setBackground(UshiColors.C_BLANCO_SUAVE);
        editButton = createButton("Editar", new AnimalActionIcon(AnimalActionIcon.EDIT));
        deleteButton = createButton("Borrar", new AnimalActionIcon(AnimalActionIcon.DELETE));
        editButton.addActionListener(e -> {
            fireEditingStopped();
            if (handler != null) {
                handler.editAnimal(animal);
            }
        });
        deleteButton.addActionListener(e -> {
            fireEditingStopped();
            if (handler != null) {
                handler.deleteAnimal(animal);
            }
        });
        panel.add(editButton);
        panel.add(deleteButton);
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Object rowObject = table.getModel().getValueAt(table.convertRowIndexToModel(row), 0);
        animal = null;
        if (table.getModel() instanceof com.andreina.ushi.desktop.model.AnimalTableModel) {
            com.andreina.ushi.desktop.model.AnimalTableModel model =
                    (com.andreina.ushi.desktop.model.AnimalTableModel) table.getModel();
            animal = model.getAnimalAt(table.convertRowIndexToModel(row));
        }
        panel.setToolTipText(rowObject == null ? null : "Animal " + rowObject);
        return panel;
    }

    private JButton createButton(String tooltip, javax.swing.Icon icon) {
        JButton button = new JButton(icon);
        button.setToolTipText(tooltip);
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setBackground(UshiColors.C_VERDE_MENTA_LECHE);
        button.setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        return button;
    }
}
