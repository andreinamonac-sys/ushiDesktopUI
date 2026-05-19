package com.andreina.ushi.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.desktop.controller.AnimalSearchController;
import com.andreina.ushi.desktop.controller.EventoSearchController;
import com.andreina.ushi.desktop.controller.GranjaSearchController;
import com.andreina.ushi.desktop.controller.MonitorizacionController;
import com.andreina.ushi.desktop.controller.UsuarioCreateController;
import com.andreina.ushi.desktop.controller.UsuarioSearchController;
import com.andreina.ushi.desktop.renderer.UserProfileIcon;
import com.andreina.ushi.desktop.view.AbstractView;
import com.andreina.ushi.desktop.view.AnimalSearchView;
import com.andreina.ushi.desktop.view.DashboardView;
import com.andreina.ushi.desktop.view.EstadisticasView;
import com.andreina.ushi.desktop.view.EventoSearchView;
import com.andreina.ushi.desktop.view.GranjaSearchView;
import com.andreina.ushi.desktop.view.LoginView;
import com.andreina.ushi.desktop.view.MonitorizacionView;
import com.andreina.ushi.desktop.view.UshiColors;
import com.andreina.ushi.desktop.view.UsuarioCreateView;
import com.andreina.ushi.desktop.view.UsuarioSearchView;
import com.andreina.ushi.model.GranjaDTO;
import com.andreina.ushi.model.UsuarioLoginDTO;
import com.andreina.ushi.service.GranjaService;
import com.andreina.ushi.service.impl.GranjaServiceImpl;
import com.formdev.flatlaf.FlatLightLaf;


public class MainWindow {

    private static final Logger logger = LogManager.getLogger(MainWindow.class.getName());

    private static MainWindow instance = null;

