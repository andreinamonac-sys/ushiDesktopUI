package com.andreina.ushi.desktop.renderer;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.andreina.ushi.desktop.view.UshiColors;

public class RowActionsEditor extends AbstractCellEditor implements TableCellEditor {

    public interface RowActionHandler {
        void editRow(int modelRow);
        void deleteRow(int modelRow);
    }

    private final JPanel panel;
    private final RowActionHandler handler;
    private int modelRow;

    public RowActionsEditor(RowActionHandler handler) {
        this.handler = handler;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        panel.setBackground(UshiColors.C_BLANCO_SUAVE);

        JButton editButton = createButton("Editar", new AnimalActionIcon(AnimalActionIcon.EDIT));
        JButton deleteButton = createButton("Borrar", new AnimalActionIcon(AnimalActionIcon.DELETE));
        editButton.addActionListener(e -> {
            fireEditingStopped();
            if (handler != null) {
                handler.editRow(modelRow);
            }
        });
        deleteButton.addActionListener(e -> {
            fireEditingStopped();
            if (handler != null) {
                handler.deleteRow(modelRow);
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
        modelRow = table.convertRowIndexToModel(row);
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
