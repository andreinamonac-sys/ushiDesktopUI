package com.andreina.ushi.desktop.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.andreina.ushi.model.AnimalDTO;

public class AnimalTableModel extends AbstractTableModel {

    public static final String[] COLUMN_NAMES = new String[] {
        "ID", "N? de registro", "Fecha de nacimiento", "Sexo", "Granja"
    };

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
                return ""; // No fecha de nacimiento en AnimalDTO
            case 3:
                return a.getSexoDescripcion() != null ? a.getSexoDescripcion() : "";
            case 4:
                return a.getGranjaId() != null ? a.getGranjaId() : "";
            default:
                return null;
        }
    }

    public List<AnimalDTO> getAnimales() {
        return animales;
    }

    public void setAnimales(List<AnimalDTO> animales) {
        this.animales = animales != null ? animales : new ArrayList<>();
        fireTableDataChanged();
    }
}

