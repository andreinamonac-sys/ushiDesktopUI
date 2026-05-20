package com.andreina.ushi.desktop.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.andreina.ushi.model.UsuarioDTO;

public class UsuarioTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = { "ID", "DNI/NIE", "Nombre", "Email", "Telefono", "Rol", "Acciones" };
    public static final int ACTIONS_COLUMN = 6;
    private List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();

    @Override
    public int getRowCount() {
        return usuarios.size();
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
        UsuarioDTO usuario = usuarios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return usuario.getId();
            case 1:
                return text(usuario.getDniNie());
            case 2:
                return fullName(usuario);
            case 3:
                return text(usuario.getEmail());
            case 4:
                return usuario.getTelefono() == null ? "" : usuario.getTelefono();
            case 5:
                return text(usuario.getNombreRol());
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

    public UsuarioDTO getUsuarioAt(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= usuarios.size()) {
            return null;
        }
        return usuarios.get(rowIndex);
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios == null ? new ArrayList<UsuarioDTO>() : usuarios;
        fireTableDataChanged();
    }

    private String fullName(UsuarioDTO usuario) {
        return (text(usuario.getNombre()) + " " + text(usuario.getApellido1()) + " " + text(usuario.getApellido2())).trim();
    }

    private String text(String value) {
        return value == null ? "" : value;
    }
}
