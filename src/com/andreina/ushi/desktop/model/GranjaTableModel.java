package com.andreina.ushi.desktop.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.andreina.ushi.model.GranjaDTO;

public class GranjaTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = { "ID", "NIF", "Calle", "Numero", "CP", "Encargado", "Acciones" };
    public static final int ACTIONS_COLUMN = 6;
    private List<GranjaDTO> granjas = new ArrayList<GranjaDTO>();

    @Override
    public int getRowCount() {
        return granjas.size();
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
        GranjaDTO granja = granjas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return granja.getId();
            case 1:
                return text(granja.getNif());
            case 2:
                return text(granja.getCalle());
            case 3:
                return granja.getNumero() == null ? "" : granja.getNumero();
            case 4:
                return granja.getCp() == null ? "" : granja.getCp();
            case 5:
                return text(granja.getUsuarioNombre());
            case 6:
                return "actions";
            default:
                return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == ACTIONS_COLUMN;
    }

    public GranjaDTO getGranjaAt(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= granjas.size()) {
            return null;
        }
        return granjas.get(rowIndex);
    }

    public void setGranjas(List<GranjaDTO> granjas) {
        this.granjas = granjas == null ? new ArrayList<GranjaDTO>() : granjas;
        fireTableDataChanged();
    }

    private String text(String value) {
        return value == null ? "" : value;
    }
}
