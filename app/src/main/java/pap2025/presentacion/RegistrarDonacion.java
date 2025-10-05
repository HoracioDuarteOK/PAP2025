package pap2025.presentacion;

import pap2025.interfaz.IControladorFachada;
import pap2025.datatypes.DTDimensiones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para registrar donaciones de libros y artículos especiales
 */
public class RegistrarDonacion extends JInternalFrame {
    
    private IControladorFachada controladorFachada;
    
    // Panel de pestañas
    private JTabbedPane tabbedPane;
    
    // Panel para donación de libros
    private JPanel panelLibro;
    private JTextField txtTituloLibro;
    private JTextField txtCantPaginas;
    private JButton btnRegistrarLibro;
    
    // Panel para donación de artículos especiales
    private JPanel panelArtEspecial;
    private JTextField txtDescripcion;
    private JTextField txtPeso;
    private JTextField txtAlto;
    private JTextField txtAncho;
    private JTextField txtProfundidad;
    private JButton btnRegistrarArtEspecial;
    
    // Botón de cancelar
    private JButton btnCancelar;
    
    public RegistrarDonacion(IControladorFachada controladorFachada) {
        this.controladorFachada = controladorFachada;
        
        // Configuración del frame
        setTitle("Registrar Nueva Donación");
        setSize(700, 500);
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
        // Panel de pestañas
        tabbedPane = new JTabbedPane();
        
        // ===== PANEL PARA LIBROS =====
        panelLibro = new JPanel();
        panelLibro.setLayout(new GridBagLayout());
        panelLibro.setBackground(new Color(248, 248, 248));
        
        txtTituloLibro = new JTextField(35);
        txtTituloLibro.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTituloLibro.setPreferredSize(new Dimension(300, 30));
        txtTituloLibro.setToolTipText("Ingrese el título del libro");
        
        txtCantPaginas = new JTextField(35);
        txtCantPaginas.setFont(new Font("Arial", Font.PLAIN, 14));
        txtCantPaginas.setPreferredSize(new Dimension(300, 30));
        txtCantPaginas.setToolTipText("Ingrese la cantidad de páginas del libro");
        
        btnRegistrarLibro = new JButton("Registrar Donación de Libro");
        btnRegistrarLibro.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarLibro.setPreferredSize(new Dimension(200, 35));
        btnRegistrarLibro.setBackground(new Color(34, 139, 34)); // Verde
        btnRegistrarLibro.setForeground(Color.WHITE);
        
        // ===== PANEL PARA ARTÍCULOS ESPECIALES =====
        panelArtEspecial = new JPanel();
        panelArtEspecial.setLayout(new GridBagLayout());
        panelArtEspecial.setBackground(new Color(248, 248, 248));
        
        txtDescripcion = new JTextField(35);
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDescripcion.setPreferredSize(new Dimension(300, 30));
        txtDescripcion.setToolTipText("Ingrese la descripción del artículo especial");
        
        txtPeso = new JTextField(35);
        txtPeso.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPeso.setPreferredSize(new Dimension(300, 30));
        txtPeso.setToolTipText("Ingrese el peso en kilogramos (ejemplo: 2.5)");
        
        txtAlto = new JTextField(35);
        txtAlto.setFont(new Font("Arial", Font.PLAIN, 14));
        txtAlto.setPreferredSize(new Dimension(300, 30));
        txtAlto.setToolTipText("Ingrese la altura en centímetros");
        
        txtAncho = new JTextField(35);
        txtAncho.setFont(new Font("Arial", Font.PLAIN, 14));
        txtAncho.setPreferredSize(new Dimension(300, 30));
        txtAncho.setToolTipText("Ingrese el ancho en centímetros");
        
        txtProfundidad = new JTextField(35);
        txtProfundidad.setFont(new Font("Arial", Font.PLAIN, 14));
        txtProfundidad.setPreferredSize(new Dimension(300, 30));
        txtProfundidad.setToolTipText("Ingrese la profundidad en centímetros");
        
        btnRegistrarArtEspecial = new JButton("Registrar Donación de Artículo");
        btnRegistrarArtEspecial.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarArtEspecial.setPreferredSize(new Dimension(200, 35));
        btnRegistrarArtEspecial.setBackground(new Color(70, 130, 180)); // Azul acero
        btnRegistrarArtEspecial.setForeground(Color.WHITE);
        
        // Botón cancelar
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.setBackground(new Color(220, 20, 60)); // Rojo
        btnCancelar.setForeground(Color.WHITE);
    }
    
    private void initLayout() {
        setLayout(new BorderLayout());
        
        // Título principal
        JLabel tituloLabel = new JLabel("REGISTRO DE NUEVAS DONACIONES");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(70, 130, 180));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ===== CONFIGURAR PANEL DE LIBROS =====
        configurarPanelLibro();
        
