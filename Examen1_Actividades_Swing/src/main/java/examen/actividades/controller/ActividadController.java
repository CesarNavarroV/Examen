package examen.actividades.controller;

import examen.actividades.model.Actividad;
import examen.actividades.model.TipoActividad;
import examen.actividades.repository.RepositorioActividadTxt;
import examen.actividades.service.ActividadService;
import examen.actividades.view.VentanaActividades;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//Une la vista con el servicio

public class ActividadController {

    private final VentanaActividades vista;
    private final ActividadService servicio;

    public ActividadController(VentanaActividades vista) {
        this.vista = vista;
        this.servicio = new ActividadService(new RepositorioActividadTxt("actividades.txt"));
    }
    public void iniciar() {
        vista.prepararTipos(TipoActividad.values());

        // Conectar los listeners
        vista.alPulsarRegistrar(e -> registrar());
        vista.alPulsarBuscar(e -> buscar());
        vista.alPulsarInscribir(e -> inscribir());
        vista.alPulsarMostrarTodas(e -> mostrarTodas());
        vista.alPulsarLimpiar(e -> limpiar());
        vista.alPulsarGuardar(e -> guardarDatos());
        vista.alPresionarEnterEnConsulta(e -> buscar()); // Enter busca

        // Carga iniciel
        try {
            servicio.cargarDatos();
            vista.mostrarMensaje("Datos cargados desde actividades.txt.");
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudieron cargar los datos. Revise el archivo actividades.txt.");
        }
        mostrarTodas();
    }

    // lee el formulario
    public void registrar() {
        try {
            String codigo = vista.leerCodigo();
            String nombre = vista.leerNombre();
            TipoActividad tipo = vista.leerTipo();
            double tarifaBase = Double.parseDouble(vista.leerTarifaBase().trim());
            int cupoTotal = Integer.parseInt(vista.leerCupoTotal().trim());

            servicio.registrarActividad(codigo, nombre, tipo, tarifaBase, cupoTotal);

            vista.mostrarMensaje("Actividad registrada con exito.");
            mostrarTodas();
        } catch (NumberFormatException e) {

            vista.mostrarMensaje("La tarifa base y el cupo total deben ser numeros.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            vista.mostrarMensaje(e.getMessage());
        }
    }

    // Busca el codigo
    public void buscar() {
        try {
            Actividad actividad = servicio.buscarPorCodigo(vista.leerCodigoConsulta());
            if (actividad == null) {
                vista.mostrarResultado("Actividad no encontrada.");
                vista.mostrarMensaje("No existe una actividad con ese código.");
            } else {
                vista.mostrarResultado(textoDe(actividad));
                vista.mostrarMensaje("Actividad encontrada.");
            }
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje(e.getMessage());
        }
    }

    // Inscribe una persona en la actividad segun el codigo
    public void inscribir() {
        try {
            servicio.inscribir(vista.leerCodigoConsulta());
            vista.mostrarMensaje("Inscripción realizada.");
            mostrarTodas();
        } catch (IllegalArgumentException | IllegalStateException e) {
            vista.mostrarMensaje(e.getMessage());
        }
    }

    // Muestra todas las actividades en orden
    public void mostrarTodas() {
        List<Actividad> actividades = servicio.listarActividades();
        if (actividades.isEmpty()) {
            vista.mostrarResultado("No hay actividades registradas.");
            return;
        }
        StringBuilder texto = new StringBuilder();
        for (Actividad actividad : actividades) {
            texto.append(textoDe(actividad));
        }
        vista.mostrarResultado(texto.toString());
    }

    // Vacía el formulario pero no borra todito
    public void limpiar() {
        vista.limpiarFormulario();
        vista.mostrarMensaje("Formulario limpio.");
    }

    // Guarda toda la lista en el TXT
    public void guardarDatos() {
        try {
            servicio.guardarDatos();
            vista.mostrarMensaje("Datos guardados correctamente en actividades.txt.");
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudieron guardar los datos. Revise la ruta y los permisos.");
        }
    }

    //el texto para la actividad
    private String textoDe(Actividad actividad) {
        return "Código: " + actividad.getCodigo()
                + "\nNombre: " + actividad.getNombre()
                + "\nTipo: " + actividad.getTipo()
                + "\nTarifa final: " + formatearTarifa(actividad.calcularTarifaFinal())
                + "\nCupo total: " + actividad.getCupoTotal()
                + "\nInscritos: " + actividad.getInscritos()
                + "\nDisponibles: " + actividad.getCuposDisponibles()
                + "\n\n";
    }

    // para que se vean 2 decimales nomas
    private String formatearTarifa(double tarifa) {
        return String.format(Locale.US, "%.2f", tarifa);
    }
}