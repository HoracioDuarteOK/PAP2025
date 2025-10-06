package pap2025.presentacion;

import pap2025.interfaz.IControladorFachada;
import pap2025.logica.Zona;
import pap2025.datatypes.DTFecha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistrarLector extends JInternalFrame {
    
    private IControladorFachada controladorFachada;
    
    // Componentes de la interfaz
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtDireccion;
    private JTextField txtFechaRegistro;
    private JComboBox<Zona> cmbZona;
    private JButton btnAceptar;
    private JButton btnCancelar;
    
    public RegistrarLector(IControladorFachada controladorFachada) {
        this.controladorFachada = controladorFachada;
        
        // Configuración del frame - Ventana más grande para mejor visibilidad
        setTitle("Registrar Nuevo Lector");
        setSize(600, 450);
        setClosable(true);
        setResizable(false);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        
        // Inicializar componentes
        initComponents();
        initLayout();
        initEvents();
        
        // Centrar en pantalla (JInternalFrame no tiene setLocationRelativeTo)
        setLocation(100, 100);
    }
    
    private void initComponents() {
        // Campos de texto más grandes y con mejor apariencia
        txtNombre = new JTextField(35);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNombre.setPreferredSize(new Dimension(300, 30));
        
        txtEmail = new JTextField(35);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setPreferredSize(new Dimension(300, 30));
        
        txtDireccion = new JTextField(35);
        txtDireccion.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDireccion.setPreferredSize(new Dimension(300, 30));
        
        txtFechaRegistro = new JTextField(35);
        txtFechaRegistro.setFont(new Font("Arial", Font.PLAIN, 14));
        txtFechaRegistro.setPreferredSize(new Dimension(300, 30));
        txtFechaRegistro.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtFechaRegistro.setEditable(false);
        txtFechaRegistro.setBackground(new Color(240, 240, 240)); // Color gris claro para campo no editable
        
        cmbZona = new JComboBox<>(Zona.values());
        cmbZona.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbZona.setPreferredSize(new Dimension(300, 30));
        
        // Botones más grandes y con mejor apariencia
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAceptar.setPreferredSize(new Dimension(120, 35));
        btnAceptar.setBackground(new Color(70, 130, 180)); // Azul acero
        btnAceptar.setForeground(Color.WHITE);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.setBackground(new Color(220, 20, 60)); // Rojo carmesí
        btnCancelar.setForeground(Color.WHITE);
    }
    
    private void initLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal con GridBagLayout para mejor organización
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 248, 248)); // Fondo gris muy claro
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título principal
        JLabel tituloLabel = new JLabel("REGISTRO DE NUEVO LECTOR");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(70, 130, 180));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(tituloLabel, gbc);
        
        // Separador
        gbc.gridy = 1;
        mainPanel.add(new JSeparator(), gbc);
        
        // Resetear gridwidth
        gbc.gridwidth = 1;
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(lblNombre, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(txtNombre, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(lblEmail, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);
        
        // Dirección
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(lblDireccion, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(txtDireccion, gbc);
        
        // Fecha de Registro
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lblFecha = new JLabel("Fecha de Registro:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(lblFecha, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(txtFechaRegistro, gbc);
        
        // Zona
        gbc.gridx = 0; gbc.gridy = 6;
        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(lblZona, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(cmbZona, gbc);
        
        // Panel de botones centrado
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(248, 248, 248));
        
        buttonPanel.add(btnAceptar);
        buttonPanel.add(btnCancelar);
        
        // Agregar panel de botones al final
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Agregar tooltips para mejor usabilidad
        txtNombre.setToolTipText("Ingrese el nombre completo del lector");
        txtEmail.setToolTipText("Ingrese el email del lector (ejemplo: usuario@email.com)");
        txtDireccion.setToolTipText("Ingrese la dirección completa del lector");
        txtFechaRegistro.setToolTipText("Fecha automática del sistema (no editable)");
        cmbZona.setToolTipText("Seleccione la zona de la biblioteca donde se registrará el lector");
    }
    
    private void initEvents() {
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarLector();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Evento para habilitar/deshabilitar el botón de aceptar
        txtNombre.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
        });
        
        txtEmail.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
        });
        
        txtDireccion.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
        });
        
        // Inicialmente deshabilitar el botón de aceptar
        btnAceptar.setEnabled(false);
    }
    
    private void registrarLector() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String direccion = txtDireccion.getText().trim();
        Zona zona = (Zona) cmbZona.getSelectedItem();
        
        // Validaciones
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese el nombre del lector", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return;
        }
        
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
        
        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese la dirección del lector", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtDireccion.requestFocus();
            return;
        }
        
        // Crear fecha de registro
        DTFecha fechaRegistro = new DTFecha(
            LocalDate.now().getDayOfMonth(),
            LocalDate.now().getMonthValue(),
            LocalDate.now().getYear()
        );
        
        // Generar contraseña por defecto (para la versión Swing)
        String password = "lector123"; // Contraseña por defecto para lectores
        
        // Intentar registrar el lector
        boolean exito = controladorFachada.registrarLector(nombre, email, password, direccion, fechaRegistro, zona);
        
        if (exito) {
            JOptionPane.showMessageDialog(this, 
                "Lector registrado exitosamente:\n\n" +
                "Nombre: " + nombre + "\n" +
                "Email: " + email + "\n" +
                "Zona: " + zona, 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar el lector.\n" +
                "Verifique que el email no esté ya registrado.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        cmbZona.setSelectedIndex(0);
        txtNombre.requestFocus();
        btnAceptar.setEnabled(false);
    }
    
    private void verificarCampos() {
        boolean camposValidos = !txtNombre.getText().trim().isEmpty() && 
                               !txtEmail.getText().trim().isEmpty() && 
                               !txtDireccion.getText().trim().isEmpty();
        
        btnAceptar.setEnabled(camposValidos);
    }
}
