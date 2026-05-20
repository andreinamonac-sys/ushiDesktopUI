package com.andreina.ushi.desktop.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.andreina.ushi.desktop.chart.StatsCharts;
import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.model.ParametroDTO;

public class EstadisticasView extends AbstractView {

    private JPanel chartsPanel;
    private JLabel statusLabel;

    public EstadisticasView() {
        super("Estadisticas");
        initComponents();
    }

    /**
     * @wbp.parser.entryPoint
     */
    private void initComponents() {
        setLayout(new BorderLayout(16, 16));
        setBackground(UshiColors.C_GRIS_NIEBLA_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout(8, 4));
        headerPanel.setOpaque(false);
        add(headerPanel, BorderLayout.NORTH);

        JLabel title = new JLabel("Estadisticas");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        headerPanel.add(title, BorderLayout.WEST);

        statusLabel = new JLabel("Cargando datos...", SwingConstants.RIGHT);
        statusLabel.setForeground(UshiColors.C_GRIS_VERDOSO_MEDIO);
        headerPanel.add(statusLabel, BorderLayout.EAST);

        chartsPanel = new JPanel(new GridLayout(2, 2, 12, 12));
        chartsPanel.setOpaque(false);
        add(chartsPanel, BorderLayout.CENTER);
    }

    public void setStatistics(List<EventoDTO> eventos, List<ParametroDTO> parametros) {
        chartsPanel.removeAll();
        chartsPanel.add(StatsCharts.createCeloPanel(eventos));
        chartsPanel.add(StatsCharts.createReproductionPanel(eventos));
        chartsPanel.add(StatsCharts.createMilkPanel(parametros));
        chartsPanel.add(StatsCharts.createHealthPanel(parametros));
        statusLabel.setText("Eventos: " + size(eventos) + " | Parametros: " + size(parametros));
        chartsPanel.revalidate();
        chartsPanel.repaint();
    }

    public void showError(String message) {
        statusLabel.setText(message);
    }

    private int size(List<?> values) {
        return values == null ? 0 : values.size();
    }
}
