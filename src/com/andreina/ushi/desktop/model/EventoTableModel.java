package com.andreina.ushi.desktop.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.andreina.ushi.model.EventoDTO;

public class EventoTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = { "ID", "Fecha", "Tipo", "Animal", "Veterinario", "Descripcion",
            "Diagnostico", "Acciones" };
    public static final int ACTIONS_COLUMN = 7;
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private List<EventoDTO> eventos = new ArrayList<EventoDTO>();

    @Override
    public int getRowCount() {
        return eventos.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EventoDTO evento = eventos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return evento.getId();
            case 1:
                return evento.getFechaDesde() == null ? "" : dateFormat.format(evento.getFechaDesde());
            case 2:
                return value(evento.getNombreTipoEvento(), evento.getTipoEventoId());
            case 3:
                return value(evento.getAnimalNumRegistro(), evento.getAnimalId());
            case 4:
                return fullName(evento.getNombreVeterinario(), evento.getApellidoVeterinario(), evento.getVeterinarioId());
            case 5:
                return text(evento.getDescripcion());
            case 6:
                return text(evento.getValorDiagnostico());
            case 7:
                return "actions";
            default:
                return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == ACTIONS_COLUMN;
    }

    public EventoDTO getEventoAt(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= eventos.size()) {
            return null;
        }
        return eventos.get(rowIndex);
    }

    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos == null ? new ArrayList<EventoDTO>() : eventos;
        fireTableDataChanged();
    }

    private String value(String label, Long id) {
        if (label != null && !label.trim().isEmpty()) {
            return label;
        }
        return id == null ? "" : String.valueOf(id);
    }

    private String fullName(String name, String surname, Long fallbackId) {
        String fullName = ((name == null ? "" : name) + " " + (surname == null ? "" : surname)).trim();
        return fullName.isEmpty() && fallbackId != null ? String.valueOf(fallbackId) : fullName;
    }

    private String text(String value) {
        return value == null ? "" : value;
    }
}
