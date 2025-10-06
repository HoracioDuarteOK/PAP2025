package pap2025.presentacion;

import pap2025.logica.*;
import pap2025.datatypes.DTFecha;
import pap2025.interfaz.IControladorFachada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Ventana para gestionar préstamos de materiales
 */
public class GestionarPrestamos extends JInternalFrame {
    
    // Controlador
    private IControladorFachada controladorFachada;
    
    // Componentes de la interfaz
    private JComboBox<Material> comboMaterial;
    private JComboBox<Lector> comboLector;
    private JComboBox<Bibliotecario> comboBibliotecario;
    private JTextField txtFechaDevolucion;
    
    // Tabla de préstamos
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    
    // Botones
    private JButton btnCrearPrestamo;
    private JButton btnActualizarEstado;
    private JButton btnActualizar;
    private JButton btnCerrar;
    

    

    
    public GestionarPrestamos(IControladorFachada controladorFachada) {
        this.controladorFachada = controladorFachada;
        
        // Configuración del frame interno
        setTitle("Gestión de Préstamos - Biblioteca");
        setSize(900, 700);
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
        cargarDatos();
    }
    
    private void initComponents() {
        // Combo boxes para selección
        comboMaterial = new JComboBox<>();
        comboMaterial.setFont(new Font("Arial", Font.PLAIN, 12));
        comboMaterial.setPreferredSize(new Dimension(300, 30));
        comboMaterial.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Material) {
                    Material material = (Material) value;
                    if (material instanceof Libro) {
                        setText("📚 " + ((Libro) material).getTitulo() + " (Libro)");
                    } else if (material instanceof ArtEspeciales) {
                        setText("🎨 " + ((ArtEspeciales) material).getDescripcion() + " (Art. Especial)");
                    } else {
                        setText("Material ID: " + material.getId());
                    }
                }
                return this;
            }
        });
        
        comboLector = new JComboBox<>();
        comboLector.setFont(new Font("Arial", Font.PLAIN, 12));
        comboLector.setPreferredSize(new Dimension(300, 30));
        comboLector.setRenderer(new DefaultListCellRenderer() {
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
        
        comboBibliotecario = new JComboBox<>();
        comboBibliotecario.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBibliotecario.setPreferredSize(new Dimension(300, 30));
        comboBibliotecario.setRenderer(new DefaultListCellRenderer() {
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
        
        // Campo de fecha de devolución
        txtFechaDevolucion = new JTextField(10);
        txtFechaDevolucion.setFont(new Font("Arial", Font.PLAIN, 12));
        txtFechaDevolucion.setPreferredSize(new Dimension(120, 30));
        txtFechaDevolucion.setToolTipText("Formato: DD/MM/AAAA");
        txtFechaDevolucion.setText(LocalDate.now().plusDays(15).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        

        
        // Botones
        btnCrearPrestamo = new JButton("📚 Crear Préstamo");
        btnCrearPrestamo.setFont(new Font("Arial", Font.BOLD, 12));
        btnCrearPrestamo.setPreferredSize(new Dimension(150, 35));
        btnCrearPrestamo.setBackground(new Color(34, 139, 34)); // Verde
        btnCrearPrestamo.setForeground(Color.WHITE);
        

        
        btnActualizar = new JButton("🔄 Actualizar");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizar.setPreferredSize(new Dimension(120, 30));
        btnActualizar.setBackground(new Color(255, 165, 0)); // Naranja
        btnActualizar.setForeground(Color.WHITE);
        
        btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setPreferredSize(new Dimension(120, 30));
        btnCerrar.setBackground(new Color(220, 20, 60)); // Rojo
        btnCerrar.setForeground(Color.WHITE);
        
        // Tabla de préstamos
        String[] columnas = {"ID", "Material", "Lector", "Bibliotecario", "Fecha Solicitud", "Fecha Devolución", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaPrestamos.setRowHeight(25);
        tablaPrestamos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaPrestamos.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaPrestamos.getTableHeader().setForeground(Color.WHITE);
        

    }
    
    private void initLayout() {
        setLayout(new BorderLayout());
        
        // Título principal
        JLabel tituloLabel = new JLabel("GESTIÓN DE PRÉSTAMOS - BIBLIOTECA");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(70, 130, 180));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tituloLabel.setBackground(new Color(248, 248, 248));
        tituloLabel.setOpaque(true);
        
        // Panel superior con formularios
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(248, 248, 248));
        
        // Panel para crear préstamos
        JPanel panelCrearPrestamo = new JPanel(new GridBagLayout());
        panelCrearPrestamo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(34, 139, 34), 2),
            "📚 Crear Nuevo Préstamo",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            new Color(34, 139, 34)
        ));
        panelCrearPrestamo.setBackground(new Color(248, 248, 248));
        
        // Botón de actualizar datos en la parte superior
        JButton btnActualizarDatos = new JButton("🔄 Actualizar Datos");
        btnActualizarDatos.setFont(new Font("Arial", Font.BOLD, 11));
        btnActualizarDatos.setPreferredSize(new Dimension(140, 25));
        btnActualizarDatos.setBackground(new Color(255, 165, 0)); // Naranja
        btnActualizarDatos.setForeground(Color.WHITE);
        btnActualizarDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
                JOptionPane.showMessageDialog(GestionarPrestamos.this, 
                    "✅ Datos actualizados correctamente!\n\n" +
                    "📚 Materiales disponibles: " + comboMaterial.getItemCount() + "\n" +
                    "👤 Lectores activos: " + comboLector.getItemCount() + "\n" +
                    "🏢 Bibliotecarios: " + comboBibliotecario.getItemCount(),
                    "Datos Actualizados", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Primera fila: Material y botón actualizar
        gbc.gridx = 0; gbc.gridy = 0;
        panelCrearPrestamo.add(new JLabel("Material:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 1;
        panelCrearPrestamo.add(comboMaterial, gbc);
        
        gbc.gridx = 2;
        panelCrearPrestamo.add(btnActualizarDatos, gbc);
        
        // Segunda fila: Lector
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panelCrearPrestamo.add(new JLabel("Lector:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelCrearPrestamo.add(comboLector, gbc);
        
        // Tercera fila: Bibliotecario
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        panelCrearPrestamo.add(new JLabel("Bibliotecario:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelCrearPrestamo.add(comboBibliotecario, gbc);
        
        // Cuarta fila: Fecha devolución y botón crear
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        panelCrearPrestamo.add(new JLabel("Fecha Devolución:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 1;
        panelCrearPrestamo.add(txtFechaDevolucion, gbc);
        
        gbc.gridx = 2;
        panelCrearPrestamo.add(btnCrearPrestamo, gbc);
        
        // Agregar panel al panel superior
        panelSuperior.add(panelCrearPrestamo, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(248, 248, 248));
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCerrar);
        
        // Agregar componentes al frame
        add(tituloLabel, BorderLayout.NORTH);
        add(panelSuperior, BorderLayout.CENTER);
        add(new JScrollPane(tablaPrestamos), BorderLayout.SOUTH);
        
        // Panel inferior con botones
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(248, 248, 248));
        panelInferior.add(panelBotones, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void initEvents() {
        btnCrearPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPrestamo();
            }
        });
        

        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
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
        System.out.println("DEBUG: Iniciando carga de datos...");
        
        // Cargar materiales disponibles
        comboMaterial.removeAllItems();
        List<Material> materialesDisponibles = controladorFachada.obtenerMaterialesDisponibles();
        System.out.println("DEBUG: Materiales disponibles encontrados: " + materialesDisponibles.size());
        for (Material material : materialesDisponibles) {
            comboMaterial.addItem(material);
            System.out.println("DEBUG: Agregado material disponible: " + material.getId() + " - " + 
                             (material instanceof Libro ? ((Libro) material).getTitulo() : 
                              material instanceof ArtEspeciales ? ((ArtEspeciales) material).getDescripcion() : "Material"));
        }
        
        // Cargar lectores activos
        comboLector.removeAllItems();
        List<Lector> lectores = controladorFachada.obtenerLectoresPorEstado(EstadoL.ACTIVO);
        System.out.println("DEBUG: Lectores activos encontrados: " + lectores.size());
        for (Lector lector : lectores) {
            comboLector.addItem(lector);
            System.out.println("DEBUG: Agregado lector: " + lector.getNombre() + " (" + lector.getEmail() + ")");
        }
        
        // Cargar bibliotecarios
        comboBibliotecario.removeAllItems();
        List<Bibliotecario> bibliotecarios = controladorFachada.getListaBibliotecarios();
        System.out.println("DEBUG: Bibliotecarios encontrados: " + bibliotecarios.size());
        for (Bibliotecario bibliotecario : bibliotecarios) {
            comboBibliotecario.addItem(bibliotecario);
            System.out.println("DEBUG: Agregado bibliotecario: " + bibliotecario.getNombre() + " (Nro. " + bibliotecario.getNroEmpleado() + ")");
        }
        

        
        // Cargar tabla de préstamos
        cargarTablaPrestamos();
        
        System.out.println("DEBUG: Carga de datos completada");
    }
    
    private void cargarTablaPrestamos() {
        modeloTabla.setRowCount(0);
        List<Prestamo> prestamos = controladorFachada.getListaPrestamos();
        
        for (Prestamo prestamo : prestamos) {
            Object[] fila = new Object[7];
            fila[0] = prestamo.getId();
            
            // Material
            if (prestamo.getMaterial() instanceof Libro) {
                fila[1] = "📚 " + ((Libro) prestamo.getMaterial()).getTitulo();
            } else if (prestamo.getMaterial() instanceof ArtEspeciales) {
                fila[1] = "🎨 " + ((ArtEspeciales) prestamo.getMaterial()).getDescripcion();
            } else {
                fila[1] = "Material ID: " + prestamo.getMaterial().getId();
            }
            
            fila[2] = "Lector ID: " + prestamo.getLector().getId();
            fila[3] = "Bibliotecario ID: " + prestamo.getBibliotecario().getId();
            fila[4] = formatearFecha(prestamo.getFechaSolicitada());
            fila[5] = formatearFecha(prestamo.getFechaDevolucion());
            
            // Estado con emoji
            switch (prestamo.getEstadoP()) {
                case ENCURSO:
                    fila[6] = "🔄 EN CURSO";
                    break;
                case DEVUELTO:
                    fila[6] = "✅ DEVUELTO";
                    break;
                case PENDIENTE:
                    fila[6] = "⏳ PENDIENTE";
                    break;
                default:
                    fila[6] = prestamo.getEstadoP().toString();
            }
            
            modeloTabla.addRow(fila);
        }
    }
    
    private void crearPrestamo() {
        // Validar selecciones
        if (comboMaterial.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "❌ Por favor seleccione un material", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (comboLector.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "❌ Por favor seleccione un lector", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (comboBibliotecario.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "❌ Por favor seleccione un bibliotecario", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Parsear fecha de devolución
        DTFecha fechaDevolucion = parsearFecha(txtFechaDevolucion.getText().trim());
        if (fechaDevolucion == null) {
            JOptionPane.showMessageDialog(this, "❌ Formato de fecha inválido. Use DD/MM/AAAA", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear préstamo
        Material material = (Material) comboMaterial.getSelectedItem();
        Lector lector = (Lector) comboLector.getSelectedItem();
        Bibliotecario bibliotecario = (Bibliotecario) comboBibliotecario.getSelectedItem();
        
        Integer idPrestamo = controladorFachada.crearPrestamo(material, lector, bibliotecario, fechaDevolucion);
        
        if (idPrestamo != null) {
            JOptionPane.showMessageDialog(this, 
                "✅ Préstamo creado exitosamente!\n\n" +
                "🆔 ID del Préstamo: " + idPrestamo + "\n" +
                "📚 Material: " + (material instanceof Libro ? ((Libro) material).getTitulo() : 
                                   material instanceof ArtEspeciales ? ((ArtEspeciales) material).getDescripcion() : "Material ID: " + material.getId()) + "\n" +
                "👤 Lector: " + lector.getEmail() + "\n" +
                "🏢 Bibliotecario: " + bibliotecario.getNroEmpleado() + "\n" +
                "📅 Fecha Devolución: " + txtFechaDevolucion.getText(),
                "Préstamo Creado", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario y actualizar tabla
            limpiarFormulario();
            cargarTablaPrestamos();
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al crear el préstamo.\n\n" +
                "Verifique que:\n" +
                "• El lector esté activo\n" +
                "• Los datos sean válidos",
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    

    
    private void limpiarFormulario() {
        comboMaterial.setSelectedIndex(-1);
        comboLector.setSelectedIndex(-1);
        comboBibliotecario.setSelectedIndex(-1);
        txtFechaDevolucion.setText(LocalDate.now().plusDays(15).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
    
    private DTFecha parsearFecha(String fechaTexto) {
        try {
            String[] partes = fechaTexto.split("/");
            if (partes.length != 3) {
                return null;
            }
            
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);
            
            if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || anio < 2000 || anio > 2100) {
                return null;
            }
            
            return new DTFecha(dia, mes, anio);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private String formatearFecha(DTFecha fecha) {
        if (fecha == null) {
            return "N/A";
        }
        return String.format("%02d/%02d/%04d", fecha.getDia(), fecha.getMes(), fecha.getAnio());
    }
    

}