    private JFrame frame;
    private JPanel contentPanel;
    private JButton inicioButton;
    private JButton animalButton;
    private JButton eventoButton;
    private JButton granjaButton;
    private JButton monitorizacionButton;
    private JButton estadisticasButton;
    private JButton usuariosMenuButton;
    private JButton usuarioButton;
    private JComboBox<ComboItem<GranjaDTO>> granjaComboBox;
    private UsuarioLoginDTO currentUser;
    private String currentRole;
    private Long selectedGranjaId;
    private final GranjaService granjaService;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    new LoginView().setVisible(true);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    private MainWindow() {
        this.granjaService = new GranjaServiceImpl();
        initialize();
        postinitialize();
    }

    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    /**
     * @wbp.parser.entryPoint
     */
    private void initialize() {
        frame = new JFrame("Ushi");
        frame.setBounds(100, 100, 700, 491);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        frame.getContentPane().add(mainPanel);

        JPanel northPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        JTextArea brandText = new JTextArea();
        brandText.setEditable(false);
        brandText.setOpaque(false);
        brandText.setText("Ushi");
        southPanel.add(brandText);

        JPanel westPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.add(westPanel, BorderLayout.WEST);

        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setBackground(UshiColors.C_GRIS_NIEBLA_CLARO);
        westPanel.add(mainMenuPanel, BorderLayout.CENTER);
        GridBagLayout gblMainMenuPanel = new GridBagLayout();
        gblMainMenuPanel.columnWidths = new int[] { 0, 0 };
        gblMainMenuPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        gblMainMenuPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
        gblMainMenuPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        mainMenuPanel.setLayout(gblMainMenuPanel);

        inicioButton = new JButton("Inicio");
        GridBagConstraints gbcInicioButton = menuConstraints(0, 0, 0);
        mainMenuPanel.add(inicioButton, gbcInicioButton);

        animalButton = new JButton("Animales");
        GridBagConstraints gbcAnimalButton = menuConstraints(0, 1, 0);
        mainMenuPanel.add(animalButton, gbcAnimalButton);

        eventoButton = new JButton("Eventos");
        GridBagConstraints gbcEventoButton = menuConstraints(0, 2, 0);
        mainMenuPanel.add(eventoButton, gbcEventoButton);

        granjaButton = new JButton("Granjas");
        GridBagConstraints gbcGranjaButton = menuConstraints(0, 3, 0);
        mainMenuPanel.add(granjaButton, gbcGranjaButton);

        monitorizacionButton = new JButton("Monitorizacion");
        GridBagConstraints gbcMonitorizacionButton = menuConstraints(0, 4, 0);
        mainMenuPanel.add(monitorizacionButton, gbcMonitorizacionButton);

        estadisticasButton = new JButton("Estadisticas");
        GridBagConstraints gbcEstadisticasButton = menuConstraints(0, 5, 0);
        mainMenuPanel.add(estadisticasButton, gbcEstadisticasButton);

        usuariosMenuButton = new JButton("Usuarios");
        GridBagConstraints gbcUsuariosMenuButton = menuConstraints(0, 6, 5);
        mainMenuPanel.add(usuariosMenuButton, gbcUsuariosMenuButton);

        contentPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel leftHeaderPanel = new JPanel();
        FlowLayout leftHeaderLayout = (FlowLayout) leftHeaderPanel.getLayout();
        leftHeaderLayout.setAlignment(FlowLayout.LEFT);
        northPanel.add(leftHeaderPanel, BorderLayout.WEST);

        JButton burgerButton = new JButton("\u2630");
        burgerButton.addActionListener(e -> {
            westPanel.setVisible(!westPanel.isVisible());
            westPanel.revalidate();
            westPanel.repaint();
        });
        burgerButton.setFocusable(false);
        leftHeaderPanel.add(burgerButton);

        granjaComboBox = new JComboBox<ComboItem<GranjaDTO>>();
        granjaComboBox.setToolTipText("Seleccionar granja");
        granjaComboBox.setEnabled(false);
        granjaComboBox.addActionListener(e -> {
            ComboItem<GranjaDTO> selectedItem = getSelectedGranjaItem();
            selectedGranjaId = selectedItem == null || selectedItem.getValue() == null
                    ? null
                    : selectedItem.getValue().getId();
        });
        leftHeaderPanel.add(granjaComboBox);

        JPanel rightHeaderPanel = new JPanel();
        FlowLayout rightHeaderLayout = (FlowLayout) rightHeaderPanel.getLayout();
        rightHeaderLayout.setAlignment(FlowLayout.RIGHT);
        northPanel.add(rightHeaderPanel, BorderLayout.EAST);

        usuarioButton = new JButton(new UserProfileIcon(28));
        usuarioButton.setPreferredSize(new Dimension(42, 38));
        usuarioButton.setFocusable(false);
        usuarioButton.setBorderPainted(false);
        usuarioButton.setContentAreaFilled(false);
        usuarioButton.addActionListener(e -> {
            if (currentUser == null) {
                JOptionPane.showMessageDialog(frame, "No hay sesion iniciada.", "Usuario",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            showProfile();
        });
        rightHeaderPanel.add(usuarioButton);
    }

    private GridBagConstraints menuConstraints(int x, int y, int topInset) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(topInset, 0, 5, 0);
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    private void postinitialize() {
        inicioButton.addActionListener(e -> openDashboard());
        animalButton.addActionListener(e -> openAnimalSearch());
        eventoButton.addActionListener(e -> openEventos());
        granjaButton.addActionListener(e -> openGranjas());
        monitorizacionButton.addActionListener(e -> openMonitorizacion());
        estadisticasButton.addActionListener(e -> openEstadisticas());
        usuariosMenuButton.addActionListener(e -> openUsuarios());
        applyRolePermissions();
    }

    private void openDashboard() {
        DashboardView view = new DashboardView();
        view.setSummary(displayUser(currentUser), displayRole(currentUser), permittedViewsLabel());
        setView(view);
    }

    private void openAnimalSearch() {
        if (!canOpenAnimals()) {
            showAccessDenied();
            return;
        }
        AnimalSearchView view = new AnimalSearchView();
        new AnimalSearchController(view);
        setView(view);
    }

    private void openEventos() {
        if (!canOpenEvents()) {
            showAccessDenied();
            return;
        }
        EventoSearchView view = new EventoSearchView();
        new EventoSearchController(view);
        setView(view);
    }

    private void openGranjas() {
        if (!canOpenFarms()) {
            showAccessDenied();
            return;
        }
        GranjaSearchView view = new GranjaSearchView();
        new GranjaSearchController(view);
        setView(view);
    }

    private void openMonitorizacion() {
        if (!canOpenMonitoring()) {
            showAccessDenied();
            return;
        }
        MonitorizacionView view = new MonitorizacionView();
        new MonitorizacionController(view);
        setView(view);
    }

    private void openEstadisticas() {
        if (!canOpenStatistics()) {
            showAccessDenied();
            return;
        }
        setView(new EstadisticasView());
    }

    private void openUsuarios() {
        if (!canOpenUsers()) {
            showAccessDenied();
            return;
        }
        UsuarioSearchView view = new UsuarioSearchView();
        new UsuarioSearchController(view);
        setView(view);
    }

    private void openUsuarioCreate() {
        UsuarioCreateView view = new UsuarioCreateView();
        new UsuarioCreateController(view);
        setView(view);
    }

    public void setView(AbstractView view) {
        contentPanel.removeAll();
        contentPanel.add(view, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void remove(AbstractView view) {
        if (view == null) {
            return;
        }
        contentPanel.remove(view);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void setCurrentUser(UsuarioLoginDTO user) {
        currentUser = user;
        if (usuarioButton == null) {
            return;
        }
        if (user == null) {
            usuarioButton.setText("");
            usuarioButton.setIcon(new UserProfileIcon(28));
            usuarioButton.setToolTipText("Sin sesion");
            selectedGranjaId = null;
            loadFarmCombo(new ArrayList<GranjaDTO>());
            applyRolePermissions();
            return;
        }

        usuarioButton.setText("");
        usuarioButton.setIcon(new UserProfileIcon(28));
        usuarioButton.setToolTipText(user.getEmail() + " (" + displayRole(user) + ")");
        loadFarmCombo(resolveAvailableFarms(user));
        applyRolePermissions();
        openDashboard();
    }

    public void setCurrentRole(String role) {
        currentRole = role;
        applyRolePermissions();
    }

    public Long getSelectedGranjaId() {
        return selectedGranjaId;
    }

    public UsuarioLoginDTO getCurrentUser() {
        return currentUser;
    }

    public String getCurrentRoleName() {
        return RolePermissions.roleOf(currentUser, currentRole);
    }

    public void showWindow() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showProfile() {
        JDialog dialog = new JDialog(frame, "Perfil de usuario", true);
        JPanel profilePanel = new JPanel(new BorderLayout(0, 14));
        profilePanel.setBackground(UshiColors.C_BLANCO_SUAVE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(22, 26, 22, 26));

        JPanel identityPanel = new JPanel(new GridBagLayout());
        identityPanel.setOpaque(false);
        GridBagConstraints identityGbc = new GridBagConstraints();
        identityGbc.gridx = 0;
        identityGbc.gridy = 0;
        identityGbc.insets = new Insets(0, 0, 8, 0);
        JLabel avatarLabel = new JLabel(new UserProfileIcon(76));
        identityPanel.add(avatarLabel, identityGbc);

        identityGbc.gridy = 1;
        JLabel userLabel = new JLabel(displayUser(currentUser));
        userLabel.setFont(userLabel.getFont().deriveFont(Font.BOLD, 15f));
        userLabel.setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        identityPanel.add(userLabel, identityGbc);
        profilePanel.add(identityPanel, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Gestionar informacion", createManageUserPanel());
        tabs.addTab("Cerrar sesion", createLogoutPanel(dialog));
        profilePanel.add(tabs, BorderLayout.CENTER);

        dialog.setContentPane(profilePanel);
        dialog.setSize(430, 430);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private JPanel createManageUserPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UshiColors.C_BLANCO_SUAVE);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 8, 8, 8));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addProfileRow(panel, gbc, 0, "Usuario", displayUser(currentUser));
        addProfileRow(panel, gbc, 1, "Rol", displayRole(currentUser));
        addProfileRow(panel, gbc, 2, "Email", currentUser.getEmail());

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton changePasswordButton = new JButton("Cambiar contrasena");
        changePasswordButton.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                "El cambio de contrasena se conectara al servicio de usuarios cuando este disponible.",
                "Perfil", JOptionPane.INFORMATION_MESSAGE));
        panel.add(changePasswordButton, gbc);
        return panel;
    }

    private JPanel createLogoutPanel(JDialog dialog) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UshiColors.C_BLANCO_SUAVE);
        panel.setBorder(BorderFactory.createEmptyBorder(22, 8, 8, 8));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Cerrar la sesion actual"), gbc);

