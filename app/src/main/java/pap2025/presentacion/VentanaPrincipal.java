package pap2025.presentacion;

import pap2025.fabrica.Fabrica;
import pap2025.interfaz.IControladorFachada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    
    private JDesktopPane desktopPane;
    private JMenuBar menuBar;
    private IControladorFachada controladorFachada;
    
    public VentanaPrincipal() {
        // Obtener instancias de la fábrica
        this.controladorFachada = Fabrica.getInstancia().getIControladorFachada();
        
        // Configuración de la ventana principal
        setTitle("🦸‍♂️ Biblioteca Comunitaria Marvel - Lectores.uy 🦸‍♀️");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Aplicar tema Marvel
        getContentPane().setBackground(new Color(15, 15, 35)); // Azul oscuro Marvel
        
        // Inicializar componentes
        initComponents();
        initLayout();
        initMenu();
        initEvents();
        
        // Hacer visible
        setVisible(true);
        
        // Mostrar mensaje de bienvenida Marvel
        mostrarBienvenidaMarvel();
    }
    
    private void initComponents() {
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(15, 15, 35)); // Azul oscuro Marvel
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(25, 25, 50)); // Azul más claro para el menú
        menuBar.setForeground(new Color(255, 255, 255)); // Texto blanco
    }
    
    private void initLayout() {
        setLayout(new BorderLayout());
        add(desktopPane, BorderLayout.CENTER);
        setJMenuBar(menuBar);
    }
    
    private void initMenu() {
        // Menú Usuarios
        JMenu menuUsuarios = new JMenu("👥 Usuarios");
        menuUsuarios.setForeground(new Color(255, 255, 255)); // Texto blanco
        
        JMenuItem menuItemRegistrarLector = new JMenuItem("Registrar Nuevo Lector");
        menuItemRegistrarLector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistrarLector();
            }
        });
        
        JMenuItem menuItemModificarEstado = new JMenuItem("Modificar Estado de Lector");
        menuItemModificarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirModificarEstadoLector();
            }
        });
        
        JMenuItem menuItemCambiarZona = new JMenuItem("Cambiar Zona de Lector");
        menuItemCambiarZona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCambiarZonaLector();
            }
        });
        
        JMenuItem menuItemRegistrarBibliotecario = new JMenuItem("Registrar Nuevo Bibliotecario");
        menuItemRegistrarBibliotecario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistrarBibliotecario();
            }
        });
        
        menuUsuarios.add(menuItemRegistrarLector);
        menuUsuarios.addSeparator();
        menuUsuarios.add(menuItemModificarEstado);
        menuUsuarios.add(menuItemCambiarZona);
        menuUsuarios.addSeparator();
        menuUsuarios.add(menuItemRegistrarBibliotecario);
        
        // Menú Materiales
        JMenu menuMateriales = new JMenu("📚 Materiales");
        menuMateriales.setForeground(new Color(255, 255, 255)); // Texto blanco
        
        JMenuItem menuItemRegistrarDonacion = new JMenuItem("Registrar Nueva Donación");
        menuItemRegistrarDonacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistrarDonacion();
            }
        });
        
        JMenuItem menuItemConsultarDonaciones = new JMenuItem("Consultar Todas las Donaciones");
        menuItemConsultarDonaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirConsultarDonaciones();
            }
        });
        
        menuMateriales.add(menuItemRegistrarDonacion);
        menuMateriales.addSeparator();
        menuMateriales.add(menuItemConsultarDonaciones);
        
        // Menú Préstamos
        JMenu menuPrestamos = new JMenu("📋 Préstamos");
        menuPrestamos.setForeground(new Color(255, 255, 255)); // Texto blanco
        
        JMenuItem menuItemGestionarPrestamos = new JMenuItem("Gestionar Préstamos");
        menuItemGestionarPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestionarPrestamos();
            }
        });
        
        JMenuItem menuItemActualizarEstadoPrestamo = new JMenuItem("Actualizar Estado de Préstamo");
        menuItemActualizarEstadoPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirActualizarEstadoPrestamo();
            }
        });
        
        JMenuItem menuItemEditarInformacionPrestamo = new JMenuItem("Editar Información de Préstamo");
        menuItemEditarInformacionPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirEditarInformacionPrestamo();
            }
        });
        
        JMenuItem menuItemListarPrestamosPorLector = new JMenuItem("Listar Préstamos por Lector");
        menuItemListarPrestamosPorLector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirListarPrestamosPorLector();
            }
        });
        
        menuPrestamos.add(menuItemGestionarPrestamos);
        menuPrestamos.addSeparator();
        menuPrestamos.add(menuItemActualizarEstadoPrestamo);
        menuPrestamos.add(menuItemEditarInformacionPrestamo);
        menuPrestamos.add(menuItemListarPrestamosPorLector);
        menuPrestamos.addSeparator();
        
        JMenuItem menuItemPanelAdmin = new JMenuItem("👑 Panel de Administrador");
        menuItemPanelAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirPanelAdministrador();
            }
        });
        menuPrestamos.add(menuItemPanelAdmin);
        
        // Menú Herramientas
        JMenu menuHerramientas = new JMenu("🔧 Herramientas");
        menuHerramientas.setForeground(new Color(255, 255, 255)); // Texto blanco
        
        JMenuItem menuItemCrearDatosPrueba = new JMenuItem("🧪 Crear Datos de Prueba");
        menuItemCrearDatosPrueba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearDatosPrueba();
            }
        });
        
        menuHerramientas.add(menuItemCrearDatosPrueba);
        
        // Agregar menús a la barra
        menuBar.add(menuUsuarios);
        menuBar.add(menuMateriales);
        menuBar.add(menuPrestamos);
        menuBar.add(menuHerramientas);
    }
    
    private void initEvents() {
        // Los eventos ahora se manejan directamente en los menús
    }
    
    private void abrirRegistrarLector() {
        // Verificar si ya hay una ventana abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof RegistrarLector) {
                frame.toFront();
                return;
            }
        }
        
        // Crear nueva ventana de registro
        RegistrarLector registrarLector = new RegistrarLector(controladorFachada);
        desktopPane.add(registrarLector);
        registrarLector.setVisible(true);
        
        // Centrar en el desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = registrarLector.getSize();
        registrarLector.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
    }
    
    private void abrirModificarEstadoLector() {
        // Verificar si ya hay una ventana abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof ModificarEstadoLector) {
                frame.toFront();
                return;
            }
        }
        
        // Crear nueva ventana de modificación de estado
        ModificarEstadoLector modificarEstadoLector = new ModificarEstadoLector(controladorFachada);
        desktopPane.add(modificarEstadoLector);
        modificarEstadoLector.setVisible(true);
        
        // Centrar en el desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = modificarEstadoLector.getSize();
        modificarEstadoLector.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
    }
    
    private void abrirCambiarZonaLector() {
        // Verificar si ya hay una ventana abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof CambiarZonaLector) {
                frame.toFront();
                return;
            }
        }
        
        // Crear nueva ventana de cambio de zona
        CambiarZonaLector cambiarZonaLector = new CambiarZonaLector(controladorFachada);
        desktopPane.add(cambiarZonaLector);
        cambiarZonaLector.setVisible(true);
        
        // Centrar en el desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = cambiarZonaLector.getSize();
        cambiarZonaLector.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
    }
    
    private void abrirRegistrarBibliotecario() {
        // Verificar si ya hay una ventana abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof RegistrarBibliotecario) {
                frame.toFront();
                return;
            }
        }
        
        // Crear nueva ventana de registro de bibliotecario
        RegistrarBibliotecario registrarBibliotecario = new RegistrarBibliotecario(controladorFachada);
        desktopPane.add(registrarBibliotecario);
        registrarBibliotecario.setVisible(true);
        
        // Centrar en el desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = registrarBibliotecario.getSize();
        registrarBibliotecario.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
    }
    
    private void abrirRegistrarDonacion() {
        // Verificar si ya hay una ventana abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof RegistrarDonacion) {
                frame.toFront();
                return;
            }
        }
        
        // Crear nueva ventana de registro de donación
        RegistrarDonacion registrarDonacion = new RegistrarDonacion(controladorFachada);
        desktopPane.add(registrarDonacion);
        registrarDonacion.setVisible(true);
        
        // Centrar en el desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = registrarDonacion.getSize();
        registrarDonacion.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
    }
    
    private void abrirConsultarDonaciones() {
        // Verificar si ya hay una ventana abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof ConsultarDonaciones) {
                frame.toFront();
                return;
            }
        }
        
        // Crear nueva ventana de consulta de donaciones
        ConsultarDonaciones consultarDonaciones = new ConsultarDonaciones(controladorFachada);
        desktopPane.add(consultarDonaciones);
        consultarDonaciones.setVisible(true);
        
        // Centrar en el desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = consultarDonaciones.getSize();
        consultarDonaciones.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
    }
    
    private void abrirGestionarPrestamos() {
        // Verificar si ya hay una ventana abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof GestionarPrestamos) {
                frame.toFront();
                return;
            }
        }
        
        // Crear nueva ventana de gestión de préstamos
        GestionarPrestamos gestionarPrestamos = new GestionarPrestamos(controladorFachada);
        desktopPane.add(gestionarPrestamos);
        gestionarPrestamos.setVisible(true);
        
        // Centrar en el desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = gestionarPrestamos.getSize();
        gestionarPrestamos.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
    }
    
    private void abrirActualizarEstadoPrestamo() {
        // Verificar si ya hay una ventana abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof ActualizarEstadoPrestamo) {
                frame.toFront();
                return;
            }
        }
        
        // Crear nueva ventana de actualización de estado
        ActualizarEstadoPrestamo actualizarEstadoPrestamo = new ActualizarEstadoPrestamo(controladorFachada);
        desktopPane.add(actualizarEstadoPrestamo);
        actualizarEstadoPrestamo.setVisible(true);
        
        // Centrar en el desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = actualizarEstadoPrestamo.getSize();
        actualizarEstadoPrestamo.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
    }
    
    private void abrirEditarInformacionPrestamo() {
        // Verificar si ya hay una ventana abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof EditarInformacionPrestamo) {
                frame.toFront();
                return;
            }
        }
        
        // Crear nueva ventana de edición de información de préstamo
        EditarInformacionPrestamo editarInformacionPrestamo = new EditarInformacionPrestamo(controladorFachada);
        desktopPane.add(editarInformacionPrestamo);
        editarInformacionPrestamo.setVisible(true);
        
        // Centrar en el desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = editarInformacionPrestamo.getSize();
        editarInformacionPrestamo.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
    }
    
    private void abrirListarPrestamosPorLector() {
        // Verificar si ya hay una ventana abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof ListarPrestamosPorLector) {
                frame.toFront();
                return;
            }
        }
        
        // Crear nueva ventana de listado de préstamos por lector
        ListarPrestamosPorLector listarPrestamosPorLector = new ListarPrestamosPorLector(controladorFachada);
        desktopPane.add(listarPrestamosPorLector);
        listarPrestamosPorLector.setVisible(true);
        
        // Centrar en el desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = listarPrestamosPorLector.getSize();
        listarPrestamosPorLector.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
    }
    
    private void abrirPanelAdministrador() {
        // Verificar si ya hay una ventana abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof PanelAdministrador) {
                frame.toFront();
                return;
            }
        }
        
        // Crear nueva ventana del panel de administrador
        PanelAdministrador panelAdmin = new PanelAdministrador(controladorFachada);
        desktopPane.add(panelAdmin);
        panelAdmin.setVisible(true);
        
        // Centrar en el desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = panelAdmin.getSize();
        panelAdmin.setLocation(
            (desktopSize.width - frameSize.width) / 2,
            (desktopSize.height - frameSize.height) / 2
        );
    }
    
    public static void main(String[] args) {
        // Configurar look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crear y mostrar la ventana principal
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal();
            }
        });
    }
    
    // ===== MÉTODO DE BIENVENIDA MARVEL =====
    
    private void mostrarBienvenidaMarvel() {
        // Crear un diálogo personalizado con tema Marvel
        JDialog bienvenidaDialog = new JDialog(this, "¡Bienvenido!", true);
        bienvenidaDialog.setSize(500, 400);
        bienvenidaDialog.setLocationRelativeTo(this);
        bienvenidaDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Panel principal con fondo personalizado Marvel
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(15, 15, 35)); // Fondo azul oscuro Marvel
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título principal
        JLabel titulo = new JLabel("🦸‍♂️ ¡BIENVENIDO A LA BIBLIOTECA COMUNITARIA MARVEL! 🦸‍♀️", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(255, 69, 0)); // Rojo Marvel
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Mensaje de bienvenida
        JTextArea mensaje = new JTextArea();
        mensaje.setText("¡Solicita y disfruta de tus libros favoritos!");
        mensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        mensaje.setForeground(new Color(255, 255, 255)); // Blanco
        mensaje.setBackground(new Color(15, 15, 35)); // Azul oscuro Marvel
        mensaje.setEditable(false);
        mensaje.setOpaque(true);
        mensaje.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Botón de cerrar
        JButton btnCerrar = new JButton("🚀 ¡Comenzar Aventura!");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrar.setBackground(new Color(255, 69, 0)); // Rojo Marvel
        btnCerrar.setForeground(new Color(255, 255, 255)); // Blanco
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createRaisedBevelBorder());
        btnCerrar.addActionListener(e -> bienvenidaDialog.dispose());
        
        // Panel de botón
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setBackground(new Color(15, 15, 35)); // Azul oscuro Marvel
        panelBoton.add(btnCerrar);
        
        // Agregar componentes al panel principal
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(mensaje), BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
        
        // Agregar panel al diálogo
        bienvenidaDialog.add(panelPrincipal);
        
        // Mostrar diálogo
        bienvenidaDialog.setVisible(true);
    }
    
    // ===== MÉTODO PARA CREAR DATOS DE PRUEBA =====
    
    private void crearDatosPrueba() {
        int respuesta = JOptionPane.showConfirmDialog(this, 
            "🧪 ¿Desea crear datos de prueba Marvel?\n\n" +
            "Esto creará:\n" +
            "• 5 lectores Marvel (Spider-Man, Iron Man, Captain America, Black Widow, Thor)\n" +
            "• 3 bibliotecarios Marvel (Nick Fury, Pepper Potts, Maria Hill)\n" +
            "• 5 libros temáticos (Spider-Man, Iron Man, Captain America, Thor, Black Widow)\n" +
            "• 4 artículos especiales (Máscara Spider-Man, Reactor Arc, Escudo Cap, Mjolnir)\n" +
            "• 6 préstamos con diferentes estados\n\n" +
            "⚠️ Los datos existentes NO se eliminarán",
            "Crear Datos Marvel", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            System.out.println("📚 Creando datos de prueba...");
            
            // Crear fechas de prueba
            pap2025.datatypes.DTFecha fechaHoy = new pap2025.datatypes.DTFecha(15, 1, 2025);
            pap2025.datatypes.DTFecha fechaAyer = new pap2025.datatypes.DTFecha(14, 1, 2025);
            
            // Crear lectores (personajes de Marvel) con contraseñas por defecto
            controladorFachada.registrarLector("Peter Parker (Spider-Man)", "spiderman@marvel.com", "spider123", "20 Ingram Street, Queens, NYC", fechaHoy, pap2025.logica.Zona.BIBLOTECA_CENTRAL);
            controladorFachada.registrarLector("Tony Stark (Iron Man)", "ironman@starkindustries.com", "iron123", "10880 Malibu Point, Malibu, CA", fechaHoy, pap2025.logica.Zona.BIBLOTECA_CENTRAL);
            controladorFachada.registrarLector("Steve Rogers (Captain America)", "captain.america@avengers.com", "cap123", "569 Leaman Place, Brooklyn, NYC", fechaAyer, pap2025.logica.Zona.SUCURSAL_ESTE);
            controladorFachada.registrarLector("Natasha Romanoff (Black Widow)", "blackwidow@shield.gov", "widow123", "Classified Location", fechaHoy, pap2025.logica.Zona.SUCURSAL_OESTE);
            controladorFachada.registrarLector("Thor Odinson", "thor@asgard.com", "thor123", "Asgard, Nine Realms", fechaAyer, pap2025.logica.Zona.BIBLOTECA_CENTRAL);
            
            // Crear bibliotecarios (personajes de Marvel) con contraseñas por defecto
            controladorFachada.registrarBibliotecario("Nick Fury", "nick.fury@shield.gov", "fury123", 1001);
            controladorFachada.registrarBibliotecario("Pepper Potts", "pepper@starkindustries.com", "pepper123", 1002);
            controladorFachada.registrarBibliotecario("Maria Hill", "maria.hill@shield.gov", "maria123", 1003);
            
            // Crear libros (temática Marvel)
            controladorFachada.crearLibro(fechaHoy, "The Amazing Spider-Man: The Complete Collection", 863);
            controladorFachada.crearLibro(fechaHoy, "Iron Man: Armor Wars", 471);
            controladorFachada.crearLibro(fechaAyer, "Captain America: The Winter Soldier", 156);
            controladorFachada.crearLibro(fechaHoy, "Thor: God of Thunder", 320);
            controladorFachada.crearLibro(fechaAyer, "Black Widow: Red Room Files", 280);
            
            // Crear artículos especiales (temática Marvel)
            pap2025.datatypes.DTDimensiones dim1 = new pap2025.datatypes.DTDimensiones(30.0, 20.0, 5.0);
            pap2025.datatypes.DTDimensiones dim2 = new pap2025.datatypes.DTDimensiones(50.0, 30.0, 10.0);
            pap2025.datatypes.DTDimensiones dim3 = new pap2025.datatypes.DTDimensiones(25.0, 15.0, 8.0);
            controladorFachada.crearArtEspecial(fechaHoy, "Máscara de Spider-Man (Réplica)", 0.5, dim1);
            controladorFachada.crearArtEspecial(fechaAyer, "Reactor Arc de Iron Man (Réplica)", 2.0, dim2);
            controladorFachada.crearArtEspecial(fechaHoy, "Escudo de Captain America (Réplica)", 1.5, dim3);
            controladorFachada.crearArtEspecial(fechaAyer, "Martillo de Thor Mjolnir (Réplica)", 3.0, dim2);
            
            // Crear préstamos de prueba con diferentes estados
            System.out.println("📋 Creando préstamos de prueba...");
            
            // Obtener materiales y usuarios para crear préstamos
            List<pap2025.logica.Material> materiales = controladorFachada.getListaMateriales();
            List<pap2025.logica.Lector> lectores = controladorFachada.getListaLectores();
            List<pap2025.logica.Bibliotecario> bibliotecarios = controladorFachada.getListaBibliotecarios();
            
            if (!materiales.isEmpty() && !lectores.isEmpty() && !bibliotecarios.isEmpty()) {
                // Crear fechas para préstamos
                pap2025.datatypes.DTFecha fechaSolicitud = new pap2025.datatypes.DTFecha(31, 8, 2025);
                pap2025.datatypes.DTFecha fechaDevolucion = new pap2025.datatypes.DTFecha(15, 9, 2025);
                pap2025.datatypes.DTFecha fechaDevolucionPendiente = new pap2025.datatypes.DTFecha(30, 9, 2025);
                
                // Préstamo 1: EN CURSO
                pap2025.logica.Material material1 = materiales.get(0); // The Amazing Spider-Man: The Complete Collection
                pap2025.logica.Lector lector1 = lectores.get(0); // Peter Parker (Spider-Man)
                pap2025.logica.Bibliotecario bibliotecario1 = bibliotecarios.get(0); // Nick Fury
                Integer prestamo1Id = controladorFachada.crearPrestamo(material1, lector1, bibliotecario1, fechaDevolucion);
                if (prestamo1Id != null) {
                    controladorFachada.actualizarEstadoPrestamo(prestamo1Id, pap2025.logica.EstadoP.ENCURSO);
                    System.out.println("✅ Préstamo EN CURSO creado con ID: " + prestamo1Id);
                }
                
                // Préstamo 2: PENDIENTE (mismo material que el anterior)
                if (materiales.size() > 1) {
                    pap2025.logica.Material material2 = materiales.get(1); // Iron Man: Armor Wars
                    pap2025.logica.Lector lector2 = lectores.get(1); // Tony Stark (Iron Man)
                    Integer prestamo2Id = controladorFachada.crearPrestamo(material2, lector2, bibliotecario1, fechaDevolucionPendiente);
                    if (prestamo2Id != null) {
                        controladorFachada.actualizarEstadoPrestamo(prestamo2Id, pap2025.logica.EstadoP.PENDIENTE);
                        System.out.println("⏳ Préstamo PENDIENTE creado con ID: " + prestamo2Id);
                    }
                }
                
                // Préstamo 3: PENDIENTE (artículo especial)
                if (materiales.size() > 3) {
                    pap2025.logica.Material material3 = materiales.get(3); // Máscara de Spider-Man (Réplica)
                    pap2025.logica.Lector lector3 = lectores.get(2); // Steve Rogers (Captain America)
                    Integer prestamo3Id = controladorFachada.crearPrestamo(material3, lector3, bibliotecario1, fechaDevolucionPendiente);
                    if (prestamo3Id != null) {
                        controladorFachada.actualizarEstadoPrestamo(prestamo3Id, pap2025.logica.EstadoP.PENDIENTE);
                        System.out.println("⏳ Préstamo PENDIENTE creado con ID: " + prestamo3Id);
                    }
                }
                
                // Préstamo 4: PENDIENTE (mismo material que el anterior para ver priorización)
                if (materiales.size() > 3) {
                    pap2025.logica.Material material4 = materiales.get(3); // Máscara de Spider-Man (Réplica) (otra vez)
                    pap2025.logica.Lector lector4 = lectores.get(0); // Peter Parker (Spider-Man)
                    Integer prestamo4Id = controladorFachada.crearPrestamo(material4, lector4, bibliotecario1, fechaDevolucionPendiente);
                    if (prestamo4Id != null) {
                        controladorFachada.actualizarEstadoPrestamo(prestamo4Id, pap2025.logica.EstadoP.PENDIENTE);
                        System.out.println("⏳ Préstamo PENDIENTE creado con ID: " + prestamo4Id);
                    }
                }
                
                // Préstamo 5: PENDIENTE (artículo especial diferente)
                if (materiales.size() > 4) {
                    pap2025.logica.Material material5 = materiales.get(4); // Reactor Arc de Iron Man (Réplica)
                    pap2025.logica.Lector lector5 = lectores.get(1); // Tony Stark (Iron Man)
                    Integer prestamo5Id = controladorFachada.crearPrestamo(material5, lector5, bibliotecarios.get(1), fechaDevolucionPendiente);
                    if (prestamo5Id != null) {
                        controladorFachada.actualizarEstadoPrestamo(prestamo5Id, pap2025.logica.EstadoP.PENDIENTE);
                        System.out.println("⏳ Préstamo PENDIENTE creado con ID: " + prestamo5Id);
                    }
                }
                
                // Préstamo 6: DEVUELTO (para completar la variedad)
                if (materiales.size() > 2) {
                    pap2025.logica.Material material6 = materiales.get(2); // Captain America: The Winter Soldier
                    pap2025.logica.Lector lector6 = lectores.get(2); // Steve Rogers (Captain America)
                    Integer prestamo6Id = controladorFachada.crearPrestamo(material6, lector6, bibliotecarios.get(1), fechaDevolucion);
                    if (prestamo6Id != null) {
                        controladorFachada.actualizarEstadoPrestamo(prestamo6Id, pap2025.logica.EstadoP.DEVUELTO);
                        System.out.println("✅ Préstamo DEVUELTO creado con ID: " + prestamo6Id);
                    }
                }
            }
            
            System.out.println("✅ Datos de prueba creados exitosamente");
            
            // Mostrar resumen
            JOptionPane.showMessageDialog(this, 
                "✅ Datos de prueba Marvel creados exitosamente!\n\n" +
                "📚 Se crearon 5 libros temáticos:\n" +
                "   • The Amazing Spider-Man: The Complete Collection\n" +
                "   • Iron Man: Armor Wars\n" +
                "   • Captain America: The Winter Soldier\n" +
                "   • Thor: God of Thunder\n" +
                "   • Black Widow: Red Room Files\n\n" +
                "🎨 Se crearon 4 artículos especiales:\n" +
                "   • Máscara de Spider-Man (Réplica)\n" +
                "   • Reactor Arc de Iron Man (Réplica)\n" +
                "   • Escudo de Captain America (Réplica)\n" +
                "   • Martillo de Thor Mjolnir (Réplica)\n\n" +
                "👤 Se crearon 5 lectores Marvel (con contraseñas):\n" +
                "   • Peter Parker (spiderman@marvel.com) - spider123\n" +
                "   • Tony Stark (ironman@starkindustries.com) - iron123\n" +
                "   • Steve Rogers (captain.america@avengers.com) - cap123\n" +
                "   • Natasha Romanoff (blackwidow@shield.gov) - widow123\n" +
                "   • Thor Odinson (thor@asgard.com) - thor123\n\n" +
                "🏢 Se crearon 3 bibliotecarios Marvel (con contraseñas):\n" +
                "   • Nick Fury (nick.fury@shield.gov) - fury123\n" +
                "   • Pepper Potts (pepper@starkindustries.com) - pepper123\n" +
                "   • Maria Hill (maria.hill@shield.gov) - maria123\n\n" +
                "📋 Se crearon 6 préstamos con diferentes estados\n\n" +
                "🔐 Sistema de autenticación implementado por detrás\n" +
                "💡 Los datos están listos para usar en las diferentes funcionalidades del sistema.",
                "Datos Marvel Creados", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            System.err.println("⚠️ Error al crear datos de prueba: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "❌ Error al crear datos de prueba:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
