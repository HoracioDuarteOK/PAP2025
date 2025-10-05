package pap2025.presentacion;

import pap2025.interfaz.IControladorFachada;
import pap2025.logica.EstadoL;
import pap2025.logica.Lector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para modificar el estado de un lector (SUSPENDIDO o ACTIVO)
 */
public class ModificarEstadoLector extends JInternalFrame {
    
    private IControladorFachada controladorFachada;
    
    // Componentes de la interfaz
    private JTextField txtEmail;
    private JComboBox<EstadoL> cmbEstado;
    private JButton btnBuscar;
    private JButton btnModificar;
    private JButton btnCancelar;
    
    // Panel de información del lector
    private JPanel panelInfoLector;
    private JLabel lblNombre;
    private JLabel lblDireccion;
    private JLabel lblZona;
    private JLabel lblEstadoActual;
    
    public ModificarEstadoLector(IControladorFachada controladorFachada) {
        this.controladorFachada = controladorFachada;
        
        // Configuración del frame - Ventana más grande para mejor visibilidad
        setTitle("Modificar Estado de Lector");
        setSize(600, 500);
        setClosable(true);
        setResizable(false);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        
        // Inicializar componentes
        initComponents();
        initLayout();
        initEvents();
        
        // Centrar en pantalla
        setLocation(100, 100);
    }
    
