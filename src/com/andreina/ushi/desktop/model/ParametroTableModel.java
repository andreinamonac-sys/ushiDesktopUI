package com.andreina.ushi.desktop.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.andreina.ushi.model.ParametroDTO;

public class ParametroTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = { "ID", "Fecha", "Tag", "Animal", "Tipo", "Valor" };
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private List<ParametroDTO> parametros = new ArrayList<ParametroDTO>();

    @Override
    public int getRowCount() {
        return parametros.size();
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
        ParametroDTO parametro = parametros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return parametro.getId();
            case 1:
                return parametro.getFechaHora() == null ? "" : dateFormat.format(parametro.getFechaHora());
            case 2:
                return parametro.getTagId() == null ? "" : parametro.getTagId();
            case 3:
                return parametro.getAnimalId() == null ? "" : parametro.getAnimalId();
            case 4:
                return text(parametro.getTipoParametroNombre());
            case 5:
                return parametro.getValorParametro() == null ? "" : parametro.getValorParametro();
            default:
                return "";
        }
    }

    public void setParametros(List<ParametroDTO> parametros) {
        this.parametros = parametros == null ? new ArrayList<ParametroDTO>() : parametros;
        fireTableDataChanged();
    }

    private String text(String value) {
        return value == null ? "" : value;
    }
}
