package examen.actividades.view;

import examen.actividades.model.TipoActividad;

import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//Ventana de la aplicacion, tiene formulario, consulta y resultados solo lee lo que escribi
//muestra textos y conecta los listeners que le pasa el controlador.

public class VentanaActividades extends JFrame {


    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<TipoActividad> cmbTipo;
    private JTextField txtTarifaBase;
    private JTextField txtCupoTotal;
    private JTextField txtCodigoConsulta;
    private JTextArea txaResultados;
    private JLabel lblMensaje;
    private JButton btnRegistrar;
    private JButton btnBuscar;
    private JButton btnInscribir;
    private JButton btnMostrarTodas;
    private JButton btnLimpiar;
    private JButton btnGuardar;

    public VentanaActividades() {
        setTitle("Examen de Progra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(900, 600);
        setLocationRelativeTo(null);


        txaResultados.setEditable(false);
    }


    public void prepararTipos(TipoActividad[] tipos) {
        cmbTipo.setModel(new DefaultComboBoxModel<>(tipos));
        cmbTipo.setSelectedIndex(-1);
    }


    //  Métodos que usa el controlador para leer los datos


    public String leerCodigo() {
        return txtCodigo.getText();
    }

    public String leerNombre() {
        return txtNombre.getText();
    }

    public String leerTarifaBase() {
        return txtTarifaBase.getText();
    }

    public String leerCupoTotal() {
        return txtCupoTotal.getText();
    }

    public String leerCodigoConsulta() {
        return txtCodigoConsulta.getText();
    }
    public TipoActividad leerTipo() {
        return (TipoActividad) cmbTipo.getSelectedItem();
    }


    // mostrar informacion

    public void mostrarResultado(String texto) {
        txaResultados.setText(texto);
        txaResultados.setCaretPosition(0);
    }
    public void mostrarMensaje(String texto) {
        lblMensaje.setText(texto);
    }
    public void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtTarifaBase.setText("");
        txtCupoTotal.setText("");
        txtCodigoConsulta.setText("");
        cmbTipo.setSelectedIndex(-1);
    }

    //Metodos para conectar los listeners desde el controlador


    public void alPulsarRegistrar(ActionListener oyente) {
        btnRegistrar.addActionListener(oyente);
    }

    public void alPulsarBuscar(ActionListener oyente) {
        btnBuscar.addActionListener(oyente);
    }

    public void alPulsarInscribir(ActionListener oyente) {
        btnInscribir.addActionListener(oyente);
    }

    public void alPulsarMostrarTodas(ActionListener oyente) {
        btnMostrarTodas.addActionListener(oyente);
    }

    public void alPulsarLimpiar(ActionListener oyente) {
        btnLimpiar.addActionListener(oyente);
    }

    public void alPulsarGuardar(ActionListener oyente) {
        btnGuardar.addActionListener(oyente);
    }

    public void alPresionarEnterEnConsulta(ActionListener oyente) {
        txtCodigoConsulta.addActionListener(oyente);
    }
}