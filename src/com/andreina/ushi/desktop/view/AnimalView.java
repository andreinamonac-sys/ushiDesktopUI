package com.andreina.ushi.desktop.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.andreina.ushi.desktop.chart.StatsCharts;
import com.andreina.ushi.desktop.model.EventoTableModel;
import com.andreina.ushi.desktop.model.ParametroTableModel;
import com.andreina.ushi.desktop.renderer.UshiTableCellRenderer;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.model.ParametroDTO;

public class AnimalView extends AbstractView {

	private static final long serialVersionUID = 1L;
	private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	private JTextField numRegistroTextField;
	private JComboBox<String> sexoComboBox;
	private JTextField fechaNacimientoTextField;
	private JTextField fechaAltaTextField;
	private JTextField fechaBajaTextField;
	private JTextField granjaNifTextField;
	private JTextField padreExternoTextField;
	private JTextField madreExternaTextField;
	private JTextField madreInternaTextField;

	/**
	 * Create the panel.
	 */
	public AnimalView() {
		super("Animal");
		initFormComponents();
	}

	public AnimalView(AnimalDTO animal, List<EventoDTO> eventos, List<ParametroDTO> parametros) {
		super(animal == null || animal.getNumRegistro() == null ? "Animal" : animal.getNumRegistro());
		initDetailComponents(animal, eventos, parametros);
	}

	private void initFormComponents() {
		setLayout(new GridBagLayout());
		setBackground(UshiColors.C_BLANCO_SUAVE);

		numRegistroTextField = new JTextField(16);
		sexoComboBox = new JComboBox<String>(new String[] { "Macho", "Hembra" });
		fechaNacimientoTextField = new JTextField(16);
		fechaAltaTextField = new JTextField(16);
		fechaBajaTextField = new JTextField(16);
		granjaNifTextField = new JTextField(16);
		padreExternoTextField = new JTextField(16);
		madreExternaTextField = new JTextField(16);
		madreInternaTextField = new JTextField(16);
		numRegistroTextField.putClientProperty("JTextField.placeholderText", "Ej. AN-041-2026");
		fechaNacimientoTextField.putClientProperty("JTextField.placeholderText", "dd/MM/yyyy");
		fechaAltaTextField.putClientProperty("JTextField.placeholderText", "dd/MM/yyyy HH:mm");
		fechaBajaTextField.putClientProperty("JTextField.placeholderText", "dd/MM/yyyy HH:mm opcional");
		granjaNifTextField.putClientProperty("JTextField.placeholderText", "NIF de la granja");
		padreExternoTextField.putClientProperty("JTextField.placeholderText", "ID externo del padre opcional");
		madreExternaTextField.putClientProperty("JTextField.placeholderText", "ID externo de la madre opcional");
		madreInternaTextField.putClientProperty("JTextField.placeholderText", "ID o num. registro de madre opcional");

		addRow(0, "Num. registro", numRegistroTextField);
		addRow(1, "Sexo", sexoComboBox);
		addRow(2, "Fecha nacimiento", fechaNacimientoTextField);
		addRow(3, "Fecha alta", fechaAltaTextField);
		addRow(4, "Fecha baja", fechaBajaTextField);
		addRow(5, "Granja NIF", granjaNifTextField);
		addRow(6, "Padre externo", padreExternoTextField);
		addRow(7, "Madre externa", madreExternaTextField);
		addRow(8, "Madre interna", madreInternaTextField);
	}

	private void initDetailComponents(AnimalDTO animal, List<EventoDTO> eventos, List<ParametroDTO> parametros) {
		setLayout(new BorderLayout(0, 0));
		setBackground(UshiColors.C_GRIS_NIEBLA_CLARO);
		JTabbedPane tabs = new JTabbedPane();
		add(tabs, BorderLayout.CENTER);

		tabs.addTab("Datos", createDataPanel(animal));
		tabs.addTab("Eventos", createEventsPanel(eventos));
		tabs.addTab("Estadisticas", createStatsPanel(eventos, parametros));
	}

