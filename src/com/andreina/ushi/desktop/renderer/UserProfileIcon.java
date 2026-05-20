package com.andreina.ushi.desktop.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;

import com.andreina.ushi.desktop.view.UshiColors;

public class UserProfileIcon implements Icon {

    private final int size;
    private final Color color;

    public UserProfileIcon() {
        this(36);
    }

    public UserProfileIcon(int size) {
        this.size = size;
        this.color = UshiColors.C_GRIS_VERDOSO_OSCURO;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(Math.max(2.6f, size / 12f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(color);
        int inset = Math.max(2, size / 14);
        int diameter = size - inset * 2;
        g2.drawOval(x + inset, y + inset, diameter, diameter);
        g2.drawOval(x + size / 2 - size / 10, y + size / 4, size / 5, size / 5);
        g2.drawArc(x + size / 4, y + size / 2, size / 2, size / 3, 0, 180);
        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}
