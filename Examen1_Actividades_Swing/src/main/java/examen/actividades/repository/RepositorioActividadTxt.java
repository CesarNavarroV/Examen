package examen.actividades.repository;

import examen.actividades.model.Actividad;
import examen.actividades.model.ActividadPresencial;
import examen.actividades.model.ActividadVirtual;
import examen.actividades.model.TipoActividad;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

//Guarda y lee las actividades TXT asi en este formato ;codigo;nombre;tarifaBase;cupoTotal;inscritos


public class RepositorioActividadTxt implements Repositorio<Actividad> {

    private String rutaArchivo;

    public RepositorioActividadTxt(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public List<Actividad> cargarTodos() throws IOException {
        List<Actividad> actividades = new ArrayList<>();
        Path archivo = Paths.get(rutaArchivo);

        //el archivo todavia no existe
        if (Files.notExists(archivo)) {
            return actividades;
        }

        // Si el archivo está vacio devuelve lista vacia
        List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
        for (String linea : lineas) {
            if (linea.trim().isEmpty()) {
                continue;
            }
            actividades.add(convertirDesdeLinea(linea));
        }
        return actividades;
    }

    @Override
    public void guardarTodos(List<Actividad> elementos) throws IOException {
        List<String> lineas = new ArrayList<>();
        for (Actividad actividad : elementos) {
            lineas.add(convertirALinea(actividad));
        }

        // el TRUNCATE_EXISTING borra lo anterior
        Files.write(Paths.get(rutaArchivo), lineas, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);
    }

    // convierte la actividad en el textito que se guarda
    private String convertirALinea(Actividad actividad) {
        return actividad.getTipo() + ";"
                + actividad.getCodigo() + ";"
                + actividad.getNombre() + ";"
                + actividad.getTarifaBase() + ";"
                + actividad.getCupoTotal() + ";"
                + actividad.getInscritos();
    }

    // lo mismo pero alrevez
    private Actividad convertirDesdeLinea(String linea) {
        String[] partes = linea.split(";");

        TipoActividad tipo = TipoActividad.valueOf(partes[0].trim());
        String codigo = partes[1].trim();
        String nombre = partes[2].trim();
        double tarifaBase = Double.parseDouble(partes[3].trim());
        int cupoTotal = Integer.parseInt(partes[4].trim());
        int inscritos = Integer.parseInt(partes[5].trim());

        if (tipo == TipoActividad.PRESENCIAL) {
            return new ActividadPresencial(codigo, nombre, tarifaBase, cupoTotal, inscritos);
        }
        return new ActividadVirtual(codigo, nombre, tarifaBase, cupoTotal, inscritos);
    }
}
