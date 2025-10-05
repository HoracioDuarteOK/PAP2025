package pap2025.presentacion;

import pap2025.interfaz.IControladorFachada;
import pap2025.logica.Material;
import pap2025.logica.Libro;
import pap2025.logica.ArtEspeciales;
import pap2025.datatypes.DTFecha;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Ventana para consultar todas las donaciones con filtro de fechas
 */
public class ConsultarDonaciones extends JInternalFrame {
    
    private IControladorFachada controladorFachada;
    
    // Componentes de filtro
    private JCheckBox chkFiltrarPorFechas;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JButton btnAplicarFiltro;
    private JButton btnMostrarTodas;
    
    // Tablas de resultados separadas
    private JTable tablaLibros;
    private JTable tablaArtEspeciales;
    private DefaultTableModel modeloLibros;
    private DefaultTableModel modeloArtEspeciales;
    
    // Botones de acción
    private JButton btnActualizar;
    private JButton btnCerrar;
    
    // Panel de estadísticas
    private JLabel lblTotalDonaciones;
    private JLabel lblTotalLibros;
    private JLabel lblTotalArtEspeciales;
    
    public ConsultarDonaciones(IControladorFachada controladorFachada) {
        this.controladorFachada = controladorFachada;
        
        // Configuración del frame
        setTitle("Consultar Donaciones - Trazabilidad del Inventario");
        setSize(800, 600);
        setClosable(true);
        setResizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        
        // Inicializar componentes
        initComponents();
        initLayout();
        initEvents();
        
        // Centrar en pantalla
        setLocation(100, 100);
        
        // Cargar datos iniciales
        cargarTodasLasDonaciones();
    }
    
