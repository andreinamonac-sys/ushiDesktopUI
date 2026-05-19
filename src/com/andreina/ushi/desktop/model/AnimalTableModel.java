package com.andreina.ushi.desktop.model;

import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.andreina.ushi.model.AnimalDTO;

public class AnimalTableModel extends AbstractTableModel {

    public static final String[] COLUMN_NAMES = new String[] {
        "ID", "Num. registro", "Nacimiento", "Alta", "Baja", "Sexo", "Granja", "Madre interna", "Acciones"
    };

    public static final int ACTIONS_COLUMN = 8;
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private List<AnimalDTO> animales;

    public AnimalTableModel() {
        this.animales = new ArrayList<>();
    }

    public AnimalTableModel(List<AnimalDTO> animales) {
        this.animales = animales != null ? animales : new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return animales.size();
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
        AnimalDTO a = animales.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return a.getId();
            case 1:
                return a.getNumRegistro();
            case 2:
                return a.getFechaNacimiento() == null ? "" : dateFormat.format(a.getFechaNacimiento());
            case 3:
                return a.getFechaAlta() == null ? "" : dateTimeFormat.format(a.getFechaAlta());
            case 4:
                return a.getFechaBaja() == null ? "" : dateTimeFormat.format(a.getFechaBaja());
            case 5:
                return a.getSexoDescripcion() != null ? a.getSexoDescripcion() : "";
            case 6:
                return a.getGranjaNif() != null ? a.getGranjaNif() : value(a.getGranjaId());
            case 7:
                return a.getNumRegistroMadreInterna() != null ? a.getNumRegistroMadreInterna() : value(a.getMadreInternaId());
            case 8:
                return "actions";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == ACTIONS_COLUMN;
    }

    public List<AnimalDTO> getAnimales() {
        return animales;
    }

    public AnimalDTO getAnimalAt(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= animales.size()) {
            return null;
        }
        return animales.get(rowIndex);
    }

    public void setAnimales(List<AnimalDTO> animales) {
        this.animales = animales != null ? animales : new ArrayList<>();
        fireTableDataChanged();
    }

    private String value(Long value) {
        return value == null ? "" : String.valueOf(value);
    }
}

