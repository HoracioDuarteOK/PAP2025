package pap2025.presentacion;

import pap2025.interfaz.IControladorFachada;
import pap2025.logica.*;
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
 * Ventana para editar información de préstamos
 */
public class EditarInformacionPrestamo extends JInternalFrame {
    
    // Controlador
    private IControladorFachada controladorFachada;
    
    // Componentes de la interfaz
    private JTable tablaPrestamos;
    private DefaultTableModel modeloPrestamos;
    private JButton btnActualizarDatos;
    private JButton btnEditarPrestamo;
    private JButton btnCerrar;
    
    // Panel de edición
    private JPanel panelEdicion;
    private JComboBox<Prestamo> comboPrestamo;
    private JComboBox<Material> comboMaterial;
    private JComboBox<Lector> comboLector;
    private JComboBox<Bibliotecario> comboBibliotecario;
    private JComboBox<EstadoP> comboEstado;
    private JTextField txtFechaSolicitud;
    private JTextField txtFechaDevolucion;
    private JButton btnGuardarCambios;
    private JButton btnCancelarEdicion;
    
    public EditarInformacionPrestamo(IControladorFachada controladorFachada) {
        this.controladorFachada = controladorFachada;
        
        // Configuración del frame interno
        setTitle("Editar Información de Préstamo - Biblioteca");
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
        cargarDatos();
    }
    
