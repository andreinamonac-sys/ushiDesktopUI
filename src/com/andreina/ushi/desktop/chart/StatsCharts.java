package com.andreina.ushi.desktop.chart;

import java.awt.BorderLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.model.ParametroDTO;
import com.andreina.ushi.desktop.view.UshiColors;

public final class StatsCharts {

    private static final DateFormat SHORT_DATE = new SimpleDateFormat("dd/MM", Locale.ROOT);

    private StatsCharts() {
    }

    public static JPanel createCeloPanel(List<EventoDTO> eventos) {
        long celos = countEventos(eventos, 7L, "celo");
        long listas = countEventos(eventos, 9L, "lista");
        long inseminaciones = countEventos(eventos, 2L, "inseminacion");

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(celos, "Eventos", "Celos");
        dataset.addValue(listas, "Eventos", "Listas");
        dataset.addValue(inseminaciones, "Eventos", "Inseminaciones");

        JFreeChart chart = ChartFactory.createBarChart("Celo e inseminaciones",
                "Tipo de evento", "Registros", dataset);
        styleCategory(chart);
        return wrap(chart, "Celos: " + celos + " | Listas: " + listas
                + " | Inseminaciones: " + inseminaciones);
    }

    public static JPanel createReproductionPanel(List<EventoDTO> eventos) {
        long inseminaciones = countEventos(eventos, 2L, "inseminacion");
        long positivos = countDiagnostico(eventos, "P");
        long negativos = countDiagnostico(eventos, "N");
        long partos = countEventos(eventos, 4L, "parto");
        double tasa = inseminaciones == 0 ? 0.0 : (positivos * 100.0 / inseminaciones);

        DefaultPieDataset<String> dataset = new DefaultPieDataset<String>();
        dataset.setValue("Gestacion positiva", positivos);
        dataset.setValue("Gestacion negativa", negativos);
        dataset.setValue("Partos", partos);

        JFreeChart chart = ChartFactory.createPieChart("Reproduccion", dataset, true, true, false);
        stylePie(chart);
        return wrap(chart, "Exito: " + format(tasa) + "% | Inseminaciones: "
                + inseminaciones + " | Partos: " + partos);
    }

    public static JPanel createMilkPanel(List<ParametroDTO> parametros) {
        LinkedHashSet<Long> animals = new LinkedHashSet<Long>();
        for (ParametroDTO parametro : safeParametros(parametros)) {
            if (Long.valueOf(8L).equals(parametro.getTipoParametroId()) && parametro.getAnimalId() != null) {
                animals.add(parametro.getAnimalId());
            }
        }
        if (animals.size() <= 1) {
            return createMilkTrendPanel(parametros);
        }

        Map<String, Average> values = new LinkedHashMap<String, Average>();
        int sequence = 1;
        for (ParametroDTO parametro : safeParametros(parametros)) {
            if (!Long.valueOf(8L).equals(parametro.getTipoParametroId())) {
                continue;
            }
            if (parametro.getValorParametro() == null) {
                continue;
            }
            String key = parametro.getAnimalId() == null ? "Registro " + sequence : "Animal " + parametro.getAnimalId();
            values.computeIfAbsent(key, k -> new Average()).add(parametro.getValorParametro());
            sequence++;
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double total = 0.0;
        int count = 0;
        for (Map.Entry<String, Average> entry : values.entrySet()) {
            double average = entry.getValue().value();
            dataset.addValue(average, "Litros", entry.getKey());
            total += entry.getValue().sum;
            count += entry.getValue().count;
        }

        JFreeChart chart = ChartFactory.createLineChart("Productividad de leche",
                "Animal", "Litros medios/dia", dataset);
        styleCategory(chart);
        double average = count == 0 ? 0.0 : total / count;
        return wrap(chart, "Media registrada: " + format(average) + " L/dia | Registros: " + count);
    }

    private static JPanel createMilkTrendPanel(List<ParametroDTO> parametros) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double total = 0.0;
        int count = 0;
        for (ParametroDTO parametro : safeParametros(parametros)) {
            if (!Long.valueOf(8L).equals(parametro.getTipoParametroId()) || parametro.getValorParametro() == null) {
                continue;
            }
            String date = parametro.getFechaHora() == null ? String.valueOf(count + 1) : SHORT_DATE.format(parametro.getFechaHora());
            dataset.addValue(parametro.getValorParametro(), "Litros", date + " #" + parametro.getId());
            total += parametro.getValorParametro();
            count++;
        }
        JFreeChart chart = ChartFactory.createLineChart("Productividad de leche",
                "Fecha", "Litros/dia", dataset);
        styleCategory(chart);
        double average = count == 0 ? 0.0 : total / count;
        return wrap(chart, "Media registrada: " + format(average) + " L/dia | Registros: " + count);
    }

    public static JPanel createHealthPanel(List<ParametroDTO> parametros) {
        long total = 0;
        long fueraRango = 0;
        for (ParametroDTO parametro : safeParametros(parametros)) {
            if (!isHealthParameter(parametro)) {
                continue;
            }
            total++;
            if (isOutOfRange(parametro)) {
                fueraRango++;
            }
        }
        long normales = total - fueraRango;
        double ratio = total == 0 ? 0.0 : (fueraRango * 100.0 / total);

        DefaultPieDataset<String> dataset = new DefaultPieDataset<String>();
        dataset.setValue("Normal", normales);
        dataset.setValue("Fuera de rango", fueraRango);

        JFreeChart chart = ChartFactory.createPieChart("Parametros de salud", dataset, true, true, false);
        stylePie(chart);
        return wrap(chart, "Fuera de rango: " + format(ratio) + "% | Registros salud: " + total);
    }

