package pap2025.presentacion;

import pap2025.logica.*;
import pap2025.interfaz.IControladorFachada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Panel de Administrador para reportes y auditoría del sistema
 */
public class PanelAdministrador extends JInternalFrame {
    
    // Controlador
    private IControladorFachada controladorFachada;
    
    // Componentes de la interfaz
    private JComboBox<Bibliotecario> comboBibliotecarioReporte;
    private JButton btnHistorialBibliotecario;
    private JButton btnReportePorZona;
    private JButton btnMaterialesPendientes;
    private JButton btnCerrar;
    
    // Tablas de reportes
    private JTable tablaHistorialBibliotecario;
    private DefaultTableModel modeloHistorialBibliotecario;
    private JTable tablaReporteZona;
    private DefaultTableModel modeloReporteZona;
    private JTable tablaMaterialesPendientes;
    private DefaultTableModel modeloMaterialesPendientes;
    
    public PanelAdministrador(IControladorFachada controladorFachada) {
        this.controladorFachada = controladorFachada;
        
        // Configuración del frame interno
        setTitle("👑 Panel de Administrador - Reportes y Auditoría");
        setSize(1200, 800);
        setResizable(true);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        
        // Inicializar componentes
        initComponents();
        initLayout();
        initEvents();
        
        // Cargar datos iniciales
        cargarDatos();
        
        // Centrar en pantalla
        setLocation(50, 50);
    }
    
