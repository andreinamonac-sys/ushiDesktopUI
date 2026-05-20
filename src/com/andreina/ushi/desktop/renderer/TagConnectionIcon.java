package com.andreina.ushi.desktop.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;

import com.andreina.ushi.desktop.view.UshiColors;

public class TagConnectionIcon implements Icon {

    private final int size;
    private final Color color;

    public TagConnectionIcon(int size) {
        this(size, UshiColors.C_GRIS_VERDOSO_OSCURO);
    }

    public TagConnectionIcon(int size, Color color) {
        this.size = size;
        this.color = color == null ? UshiColors.C_GRIS_VERDOSO_OSCURO : color;
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(Math.max(2f, size / 11f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int left = x + size / 7;
        int bottom = y + size - size / 6;
        int barWidth = Math.max(3, size / 8);
        int gap = Math.max(3, size / 10);
        for (int i = 0; i < 4; i++) {
            int h = size / 5 + i * size / 8;
            int bx = left + i * (barWidth + gap);
            g2.fillRoundRect(bx, bottom - h, barWidth, h, barWidth, barWidth);
        }
        g2.drawLine(x + size / 6, y + size / 5, x + size - size / 7, y + size - size / 7);
        g2.dispose();
    }
}
