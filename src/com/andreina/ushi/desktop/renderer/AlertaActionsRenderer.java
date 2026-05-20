package com.andreina.ushi.desktop.renderer;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.andreina.ushi.desktop.view.UshiColors;

public class AlertaActionsRenderer extends JPanel implements TableCellRenderer {

    public AlertaActionsRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 4, 0));
        setOpaque(true);
        add(createButton());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        setBackground(isSelected ? UshiColors.C_VERDE_MENTA_GRISACEO : UshiColors.C_BLANCO_SUAVE);
        return this;
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
