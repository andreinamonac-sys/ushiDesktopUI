package com.andreina.ushi.desktop.view;

import com.andreina.ushi.desktop.MainWindow;
import com.andreina.ushi.model.UsuarioLoginDTO;
import com.andreina.ushi.service.AuthenticationService;
import com.andreina.ushi.service.impl.AuthenticationServiceImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * LoginView: Pantalla de inicio de sesión para la aplicación Ushi.
 * 
 * Características:
 * - Diseño moderno con paleta de colores suave y tipografía clara.
 * - Campos para email, contraseña y selección de rol.
 * - Validación básica de campos vacíos.
 * - Feedback visual para errores y estado de autenticación.
 * - Carga de logo desde recursos o placeholder dibujado.
 * 
 * El controlador inyecta un callback para manejar la lógica de autenticación
 * sin acoplar esta clase a detalles específicos del backend o servicios.
 */
public class LoginView extends JFrame {

    // ── Paleta ──────────────────────────────────────────────────────────────
	private static final Color C_GRIS_NIEBLA_CLARO     = new Color(0xF7, 0xF8, 0xF6); // Fondo general de la app
	private static final Color C_BLANCO_SUAVE          = new Color(0xFF, 0xFF, 0xFF); // Cards, paneles y formularios
	private static final Color C_VERDE_MENTA_GRISACEO  = new Color(0xBE, 0xDE, 0xC9); // Botones principales y selección
	private static final Color C_VERDE_MENTA_LECHE     = new Color(0xC7, 0xE8, 0xCD); // Secciones suaves, chips y badges
	private static final Color C_GRIS_VERDOSO_OSCURO   = new Color(0x2F, 0x3A, 0x33); // Títulos, texto principal e iconos
	private static final Color C_GRIS_VERDOSO_MEDIO    = new Color(0x6B, 0x76, 0x6E); // Subtítulos, descripciones y placeholders
	private static final Color C_GRIS_VERDOSO_BORDE    = new Color(0xDD, 0xE5, 0xDD); // Bordes, separadores y contornos
    // ── Fuentes ─────────────────────────────────────────────────────────────
    private static final Font FONT_TITLE  = new Font("Georgia", Font.BOLD, 28);
    private static final Font FONT_SUB    = new Font("Georgia", Font.ITALIC, 13);
    private static final Font FONT_LABEL  = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_FIELD  = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BTN    = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);

    // ── Componentes ─────────────────────────────────────────────────────────
    private JTextField     txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRol;
    private JButton        btnLogin;
    private JLabel         lblError;
    private JCheckBox      chkMostrar;

    // ── Imagen logo ─────────────────────────────────────────────────────────
    private Image logoImage;

    // ── Callback de autenticación (inyectado por el controlador) ────────────
    private LoginCallback callback;
    private AuthenticationService authenticationService;
    private UsuarioLoginDTO authenticatedUser;

    public interface LoginCallback {
        /** Devuelve true si las credenciales son válidas. */
        boolean autenticar(String email, char[] password, String rol);
    }

    
    public LoginView() {
        this(null);
    }

    public LoginView(LoginCallback callback) {
        this.callback = callback;
        this.authenticationService = new AuthenticationServiceImpl();
        aplicarLookAndFeel();
        initUI();
    }

    
    private void aplicarLookAndFeel() {
        try {
            // Propiedades FlatLaf
            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.width", 8);
            UIManager.put("defaultFont", FONT_FIELD);
            Class<?> flatClass = Class.forName("com.formdev.flatlaf.FlatLightLaf");
            UIManager.setLookAndFeel((LookAndFeel) flatClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception ex) {
                // default
            }
        }
    }

    
    private void initUI() {
        setTitle("Ushi — Monitorización en tiempo real");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cargarLogo();

        // Panel raíz con dos mitades
        JPanel root = new JPanel(new GridLayout(1, 2, 0, 0));
        root.setPreferredSize(new Dimension(860, 540));
        root.add(crearPanelIzquierdo());
        root.add(crearPanelDerecho());

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);

        // Acción Enter en password
        txtPassword.addActionListener(e -> intentarLogin());
        txtEmail.addActionListener(e -> txtPassword.requestFocusInWindow());
    }

    // ── Panel izquierdo (verde menta + logo) ───────────────────────────────
    private JPanel crearPanelIzquierdo() {
        // Wrapper con paintComponent para el gradiente
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, C_VERDE_MENTA_GRISACEO,
                        0, getHeight(), C_VERDE_MENTA_LECHE);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(C_VERDE_MENTA_LECHE);
                for (int y = 0; y < getHeight(); y += 22)
                    for (int x = 0; x < getWidth(); x += 22)
                        g2.fillOval(x, y, 3, 3);
                g2.dispose();
            }
        };
        panel.setOpaque(true);

        // Columna central con BoxLayout — sin GBC reutilizado
        JPanel col = new JPanel();
        col.setOpaque(false);
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBorder(new EmptyBorder(40, 20, 40, 20));

        // ── Logo ──
        if (logoImage != null) {
            Image scaled = logoImage.getScaledInstance(260, 220, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel();
            logoLabel.setOpaque(false);
            logoLabel.setIcon(new ImageIcon(scaled));
            logoLabel.setPreferredSize(new Dimension(260, 220));
            logoLabel.setMaximumSize(new Dimension(260, 220));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            col.add(logoLabel);
            col.add(Box.createRigidArea(new Dimension(0, 16)));
        }

        // ── Título ──
        JLabel lblApp = new JLabel("Ushi");
        lblApp.setFont(new Font("Georgia", Font.BOLD, 42));
        lblApp.setForeground(C_GRIS_VERDOSO_OSCURO);
        lblApp.setAlignmentX(Component.CENTER_ALIGNMENT);
        col.add(lblApp);

        col.add(Box.createRigidArea(new Dimension(0, 6)));

        // ── Subtítulo ──
        JLabel lblSub = new JLabel("Monitorización en tiempo real");
        lblSub.setFont(FONT_SUB);
        lblSub.setForeground(C_GRIS_VERDOSO_MEDIO);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        col.add(lblSub);

        col.add(Box.createRigidArea(new Dimension(0, 18)));

        // ── Separador decorativo ──
        JSeparator sep = new JSeparator();
        sep.setForeground(C_GRIS_VERDOSO_BORDE);
        sep.setMaximumSize(new Dimension(200, 1));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);
        col.add(sep);

        col.add(Box.createRigidArea(new Dimension(0, 18)));

        // Centrar verticalmente
        panel.add(col, BorderLayout.CENTER);
        return panel;
    }

    // ── Panel derecho (formulario login) ────────────────────────────────────
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(C_GRIS_NIEBLA_CLARO);
        panel.setBorder(new EmptyBorder(40, 50, 40, 50));

        int row = 0;

        // ── Título sección ──
        JLabel lblTitulo = new JLabel("Iniciar sesión");
        lblTitulo.setFont(FONT_TITLE);
        lblTitulo.setForeground(C_GRIS_VERDOSO_OSCURO);
        panel.add(lblTitulo, createConstraints(row++, 1.0, new Insets(0, 0, 4, 0)));

        JLabel lblBienvenida = new JLabel("Accede con tus credenciales");
        lblBienvenida.setFont(FONT_SUB);
        lblBienvenida.setForeground(C_GRIS_VERDOSO_MEDIO);
        panel.add(lblBienvenida, createConstraints(row++, 1.0, new Insets(0, 0, 24, 0)));

        // ── Campo Email ──
        panel.add(crearLabel("Correo electrónico"), createConstraints(row++, 1.0, new Insets(6, 0, 2, 0)));

        txtEmail = new JTextField();
        txtEmail.setFont(FONT_FIELD);
        txtEmail.setBackground(C_BLANCO_SUAVE);
        txtEmail.setBorder(crearBordeCampo());
        txtEmail.setPreferredSize(new Dimension(0, 42));
        txtEmail.putClientProperty("JTextField.placeholderText", "usuario@ushi.com");
        panel.add(txtEmail, createConstraints(row++, 1.0, new Insets(0, 0, 10, 0)));

        // ── Campo Contraseña ──
        panel.add(crearLabel("Contraseña"), createConstraints(row++, 1.0, new Insets(6, 0, 2, 0)));

        txtPassword = new JPasswordField();
        txtPassword.setFont(FONT_FIELD);
        txtPassword.setBackground(C_BLANCO_SUAVE);
        txtPassword.setBorder(crearBordeCampo());
        txtPassword.setPreferredSize(new Dimension(0, 42));
        txtPassword.putClientProperty("JTextField.placeholderText", "••••••••");
        panel.add(txtPassword, createConstraints(row++, 1.0, new Insets(0, 0, 2, 0)));

        // ── Mostrar contraseña ──
        chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.setFont(FONT_SMALL);
        chkMostrar.setForeground(C_GRIS_VERDOSO_MEDIO);
        chkMostrar.setBackground(C_GRIS_NIEBLA_CLARO);
        chkMostrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chkMostrar.addActionListener(e ->
                txtPassword.setEchoChar(chkMostrar.isSelected() ? (char) 0 : '•'));
        panel.add(chkMostrar, createConstraints(row++, 1.0, new Insets(0, 0, 10, 0)));

        // ── Selector de Rol ──
        panel.add(crearLabel("Rol de usuario"), createConstraints(row++, 1.0, new Insets(6, 0, 2, 0)));

        String[] roles = {"Administrador", "Encargado", "Operario", "Veterinario"};
        cmbRol = new JComboBox<>(roles);
        cmbRol.setFont(FONT_FIELD);
        cmbRol.setBackground(C_BLANCO_SUAVE);
        cmbRol.setPreferredSize(new Dimension(0, 42));
        estilizarCombo(cmbRol);
        panel.add(cmbRol, createConstraints(row++, 1.0, new Insets(0, 0, 20, 0)));

        // ── Mensaje de error ──
        lblError = new JLabel(" ");
        lblError.setFont(FONT_SMALL);
        lblError.setForeground(C_GRIS_VERDOSO_MEDIO);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblError, createConstraints(row++, 1.0, new Insets(0, 0, 6, 0)));

        // ── Botón login ──
        btnLogin = crearBotonLogin();
        panel.add(btnLogin, createConstraints(row++, 1.0, new Insets(0, 0, 20, 0)));

        // ── Footer (anclado al sur) ──
        
        JLabel lblFooter = new JLabel("Ushi © 2026");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblFooter.setForeground(C_GRIS_VERDOSO_MEDIO);
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcFooter = createConstraints(row, 0.0, new Insets(10, 0, 0, 0));
        gbcFooter.weighty = 1.0;
        gbcFooter.anchor  = GridBagConstraints.SOUTH;
        panel.add(lblFooter, gbcFooter);

        return panel;
    }

    /**
     * Helper: crea un GridBagConstraints nuevo cada vez para evitar
     * el warning "component added to parent more than once".
     */
    private GridBagConstraints createConstraints(int gridy, double weightx, Insets insets) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = weightx;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = insets;
        return constraints;
    }

    // ════════════════════════════════════════════════════════════════════════
    // Helpers de UI
    // ════════════════════════════════════════════════════════════════════════
    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(C_GRIS_VERDOSO_MEDIO);
        return lbl;
    }

    private Border crearBordeCampo() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_GRIS_VERDOSO_BORDE, 1, true),
                BorderFactory.createEmptyBorder(5, 12, 5, 12));
    }

    private void estilizarCombo(JComboBox<?> combo) {
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_GRIS_VERDOSO_BORDE, 1, true),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFont(FONT_FIELD);
                if (isSelected) {
                    setBackground(C_VERDE_MENTA_GRISACEO);
                    setForeground(C_GRIS_VERDOSO_OSCURO);
                } else {
                    setBackground(C_BLANCO_SUAVE);
                    setForeground(C_GRIS_VERDOSO_OSCURO);
                }
                setBorder(new EmptyBorder(6, 12, 6, 12));
                return this;
            }
        });
    }

    private JButton crearBotonLogin() {
        JButton btn = new JButton("Entrar") {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hover = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = hover ? C_VERDE_MENTA_LECHE : C_VERDE_MENTA_GRISACEO;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(C_GRIS_VERDOSO_OSCURO);
        btn.setPreferredSize(new Dimension(0, 46));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> intentarLogin());
        return btn;
    }

    // ════════════════════════════════════════════════════════════════════════
    // Lógica de autenticación
    // ════════════════════════════════════════════════════════════════════════
    private void intentarLogin() {
        String email    = txtEmail.getText().trim();
        char[] password = txtPassword.getPassword();
        String rol      = (String) cmbRol.getSelectedItem();

        // Validación básica de campos vacíos
        if (email.isEmpty()) {
            mostrarError("Introduce tu correo electrónico.");
            txtEmail.requestFocusInWindow();
            return;
        }
        if (password.length == 0) {
            mostrarError("Introduce tu contraseña.");
            txtPassword.requestFocusInWindow();
            return;
        }

        btnLogin.setEnabled(false);
        lblError.setText("Verificando credenciales...");
        lblError.setForeground(C_GRIS_VERDOSO_MEDIO);

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                if (callback != null) {
                    return callback.autenticar(email, password, rol);
                }
                // Sin callback: simulación (quitar en producción)
                authenticatedUser = authenticationService.login(email, new String(password));
                return roleMatches(authenticatedUser, rol);
            }

            @Override
            protected void done() {
                try {
                    boolean ok = get();
                    if (ok) {
                        lblError.setText("✓ Acceso correcto");
                        lblError.setForeground(C_VERDE_MENTA_GRISACEO);
                        Timer t = new Timer(600, ev -> {
                            abrirMainFrame(email, rol);
                        });
                        t.setRepeats(false);
                        t.start();
                    } else {
                        mostrarError("Credenciales incorrectas, usuario inactivo o rol no coincidente.");
                        txtPassword.setText("");
                        btnLogin.setEnabled(true);
                    }
                } catch (Exception ex) {
                    mostrarError("Error de conexión. Inténtalo de nuevo.");
                    btnLogin.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private void mostrarError(String msg) {
        lblError.setForeground(C_GRIS_VERDOSO_OSCURO);
        lblError.setText(msg);
        // Animación de shake
        Point orig = getLocation();
        Timer shake = new Timer(30, null);
        final int[] count = {0};
        final int[] dir   = {1};
        shake.addActionListener(e -> {
            setLocation(orig.x + dir[0] * 5, orig.y);
            dir[0] = -dir[0];
            if (++count[0] >= 8) {
                shake.stop();
                setLocation(orig);
            }
        });
        shake.start();
    }

    private boolean roleMatches(UsuarioLoginDTO user, String selectedRole) {
        if (user == null || user.getRolNombre() == null || selectedRole == null) {
            return false;
        }
        return normalizeRole(user.getRolNombre()).equals(normalizeRole(selectedRole));
    }

    private String normalizeRole(String role) {
        return role == null ? "" : role.trim().toUpperCase();
    }

    private void abrirMainFrame(String email, String rol) {
        dispose();
        SwingUtilities.invokeLater(() -> {
            MainWindow main = MainWindow.getInstance();
            main.setCurrentUser(authenticatedUser);
            main.setCurrentRole(authenticatedUser == null ? rol : authenticatedUser.getRolNombre());
            main.showWindow();
        });
    }

    // ════════════════════════════════════════════════════════════════════════
    // Carga de logo
    // ════════════════════════════════════════════════════════════════════════
    private void cargarLogo() {
        // 1) Desde recursos del proyecto actual
        try {
            File f = new File("resources/LogoUI_lineart_transparente_recortado.png");
            if (f.exists()) { logoImage = ImageIO.read(f); return; }
        } catch (IOException ignored) {}

        // 2) Desde ruta conocida del proyecto
        try {
            File f = new File("../UsuarioInterface/LogoUI_lineart_transparente_recortado.png");
            if (f.exists()) { logoImage = ImageIO.read(f); return; }
        } catch (IOException ignored) {}

        // 3) Desde la carpeta padre si se ejecuta desde ProyectoCodex
        try {
            File f = new File("UsuarioInterface/LogoUI_lineart_transparente_recortado.png");
            if (f.exists()) { logoImage = ImageIO.read(f); return; }
        } catch (IOException ignored) {}

        // 4) Desde classpath
        try {
            URL logoUrl = getClass().getResource("/LogoUI_lineart_transparente_recortado.png");
            if (logoUrl != null) { logoImage = ImageIO.read(logoUrl); return; }
        } catch (IOException ignored) {}

        // 5) Placeholder dibujado si el recurso no está disponible
        logoImage = crearLogoPlaceholder();
    }

    /** Dibuja un ícono de vaca sencillo si el recurso no está disponible. */
    private Image crearLogoPlaceholder() {
        BufferedImage img = new BufferedImage(180, 180, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(C_VERDE_MENTA_LECHE);
        g.fillOval(0, 0, 180, 180);
        g.setColor(C_GRIS_VERDOSO_OSCURO);
        g.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 90));
        FontMetrics fm = g.getFontMetrics();
        String emoji = "🐄";
        int x = (180 - fm.stringWidth(emoji)) / 2;
        int y = (180 + fm.getAscent()) / 2 - 10;
        g.drawString(emoji, x, y);
        g.dispose();
        return img;
    }

    // ════════════════════════════════════════════════════════════════════════
    // Main de prueba
    // ════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView((email, pwd, rol) -> {
                // Aquí llamarás a tu UsuarioService.autenticar(email, pwd, rol)
                return true; // aceptar todo en prueba
            });
            login.setVisible(true);
        });
    }
}
