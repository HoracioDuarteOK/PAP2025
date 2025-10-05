package pap2025.presentacion;

import pap2025.logica.*;
import pap2025.datatypes.DTFecha;
import pap2025.interfaz.IControladorFachada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Ventana para actualizar el estado de préstamos
 */
public class ActualizarEstadoPrestamo extends JInternalFrame {
    
    // Controlador
    private IControladorFachada controladorFachada;
    
    // Componentes de la interfaz
    private JTextField txtIdPrestamo;
    private JComboBox<EstadoP> comboEstado;
    private JButton btnActualizarEstado;
    private JButton btnActualizar;
    private JButton btnCerrar;
    
    // Tabla de préstamos
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    
    public ActualizarEstadoPrestamo(IControladorFachada controladorFachada) {
        this.controladorFachada = controladorFachada;
        
        // Configuración del frame interno
        setTitle("Actualizar Estado de Préstamo - Biblioteca");
        setSize(800, 600);
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
        cargarTablaPrestamos();
    }
    
    private void initComponents() {
        // Campo para ID del préstamo
        txtIdPrestamo = new JTextField(10);
        txtIdPrestamo.setFont(new Font("Arial", Font.PLAIN, 12));
        txtIdPrestamo.setPreferredSize(new Dimension(100, 30));
        txtIdPrestamo.setToolTipText("ID del préstamo a actualizar");
        
        // Combo para estado
        comboEstado = new JComboBox<>(EstadoP.values());
        comboEstado.setFont(new Font("Arial", Font.PLAIN, 12));
        comboEstado.setPreferredSize(new Dimension(150, 30));
        
        // Botones
        btnActualizarEstado = new JButton("🔄 Actualizar Estado");
        btnActualizarEstado.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizarEstado.setPreferredSize(new Dimension(150, 35));
        btnActualizarEstado.setBackground(new Color(70, 130, 180)); // Azul
        btnActualizarEstado.setForeground(Color.WHITE);
        
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
        JLabel tituloLabel = new JLabel("ACTUALIZAR ESTADO DE PRÉSTAMO - BIBLIOTECA");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(70, 130, 180));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel superior con formulario
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(248, 248, 248));
        
        // Panel para actualizar estado
        JPanel panelActualizarEstado = new JPanel(new GridBagLayout());
        panelActualizarEstado.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            "🔄 Actualizar Estado de Préstamo",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));
        panelActualizarEstado.setBackground(new Color(248, 248, 248));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Primera fila: ID Préstamo
        gbc.gridx = 0; gbc.gridy = 0;
        panelActualizarEstado.add(new JLabel("ID Préstamo:"), gbc);
        
        gbc.gridx = 1;
        panelActualizarEstado.add(txtIdPrestamo, gbc);
        
        // Segunda fila: Estado
        gbc.gridx = 0; gbc.gridy = 1;
        panelActualizarEstado.add(new JLabel("Nuevo Estado:"), gbc);
        
        gbc.gridx = 1;
        panelActualizarEstado.add(comboEstado, gbc);
        
        gbc.gridx = 2;
        panelActualizarEstado.add(btnActualizarEstado, gbc);
        
        // Agregar panel al panel superior
        panelSuperior.add(panelActualizarEstado, BorderLayout.CENTER);
        
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
        btnActualizarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstadoPrestamo();
            }
        });
        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarTablaPrestamos();
            }
        });
        
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
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
    
    private void actualizarEstadoPrestamo() {
        // Validar ID del préstamo
        String idTexto = txtIdPrestamo.getText().trim();
        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Por favor ingrese el ID del préstamo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int idPrestamo = Integer.parseInt(idTexto);
            EstadoP nuevoEstado = (EstadoP) comboEstado.getSelectedItem();
            
            boolean exito = controladorFachada.actualizarEstadoPrestamo(idPrestamo, nuevoEstado);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Estado del préstamo actualizado exitosamente!\n\n" +
                    "🆔 ID del Préstamo: " + idPrestamo + "\n" +
                    "🔄 Nuevo Estado: " + nuevoEstado,
                    "Estado Actualizado", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Limpiar campo y actualizar tabla
                txtIdPrestamo.setText("");
                cargarTablaPrestamos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error al actualizar el estado del préstamo.\n\n" +
                    "Verifique que el ID del préstamo sea válido.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ El ID del préstamo debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String formatearFecha(DTFecha fecha) {
        if (fecha == null) {
            return "N/A";
        }
        return String.format("%02d/%02d/%04d", fecha.getDia(), fecha.getMes(), fecha.getAnio());
    }
}