	private JPanel createDataPanel(AnimalDTO animal) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(UshiColors.C_BLANCO_SUAVE);
		panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		addDataRow(panel, 0, "Num. registro", animal == null ? "" : animal.getNumRegistro());
		addDataRow(panel, 1, "Sexo", animal == null ? "" : resolveSexoDescripcion(animal));
		addDataRow(panel, 2, "Nacimiento", animal == null || animal.getFechaNacimiento() == null ? "" : dateFormat.format(animal.getFechaNacimiento()));
		addDataRow(panel, 3, "Alta", animal == null || animal.getFechaAlta() == null ? "" : dateTimeFormat.format(animal.getFechaAlta()));
		addDataRow(panel, 4, "Baja", animal == null || animal.getFechaBaja() == null ? "" : dateTimeFormat.format(animal.getFechaBaja()));
		addDataRow(panel, 5, "Granja", animal == null ? "" : text(animal.getGranjaNif()));
		addDataRow(panel, 6, "Madre interna", animal == null ? "" : text(animal.getNumRegistroMadreInterna()));
		return panel;
	}

	private JScrollPane createEventsPanel(List<EventoDTO> eventos) {
		EventoTableModel model = new EventoTableModel();
		model.setEventos(eventos);
		JTable table = new JTable(model);
		table.setDefaultRenderer(Object.class, new UshiTableCellRenderer());
		table.setRowHeight(28);
		table.getTableHeader().setBackground(UshiColors.C_VERDE_MENTA_LECHE);
		return new JScrollPane(table);
	}

	private JPanel createStatsPanel(List<EventoDTO> eventos, List<ParametroDTO> parametros) {
		JPanel panel = new JPanel(new BorderLayout(0, 10));
		panel.setBackground(UshiColors.C_GRIS_NIEBLA_CLARO);
		panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		ParametroTableModel model = new ParametroTableModel();
		model.setParametros(parametros);
		JTable table = new JTable(model);
		table.setDefaultRenderer(Object.class, new UshiTableCellRenderer());
		table.setRowHeight(26);

		JTabbedPane statsTabs = new JTabbedPane();
		statsTabs.addTab("Celo e inseminacion", StatsCharts.createCeloPanel(eventos));
		statsTabs.addTab("Reproduccion", StatsCharts.createReproductionPanel(eventos));
		statsTabs.addTab("Leche", StatsCharts.createMilkPanel(parametros));
		statsTabs.addTab("Salud", StatsCharts.createHealthPanel(parametros));
		statsTabs.addTab("Evolucion", StatsCharts.createParameterTrendPanel(parametros));
		statsTabs.addTab("Parametros", new JScrollPane(table));
		panel.add(statsTabs, BorderLayout.CENTER);
		return panel;
	}

	public void setAnimal(AnimalDTO animal) {
		if (animal == null) {
			return;
		}
		numRegistroTextField.setText(text(animal.getNumRegistro()));
		sexoComboBox.setSelectedItem(resolveSexoDescripcion(animal));
		fechaNacimientoTextField.setText(animal.getFechaNacimiento() == null ? "" : dateFormat.format(animal.getFechaNacimiento()));
		fechaAltaTextField.setText(animal.getFechaAlta() == null ? "" : dateTimeFormat.format(animal.getFechaAlta()));
		fechaBajaTextField.setText(animal.getFechaBaja() == null ? "" : dateTimeFormat.format(animal.getFechaBaja()));
		granjaNifTextField.setText(text(animal.getGranjaNif()));
		padreExternoTextField.setText(text(animal.getPadreExternoId()));
		madreExternaTextField.setText(text(animal.getMadreExternaId()));
		madreInternaTextField.setText(text(animal.getMadreInternaId()));
	}

	public AnimalDTO updateAnimal(AnimalDTO animal) throws ParseException {
		animal.setNumRegistro(trimToNull(numRegistroTextField.getText()));
		animal.setSexoId(toSexoId((String) sexoComboBox.getSelectedItem()));
		animal.setSexoDescripcion((String) sexoComboBox.getSelectedItem());
		animal.setFechaNacimiento(toDate(fechaNacimientoTextField.getText()));
		animal.setFechaAlta(toDateTime(fechaAltaTextField.getText()));
		animal.setFechaBaja(toDateTime(fechaBajaTextField.getText()));
		animal.setGranjaNif(getGranjaNif());
		animal.setPadreExternoId(toLong(padreExternoTextField.getText()));
		animal.setMadreExternaId(toLong(madreExternaTextField.getText()));
		setMadreInterna(animal, madreInternaTextField.getText());
		return animal;
	}

	public String getGranjaNif() {
		return trimToNull(granjaNifTextField.getText());
	}

	public String getMadreInterna() {
		return trimToNull(madreInternaTextField.getText());
	}

	private void addRow(int row, String label, java.awt.Component field) {
		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.gridx = 0;
		gbcLabel.gridy = row;
		gbcLabel.insets = new Insets(5, 5, 5, 8);
		gbcLabel.anchor = GridBagConstraints.WEST;
		add(new JLabel(label), gbcLabel);

		GridBagConstraints gbcField = new GridBagConstraints();
		gbcField.gridx = 1;
		gbcField.gridy = row;
		gbcField.insets = new Insets(5, 5, 5, 5);
		gbcField.fill = GridBagConstraints.HORIZONTAL;
		gbcField.weightx = 1.0;
		add(field, gbcField);
	}

	private void addDataRow(JPanel panel, int row, String label, String value) {
		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.gridx = 0;
		gbcLabel.gridy = row;
		gbcLabel.insets = new Insets(7, 8, 7, 18);
		gbcLabel.anchor = GridBagConstraints.WEST;
		JLabel labelComponent = new JLabel(label);
		labelComponent.setForeground(UshiColors.C_GRIS_VERDOSO_MEDIO);
		panel.add(labelComponent, gbcLabel);

		GridBagConstraints gbcValue = new GridBagConstraints();
		gbcValue.gridx = 1;
		gbcValue.gridy = row;
		gbcValue.insets = new Insets(7, 8, 7, 8);
		gbcValue.anchor = GridBagConstraints.WEST;
		gbcValue.weightx = 1.0;
		JLabel valueComponent = new JLabel(value == null ? "" : value);
		valueComponent.setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
		panel.add(valueComponent, gbcValue);
	}

	private String text(Object value) {
		return value == null ? "" : String.valueOf(value);
	}

	private Long toLong(String value) {
		String text = trimToNull(value);
		return text == null ? null : Long.valueOf(text);
	}

	private void setMadreInterna(AnimalDTO animal, String value) {
		String text = trimToNull(value);
		if (text == null) {
			animal.setMadreInternaId(null);
			animal.setNumRegistroMadreInterna(null);
			return;
		}
		try {
			animal.setMadreInternaId(Long.valueOf(text));
			animal.setNumRegistroMadreInterna(null);
		} catch (NumberFormatException e) {
			animal.setMadreInternaId(null);
			animal.setNumRegistroMadreInterna(text);
		}
	}

	private Long toSexoId(String sexoDescripcion) {
		return "Hembra".equalsIgnoreCase(sexoDescripcion) ? Long.valueOf(2L) : Long.valueOf(1L);
	}

	private String resolveSexoDescripcion(AnimalDTO animal) {
		if (animal.getSexoDescripcion() != null && !animal.getSexoDescripcion().trim().isEmpty()) {
			return animal.getSexoDescripcion();
		}
		return Long.valueOf(2L).equals(animal.getSexoId()) ? "Hembra" : "Macho";
	}

	private java.util.Date toDate(String value) throws ParseException {
		String text = trimToNull(value);
		return text == null ? null : dateFormat.parse(text);
	}

	private java.util.Date toDateTime(String value) throws ParseException {
		String text = trimToNull(value);
		if (text == null) {
			return null;
		}
		try {
			return dateTimeFormat.parse(text);
		} catch (ParseException e) {
			return dateFormat.parse(text);
		}
	}

	private String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}
}