        // ===== CONFIGURAR PANEL DE ARTÍCULOS ESPECIALES =====
        configurarPanelArtEspecial();
        
        // Agregar pestañas
        tabbedPane.addTab("📚 Donación de Libro", new ImageIcon(), panelLibro, "Registrar donación de un nuevo libro");
        tabbedPane.addTab("🎨 Donación de Artículo Especial", new ImageIcon(), panelArtEspecial, "Registrar donación de un artículo especial");
        
        // Panel de botones inferior
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(248, 248, 248));
        buttonPanel.add(btnCancelar);
        
        // Agregar componentes al frame
        add(tituloLabel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Inicialmente deshabilitar botones
        btnRegistrarLibro.setEnabled(false);
        btnRegistrarArtEspecial.setEnabled(false);
    }
    
    private void configurarPanelLibro() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título del libro
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblTitulo = new JLabel("Título del Libro:");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(70, 130, 180));
        panelLibro.add(lblTitulo, gbc);
        
        gbc.gridx = 1;
        panelLibro.add(txtTituloLibro, gbc);
        
        // Cantidad de páginas
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblPaginas = new JLabel("Cantidad de Páginas:");
        lblPaginas.setFont(new Font("Arial", Font.BOLD, 14));
        lblPaginas.setForeground(new Color(70, 130, 180));
        panelLibro.add(lblPaginas, gbc);
        
        gbc.gridx = 1;
        panelLibro.add(txtCantPaginas, gbc);
        
        // Botón registrar
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelLibro.add(btnRegistrarLibro, gbc);
    }
    
    private void configurarPanelArtEspecial() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Descripción
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("Arial", Font.BOLD, 14));
        lblDescripcion.setForeground(new Color(70, 130, 180));
        panelArtEspecial.add(lblDescripcion, gbc);
        
        gbc.gridx = 1;
        panelArtEspecial.add(txtDescripcion, gbc);
        
        // Peso
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblPeso = new JLabel("Peso (kg):");
        lblPeso.setFont(new Font("Arial", Font.BOLD, 14));
        lblPeso.setForeground(new Color(70, 130, 180));
        panelArtEspecial.add(lblPeso, gbc);
        
        gbc.gridx = 1;
        panelArtEspecial.add(txtPeso, gbc);
        
        // Dimensiones
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblDimensiones = new JLabel("Dimensiones (cm):");
        lblDimensiones.setFont(new Font("Arial", Font.BOLD, 14));
        lblDimensiones.setForeground(new Color(70, 130, 180));
        panelArtEspecial.add(lblDimensiones, gbc);
        
        gbc.gridx = 1;
        panelArtEspecial.add(new JLabel(""), gbc); // Espacio vacío
        
        // Alto
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblAlto = new JLabel("Alto:");
        lblAlto.setFont(new Font("Arial", Font.BOLD, 12));
        lblAlto.setForeground(new Color(70, 130, 180));
        panelArtEspecial.add(lblAlto, gbc);
        
        gbc.gridx = 1;
        panelArtEspecial.add(txtAlto, gbc);
        
        // Ancho
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblAncho = new JLabel("Ancho:");
        lblAncho.setFont(new Font("Arial", Font.BOLD, 12));
        lblAncho.setForeground(new Color(70, 130, 180));
        panelArtEspecial.add(lblAncho, gbc);
        
        gbc.gridx = 1;
        panelArtEspecial.add(txtAncho, gbc);
        
        // Profundidad
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lblProfundidad = new JLabel("Profundidad:");
        lblProfundidad.setFont(new Font("Arial", Font.BOLD, 12));
        lblProfundidad.setForeground(new Color(70, 130, 180));
        panelArtEspecial.add(lblProfundidad, gbc);
        
        gbc.gridx = 1;
        panelArtEspecial.add(txtProfundidad, gbc);
        
        // Botón registrar
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelArtEspecial.add(btnRegistrarArtEspecial, gbc);
    }
    
    private void initEvents() {
        btnRegistrarLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarDonacionLibro();
            }
        });
        
        btnRegistrarArtEspecial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarDonacionArtEspecial();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Eventos para habilitar/deshabilitar botones
        txtTituloLibro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCamposLibro(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCamposLibro(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCamposLibro(); }
        });
        
        txtCantPaginas.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCamposLibro(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCamposLibro(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCamposLibro(); }
        });
        
        txtDescripcion.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
        });
        
        txtPeso.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
        });
        
        txtAlto.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
        });
        
        txtAncho.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
        });
        
        txtProfundidad.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { verificarCamposArtEspecial(); }
        });
    }
    
    private void registrarDonacionLibro() {
        String titulo = txtTituloLibro.getText().trim();
        String cantPaginasStr = txtCantPaginas.getText().trim();
        
        // Validaciones
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese el título del libro", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtTituloLibro.requestFocus();
            return;
        }
        
        if (cantPaginasStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese la cantidad de páginas", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtCantPaginas.requestFocus();
            return;
        }
        
        int cantPaginas;
        try {
            cantPaginas = Integer.parseInt(cantPaginasStr);
            if (cantPaginas <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "La cantidad de páginas debe ser un número positivo", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                txtCantPaginas.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "La cantidad de páginas debe ser un número válido", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtCantPaginas.requestFocus();
            return;
        }
        
        // Registrar la donación
        Integer idMaterial = controladorFachada.registrarDonacionLibro(titulo, cantPaginas);
        
        if (idMaterial != null) {
            JOptionPane.showMessageDialog(this, 
                "✅ Donación de libro registrada exitosamente!\n\n" +
                "📋 Detalles del registro:\n" +
                "   • Título: " + titulo + "\n" +
                "   • Páginas: " + cantPaginas + "\n" +
                "   • ID del Material: " + idMaterial + "\n\n" +
                "El libro ha sido agregado al inventario de la biblioteca.", 
                "Donación Registrada", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario
            limpiarFormularioLibro();
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al registrar la donación del libro.\n\n" +
                "Por favor, intente nuevamente o contacte al administrador del sistema.", 
                "Error en el Registro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void registrarDonacionArtEspecial() {
        String descripcion = txtDescripcion.getText().trim();
        String pesoStr = txtPeso.getText().trim();
        String altoStr = txtAlto.getText().trim();
        String anchoStr = txtAncho.getText().trim();
        String profundidadStr = txtProfundidad.getText().trim();
        
        // Validaciones
        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese la descripción del artículo", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtDescripcion.requestFocus();
            return;
        }
        
        if (pesoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese el peso del artículo", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtPeso.requestFocus();
            return;
        }
        
        double peso;
        try {
            peso = Double.parseDouble(pesoStr);
            if (peso <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "El peso debe ser un número positivo", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                txtPeso.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El peso debe ser un número válido", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtPeso.requestFocus();
            return;
        }
        
        if (altoStr.isEmpty() || anchoStr.isEmpty() || profundidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor complete todas las dimensiones", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int alto, ancho, profundidad;
        try {
            alto = Integer.parseInt(altoStr);
            ancho = Integer.parseInt(anchoStr);
            profundidad = Integer.parseInt(profundidadStr);
            
            if (alto <= 0 || ancho <= 0 || profundidad <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Todas las dimensiones deben ser números positivos", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Las dimensiones deben ser números válidos", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear objeto de dimensiones
        DTDimensiones dimensiones = new DTDimensiones(alto, ancho, profundidad);
        
        // Registrar la donación
        Integer idMaterial = controladorFachada.registrarDonacionArtEspecial(descripcion, peso, dimensiones);
        
        if (idMaterial != null) {
            JOptionPane.showMessageDialog(this, 
                "✅ Donación de artículo especial registrada exitosamente!\n\n" +
                "📋 Detalles del registro:\n" +
                "   • Descripción: " + descripcion + "\n" +
                "   • Peso: " + peso + " kg\n" +
                "   • Dimensiones: " + alto + " x " + ancho + " x " + profundidad + " cm\n" +
                "   • ID del Material: " + idMaterial + "\n\n" +
                "El artículo ha sido agregado al inventario de la biblioteca.", 
                "Donación Registrada", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario
            limpiarFormularioArtEspecial();
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al registrar la donación del artículo.\n\n" +
                "Por favor, intente nuevamente o contacte al administrador del sistema.", 
                "Error en el Registro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormularioLibro() {
        txtTituloLibro.setText("");
        txtCantPaginas.setText("");
        btnRegistrarLibro.setEnabled(false);
        txtTituloLibro.requestFocus();
    }
    
    private void limpiarFormularioArtEspecial() {
        txtDescripcion.setText("");
        txtPeso.setText("");
        txtAlto.setText("");
        txtAncho.setText("");
        txtProfundidad.setText("");
        btnRegistrarArtEspecial.setEnabled(false);
        txtDescripcion.requestFocus();
    }
    
    private void verificarCamposLibro() {
        boolean camposValidos = !txtTituloLibro.getText().trim().isEmpty() && 
                               !txtCantPaginas.getText().trim().isEmpty();
        btnRegistrarLibro.setEnabled(camposValidos);
    }
    
    private void verificarCamposArtEspecial() {
        boolean camposValidos = !txtDescripcion.getText().trim().isEmpty() && 
                               !txtPeso.getText().trim().isEmpty() &&
                               !txtAlto.getText().trim().isEmpty() &&
                               !txtAncho.getText().trim().isEmpty() &&
                               !txtProfundidad.getText().trim().isEmpty();
        btnRegistrarArtEspecial.setEnabled(camposValidos);
    }
}
