package pap2025.presentacion;

import pap2025.interfaz.IControladorFachada;
import pap2025.logica.*;
import pap2025.datatypes.DTFecha;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Ventana para listar préstamos por lector
 */
public class ListarPrestamosPorLector extends JInternalFrame {
    
    // Controlador
    private IControladorFachada controladorFachada;
    
    // Componentes de la interfaz
    private JTable tablaPrestamos;
    private DefaultTableModel modeloPrestamos;
    private JComboBox<Lector> comboLectores;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JButton btnCerrar;
    private JLabel lblTotalPrestamos;
    private JLabel lblPrestamosEnCurso;
    private JLabel lblPrestamosDevueltos;
    private JLabel lblPrestamosPendientes;
    
    public ListarPrestamosPorLector(IControladorFachada controladorFachada) {
        this.controladorFachada = controladorFachada;
        
        // Configuración del frame interno
        setTitle("Listar Préstamos por Lector - Biblioteca");
        setSize(1000, 700);
        setResizable(true);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        
        // Inicializar componentes
        initComponents();
        initLayout();
        initEvents();
        
        // Centrar en pantalla
        setLocation(50, 50);
        
        // Cargar datos iniciales
        cargarLectores();
    }
    
    private void initComponents() {
        // Tabla de préstamos
        String[] columnas = {"ID", "Material", "Bibliotecario", "Estado", "Fecha Solicitud", "Fecha Devolución"};
        modeloPrestamos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La tabla no es editable
            }
        };
        
        tablaPrestamos = new JTable(modeloPrestamos);
        tablaPrestamos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaPrestamos.setRowHeight(25);
        tablaPrestamos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaPrestamos.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaPrestamos.getTableHeader().setForeground(Color.WHITE);
        tablaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPrestamos.getTableHeader().setReorderingAllowed(false);
        
        // Combo de lectores
        comboLectores = new JComboBox<>();
        comboLectores.setFont(new Font("Arial", Font.PLAIN, 12));
        comboLectores.setPreferredSize(new Dimension(300, 30));
        comboLectores.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Lector) {
                    Lector lector = (Lector) value;
                    setText("👤 " + lector.getNombre() + " (" + lector.getEmail() + ")");
                }
                return this;
            }
        });
        
        // Botones
        btnBuscar = new JButton("🔍 Buscar Préstamos");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setPreferredSize(new Dimension(150, 30));
        btnBuscar.setBackground(new Color(70, 130, 180)); // Azul
        btnBuscar.setForeground(Color.WHITE);
        
        btnLimpiar = new JButton("🧹 Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 12));
        btnLimpiar.setPreferredSize(new Dimension(120, 30));
        btnLimpiar.setBackground(new Color(255, 165, 0)); // Naranja
        btnLimpiar.setForeground(Color.WHITE);
        
        btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setPreferredSize(new Dimension(120, 30));
        btnCerrar.setBackground(new Color(220, 20, 60)); // Rojo
        btnCerrar.setForeground(Color.WHITE);
        
        // Labels de estadísticas
        lblTotalPrestamos = new JLabel("📊 Total: 0");
        lblTotalPrestamos.setFont(new Font("Arial", Font.BOLD, 12));
        lblTotalPrestamos.setForeground(new Color(70, 130, 180));
        
        lblPrestamosEnCurso = new JLabel("🔄 En Curso: 0");
        lblPrestamosEnCurso.setFont(new Font("Arial", Font.BOLD, 12));
        lblPrestamosEnCurso.setForeground(new Color(255, 140, 0));
        
        lblPrestamosDevueltos = new JLabel("✅ Devueltos: 0");
        lblPrestamosDevueltos.setFont(new Font("Arial", Font.BOLD, 12));
        lblPrestamosDevueltos.setForeground(new Color(34, 139, 34));
        
        lblPrestamosPendientes = new JLabel("⏳ Pendientes: 0");
        lblPrestamosPendientes.setFont(new Font("Arial", Font.BOLD, 12));
        lblPrestamosPendientes.setForeground(new Color(255, 69, 0));
    }
    
    private void initLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con controles
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Seleccionar Lector"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Fila 1: Selección de lector
        gbc.gridx = 0; gbc.gridy = 0;
        panelSuperior.add(new JLabel("Lector:"), gbc);
        gbc.gridx = 1;
        panelSuperior.add(comboLectores, gbc);
        gbc.gridx = 2;
        panelSuperior.add(btnBuscar, gbc);
        gbc.gridx = 3;
        panelSuperior.add(btnLimpiar, gbc);
        gbc.gridx = 4;
        panelSuperior.add(btnCerrar, gbc);
        
        // Fila 2: Estadísticas
        gbc.gridx = 0; gbc.gridy = 1;
        panelSuperior.add(lblTotalPrestamos, gbc);
        gbc.gridx = 1;
        panelSuperior.add(lblPrestamosEnCurso, gbc);
        gbc.gridx = 2;
        panelSuperior.add(lblPrestamosDevueltos, gbc);
        gbc.gridx = 3;
        panelSuperior.add(lblPrestamosPendientes, gbc);
        
        // Panel central con tabla
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Préstamos del Lector Seleccionado"));
        
        // Agregar componentes a la ventana
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void initEvents() {
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPrestamosPorLector();
            }
        });
        
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarResultados();
            }
        });
        
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void cargarLectores() {
        try {
            comboLectores.removeAllItems();
            List<Lector> lectores = controladorFachada.getListaLectores();
            
            // Agregar opción por defecto
            comboLectores.addItem(null);
            
            for (Lector lector : lectores) {
                comboLectores.addItem(lector);
            }
            
            System.out.println("DEBUG: Lectores cargados: " + lectores.size());
            
        } catch (Exception e) {
            System.err.println("⚠️ Error al cargar lectores: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "❌ Error al cargar la lista de lectores:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarPrestamosPorLector() {
        try {
            Lector lectorSeleccionado = (Lector) comboLectores.getSelectedItem();
            
            if (lectorSeleccionado == null) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ Por favor, seleccione un lector de la lista.",
                    "Selección Requerida", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            System.out.println("DEBUG: Buscando préstamos para lector: " + lectorSeleccionado.getNombre());
            
            // Limpiar tabla
            modeloPrestamos.setRowCount(0);
            
            // Cargar préstamos del lector seleccionado
            List<Prestamo> prestamos = controladorFachada.getListaPrestamos();
            List<Material> materiales = controladorFachada.getListaMateriales();
            List<Bibliotecario> bibliotecarios = controladorFachada.getListaBibliotecarios();
            
            int totalPrestamos = 0;
            int prestamosEnCurso = 0;
            int prestamosDevueltos = 0;
            int prestamosPendientes = 0;
            
            for (Prestamo prestamo : prestamos) {
                // Verificar si el préstamo pertenece al lector seleccionado
                if (prestamo.getLector().getId() == lectorSeleccionado.getId()) {
                    Object[] fila = new Object[6];
                    fila[0] = prestamo.getId();
                    
                    // Material - buscar por ID para obtener nombre/título
                    String nombreMaterial = "Material ID: " + prestamo.getMaterial().getId();
                    try {
                        for (Material material : materiales) {
                            if (material != null && material.getId() == prestamo.getMaterial().getId()) {
                                if (material instanceof Libro) {
                                    nombreMaterial = ((Libro) material).getTitulo();
                                } else if (material instanceof ArtEspeciales) {
                                    nombreMaterial = ((ArtEspeciales) material).getDescripcion();
                                } else {
                                    nombreMaterial = "Material ID: " + material.getId();
                                }
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("⚠️ Error al obtener nombre del material: " + e.getMessage());
                    }
                    fila[1] = nombreMaterial;
                    
                    // Bibliotecario - buscar por ID para obtener nombre
                    String nombreBibliotecario = "Bibliotecario ID: " + prestamo.getBibliotecario().getId();
                    try {
                        for (Bibliotecario bibliotecario : bibliotecarios) {
                            if (bibliotecario != null && bibliotecario.getId() == prestamo.getBibliotecario().getId()) {
                                nombreBibliotecario = bibliotecario.getNombre();
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("⚠️ Error al obtener nombre del bibliotecario: " + e.getMessage());
                    }
                    fila[2] = nombreBibliotecario;
                    
                    fila[3] = prestamo.getEstadoP();
                    fila[4] = formatearFecha(prestamo.getFechaSolicitada());
                    fila[5] = formatearFecha(prestamo.getFechaDevolucion());
                    
                    modeloPrestamos.addRow(fila);
                    totalPrestamos++;
                    
                    // Contar por estado
                    switch (prestamo.getEstadoP()) {
                        case ENCURSO:
                            prestamosEnCurso++;
                            break;
                        case DEVUELTO:
                            prestamosDevueltos++;
                            break;
                        case PENDIENTE:
                            prestamosPendientes++;
                            break;
                    }
                    
                    System.out.println("DEBUG: Agregado préstamo: " + prestamo.getId() + " - Estado: " + prestamo.getEstadoP());
                }
            }
            
            // Actualizar estadísticas
            actualizarEstadisticas(totalPrestamos, prestamosEnCurso, prestamosDevueltos, prestamosPendientes);
            
            System.out.println("DEBUG: Búsqueda completada. Préstamos encontrados: " + totalPrestamos);
            
            JOptionPane.showMessageDialog(this, 
                "✅ Búsqueda completada!\n\n" +
                "👤 Lector: " + lectorSeleccionado.getNombre() + "\n" +
                "📊 Total de préstamos: " + totalPrestamos + "\n" +
                "🔄 En curso: " + prestamosEnCurso + "\n" +
                "✅ Devueltos: " + prestamosDevueltos + "\n" +
                "⏳ Pendientes: " + prestamosPendientes,
                "Resultados de la Búsqueda", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            System.err.println("⚠️ Error al buscar préstamos: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "❌ Error al buscar los préstamos:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarEstadisticas(int total, int enCurso, int devueltos, int pendientes) {
        lblTotalPrestamos.setText("📊 Total: " + total);
        lblPrestamosEnCurso.setText("🔄 En Curso: " + enCurso);
        lblPrestamosDevueltos.setText("✅ Devueltos: " + devueltos);
        lblPrestamosPendientes.setText("⏳ Pendientes: " + pendientes);
    }
    
    private void limpiarResultados() {
        modeloPrestamos.setRowCount(0);
        comboLectores.setSelectedItem(null);
        actualizarEstadisticas(0, 0, 0, 0);
        
        JOptionPane.showMessageDialog(this, 
            "🧹 Resultados limpiados correctamente!",
            "Limpieza Completada", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String formatearFecha(DTFecha fecha) {
        if (fecha == null) {
            return "N/A";
        }
        return fecha.getDia() + "/" + fecha.getMes() + "/" + fecha.getAnio();
    }
}
