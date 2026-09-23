package examen.actividades;

import examen.actividades.controller.ActividadController;
import examen.actividades.view.VentanaActividades;

import javax.swing.SwingUtilities;

// Arranque de la aplicación en el hilo de eventos de Swing.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaActividades ventana = new VentanaActividades();
            ActividadController controlador = new ActividadController(ventana);
            controlador.iniciar();
            ventana.setVisible(true);
        });
    }
}