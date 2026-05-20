package com.andreina.ushi.desktop.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;

import com.andreina.ushi.desktop.view.UshiColors;

public class AnimalActionIcon implements Icon {

    public static final int EDIT = 1;
    public static final int DELETE = 2;

    private final int type;

    public AnimalActionIcon(int type) {
        this.type = type;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(type == DELETE ? new Color(0xA8, 0x3A, 0x3A) : UshiColors.C_GRIS_VERDOSO_OSCURO);
        if (type == DELETE) {
            paintTrash(g2, x, y);
        } else {
            paintPencil(g2, x, y);
        }
        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return 16;
    }

    @Override
    public int getIconHeight() {
        return 16;
    }

    private void paintPencil(Graphics2D g2, int x, int y) {
        g2.drawLine(x + 4, y + 12, x + 12, y + 4);
        g2.drawLine(x + 3, y + 13, x + 6, y + 12);
        g2.drawLine(x + 11, y + 3, x + 13, y + 5);
        g2.drawLine(x + 9, y + 5, x + 11, y + 7);
    }

    private void paintTrash(Graphics2D g2, int x, int y) {
        g2.drawLine(x + 4, y + 5, x + 12, y + 5);
        g2.drawLine(x + 6, y + 3, x + 10, y + 3);
        g2.drawRect(x + 5, y + 6, 6, 8);
        g2.drawLine(x + 7, y + 8, x + 7, y + 12);
        g2.drawLine(x + 9, y + 8, x + 9, y + 12);
    }
}