    private void initComponents() {
        // Combo para seleccionar bibliotecario para reporte
        comboBibliotecarioReporte = new JComboBox<>();
        comboBibliotecarioReporte.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBibliotecarioReporte.setPreferredSize(new Dimension(250, 30));
        comboBibliotecarioReporte.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Bibliotecario) {
                    Bibliotecario bibliotecario = (Bibliotecario) value;
                    setText("🏢 " + bibliotecario.getNombre() + " (Nro. " + bibliotecario.getNroEmpleado() + ")");
                }
                return this;
            }
        });
        
        // Botones de reportes de administrador
        btnHistorialBibliotecario = new JButton("📊 Historial Bibliotecario");
        btnHistorialBibliotecario.setFont(new Font("Arial", Font.BOLD, 12));
        btnHistorialBibliotecario.setPreferredSize(new Dimension(180, 35));
        btnHistorialBibliotecario.setBackground(new Color(138, 43, 226)); // Violeta
        btnHistorialBibliotecario.setForeground(Color.WHITE);
        
        btnReportePorZona = new JButton("🗺️ Reporte por Zona");
        btnReportePorZona.setFont(new Font("Arial", Font.BOLD, 12));
        btnReportePorZona.setPreferredSize(new Dimension(180, 35));
        btnReportePorZona.setBackground(new Color(255, 69, 0)); // Naranja rojizo
        btnReportePorZona.setForeground(Color.WHITE);
        
        btnMaterialesPendientes = new JButton("⚠️ Materiales Pendientes");
        btnMaterialesPendientes.setFont(new Font("Arial", Font.BOLD, 12));
        btnMaterialesPendientes.setPreferredSize(new Dimension(180, 35));
        btnMaterialesPendientes.setBackground(new Color(255, 140, 0)); // Naranja oscuro
        btnMaterialesPendientes.setForeground(Color.WHITE);
        
        btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setPreferredSize(new Dimension(120, 30));
        btnCerrar.setBackground(new Color(220, 20, 60)); // Rojo
        btnCerrar.setForeground(Color.WHITE);
        
        // Tabla de historial de bibliotecario
        String[] columnasHistorial = {"ID Préstamo", "📚 Material Prestado", "👤 Lector", "📅 Fecha Real Solicitud", "⏰ Fecha Real Límite", "📊 Estado Actual"};
        modeloHistorialBibliotecario = new DefaultTableModel(columnasHistorial, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaHistorialBibliotecario = new JTable(modeloHistorialBibliotecario);
        tablaHistorialBibliotecario.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaHistorialBibliotecario.setRowHeight(30);
        tablaHistorialBibliotecario.setPreferredScrollableViewportSize(new Dimension(1100, 400));
        tablaHistorialBibliotecario.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaHistorialBibliotecario.getTableHeader().setBackground(new Color(138, 43, 226));
        tablaHistorialBibliotecario.getTableHeader().setForeground(Color.WHITE);
        
        // Tabla de reporte por zona
        String[] columnasZona = {"Zona", "Total Préstamos", "En Curso", "Devueltos", "Pendientes"};
        modeloReporteZona = new DefaultTableModel(columnasZona, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaReporteZona = new JTable(modeloReporteZona);
        tablaReporteZona.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaReporteZona.setRowHeight(30);
        tablaReporteZona.setPreferredScrollableViewportSize(new Dimension(1100, 400));
        tablaReporteZona.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaReporteZona.getTableHeader().setBackground(new Color(255, 69, 0));
        tablaReporteZona.getTableHeader().setForeground(Color.WHITE);
        
        // Tabla de materiales pendientes
        String[] columnasMateriales = {"ID", "Material", "Pendientes", "En Curso", "Total"};
        modeloMaterialesPendientes = new DefaultTableModel(columnasMateriales, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaMaterialesPendientes = new JTable(modeloMaterialesPendientes);
        tablaMaterialesPendientes.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaMaterialesPendientes.setRowHeight(30);
        tablaMaterialesPendientes.setPreferredScrollableViewportSize(new Dimension(1100, 400));
        tablaMaterialesPendientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaMaterialesPendientes.getTableHeader().setBackground(new Color(255, 140, 0));
        tablaMaterialesPendientes.getTableHeader().setForeground(Color.WHITE);
    }
    
    private void initLayout() {
        setLayout(new BorderLayout());
        
        // Título principal
        JLabel tituloLabel = new JLabel("👑 PANEL DE ADMINISTRADOR - REPORTES Y AUDITORÍA");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(138, 43, 226));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tituloLabel.setBackground(new Color(248, 248, 248));
        tituloLabel.setOpaque(true);
        
        // Panel superior de controles
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelControles.setBackground(new Color(248, 248, 248));
        panelControles.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(138, 43, 226), 2),
            "🎛️ Controles de Reportes",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            new Color(138, 43, 226)
        ));
        
        panelControles.add(new JLabel("Bibliotecario para auditoría:"));
        panelControles.add(comboBibliotecarioReporte);
        panelControles.add(btnHistorialBibliotecario);
        panelControles.add(btnReportePorZona);
        panelControles.add(btnMaterialesPendientes);
        
        // Panel de pestañas para los reportes
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.setPreferredSize(new Dimension(1100, 500));
        
        // Pestaña 1: Historial de Bibliotecario
        JPanel panelHistorial = new JPanel(new BorderLayout());
        panelHistorial.setBorder(BorderFactory.createTitledBorder("📊 Historial de Préstamos por Bibliotecario"));
        panelHistorial.add(new JScrollPane(tablaHistorialBibliotecario), BorderLayout.CENTER);
        
        // Pestaña 2: Reporte por Zona
        JPanel panelZona = new JPanel(new BorderLayout());
        panelZona.setBorder(BorderFactory.createTitledBorder("🗺️ Reporte de Préstamos por Zona"));
        panelZona.add(new JScrollPane(tablaReporteZona), BorderLayout.CENTER);
        
        // Pestaña 3: Materiales Pendientes
        JPanel panelMateriales = new JPanel(new BorderLayout());
        panelMateriales.setBorder(BorderFactory.createTitledBorder("⚠️ Materiales con Préstamos Pendientes"));
        panelMateriales.add(new JScrollPane(tablaMaterialesPendientes), BorderLayout.CENTER);
        
        tabbedPane.addTab("📊 Historial Bibliotecario", panelHistorial);
        tabbedPane.addTab("🗺️ Reporte por Zona", panelZona);
        tabbedPane.addTab("⚠️ Materiales Pendientes", panelMateriales);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(248, 248, 248));
        panelBotones.add(btnCerrar);
        
        // Panel principal que contendrá controles y tabbedPane
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(248, 248, 248));
        
        // Agregar controles en la parte superior del panel principal
        panelPrincipal.add(panelControles, BorderLayout.NORTH);
        
        // Agregar tabbedPane en el centro del panel principal
        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);
        
        // Agregar componentes al frame
        add(tituloLabel, BorderLayout.NORTH);
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelInferior.setBackground(new Color(248, 248, 248));
        panelInferior.add(btnCerrar);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void initEvents() {
        btnHistorialBibliotecario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarHistorialBibliotecario();
            }
        });
        
        btnReportePorZona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReportePorZona();
            }
        });
        
        btnMaterialesPendientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporteMaterialesPendientes();
            }
        });
        
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void cargarDatos() {
        try {
            System.out.println("DEBUG: Iniciando carga de bibliotecarios para auditoría...");
            
            // Cargar bibliotecarios para reportes
            comboBibliotecarioReporte.removeAllItems();
            List<Bibliotecario> bibliotecarios = controladorFachada.getListaBibliotecarios();
            
            System.out.println("DEBUG: Bibliotecarios encontrados: " + bibliotecarios.size());
            
            for (Bibliotecario bibliotecario : bibliotecarios) {
                System.out.println("DEBUG: Agregando bibliotecario: " + bibliotecario.getNombre() + " (Nro. " + bibliotecario.getNroEmpleado() + ")");
                comboBibliotecarioReporte.addItem(bibliotecario);
            }
            
            System.out.println("DEBUG: Carga de bibliotecarios completada. Total en combo: " + comboBibliotecarioReporte.getItemCount());
            
        } catch (Exception e) {
            System.err.println("ERROR al cargar bibliotecarios: " + e.getMessage());
            e.printStackTrace();
            
            // Mostrar mensaje de error al usuario
            JOptionPane.showMessageDialog(this, 
                "❌ Error al cargar bibliotecarios para auditoría:\n" + e.getMessage(),
                "Error de Carga", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ===== MÉTODOS DE ADMINISTRADOR =====
    
    private void generarHistorialBibliotecario() {
        if (comboBibliotecarioReporte.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "❌ Por favor seleccione un bibliotecario para generar el historial", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Bibliotecario bibliotecario = (Bibliotecario) comboBibliotecarioReporte.getSelectedItem();
        List<Prestamo> historial = controladorFachada.obtenerHistorialPrestamosPorBibliotecario(bibliotecario.getEmail());
        
        // Limpiar tabla
        modeloHistorialBibliotecario.setRowCount(0);
        
        if (historial.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "📊 Historial del Bibliotecario\n\n" +
                "🏢 Bibliotecario: " + bibliotecario.getNombre() + "\n" +
                "📋 Total de préstamos: 0\n" +
                "💡 Este bibliotecario aún no ha gestionado ningún préstamo",
                "Historial Vacío", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Cargar listas completas para evitar LazyInitializationException
        List<Lector> lectores = controladorFachada.getListaLectores();
        List<Material> materiales = controladorFachada.getListaMateriales();
        
        // Llenar tabla con datos
        for (Prestamo prestamo : historial) {
            Object[] fila = new Object[6];
            fila[0] = prestamo.getId();
            
            // Material prestado con información real
            String materialInfo = "Material ID: " + prestamo.getMaterial().getId();
            for (Material material : materiales) {
                if (material.getId() == prestamo.getMaterial().getId()) {
                    if (material instanceof Libro) {
                        Libro libro = (Libro) material;
                        materialInfo = "Libro: " + libro.getTitulo();
                    } else if (material instanceof ArtEspeciales) {
                        ArtEspeciales artEspecial = (ArtEspeciales) material;
                        materialInfo = "Artículo: " + artEspecial.getDescripcion();
                    }
                    break;
                }
            }
            fila[1] = materialInfo;
            
            // Lector con nombre real
            String lectorInfo = "Lector ID: " + prestamo.getLector().getId();
            for (Lector lector : lectores) {
                if (lector.getId() == prestamo.getLector().getId()) {
                    lectorInfo = lector.getNombre() + " (" + lector.getEmail() + ")";
                    break;
                }
            }
            fila[2] = lectorInfo;
            // Fecha real de solicitud del préstamo
            fila[3] = formatearFecha(prestamo.getFechaSolicitada());
            
            // Fecha real límite de devolución
            fila[4] = formatearFecha(prestamo.getFechaDevolucion());
            
            // Estado actual del préstamo
            String estadoInfo = "";
            switch (prestamo.getEstadoP()) {
                case ENCURSO:
                    estadoInfo = "EN CURSO - Préstamo activo";
                    break;
                case DEVUELTO:
                    estadoInfo = "DEVUELTO - Material retornado";
                    break;
                case PENDIENTE:
                    estadoInfo = "PENDIENTE - Esperando aprobación";
                    break;
                default:
                    estadoInfo = prestamo.getEstadoP().toString();
            }
            fila[5] = estadoInfo;
            
            modeloHistorialBibliotecario.addRow(fila);
        }
        
        // Mensaje de confirmación con información real
        String mensaje = "📊 Historial Real del Bibliotecario Generado!\n\n" +
                        "🏢 Bibliotecario: " + bibliotecario.getNombre() + "\n" +
                        "📋 Total de préstamos otorgados: " + historial.size() + "\n\n";
        
        JOptionPane.showMessageDialog(this, mensaje, "Historial Generado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void generarReportePorZona() {
        List<Object[]> reporte = controladorFachada.obtenerReportePrestamosPorZona();
        
        // Limpiar tabla
        modeloReporteZona.setRowCount(0);
        
        // Llenar tabla con datos
        for (Object[] fila : reporte) {
            modeloReporteZona.addRow(fila);
        }
        
        JOptionPane.showMessageDialog(this, 
            "🗺️ Reporte por zona generado exitosamente!\n\n" +
            "📊 Total de zonas con actividad: " + reporte.size() + "\n" +
            "📈 Datos incluyen totales, en curso, devueltos y pendientes por zona",
            "Reporte Generado", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void generarReporteMaterialesPendientes() {
        List<Object[]> materiales = controladorFachada.obtenerMaterialesConPrestamosPendientes();
        
        // Limpiar tabla
        modeloMaterialesPendientes.setRowCount(0);
        
        // Llenar tabla con datos
        for (Object[] fila : materiales) {
            modeloMaterialesPendientes.addRow(fila);
        }
        
        JOptionPane.showMessageDialog(this, 
            "⚠️ Reporte de materiales pendientes generado!\n\n" +
            "📚 Total de materiales con préstamos: " + materiales.size() + "\n" +
            "🔍 Ordenados por prioridad (más préstamos primero)\n" +
            "💡 Use esta información para priorizar devoluciones",
            "Reporte Generado", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String formatearFecha(pap2025.datatypes.DTFecha fecha) {
        if (fecha == null) {
            return "N/A";
        }
        return String.format("%02d/%02d/%04d", fecha.getDia(), fecha.getMes(), fecha.getAnio());
    }
    
}