    private void initComponents() {
        // Campo email más grande y visible
        txtEmail = new JTextField(35);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setPreferredSize(new Dimension(300, 30));
        txtEmail.setToolTipText("Ingrese el email del lector");
        
        // Combo estado más grande
        cmbEstado = new JComboBox<>(EstadoL.values());
        cmbEstado.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbEstado.setPreferredSize(new Dimension(300, 30));
        cmbEstado.setToolTipText("Seleccione el nuevo estado");
        
        // Botones más grandes y con mejor apariencia
        btnBuscar = new JButton("Buscar Lector");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.setPreferredSize(new Dimension(150, 35));
        btnBuscar.setBackground(new Color(70, 130, 180)); // Azul acero
        btnBuscar.setForeground(Color.WHITE);
        
        btnModificar = new JButton("Modificar Estado");
        btnModificar.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificar.setPreferredSize(new Dimension(120, 35));
        btnModificar.setBackground(new Color(34, 139, 34)); // Verde bosque
        btnModificar.setForeground(Color.WHITE);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.setBackground(new Color(220, 20, 60)); // Rojo carmesí
        btnCancelar.setForeground(Color.WHITE);
        
        // Panel de información del lector mejorado
        panelInfoLector = new JPanel();
        panelInfoLector.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            "Información del Lector",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));
        panelInfoLector.setLayout(new GridLayout(4, 2, 15, 10));
        panelInfoLector.setBackground(new Color(248, 248, 248));
        
        lblNombre = new JLabel("");
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDireccion = new JLabel("");
        lblDireccion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblZona = new JLabel("");
        lblZona.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEstadoActual = new JLabel("");
        lblEstadoActual.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Agregar labels al panel con mejor formato
        JLabel lblNombreTitle = new JLabel("Nombre:");
        lblNombreTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombreTitle.setForeground(new Color(70, 130, 180));
        
        JLabel lblDireccionTitle = new JLabel("Dirección:");
        lblDireccionTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblDireccionTitle.setForeground(new Color(70, 130, 180));
        
        JLabel lblZonaTitle = new JLabel("Zona:");
        lblZonaTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblZonaTitle.setForeground(new Color(70, 130, 180));
        
        JLabel lblEstadoTitle = new JLabel("Estado actual:");
        lblEstadoTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblEstadoTitle.setForeground(new Color(70, 130, 180));
        
        panelInfoLector.add(lblNombreTitle);
        panelInfoLector.add(lblNombre);
        panelInfoLector.add(lblDireccionTitle);
        panelInfoLector.add(lblDireccion);
        panelInfoLector.add(lblZonaTitle);
        panelInfoLector.add(lblZona);
        panelInfoLector.add(lblEstadoTitle);
        panelInfoLector.add(lblEstadoActual);
        
        // Inicialmente ocultar el panel de información
        panelInfoLector.setVisible(false);
    }
    
    private void initLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal con mejor organización
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 248, 248));
        
        // Título principal
        JLabel tituloLabel = new JLabel("MODIFICACIÓN DE ESTADO DE LECTOR");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(70, 130, 180));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setBackground(new Color(248, 248, 248));
        searchPanel.add(new JLabel("Email del lector:"));
        searchPanel.add(txtEmail);
        searchPanel.add(btnBuscar);
        
        // Panel de estado
        JPanel estadoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        estadoPanel.setBackground(new Color(248, 248, 248));
        estadoPanel.add(new JLabel("Nuevo estado:"));
        estadoPanel.add(cmbEstado);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(248, 248, 248));
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnCancelar);
        
        // Agregar componentes al panel principal
        mainPanel.add(tituloLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(searchPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(estadoPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(panelInfoLector);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Inicialmente deshabilitar el botón de modificar
        btnModificar.setEnabled(false);
    }
    
    private void initEvents() {
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLector();
            }
        });
        
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarEstado();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Evento para habilitar/deshabilitar el botón de modificar
        txtEmail.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
        });
        
        // Evento para verificar si el estado seleccionado es diferente al actual
        cmbEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarCampos();
            }
        });
    }
    
    private void buscarLector() {
        String email = txtEmail.getText().trim();
        
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese el email del lector", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return;
        }
        
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese un email válido", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return;
        }
        
        // Buscar el lector
        Lector lector = controladorFachada.obtenerLector(email);
        
        if (lector == null) {
            JOptionPane.showMessageDialog(this, 
                "No se encontró un lector con ese email", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            limpiarInformacionLector();
            return;
        }
        
        // Mostrar información del lector
        mostrarInformacionLector(lector);
        
        // Seleccionar el estado opuesto al actual
        if (lector.getEstadoL() == EstadoL.ACTIVO) {
            cmbEstado.setSelectedItem(EstadoL.SUSPENDIDO);
        } else {
            cmbEstado.setSelectedItem(EstadoL.ACTIVO);
        }
        
        // Habilitar el botón de modificar
        btnModificar.setEnabled(true);
    }
    
    private void mostrarInformacionLector(Lector lector) {
        lblNombre.setText(lector.getNombre());
        lblDireccion.setText(lector.getDireccion());
        lblZona.setText(lector.getZona().toString());
        lblEstadoActual.setText(lector.getEstadoL().toString());
        
        panelInfoLector.setVisible(true);
        
        // Cambiar color del estado actual
        if (lector.getEstadoL() == EstadoL.ACTIVO) {
            lblEstadoActual.setForeground(new Color(0, 128, 0)); // Verde
        } else {
            lblEstadoActual.setForeground(new Color(255, 0, 0)); // Rojo
        }
    }
    
    private void limpiarInformacionLector() {
        lblNombre.setText("");
        lblDireccion.setText("");
        lblZona.setText("");
        lblEstadoActual.setText("");
        panelInfoLector.setVisible(false);
        btnModificar.setEnabled(false);
    }
    
    private void modificarEstado() {
        String email = txtEmail.getText().trim();
        EstadoL nuevoEstado = (EstadoL) cmbEstado.getSelectedItem();
        
        // Obtener el lector para verificar el estado actual
        Lector lector = controladorFachada.obtenerLector(email);
        if (lector == null) {
            JOptionPane.showMessageDialog(this, 
                "Error: No se pudo obtener la información del lector", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar que el estado sea diferente
        if (lector.getEstadoL() == nuevoEstado) {
            JOptionPane.showMessageDialog(this, 
                "El nuevo estado debe ser diferente al estado actual", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            cmbEstado.requestFocus();
            return;
        }
        
        // Confirmar la acción con información clara
        String mensajeConfirmacion = String.format(
            "¿Está seguro que desea cambiar el estado del lector?\n\n" +
            "Lector: %s (%s)\n" +
            "Estado actual: %s\n" +
            "Nuevo estado: %s\n\n" +
            "Esta acción %s al lector en el sistema.",
            lector.getNombre(), email, lector.getEstadoL(), nuevoEstado,
            nuevoEstado == EstadoL.SUSPENDIDO ? "suspenderá" : "activará"
        );
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            mensajeConfirmacion, 
            "Confirmar cambio de estado", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Realizar el cambio de estado
        boolean exito = false;
        if (nuevoEstado == EstadoL.SUSPENDIDO) {
            exito = controladorFachada.suspenderLector(email);
        } else {
            exito = controladorFachada.activarLector(email);
        }
        
        if (exito) {
            // Mostrar mensaje de éxito con información detallada
            String mensaje = String.format(
                "✅ Estado del lector modificado exitosamente!\n\n" +
                "📋 Detalles del cambio:\n" +
                "   • Lector: %s\n" +
                "   • Email: %s\n" +
                "   • Estado anterior: %s\n" +
                "   • Nuevo estado: %s\n\n" +
                "El lector ahora está %s en el sistema.",
                lector.getNombre(), email, lector.getEstadoL(), nuevoEstado,
                nuevoEstado == EstadoL.SUSPENDIDO ? "SUSPENDIDO" : "ACTIVO"
            );
            
            JOptionPane.showMessageDialog(this, 
                mensaje, 
                "Cambio de Estado Exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al modificar el estado del lector.\n\n" +
                "Por favor, intente nuevamente o contacte al administrador del sistema.", 
                "Error en el Cambio", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtEmail.setText("");
        limpiarInformacionLector();
        cmbEstado.setSelectedIndex(0);
        btnModificar.setEnabled(false);
        txtEmail.requestFocus();
    }
    
    private void verificarCampos() {
        boolean camposValidos = !txtEmail.getText().trim().isEmpty() && panelInfoLector.isVisible();
        
        // Verificar que el estado seleccionado sea diferente al actual
        if (camposValidos && panelInfoLector.isVisible()) {
            Lector lector = controladorFachada.obtenerLector(txtEmail.getText().trim());
            if (lector != null) {
                EstadoL estadoSeleccionado = (EstadoL) cmbEstado.getSelectedItem();
                camposValidos = lector.getEstadoL() != estadoSeleccionado;
            }
        }
        
        btnModificar.setEnabled(camposValidos);
    }
}
