package com.andreina.ushi.desktop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.FlowLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.andreina.ushi.desktop.controller.AnimalSearchController;
import com.andreina.ushi.desktop.controller.UsuarioCreateController;
import com.andreina.ushi.desktop.view.AnimalSearchView;
import com.andreina.ushi.desktop.view.UsuarioCreateView;
import javax.swing.JTabbedPane;

public class MainWindow {

    private JFrame frame;
    private JPanel centerPanel;
    private AnimalSearchView animalSearchView;
    private AnimalSearchController animalSearchController;
    private UsuarioCreateView usuarioCreateView;
    private UsuarioCreateController usuarioCreateController;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow window = new MainWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BorderLayout(0, 0));

        JPanel northPanel = new JPanel();
        mainPanel.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new BorderLayout(0, 0));

        JPanel menuPanel = new JPanel();
        northPanel.add(menuPanel, BorderLayout.CENTER);
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JMenuBar menuBar = new JMenuBar();
        menuPanel.add(menuBar);

        JMenu animalMenu = new JMenu("Animal");
        menuBar.add(animalMenu);

        JMenuItem buscarAnimalMenuItem = new JMenuItem("Buscar");
        buscarAnimalMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarBuscadorAnimales();
            }
        });
        animalMenu.add(buscarAnimalMenuItem);

        JMenuItem nuevoAnimalMenuItem = new JMenuItem("Nuevo");
        animalMenu.add(nuevoAnimalMenuItem);

        JMenu usuarioMenu = new JMenu("Usuario");
        menuBar.add(usuarioMenu);

        JMenuItem buscarUsuarioMenuItem = new JMenuItem("Buscar");
        usuarioMenu.add(buscarUsuarioMenuItem);

        JMenuItem nuevoUsuarioMenuItem = new JMenuItem("Nuevo");
        nuevoUsuarioMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCrearUsuario();
            }
        });
        usuarioMenu.add(nuevoUsuarioMenuItem);

        JPanel toolBarPanel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) toolBarPanel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        northPanel.add(toolBarPanel, BorderLayout.SOUTH);

        JToolBar animalToolBar = new JToolBar();
        toolBarPanel.add(animalToolBar);

        JButton buscarAnimalButton = new JButton("");
        buscarAnimalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarBuscadorAnimales();
            }
        });
        buscarAnimalButton.setIcon(new ImageIcon(MainWindow.class.getResource("/nuvola/32x32/1877_viewmag_viewmag.png")));
        animalToolBar.add(buscarAnimalButton);

        centerPanel = new JPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BorderLayout(0, 0));
        
        JTabbedPane contentTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        centerPanel.add(contentTabbedPane, BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        southPanel.setLayout(new BorderLayout(0, 0));

        JPanel westPanel = new JPanel();
        mainPanel.add(westPanel, BorderLayout.WEST);
        westPanel.setLayout(new BorderLayout(0, 0));

        JPanel eastPanel = new JPanel();
        mainPanel.add(eastPanel, BorderLayout.EAST);
        eastPanel.setLayout(new BorderLayout(0, 0));
    }

    private void mostrarBuscadorAnimales() {
        if (animalSearchView == null) {
            animalSearchView = new AnimalSearchView();
            animalSearchController = new AnimalSearchController(animalSearchView);
        }
        centerPanel.removeAll();
        centerPanel.add(animalSearchView, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void mostrarCrearUsuario() {
        if (usuarioCreateView == null) {
            usuarioCreateView = new UsuarioCreateView();
            usuarioCreateController = new UsuarioCreateController(usuarioCreateView);
        }
        centerPanel.removeAll();
        centerPanel.add(usuarioCreateView, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }
}
