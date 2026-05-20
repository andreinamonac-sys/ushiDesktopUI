package com.andreina.ushi.desktop.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.andreina.ushi.model.Tag;

public class TagTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = { "ID", "Numero", "Tipo", "Version", "Ultima actualizacion",
            "Incidencias", "Acciones" };
    public static final int ACTIONS_COLUMN = 6;
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private List<Tag> tags = new ArrayList<Tag>();

    @Override
    public int getRowCount() {
        return tags.size();
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
        Tag tag = tags.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tag.getId();
            case 1:
                return text(tag.getNumero());
            case 2:
                return text(tag.getTipo());
            case 3:
                return text(tag.getVersionSoftware());
            case 4:
                return tag.getUltimaActualizacion() == null ? "" : dateFormat.format(tag.getUltimaActualizacion());
            case 5:
                return text(tag.getIncidencias());
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

    public Tag getTagAt(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= tags.size()) {
            return null;
        }
        return tags.get(rowIndex);
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags == null ? new ArrayList<Tag>() : tags;
        fireTableDataChanged();
    }

    private String text(String value) {
        return value == null ? "" : value;
    }
}
