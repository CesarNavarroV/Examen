package examen.actividades.service;

import examen.actividades.model.Actividad;
import examen.actividades.model.ActividadPresencial;
import examen.actividades.model.ActividadVirtual;
import examen.actividades.model.TipoActividad;
import examen.actividades.repository.Repositorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Guarda las actividades en memoria y pide al repositorio que las lea o escriba

public class ActividadService {

    private List<Actividad> actividades;
    private Repositorio<Actividad> repositorio;

    public ActividadService(Repositorio<Actividad> repositorio) {
        this.repositorio = repositorio;
        this.actividades = new ArrayList<>();
    }

    //Registra una actividad nueva
    public void registrarActividad(String codigo, String nombre, TipoActividad tipo, double tarifaBase, int cupoTotal) {
        if (tipo == null) {
            throw new IllegalArgumentException("Debe seleccionar el tipo de actividad (PRESENCIAL o VIRTUAL).");
        }

        //rechaza un codigos vacios o duplicaos
        Actividad existente = buscarPorCodigo(codigo);
        if (existente != null) {
            throw new IllegalArgumentException("Ya existe una actividad con el código " + existente.getCodigo() + ".");
        }

        Actividad nueva;
        if (tipo == TipoActividad.PRESENCIAL) {
            nueva = new ActividadPresencial(codigo, nombre, tarifaBase, cupoTotal, 0);
        } else {
            nueva = new ActividadVirtual(codigo, nombre, tarifaBase, cupoTotal, 0);
        }
        actividades.add(nueva);
    }

    //Busca por codigo
    public Actividad buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo no puede estar vacio.");
        }
        String buscado = codigo.trim();
        for (Actividad actividad : actividades) {
            if (actividad.getCodigo().equalsIgnoreCase(buscado)) {
                return actividad;
            }
        }
        return null;
    }

    // Devuelve una copia de la lista para que nadie la modifique desde afuera
    public List<Actividad> listarActividades() {
        return new ArrayList<>(actividades);
    }

    // Inscribe una persona en la actividad een el codigo que es
    public void inscribir(String codigo) {
        Actividad actividad = buscarPorCodigo(codigo);
        if (actividad == null) {
            throw new IllegalArgumentException("No existe una actividad con el código " + codigo.trim() + ".");
        }
        actividad.inscribir();
    }


    public void cargarDatos() throws IOException {
        List<Actividad> cargadas = repositorio.cargarTodos();
        this.actividades = cargadas;
    }


    public void guardarDatos() throws IOException {
        repositorio.guardarTodos(actividades);
    }
}
