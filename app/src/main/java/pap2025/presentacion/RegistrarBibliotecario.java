package pap2025.presentacion;

import pap2025.interfaz.IControladorFachada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para registrar nuevos bibliotecarios
 */
public class RegistrarBibliotecario extends JInternalFrame {
    
    private IControladorFachada controladorFachada;
    
    // Componentes de la interfaz
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtNroEmpleado;
    private JButton btnAceptar;
    private JButton btnCancelar;
    
    public RegistrarBibliotecario(IControladorFachada controladorFachada) {
        this.controladorFachada = controladorFachada;
        
        // Configuración del frame - Ventana más grande para mejor visibilidad
        setTitle("Registrar Nuevo Bibliotecario");
        setSize(600, 400);
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
        // Campos de texto más grandes y con mejor apariencia
        txtNombre = new JTextField(35);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNombre.setPreferredSize(new Dimension(300, 30));
        txtNombre.setToolTipText("Ingrese el nombre completo del bibliotecario");
        
        txtEmail = new JTextField(35);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setPreferredSize(new Dimension(300, 30));
        txtEmail.setToolTipText("Ingrese el email del bibliotecario (ejemplo: bibliotecario@biblioteca.com)");
        
        txtNroEmpleado = new JTextField(35);
        txtNroEmpleado.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNroEmpleado.setPreferredSize(new Dimension(300, 30));
        txtNroEmpleado.setToolTipText("Ingrese el número de empleado (debe ser un número positivo)");
        
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
        mainPanel.setBackground(new Color(248, 248, 248));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título principal
        JLabel tituloLabel = new JLabel("REGISTRO DE NUEVO BIBLIOTECARIO");
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
        lblNombre.setForeground(new Color(70, 130, 180));
        mainPanel.add(lblNombre, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(txtNombre, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 14));
        lblEmail.setForeground(new Color(70, 130, 180));
        mainPanel.add(lblEmail, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);
        
        // Número de Empleado
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblNroEmpleado = new JLabel("Número de Empleado:");
        lblNroEmpleado.setFont(new Font("Arial", Font.BOLD, 14));
        lblNroEmpleado.setForeground(new Color(70, 130, 180));
        mainPanel.add(lblNroEmpleado, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(txtNroEmpleado, gbc);
        
        // Panel de botones centrado
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(248, 248, 248));
        
        buttonPanel.add(btnAceptar);
        buttonPanel.add(btnCancelar);
        
        // Agregar panel de botones al final
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Inicialmente deshabilitar el botón de aceptar
        btnAceptar.setEnabled(false);
    }
    
    private void initEvents() {
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarBibliotecario();
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
        
        txtNroEmpleado.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCampos(); }
        });
    }
    
    private void registrarBibliotecario() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String nroEmpleadoStr = txtNroEmpleado.getText().trim();
        
        // Validaciones
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese el nombre del bibliotecario", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return;
        }
        
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese el email del bibliotecario", 
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
        
        if (nroEmpleadoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese el número de empleado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtNroEmpleado.requestFocus();
            return;
        }
        
        int nroEmpleado;
        try {
            nroEmpleado = Integer.parseInt(nroEmpleadoStr);
            if (nroEmpleado <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "El número de empleado debe ser un número positivo", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                txtNroEmpleado.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El número de empleado debe ser un número válido", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtNroEmpleado.requestFocus();
            return;
        }
        
        // Verificar que no exista ya un usuario con ese email
        if (controladorFachada.existeUsuario(email)) {
            JOptionPane.showMessageDialog(this, 
                "Ya existe un usuario con ese email en el sistema", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return;
        }
        
        // Verificar que no exista ya un bibliotecario con ese número de empleado
        if (controladorFachada.existeBibliotecarioPorNroEmpleado(nroEmpleado)) {
            JOptionPane.showMessageDialog(this, 
                "Ya existe un bibliotecario con ese número de empleado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtNroEmpleado.requestFocus();
            return;
        }
        
        // Generar contraseña por defecto (para la versión Swing)
        String password = "biblio123"; // Contraseña por defecto para bibliotecarios
        
        // Intentar registrar el bibliotecario
        boolean exito = controladorFachada.registrarBibliotecario(nombre, email, password, nroEmpleado);
        
        if (exito) {
            JOptionPane.showMessageDialog(this, 
                "✅ Bibliotecario registrado exitosamente!\n\n" +
                "📋 Detalles del registro:\n" +
                "   • Nombre: " + nombre + "\n" +
                "   • Email: " + email + "\n" +
                "   • Número de Empleado: " + nroEmpleado + "\n\n" +
                "El bibliotecario ahora puede gestionar préstamos en el sistema.", 
                "Registro Exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al registrar el bibliotecario.\n\n" +
                "Por favor, intente nuevamente o contacte al administrador del sistema.", 
                "Error en el Registro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtNroEmpleado.setText("");
        btnAceptar.setEnabled(false);
        txtNombre.requestFocus();
    }
    
    private void verificarCampos() {
        boolean camposValidos = !txtNombre.getText().trim().isEmpty() && 
                               !txtEmail.getText().trim().isEmpty() && 
                               !txtNroEmpleado.getText().trim().isEmpty();
        
        btnAceptar.setEnabled(camposValidos);
    }
}
