package com.andreina.ushi.desktop.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTable;

import com.andreina.ushi.desktop.model.AlertaTableModel;
import com.andreina.ushi.desktop.renderer.AlertaActionsEditor;
import com.andreina.ushi.desktop.renderer.AlertaActionsRenderer;
import com.andreina.ushi.desktop.renderer.UshiTableCellRenderer;
import com.andreina.ushi.model.AlertaDTO;

public class DashboardView extends AbstractView {

    private JLabel userLabel;
    private JLabel roleLabel;
    private JLabel permissionsLabel;
    private JTable alertasTable;
    private AlertaTableModel alertasModel;

    public DashboardView() {
        super("Inicio");
        initComponents();
    }

    /**
     * @wbp.parser.entryPoint
     */
    private void initComponents() {
        setLayout(new BorderLayout(16, 16));
        setBackground(UshiColors.C_GRIS_NIEBLA_CLARO);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel titleLabel = new JLabel("Ushi");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(1, 3, 16, 16));
        contentPanel.setOpaque(false);
        add(contentPanel, BorderLayout.NORTH);

        userLabel = createCard("Usuario", "Sin sesion");
        roleLabel = createCard("Rol", "Sin rol");
        permissionsLabel = createCard("Vistas", "Sin permisos cargados");

        contentPanel.add(userLabel);
        contentPanel.add(roleLabel);
        contentPanel.add(permissionsLabel);

        JPanel alertasPanel = new JPanel(new BorderLayout(8, 8));
        alertasPanel.setOpaque(true);
        alertasPanel.setBackground(UshiColors.C_BLANCO_SUAVE);
        alertasPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UshiColors.C_GRIS_VERDOSO_BORDE),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));
        JLabel alertasTitle = new JLabel("Alertas");
        alertasTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        alertasTitle.setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        alertasPanel.add(alertasTitle, BorderLayout.NORTH);
        alertasModel = new AlertaTableModel();
        alertasTable = new JTable(alertasModel);
        alertasTable.setDefaultRenderer(Object.class, new UshiTableCellRenderer());
        alertasTable.setRowHeight(28);
        alertasTable.getTableHeader().setBackground(UshiColors.C_VERDE_MENTA_LECHE);
        alertasTable.getTableHeader().setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        alertasTable.getColumnModel().getColumn(AlertaTableModel.ACTIONS_COLUMN)
                .setCellRenderer(new AlertaActionsRenderer());
        alertasTable.getColumnModel().getColumn(AlertaTableModel.ACTIONS_COLUMN).setPreferredWidth(95);
        alertasPanel.add(new JScrollPane(alertasTable), BorderLayout.CENTER);
        add(alertasPanel, BorderLayout.CENTER);
    }

    public void setSummary(String user, String role, String permissions) {
        userLabel.setText(toHtml("Usuario", user));
        roleLabel.setText(toHtml("Rol", role));
        permissionsLabel.setText(toHtml("Vistas", permissions));
    }

    public void setAlertas(List<AlertaDTO> alertas) {
        alertasModel.setAlertas(alertas);
    }

    public JTable getAlertasTable() {
        return alertasTable;
    }

    public AlertaDTO getAlertaAt(int modelRow) {
        return alertasModel.getAlertaAt(modelRow);
    }

    public void setAlertaActionsHandler(AlertaActionsEditor.AlertaActionHandler handler) {
        alertasTable.getColumnModel().getColumn(AlertaTableModel.ACTIONS_COLUMN)
                .setCellEditor(new AlertaActionsEditor(handler));
    }

    private JLabel createCard(String title, String value) {
        JLabel label = new JLabel(toHtml(title, value), SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(UshiColors.C_BLANCO_SUAVE);
        label.setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UshiColors.C_GRIS_VERDOSO_BORDE),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        return label;
    }

    private String toHtml(String title, String value) {
        return "<html><div style='text-align:center'><b>" + title + "</b><br><br>" + safe(value) + "</div></html>";
    }

    private String safe(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }
}