    private void initComponents() {
        // Checkbox para filtrar por fechas
        chkFiltrarPorFechas = new JCheckBox("Filtrar por rango de fechas");
        chkFiltrarPorFechas.setFont(new Font("Arial", Font.BOLD, 14));
        chkFiltrarPorFechas.setForeground(new Color(70, 130, 180));
        
        // Campos de fecha
        txtFechaInicio = new JTextField(8);
        txtFechaInicio.setFont(new Font("Arial", Font.PLAIN, 12));
        txtFechaInicio.setPreferredSize(new Dimension(80, 25));
        txtFechaInicio.setToolTipText("Formato: DD/MM/AAAA");
        txtFechaInicio.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        txtFechaFin = new JTextField(8);
        txtFechaFin.setFont(new Font("Arial", Font.PLAIN, 12));
        txtFechaFin.setPreferredSize(new Dimension(80, 25));
        txtFechaFin.setToolTipText("Formato: DD/MM/AAAA");
        txtFechaFin.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        // Botones
        btnAplicarFiltro = new JButton("Aplicar Filtro");
        btnAplicarFiltro.setFont(new Font("Arial", Font.BOLD, 11));
        btnAplicarFiltro.setPreferredSize(new Dimension(100, 25));
        btnAplicarFiltro.setBackground(new Color(34, 139, 34)); // Verde
        btnAplicarFiltro.setForeground(Color.WHITE);
        
        btnMostrarTodas = new JButton("Mostrar Todas");
        btnMostrarTodas.setFont(new Font("Arial", Font.BOLD, 11));
        btnMostrarTodas.setPreferredSize(new Dimension(100, 25));
        btnMostrarTodas.setBackground(new Color(70, 130, 180)); // Azul acero
        btnMostrarTodas.setForeground(Color.WHITE);
        
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 11));
        btnActualizar.setPreferredSize(new Dimension(80, 25));
        btnActualizar.setBackground(new Color(255, 165, 0)); // Naranja
        btnActualizar.setForeground(Color.WHITE);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 11));
        btnCerrar.setPreferredSize(new Dimension(80, 25));
        btnCerrar.setBackground(new Color(220, 20, 60)); // Rojo
        btnCerrar.setForeground(Color.WHITE);
        
        // Tabla de Libros
        String[] columnasLibros = {"ID", "Título", "Cantidad de Páginas", "Fecha Ingreso", "Estado"};
        modeloLibros = new DefaultTableModel(columnasLibros, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tablaLibros = new JTable(modeloLibros);
        tablaLibros.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaLibros.setRowHeight(25);
        tablaLibros.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaLibros.getTableHeader().setBackground(new Color(34, 139, 34)); // Verde para libros
        tablaLibros.getTableHeader().setForeground(Color.WHITE);
        
        // Tabla de Artículos Especiales
        String[] columnasArtEspeciales = {"ID", "Descripción", "Peso (kg)", "Alto (cm)", "Ancho (cm)", "Profundidad (cm)", "Fecha Ingreso", "Estado"};
        modeloArtEspeciales = new DefaultTableModel(columnasArtEspeciales, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tablaArtEspeciales = new JTable(modeloArtEspeciales);
        tablaArtEspeciales.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaArtEspeciales.setRowHeight(25);
        tablaArtEspeciales.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaArtEspeciales.getTableHeader().setBackground(new Color(70, 130, 180)); // Azul para artículos especiales
        tablaArtEspeciales.getTableHeader().setForeground(Color.WHITE);
        
        // Labels de estadísticas
        lblTotalDonaciones = new JLabel("Total Donaciones: 0");
        lblTotalDonaciones.setFont(new Font("Arial", Font.BOLD, 12));
        lblTotalDonaciones.setForeground(new Color(70, 130, 180));
        lblTotalDonaciones.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblTotalLibros = new JLabel("Total Libros: 0");
        lblTotalLibros.setFont(new Font("Arial", Font.BOLD, 12));
        lblTotalLibros.setForeground(new Color(34, 139, 34));
        lblTotalLibros.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblTotalArtEspeciales = new JLabel("Total Art. Especiales: 0");
        lblTotalArtEspeciales.setFont(new Font("Arial", Font.BOLD, 12));
        lblTotalArtEspeciales.setForeground(new Color(70, 130, 180));
        lblTotalArtEspeciales.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Inicialmente deshabilitar campos de fecha
        txtFechaInicio.setEnabled(false);
        txtFechaFin.setEnabled(false);
        btnAplicarFiltro.setEnabled(false);
    }
    
    private void initLayout() {
        setLayout(new BorderLayout());
        
        // Título principal
        JLabel tituloLabel = new JLabel("CONSULTA DE DONACIONES - TRAZABILIDAD DEL INVENTARIO");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(70, 130, 180));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel de filtros
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            "Filtros de Consulta",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));
        panelFiltros.setBackground(new Color(248, 248, 248));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Primera fila: Checkbox
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelFiltros.add(chkFiltrarPorFechas, gbc);
        
        // Segunda fila: Fechas
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panelFiltros.add(new JLabel("Fecha Inicio:"), gbc);
        
        gbc.gridx = 1;
        panelFiltros.add(txtFechaInicio, gbc);
        
        gbc.gridx = 2;
        panelFiltros.add(new JLabel("Fecha Fin:"), gbc);
        
        gbc.gridx = 3;
        panelFiltros.add(txtFechaFin, gbc);
        
        // Tercera fila: Botones
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFiltros.add(btnAplicarFiltro, gbc);
        
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        panelFiltros.add(btnMostrarTodas, gbc);
        
        // Panel de estadísticas
        JPanel panelEstadisticas = new JPanel(new GridLayout(1, 3, 15, 5));
        panelEstadisticas.setBackground(new Color(248, 248, 248));
        panelEstadisticas.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            "Estadísticas",
            0, 0,
            new Font("Arial", Font.BOLD, 12),
            new Color(70, 130, 180)
        ));
        panelEstadisticas.add(lblTotalDonaciones);
        panelEstadisticas.add(lblTotalLibros);
        panelEstadisticas.add(lblTotalArtEspeciales);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        panelBotones.setBackground(new Color(248, 248, 248));
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCerrar);
        
        // Panel superior que contiene filtros y estadísticas
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(248, 248, 248));
        panelSuperior.add(panelFiltros, BorderLayout.NORTH);
        panelSuperior.add(panelEstadisticas, BorderLayout.CENTER);
        
        // Panel de tablas con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("📚 Libros", new ImageIcon(), new JScrollPane(tablaLibros), "Ver donaciones de libros");
        tabbedPane.addTab("🎨 Artículos Especiales", new ImageIcon(), new JScrollPane(tablaArtEspeciales), "Ver donaciones de artículos especiales");
        
        // Configurar tamaño preferido para las tablas
        tablaLibros.setPreferredScrollableViewportSize(new Dimension(700, 200));
        tablaArtEspeciales.setPreferredScrollableViewportSize(new Dimension(700, 200));
        
        // Panel principal que contiene las tablas y botones
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        // Agregar componentes al frame
        add(tituloLabel, BorderLayout.NORTH);
        add(panelSuperior, BorderLayout.CENTER);
        add(panelPrincipal, BorderLayout.SOUTH);
    }
    
    private void initEvents() {
        chkFiltrarPorFechas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean filtrar = chkFiltrarPorFechas.isSelected();
                txtFechaInicio.setEnabled(filtrar);
                txtFechaFin.setEnabled(filtrar);
                btnAplicarFiltro.setEnabled(filtrar);
                
                if (!filtrar) {
                    cargarTodasLasDonaciones();
                }
            }
        });
        
        btnAplicarFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltroFechas();
            }
        });
        
        btnMostrarTodas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chkFiltrarPorFechas.setSelected(false);
                cargarTodasLasDonaciones();
            }
        });
        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkFiltrarPorFechas.isSelected()) {
                    aplicarFiltroFechas();
                } else {
                    cargarTodasLasDonaciones();
                }
            }
        });
        
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void aplicarFiltroFechas() {
        try {
            DTFecha fechaInicio = parsearFecha(txtFechaInicio.getText().trim());
            DTFecha fechaFin = parsearFecha(txtFechaFin.getText().trim());
            
            if (fechaInicio == null || fechaFin == null) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor ingrese fechas válidas en formato DD/MM/AAAA", 
                    "Error en Fechas", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar que fecha inicio sea menor que fecha fin
            if (!fechaInicioMenorOIgual(fechaInicio, fechaFin)) {
                JOptionPane.showMessageDialog(this, 
                    "La fecha de inicio debe ser menor o igual a la fecha de fin", 
                    "Error en Rango de Fechas", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            cargarDonacionesEnRango(fechaInicio, fechaFin);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al procesar las fechas. Formato esperado: DD/MM/AAAA", 
                "Error en Formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private DTFecha parsearFecha(String fechaStr) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            String[] partes = fechaStr.split("/");
            if (partes.length != 3) {
                return null;
            }
            
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);
            
            return new DTFecha(dia, mes, anio);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private void cargarDonacionesEnRango(DTFecha fechaInicio, DTFecha fechaFin) {
        List<Material> donacionesEnRango = controladorFachada.obtenerDonacionesEnRango(fechaInicio, fechaFin);
        List<Libro> librosEnRango = controladorFachada.obtenerDonacionesLibrosEnRango(fechaInicio, fechaFin);
        List<ArtEspeciales> artEspecialesEnRango = controladorFachada.obtenerDonacionesArtEspecialesEnRango(fechaInicio, fechaFin);
        
        mostrarDonacionesEnTabla(donacionesEnRango);
        actualizarEstadisticas(donacionesEnRango.size(), librosEnRango.size(), artEspecialesEnRango.size());
        
        JOptionPane.showMessageDialog(this, 
            "✅ Filtro aplicado exitosamente!\n\n" +
            "📋 Resultados del filtro:\n" +
            "   • Total donaciones en rango: " + donacionesEnRango.size() + "\n" +
            "   • 📚 Libros en rango: " + librosEnRango.size() + "\n" +
            "   • 🎨 Artículos especiales en rango: " + artEspecialesEnRango.size() + "\n\n" +
            "📅 Rango de fechas: " + txtFechaInicio.getText() + " a " + txtFechaFin.getText() + "\n\n" +
            "💡 Los detalles completos se muestran en la tabla de resultados.", 
            "Filtro Aplicado", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cargarTodasLasDonaciones() {
        List<Material> todasLasDonaciones = controladorFachada.obtenerTodasLasDonaciones();
        List<Libro> todosLosLibros = controladorFachada.obtenerTodasLasDonacionesLibros();
        List<ArtEspeciales> todosLosArtEspeciales = controladorFachada.obtenerTodasLasDonacionesArtEspeciales();
        
        System.out.println("DEBUG: Total donaciones obtenidas: " + todasLasDonaciones.size());
        System.out.println("DEBUG: Total libros obtenidos: " + todosLosLibros.size());
        System.out.println("DEBUG: Total artículos especiales obtenidos: " + todosLosArtEspeciales.size());
        
        mostrarDonacionesEnTabla(todasLasDonaciones);
        actualizarEstadisticas(todasLasDonaciones.size(), todosLosLibros.size(), todosLosArtEspeciales.size());
        
        System.out.println("DEBUG: Tablas actualizadas - Filas en tabla libros: " + modeloLibros.getRowCount());
        System.out.println("DEBUG: Tablas actualizadas - Filas en tabla artículos especiales: " + modeloArtEspeciales.getRowCount());
    }
    
    private void mostrarDonacionesEnTabla(List<Material> donaciones) {
        System.out.println("DEBUG: Iniciando mostrarDonacionesEnTabla con " + donaciones.size() + " materiales");
        
        // Limpiar ambas tablas
        modeloLibros.setRowCount(0);
        modeloArtEspeciales.setRowCount(0);
        
        int contadorLibros = 0;
        int contadorArtEspeciales = 0;
        
        // Separar materiales por tipo y agregar a sus respectivas tablas
        for (Material material : donaciones) {
            // Verificar si el material está disponible para préstamo
            boolean estaDisponible = controladorFachada.estaMaterialDisponible(material);
            String estadoDisponibilidad = estaDisponible ? "✅ Disponible" : "❌ En Préstamo";
            
            if (material instanceof Libro) {
                Libro libro = (Libro) material;
                Object[] filaLibro = new Object[5];
                filaLibro[0] = libro.getId();
                filaLibro[1] = libro.getTitulo();
                filaLibro[2] = libro.getCantPaginas();
                filaLibro[3] = formatearFecha(libro.getFechaIngreso());
                filaLibro[4] = estadoDisponibilidad;
                modeloLibros.addRow(filaLibro);
                contadorLibros++;
                System.out.println("DEBUG: Agregado libro: " + libro.getTitulo() + " (ID: " + libro.getId() + ") - Estado: " + estadoDisponibilidad);
            } else if (material instanceof ArtEspeciales) {
                ArtEspeciales artEspecial = (ArtEspeciales) material;
                Object[] filaArtEspecial = new Object[8];
                filaArtEspecial[0] = artEspecial.getId();
                filaArtEspecial[1] = artEspecial.getDescripcion();
                filaArtEspecial[2] = String.format("%.2f", artEspecial.getPeso());
                filaArtEspecial[3] = String.format("%.1f", artEspecial.getDimensiones().getAlto());
                filaArtEspecial[4] = String.format("%.1f", artEspecial.getDimensiones().getAncho());
                filaArtEspecial[5] = String.format("%.1f", artEspecial.getDimensiones().getProfundidad());
                filaArtEspecial[6] = formatearFecha(artEspecial.getFechaIngreso());
                filaArtEspecial[7] = estadoDisponibilidad;
                modeloArtEspeciales.addRow(filaArtEspecial);
                contadorArtEspeciales++;
                System.out.println("DEBUG: Agregado artículo especial: " + artEspecial.getDescripcion() + " (ID: " + artEspecial.getId() + ") - Estado: " + estadoDisponibilidad);
            }
        }
        
        System.out.println("DEBUG: Procesados " + contadorLibros + " libros y " + contadorArtEspeciales + " artículos especiales");
    }
    
    private String formatearFecha(DTFecha fecha) {
        if (fecha == null) {
            return "N/A";
        }
        return String.format("%02d/%02d/%04d", fecha.getDia(), fecha.getMes(), fecha.getAnio());
    }
    
    private void actualizarEstadisticas(int totalDonaciones, int totalLibros, int totalArtEspeciales) {
        lblTotalDonaciones.setText("Total Donaciones: " + totalDonaciones);
        lblTotalLibros.setText("Total Libros: " + totalLibros);
        lblTotalArtEspeciales.setText("Total Artículos Especiales: " + totalArtEspeciales);
    }
    
    /**
     * Método auxiliar para verificar si una fecha de inicio es menor o igual a una fecha de fin
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return true si fechaInicio <= fechaFin, false en caso contrario
     */
    private boolean fechaInicioMenorOIgual(DTFecha fechaInicio, DTFecha fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return false;
        }
        
        // Convertir a días desde una fecha base para comparación
        int diasInicio = fechaInicio.getAnio() * 365 + fechaInicio.getMes() * 30 + fechaInicio.getDia();
        int diasFin = fechaFin.getAnio() * 365 + fechaFin.getMes() * 30 + fechaFin.getDia();
        
        return diasInicio <= diasFin;
    }
}