    private void initComponents() {
        // Tabla de préstamos
        String[] columnas = {"ID", "Material", "Lector", "Bibliotecario", "Estado", "Fecha Solicitud", "Fecha Devolución"};
        modeloPrestamos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La tabla no es editable directamente
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
        
        // Botones principales
        btnActualizarDatos = new JButton("🔄 Actualizar");
        btnActualizarDatos.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizarDatos.setPreferredSize(new Dimension(120, 30));
        btnActualizarDatos.setBackground(new Color(255, 165, 0)); // Naranja
        btnActualizarDatos.setForeground(Color.WHITE);
        
        btnEditarPrestamo = new JButton("✏️ Editar Préstamo");
        btnEditarPrestamo.setFont(new Font("Arial", Font.BOLD, 12));
        btnEditarPrestamo.setPreferredSize(new Dimension(150, 30));
        btnEditarPrestamo.setBackground(new Color(70, 130, 180)); // Azul
        btnEditarPrestamo.setForeground(Color.WHITE);
        
        btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setPreferredSize(new Dimension(120, 30));
        btnCerrar.setBackground(new Color(220, 20, 60)); // Rojo
        btnCerrar.setForeground(Color.WHITE);
        
        // Panel de edición (inicialmente oculto)
        panelEdicion = new JPanel();
        panelEdicion.setBorder(BorderFactory.createTitledBorder("Editar Información del Préstamo"));
        panelEdicion.setVisible(false);
        
        // Componentes del panel de edición
        comboPrestamo = new JComboBox<>();
        comboPrestamo.setFont(new Font("Arial", Font.PLAIN, 12));
        comboPrestamo.setPreferredSize(new Dimension(300, 30));
        comboPrestamo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Prestamo) {
                    Prestamo prestamo = (Prestamo) value;
                    try {
                        // Solo usar propiedades que no requieren lazy loading
                        setText("🆔 Préstamo ID: " + prestamo.getId() + " - Estado: " + prestamo.getEstadoP());
                    } catch (Exception e) {
                        // Si hay error, mostrar solo el ID
                        setText("🆔 Préstamo ID: " + prestamo.getId());
                    }
                } else {
                    setText(value != null ? value.toString() : "");
                }
                return this;
            }
        });
        
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
                    setText("🏢 " + bibliotecario.getNombre() + " (Nro: " + bibliotecario.getNroEmpleado() + ")");
                }
                return this;
            }
        });
        
        comboEstado = new JComboBox<>(EstadoP.values());
        comboEstado.setFont(new Font("Arial", Font.PLAIN, 12));
        comboEstado.setPreferredSize(new Dimension(150, 30));
        
        txtFechaSolicitud = new JTextField(15);
        txtFechaSolicitud.setFont(new Font("Arial", Font.PLAIN, 12));
        txtFechaSolicitud.setPreferredSize(new Dimension(150, 30));
        txtFechaSolicitud.setToolTipText("Formato: DD/MM/AAAA");
        
        txtFechaDevolucion = new JTextField(15);
        txtFechaDevolucion.setFont(new Font("Arial", Font.PLAIN, 12));
        txtFechaDevolucion.setPreferredSize(new Dimension(150, 30));
        txtFechaDevolucion.setToolTipText("Formato: DD/MM/AAAA");
        
        btnGuardarCambios = new JButton("💾 Guardar Cambios");
        btnGuardarCambios.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardarCambios.setPreferredSize(new Dimension(150, 35));
        btnGuardarCambios.setBackground(new Color(34, 139, 34)); // Verde
        btnGuardarCambios.setForeground(Color.WHITE);
        
        btnCancelarEdicion = new JButton("❌ Cancelar");
        btnCancelarEdicion.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelarEdicion.setPreferredSize(new Dimension(120, 35));
        btnCancelarEdicion.setBackground(new Color(220, 20, 60)); // Rojo
        btnCancelarEdicion.setForeground(Color.WHITE);
    }
    
    private void initLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con botones
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(btnActualizarDatos);
        panelSuperior.add(btnEditarPrestamo);
        panelSuperior.add(btnCerrar);
        
        // Panel central con tabla
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        
        // Panel de edición
        initPanelEdicion();
        
        // Agregar componentes a la ventana
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelEdicion, BorderLayout.SOUTH);
    }
    
    private void initPanelEdicion() {
        panelEdicion.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Fila 1: Préstamo seleccionado
        gbc.gridx = 0; gbc.gridy = 0;
        panelEdicion.add(new JLabel("Préstamo:"), gbc);
        gbc.gridx = 1;
        panelEdicion.add(comboPrestamo, gbc);
        
        // Fila 2: Material
        gbc.gridx = 0; gbc.gridy = 1;
        panelEdicion.add(new JLabel("Material:"), gbc);
        gbc.gridx = 1;
        panelEdicion.add(comboMaterial, gbc);
        
        // Fila 3: Lector
        gbc.gridx = 0; gbc.gridy = 2;
        panelEdicion.add(new JLabel("Lector:"), gbc);
        gbc.gridx = 1;
        panelEdicion.add(comboLector, gbc);
        
        // Fila 4: Bibliotecario
        gbc.gridx = 0; gbc.gridy = 3;
        panelEdicion.add(new JLabel("Bibliotecario:"), gbc);
        gbc.gridx = 1;
        panelEdicion.add(comboBibliotecario, gbc);
        
        // Fila 5: Estado
        gbc.gridx = 0; gbc.gridy = 4;
        panelEdicion.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        panelEdicion.add(comboEstado, gbc);
        
        // Fila 6: Fecha Solicitud
        gbc.gridx = 0; gbc.gridy = 5;
        panelEdicion.add(new JLabel("Fecha Solicitud:"), gbc);
        gbc.gridx = 1;
        panelEdicion.add(txtFechaSolicitud, gbc);
        
        // Fila 7: Fecha Devolución
        gbc.gridx = 0; gbc.gridy = 6;
        panelEdicion.add(new JLabel("Fecha Devolución:"), gbc);
        gbc.gridx = 1;
        panelEdicion.add(txtFechaDevolucion, gbc);
        
        // Fila 8: Botones
        gbc.gridx = 0; gbc.gridy = 7;
        panelEdicion.add(btnGuardarCambios, gbc);
        gbc.gridx = 1;
        panelEdicion.add(btnCancelarEdicion, gbc);
    }
    
    private void initEvents() {
        btnActualizarDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
                JOptionPane.showMessageDialog(EditarInformacionPrestamo.this, 
                    "✅ Datos actualizados correctamente!\n\n" +
                    "📋 Préstamos cargados: " + modeloPrestamos.getRowCount(),
                    "Datos Actualizados", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        btnEditarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaPrestamos.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(EditarInformacionPrestamo.this, 
                        "⚠️ Por favor, seleccione un préstamo de la tabla para editar.",
                        "Selección Requerida", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Obtener el ID del préstamo seleccionado
                Integer prestamoId = (Integer) modeloPrestamos.getValueAt(filaSeleccionada, 0);
                abrirPanelEdicion(prestamoId);
            }
        });
        
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        btnGuardarCambios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });
        
        btnCancelarEdicion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarPanelEdicion();
            }
        });
    }
    
    private void cargarDatos() {
        try {
            System.out.println("DEBUG: Iniciando carga de datos...");
            
            // Limpiar tabla
            modeloPrestamos.setRowCount(0);
            
            // Cargar préstamos
            List<Prestamo> prestamos = controladorFachada.getListaPrestamos();
            System.out.println("DEBUG: Préstamos encontrados: " + prestamos.size());
            
            // Cargar listas completas para evitar lazy loading
            List<Material> materiales = controladorFachada.getListaMateriales();
            List<Lector> lectores = controladorFachada.getListaLectores();
            List<Bibliotecario> bibliotecarios = controladorFachada.getListaBibliotecarios();
            
            for (Prestamo prestamo : prestamos) {
                Object[] fila = new Object[7];
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
                
                // Lector - buscar por ID para obtener nombre
                String nombreLector = "Lector ID: " + prestamo.getLector().getId();
                try {
                    for (Lector lector : lectores) {
                        if (lector != null && lector.getId() == prestamo.getLector().getId()) {
                            nombreLector = lector.getNombre();
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Error al obtener nombre del lector: " + e.getMessage());
                }
                fila[2] = nombreLector;
                
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
                fila[3] = nombreBibliotecario;
                
                fila[4] = prestamo.getEstadoP();
                fila[5] = formatearFecha(prestamo.getFechaSolicitada());
                fila[6] = formatearFecha(prestamo.getFechaDevolucion());
                
                modeloPrestamos.addRow(fila);
                System.out.println("DEBUG: Agregado préstamo: " + prestamo.getId() + " - Estado: " + prestamo.getEstadoP());
            }
            
            System.out.println("DEBUG: Carga de datos completada");
            
        } catch (Exception e) {
            System.err.println("⚠️ Error al cargar datos: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "❌ Error al cargar los datos:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirPanelEdicion(Integer prestamoId) {
        try {
            // Buscar el préstamo seleccionado
            Prestamo prestamoSeleccionado = null;
            List<Prestamo> prestamos = controladorFachada.getListaPrestamos();
            for (Prestamo prestamo : prestamos) {
                if (prestamo.getId() == prestamoId) {
                    prestamoSeleccionado = prestamo;
                    break;
                }
            }
            
            if (prestamoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, 
                    "❌ No se pudo encontrar el préstamo seleccionado.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Cargar datos en los combos
            cargarDatosParaEdicion();
            
            // Seleccionar el préstamo en el combo
            comboPrestamo.setSelectedItem(prestamoSeleccionado);
            
            // Cargar información del préstamo seleccionado
            cargarInformacionPrestamo(prestamoSeleccionado);
            
            // Mostrar panel de edición
            panelEdicion.setVisible(true);
            pack(); // Ajustar tamaño de la ventana
            
        } catch (Exception e) {
            System.err.println("⚠️ Error al abrir panel de edición: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "❌ Error al abrir el panel de edición:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarDatosParaEdicion() {
        // Cargar préstamos
        comboPrestamo.removeAllItems();
        List<Prestamo> prestamos = controladorFachada.getListaPrestamos();
        for (Prestamo prestamo : prestamos) {
            comboPrestamo.addItem(prestamo);
        }
        
        // Cargar materiales
        comboMaterial.removeAllItems();
        List<Material> materiales = controladorFachada.getListaMateriales();
        for (Material material : materiales) {
            comboMaterial.addItem(material);
        }
        
        // Cargar lectores
        comboLector.removeAllItems();
        List<Lector> lectores = controladorFachada.getListaLectores();
        for (Lector lector : lectores) {
            comboLector.addItem(lector);
        }
        
        // Cargar bibliotecarios
        comboBibliotecario.removeAllItems();
        List<Bibliotecario> bibliotecarios = controladorFachada.getListaBibliotecarios();
        for (Bibliotecario bibliotecario : bibliotecarios) {
            comboBibliotecario.addItem(bibliotecario);
        }
    }
    
    private void cargarInformacionPrestamo(Prestamo prestamo) {
        try {
            // Buscar material por ID para evitar lazy loading
            Material materialSeleccionado = null;
            for (int i = 0; i < comboMaterial.getItemCount(); i++) {
                Material material = comboMaterial.getItemAt(i);
                if (material != null && material.getId() == prestamo.getMaterial().getId()) {
                    materialSeleccionado = material;
                    break;
                }
            }
            comboMaterial.setSelectedItem(materialSeleccionado);
            
            // Buscar lector por ID para evitar lazy loading
            Lector lectorSeleccionado = null;
            for (int i = 0; i < comboLector.getItemCount(); i++) {
                Lector lector = comboLector.getItemAt(i);
                if (lector != null && lector.getId() == prestamo.getLector().getId()) {
                    lectorSeleccionado = lector;
                    break;
                }
            }
            comboLector.setSelectedItem(lectorSeleccionado);
            
            // Buscar bibliotecario por ID para evitar lazy loading
            Bibliotecario bibliotecarioSeleccionado = null;
            for (int i = 0; i < comboBibliotecario.getItemCount(); i++) {
                Bibliotecario bibliotecario = comboBibliotecario.getItemAt(i);
                if (bibliotecario != null && bibliotecario.getId() == prestamo.getBibliotecario().getId()) {
                    bibliotecarioSeleccionado = bibliotecario;
                    break;
                }
            }
            comboBibliotecario.setSelectedItem(bibliotecarioSeleccionado);
            
            // Seleccionar estado (no requiere lazy loading)
            comboEstado.setSelectedItem(prestamo.getEstadoP());
            
            // Cargar fechas (no requieren lazy loading)
            txtFechaSolicitud.setText(formatearFecha(prestamo.getFechaSolicitada()));
            txtFechaDevolucion.setText(formatearFecha(prestamo.getFechaDevolucion()));
            
        } catch (Exception e) {
            System.err.println("⚠️ Error al cargar información del préstamo: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "❌ Error al cargar la información del préstamo:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void guardarCambios() {
        try {
            Prestamo prestamoSeleccionado = (Prestamo) comboPrestamo.getSelectedItem();
            if (prestamoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ No hay préstamo seleccionado.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener nuevos valores
            Material nuevoMaterial = (Material) comboMaterial.getSelectedItem();
            Lector nuevoLector = (Lector) comboLector.getSelectedItem();
            Bibliotecario nuevoBibliotecario = (Bibliotecario) comboBibliotecario.getSelectedItem();
            EstadoP nuevoEstado = (EstadoP) comboEstado.getSelectedItem();
            
            // Validar que todos los campos estén seleccionados
            if (nuevoMaterial == null || nuevoLector == null || nuevoBibliotecario == null || nuevoEstado == null) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ Todos los campos son obligatorios.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar y parsear fechas
            DTFecha nuevaFechaSolicitud = parsearFecha(txtFechaSolicitud.getText().trim());
            DTFecha nuevaFechaDevolucion = parsearFecha(txtFechaDevolucion.getText().trim());
            
            if (nuevaFechaSolicitud == null || nuevaFechaDevolucion == null) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ Las fechas deben tener el formato DD/MM/AAAA y ser válidas.",
                    "Error de Formato", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Confirmar cambios
            int respuesta = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de que desea guardar los cambios?\n\n" +
                "Préstamo ID: " + prestamoSeleccionado.getId() + "\n" +
                "Nuevo Material: " + (nuevoMaterial instanceof Libro ? ((Libro) nuevoMaterial).getTitulo() : 
                                      nuevoMaterial instanceof ArtEspeciales ? ((ArtEspeciales) nuevoMaterial).getDescripcion() : "Material ID: " + nuevoMaterial.getId()) + "\n" +
                "Nuevo Lector: " + nuevoLector.getEmail() + "\n" +
                "Nuevo Bibliotecario: " + nuevoBibliotecario.getNroEmpleado() + "\n" +
                "Nuevo Estado: " + nuevoEstado + "\n" +
                "Nueva Fecha Solicitud: " + formatearFecha(nuevaFechaSolicitud) + "\n" +
                "Nueva Fecha Devolución: " + formatearFecha(nuevaFechaDevolucion),
                "Confirmar Cambios", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
            
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            
            // Actualizar el préstamo completamente
            boolean actualizado = controladorFachada.actualizarPrestamoCompleto(
                prestamoSeleccionado.getId(),
                nuevoMaterial,
                nuevoLector,
                nuevoBibliotecario,
                nuevoEstado,
                nuevaFechaSolicitud,
                nuevaFechaDevolucion
            );
            
            if (!actualizado) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error al actualizar el préstamo. Verifique los datos e intente nuevamente.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("✅ Préstamo actualizado exitosamente:");
            System.out.println("   🆔 ID: " + prestamoSeleccionado.getId());
            System.out.println("   📊 Nuevo Estado: " + nuevoEstado);
            System.out.println("   📅 Nueva Fecha Solicitud: " + formatearFecha(nuevaFechaSolicitud));
            System.out.println("   📅 Nueva Fecha Devolución: " + formatearFecha(nuevaFechaDevolucion));
            
            JOptionPane.showMessageDialog(this, 
                "✅ Préstamo actualizado exitosamente!\n\n" +
                "🆔 ID: " + prestamoSeleccionado.getId() + "\n" +
                "📚 Material: " + (nuevoMaterial instanceof Libro ? ((Libro) nuevoMaterial).getTitulo() : 
                                  nuevoMaterial instanceof ArtEspeciales ? ((ArtEspeciales) nuevoMaterial).getDescripcion() : "Material ID: " + nuevoMaterial.getId()) + "\n" +
                "👤 Lector: " + nuevoLector.getEmail() + "\n" +
                "🏢 Bibliotecario: " + nuevoBibliotecario.getNroEmpleado() + "\n" +
                "📊 Estado: " + nuevoEstado + "\n" +
                "📅 Fecha Solicitud: " + formatearFecha(nuevaFechaSolicitud) + "\n" +
                "📅 Fecha Devolución: " + formatearFecha(nuevaFechaDevolucion),
                "Préstamo Actualizado", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Cerrar panel de edición y actualizar datos
            cerrarPanelEdicion();
            cargarDatos();
            
        } catch (Exception e) {
            System.err.println("⚠️ Error al guardar cambios: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "❌ Error al guardar los cambios:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cerrarPanelEdicion() {
        panelEdicion.setVisible(false);
        pack(); // Ajustar tamaño de la ventana
    }
    
    private String formatearFecha(DTFecha fecha) {
        if (fecha == null) {
            return "N/A";
        }
        return fecha.getDia() + "/" + fecha.getMes() + "/" + fecha.getAnio();
    }
    
    private DTFecha parsearFecha(String fechaStr) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            // Formato esperado: DD/MM/AAAA
            String[] partes = fechaStr.trim().split("/");
            if (partes.length != 3) {
                return null;
            }
            
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);
            
            // Validaciones básicas
            if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || anio < 1900 || anio > 2100) {
                return null;
            }
            
            return new DTFecha(dia, mes, anio);
            
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
