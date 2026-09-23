package examen.actividades.repository;

import java.io.IOException;
import java.util.List;

//Contrato de persistencia para cualquier tipo T.

public interface Repositorio<T> {

    // Devuelve todito lo guardado
    List<T> cargarTodos() throws IOException;

    //Guarda la lista
    void guardarTodos(List<T> elementos) throws IOException;
}