    public static JPanel createParameterTrendPanel(List<ParametroDTO> parametros) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int row = 0;
        for (ParametroDTO parametro : safeParametros(parametros)) {
            if (row >= 40) {
                break;
            }
            String series = parametro.getTipoParametroNombre() == null
                    ? "Parametro " + parametro.getTipoParametroId()
                    : parametro.getTipoParametroNombre();
            String date = parametro.getFechaHora() == null ? String.valueOf(row + 1) : SHORT_DATE.format(parametro.getFechaHora());
            if (parametro.getValorParametro() != null) {
                dataset.addValue(parametro.getValorParametro(), series, date + " #" + parametro.getId());
            }
            row++;
        }
        JFreeChart chart = ChartFactory.createLineChart("Evolucion de parametros",
                "Fecha", "Valor", dataset);
        styleCategory(chart);
        return wrap(chart, "Ultimos registros mostrados: " + row);
    }

    private static JPanel wrap(JFreeChart chart, String footer) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(UshiColors.C_BLANCO_SUAVE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UshiColors.C_GRIS_VERDOSO_BORDE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setOpaque(false);
        panel.add(chartPanel, BorderLayout.CENTER);
        JLabel footerLabel = new JLabel(footer);
        footerLabel.setForeground(UshiColors.C_GRIS_VERDOSO_MEDIO);
        panel.add(footerLabel, BorderLayout.SOUTH);
        return panel;
    }

    private static void styleCategory(JFreeChart chart) {
        chart.setBackgroundPaint(UshiColors.C_BLANCO_SUAVE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(UshiColors.C_BLANCO_SUAVE);
        plot.setRangeGridlinePaint(UshiColors.C_GRIS_VERDOSO_BORDE);
    }

    @SuppressWarnings("rawtypes")
    private static void stylePie(JFreeChart chart) {
        chart.setBackgroundPaint(UshiColors.C_BLANCO_SUAVE);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(UshiColors.C_BLANCO_SUAVE);
        plot.setOutlinePaint(UshiColors.C_GRIS_VERDOSO_BORDE);
    }

    private static List<ParametroDTO> safeParametros(List<ParametroDTO> parametros) {
        return parametros == null ? java.util.Collections.<ParametroDTO>emptyList() : parametros;
    }

    private static List<EventoDTO> safeEventos(List<EventoDTO> eventos) {
        return eventos == null ? java.util.Collections.<EventoDTO>emptyList() : eventos;
    }

    private static long countEventos(List<EventoDTO> eventos, Long tipoId, String nameFragment) {
        long count = 0;
        for (EventoDTO evento : safeEventos(eventos)) {
            if (tipoId.equals(evento.getTipoEventoId()) || containsNormalized(evento.getNombreTipoEvento(), nameFragment)
                    || containsNormalized(evento.getDescripcion(), nameFragment)) {
                count++;
            }
        }
        return count;
    }

    private static long countDiagnostico(List<EventoDTO> eventos, String value) {
        long count = 0;
        for (EventoDTO evento : safeEventos(eventos)) {
            if (Long.valueOf(1L).equals(evento.getTipoEventoId())
                    && value.equalsIgnoreCase(trim(evento.getValorDiagnostico()))) {
                count++;
            }
        }
        return count;
    }

    private static boolean isHealthParameter(ParametroDTO parametro) {
        Long type = parametro.getTipoParametroId();
        return Long.valueOf(1L).equals(type) || Long.valueOf(2L).equals(type)
                || Long.valueOf(3L).equals(type) || Long.valueOf(5L).equals(type)
                || Long.valueOf(6L).equals(type) || Long.valueOf(7L).equals(type);
    }

    private static boolean isOutOfRange(ParametroDTO parametro) {
        if (parametro.getValorParametro() == null) {
            return false;
        }
        double value = parametro.getValorParametro();
        Long type = parametro.getTipoParametroId();
        if (Long.valueOf(1L).equals(type)) {
            return value < 37.5 || value > 39.0;
        }
        if (Long.valueOf(2L).equals(type)) {
            return value < 50.0 || value > 80.0;
        }
        if (Long.valueOf(3L).equals(type)) {
            return value < 10.0 || value > 40.0;
        }
        if (Long.valueOf(5L).equals(type)) {
            return value < 60.0;
        }
        if (Long.valueOf(6L).equals(type)) {
            return value < 250.0 || value > 500.0;
        }
        if (Long.valueOf(7L).equals(type)) {
            return value < 5.8 || value > 7.0;
        }
        return false;
    }

    private static boolean containsNormalized(String value, String fragment) {
        return normalize(value).contains(normalize(fragment));
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }
        return java.text.Normalizer.normalize(value, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toLowerCase(Locale.ROOT);
    }

    private static String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private static String format(double value) {
        return String.format(Locale.ROOT, "%.1f", value);
    }

    private static final class Average {
        private double sum;
        private int count;

        private void add(double value) {
            sum += value;
            count++;
        }

        private double value() {
            return count == 0 ? 0.0 : sum / count;
        }
    }
}
