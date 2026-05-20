package com.andreina.ushi.desktop.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.andreina.ushi.model.AlertaDTO;

public class AlertaTableModel extends AbstractTableModel {

    public static final int ACTIONS_COLUMN = 4;

    private final String[] columns = { "Fecha", "Mensaje", "Tag", "Animal", "Acciones" };
    private final DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private List<AlertaDTO> alertas = new ArrayList<AlertaDTO>();

    @Override
    public int getRowCount() {
        return alertas.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AlertaDTO alerta = alertas.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return alerta.getFechaHora() == null ? "" : dateTimeFormat.format(alerta.getFechaHora());
        case 1:
            return text(alerta.getMensaje());
        case 2:
            return alerta.getTagId() == null ? "" : alerta.getTagId();
        case 3:
            return text(alerta.getAnimalNumRegistro());
        case ACTIONS_COLUMN:
            return "";
        default:
            return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == ACTIONS_COLUMN;
    }

    public void setAlertas(List<AlertaDTO> alertas) {
        this.alertas = alertas == null ? new ArrayList<AlertaDTO>() : alertas;
        fireTableDataChanged();
    }

    public AlertaDTO getAlertaAt(int modelRow) {
        if (modelRow < 0 || modelRow >= alertas.size()) {
            return null;
        }
        return alertas.get(modelRow);
    }

    private String text(String value) {
        return value == null ? "" : value;
    }
}
