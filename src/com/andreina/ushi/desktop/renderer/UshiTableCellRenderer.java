package com.andreina.ushi.desktop.renderer;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.andreina.ushi.desktop.view.UshiColors;

public class UshiTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            component.setBackground(UshiColors.C_VERDE_MENTA_GRISACEO);
            component.setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        } else {
            component.setBackground(row % 2 == 0 ? UshiColors.C_BLANCO_SUAVE : UshiColors.C_GRIS_NIEBLA_CLARO);
            component.setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        }
        setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        return component;
    }
}
