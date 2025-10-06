package pap2025.presentacion;

import pap2025.interfaz.IControladorFachada;
import pap2025.logica.Lector;
import pap2025.logica.Zona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para cambiar la zona (barrio) de un lector
 */
public class CambiarZonaLector extends JInternalFrame {
    
    private IControladorFachada controladorFachada;
    
    // Componentes de la interfaz
    private JTextField txtEmail;
    private JComboBox<Zona> cmbZona;
    private JButton btnBuscar;
    private JButton btnCambiar;
    private JButton btnCancelar;
    
    // Panel de información del lector
    private JPanel panelInfoLector;
    private JLabel lblNombre;
    private JLabel lblDireccion;
    private JLabel lblZonaActual;
    private JLabel lblEstado;
    
    public CambiarZonaLector(IControladorFachada controladorFachada) {
        this.controladorFachada = controladorFachada;
        
        // Configuración del frame - Ventana más grande para mejor visibilidad
        setTitle("Cambiar Zona de Lector");
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
        
        // Combo zona más grande
        cmbZona = new JComboBox<>(Zona.values());
        cmbZona.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbZona.setPreferredSize(new Dimension(300, 30));
        cmbZona.setToolTipText("Seleccione la nueva zona");
        
        // Botones más grandes y con mejor apariencia
        btnBuscar = new JButton("Buscar Lector");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.setPreferredSize(new Dimension(150, 35));
        btnBuscar.setBackground(new Color(70, 130, 180)); // Azul acero
        btnBuscar.setForeground(Color.WHITE);
        
        btnCambiar = new JButton("Cambiar Zona");
        btnCambiar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCambiar.setPreferredSize(new Dimension(120, 35));
        btnCambiar.setBackground(new Color(34, 139, 34)); // Verde bosque
        btnCambiar.setForeground(Color.WHITE);
        
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
        lblZonaActual = new JLabel("");
        lblZonaActual.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEstado = new JLabel("");
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Agregar labels al panel con mejor formato
        JLabel lblNombreTitle = new JLabel("Nombre:");
        lblNombreTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombreTitle.setForeground(new Color(70, 130, 180));
        
        JLabel lblDireccionTitle = new JLabel("Dirección:");
        lblDireccionTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblDireccionTitle.setForeground(new Color(70, 130, 180));
        
        JLabel lblZonaTitle = new JLabel("Zona actual:");
        lblZonaTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblZonaTitle.setForeground(new Color(70, 130, 180));
        
        JLabel lblEstadoTitle = new JLabel("Estado:");
        lblEstadoTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblEstadoTitle.setForeground(new Color(70, 130, 180));
        
        panelInfoLector.add(lblNombreTitle);
        panelInfoLector.add(lblNombre);
        panelInfoLector.add(lblDireccionTitle);
        panelInfoLector.add(lblDireccion);
        panelInfoLector.add(lblZonaTitle);
        panelInfoLector.add(lblZonaActual);
        panelInfoLector.add(lblEstadoTitle);
        panelInfoLector.add(lblEstado);
        
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
        JLabel tituloLabel = new JLabel("CAMBIO DE ZONA DE LECTOR");
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
        
        // Panel de zona
        JPanel zonaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        zonaPanel.setBackground(new Color(248, 248, 248));
        zonaPanel.add(new JLabel("Nueva zona:"));
        zonaPanel.add(cmbZona);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(248, 248, 248));
        buttonPanel.add(btnCambiar);
        buttonPanel.add(btnCancelar);
        
        // Agregar componentes al panel principal
        mainPanel.add(tituloLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(searchPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(zonaPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(panelInfoLector);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Inicialmente deshabilitar el botón de cambiar
        btnCambiar.setEnabled(false);
    }
    
    private void initEvents() {
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLector();
            }
        });
        
        btnCambiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarZona();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Evento para habilitar/deshabilitar el botón de cambiar
        txtEmail.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
        });
        
        // Evento para verificar si la zona seleccionada es diferente a la actual
        cmbZona.addActionListener(new ActionListener() {
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
        
        // Seleccionar la zona actual en el combo
        cmbZona.setSelectedItem(lector.getZona());
        
        // Habilitar el botón de cambiar
        btnCambiar.setEnabled(true);
    }
    
    private void mostrarInformacionLector(Lector lector) {
        lblNombre.setText(lector.getNombre());
        lblDireccion.setText(lector.getDireccion());
        lblZonaActual.setText(lector.getZona().toString());
        lblEstado.setText(lector.getEstadoL().toString());
        
        panelInfoLector.setVisible(true);
        
        // Cambiar color del estado
        if (lector.getEstadoL().toString().equals("ACTIVO")) {
            lblEstado.setForeground(new Color(0, 128, 0)); // Verde
        } else {
            lblEstado.setForeground(new Color(255, 0, 0)); // Rojo
        }
        
        // Resaltar la zona actual
        lblZonaActual.setForeground(new Color(0, 0, 128)); // Azul
    }
    
    private void limpiarInformacionLector() {
        lblNombre.setText("");
        lblDireccion.setText("");
        lblZonaActual.setText("");
        lblEstado.setText("");
        panelInfoLector.setVisible(false);
        btnCambiar.setEnabled(false);
    }
    
    private void cambiarZona() {
        String email = txtEmail.getText().trim();
        Zona nuevaZona = (Zona) cmbZona.getSelectedItem();
        
        // Obtener el lector para verificar la zona actual
        Lector lector = controladorFachada.obtenerLector(email);
        if (lector == null) {
            JOptionPane.showMessageDialog(this, 
                "Error: No se pudo obtener la información del lector", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar que la zona sea diferente
        if (lector.getZona() == nuevaZona) {
            JOptionPane.showMessageDialog(this, 
                "La nueva zona debe ser diferente a la zona actual", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            cmbZona.requestFocus();
            return;
        }
        
        // Confirmar la acción con información clara
        String mensajeConfirmacion = String.format(
            "¿Está seguro que desea cambiar la zona del lector?\n\n" +
            "Lector: %s (%s)\n" +
            "Zona actual: %s\n" +
            "Nueva zona: %s\n\n" +
            "Esta acción actualizará la ubicación del lector en el sistema.",
            lector.getNombre(), email, lector.getZona(), nuevaZona
        );
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            mensajeConfirmacion, 
            "Confirmar cambio de zona", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Realizar el cambio de zona
        boolean exito = controladorFachada.cambiarZonaLector(email, nuevaZona);
        
        if (exito) {
            // Mostrar mensaje de éxito con información detallada
            String mensaje = String.format(
                "✅ Zona del lector cambiada exitosamente!\n\n" +
                "📋 Detalles del cambio:\n" +
                "   • Lector: %s\n" +
                "   • Email: %s\n" +
                "   • Zona anterior: %s\n" +
                "   • Nueva zona: %s\n\n" +
                "La ubicación del lector ha sido actualizada en el sistema.",
                lector.getNombre(), email, lector.getZona(), nuevaZona
            );
            
            JOptionPane.showMessageDialog(this, 
                mensaje, 
                "Cambio de Zona Exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al cambiar la zona del lector.\n\n" +
                "Por favor, intente nuevamente o contacte al administrador del sistema.", 
                "Error en el Cambio", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtEmail.setText("");
        limpiarInformacionLector();
        cmbZona.setSelectedIndex(0);
        btnCambiar.setEnabled(false);
        txtEmail.requestFocus();
    }
    
    private void verificarCampos() {
        boolean camposValidos = !txtEmail.getText().trim().isEmpty() && panelInfoLector.isVisible();
        
        // Verificar que la zona seleccionada sea diferente a la actual
        if (camposValidos && panelInfoLector.isVisible()) {
            Lector lector = controladorFachada.obtenerLector(txtEmail.getText().trim());
            if (lector != null) {
                Zona zonaSeleccionada = (Zona) cmbZona.getSelectedItem();
                camposValidos = lector.getZona() != zonaSeleccionada;
            }
        }
        
        btnCambiar.setEnabled(camposValidos);
    }
}