        gbc.gridy = 1;
        JButton logoutButton = new JButton("Salir");
        logoutButton.setForeground(UshiColors.C_GRIS_VERDOSO_OSCURO);
        logoutButton.setBackground(UshiColors.C_VERDE_MENTA_GRISACEO);
        logoutButton.addActionListener(e -> {
            dialog.dispose();
            logout();
        });
        panel.add(logoutButton, gbc);
        return panel;
    }

    private void addProfileRow(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label + ":"), gbc);

        gbc.gridx = 1;
        JTextField valueField = new JTextField(value == null ? "" : value, 22);
        valueField.setEditable(false);
        panel.add(valueField, gbc);
    }

    public void logout() {
        int option = JOptionPane.showConfirmDialog(frame, "Cerrar la sesion actual?", "Cerrar sesion",
                JOptionPane.YES_NO_OPTION);
        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        setCurrentUser(null);
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();

        frame.dispose();
        instance = null;
        new LoginView().setVisible(true);
    }

    private List<GranjaDTO> resolveAvailableFarms(UsuarioLoginDTO user) {
        if (user == null) {
            return new ArrayList<GranjaDTO>();
        }
        try {
            List<GranjaDTO> granjas = isAdmin(user)
                    ? granjaService.findAll()
                    : granjaService.findByEncargadoId(user.getId());
            return granjas == null ? new ArrayList<GranjaDTO>() : granjas;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(frame, "No se pudieron cargar las granjas disponibles.", "Ushi",
                    JOptionPane.ERROR_MESSAGE);
            return new ArrayList<GranjaDTO>();
        }
    }

    private boolean isAdmin(UsuarioLoginDTO user) {
        return user != null && (Long.valueOf(1L).equals(user.getRolId())
                || "ADMINISTRADOR".equalsIgnoreCase(displayRole(user)));
    }

    private void applyRolePermissions() {
        if (inicioButton == null) {
            return;
        }

        inicioButton.setEnabled(true);
        animalButton.setEnabled(canOpenAnimals());
        eventoButton.setEnabled(canOpenEvents());
        granjaButton.setEnabled(canOpenFarms());
        monitorizacionButton.setEnabled(canOpenMonitoring());
        estadisticasButton.setEnabled(canOpenStatistics());
        usuariosMenuButton.setEnabled(canOpenUsers());
    }

    private boolean canOpenAnimals() {
        return currentUser == null || RolePermissions.canOpenAnimals(getCurrentRoleName());
    }

    private boolean canOpenEvents() {
        return RolePermissions.canOpenEvents(getCurrentRoleName());
    }

    private boolean canOpenFarms() {
        return RolePermissions.canOpenFarms(getCurrentRoleName());
    }

    private boolean canOpenMonitoring() {
        return RolePermissions.canOpenMonitoring(getCurrentRoleName());
    }

    private boolean canOpenStatistics() {
        return RolePermissions.canOpenStatistics(getCurrentRoleName());
    }

    private boolean canOpenUsers() {
        return RolePermissions.canOpenUsers(getCurrentRoleName());
    }

    private String permittedViewsLabel() {
        List<String> views = new ArrayList<String>();
        if (canOpenAnimals()) {
            views.add("Animales");
        }
        if (canOpenEvents()) {
            views.add("Eventos");
        }
        if (canOpenFarms()) {
            views.add("Granjas");
        }
        if (canOpenMonitoring()) {
            views.add("Monitorizacion");
        }
        if (canOpenStatistics()) {
            views.add("Estadisticas");
        }
        if (canOpenUsers()) {
            views.add("Usuarios");
        }
        return views.isEmpty() ? "Sin vistas" : String.join(", ", views);
    }

    private String displayUser(UsuarioLoginDTO user) {
        if (user == null) {
            return "";
        }
        return user.getEmail() == null ? "Usuario" : user.getEmail();
    }

    private String displayRole(UsuarioLoginDTO user) {
        if (user != null && user.getRolNombre() != null) {
            return user.getRolNombre();
        }
        return currentRole == null ? "" : currentRole;
    }

    private void loadFarmCombo(List<GranjaDTO> granjas) {
        DefaultComboBoxModel<ComboItem<GranjaDTO>> model = new DefaultComboBoxModel<ComboItem<GranjaDTO>>();
        if (granjas != null) {
            for (GranjaDTO granja : granjas) {
                model.addElement(new ComboItem<GranjaDTO>(granja, farmLabel(granja)));
            }
        }
        granjaComboBox.setModel(model);
        granjaComboBox.setEnabled(model.getSize() > 0);
        if (model.getSize() > 0) {
            granjaComboBox.setSelectedIndex(0);
            ComboItem<GranjaDTO> firstItem = model.getElementAt(0);
            selectedGranjaId = firstItem.getValue() == null ? null : firstItem.getValue().getId();
        } else {
            granjaComboBox.setSelectedIndex(-1);
            selectedGranjaId = null;
        }
    }

    private void showAccessDenied() {
        JOptionPane.showMessageDialog(frame, "Tu rol no tiene permiso para abrir esta vista.", "Permisos",
                JOptionPane.WARNING_MESSAGE);
    }

    private String farmLabel(GranjaDTO granja) {
        if (granja == null) {
            return "";
        }
        if (granja.getNif() != null) {
            return granja.getNif();
        }
        if (granja.getCalle() != null) {
            return granja.getCalle();
        }
        return granja.getId() == null ? "Granja" : "Granja " + granja.getId();
    }

    private ComboItem<GranjaDTO> getSelectedGranjaItem() {
        @SuppressWarnings("unchecked")
        ComboItem<GranjaDTO> selectedItem = granjaComboBox.getSelectedItem() instanceof ComboItem
                ? (ComboItem<GranjaDTO>) granjaComboBox.getSelectedItem()
                : null;
        return selectedItem;
    }

    private static class ComboItem<T> {

        private final T value;
        private final String label;

        ComboItem(T value, String label) {
            this.value = value;
            this.label = label;
        }

        public T getValue() {
            return value;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private static class SimpleMessageView extends AbstractView {

        SimpleMessageView(String name, String message) {
            super(name);
            setLayout(new BorderLayout());
            add(new JLabel(message, JLabel.CENTER), BorderLayout.CENTER);
        }
    }
}
