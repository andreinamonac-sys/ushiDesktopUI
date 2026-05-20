package com.andreina.ushi.desktop.renderer;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.andreina.ushi.desktop.model.AlertaTableModel;
import com.andreina.ushi.desktop.view.UshiColors;
import com.andreina.ushi.model.AlertaDTO;

public class AlertaActionsEditor extends AbstractCellEditor implements TableCellEditor {

    public interface AlertaActionHandler {
        void markAsRead(AlertaDTO alerta);
    }

    private final JPanel panel;
    private final AlertaActionHandler handler;
    private AlertaDTO alerta;

    public AlertaActionsEditor(AlertaActionHandler handler) {
        this.handler = handler;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        panel.setBackground(UshiColors.C_BLANCO_SUAVE);
        JButton button = createButton();
        button.addActionListener(e -> {
            fireEditingStopped();
            if (handler != null) {
                handler.markAsRead(alerta);
            }
        });
        panel.add(button);
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        alerta = null;
        if (table.getModel() instanceof AlertaTableModel) {
            AlertaTableModel model = (AlertaTableModel) table.getModel();
            alerta = model.getAlertaAt(table.convertRowIndexToModel(row));
        }
        return panel;
    }

    private JButton createButton() {
        JButton button = new JButton("Leida");
        button.setToolTipText("Marcar como leida");
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setBackground(UshiColors.C_VERDE_MENTA_LECHE);
        button.setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        return button;
    }
}
