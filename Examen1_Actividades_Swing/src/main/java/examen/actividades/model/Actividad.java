package examen.actividades.model;


 //Clase padre de las actividades, guarda los datos comunes y deja en abstracto el calculo de la tarifa final y el tipo

public abstract class Actividad {

    private String codigo;
    private String nombre;
    private double tarifaBase;
    private int cupoTotal;
    private int inscritos;

    public Actividad(String codigo, String nombre, double tarifaBase, int cupoTotal, int inscritos) {
        this.codigo = validarTexto(codigo, "El código");
        this.nombre = validarTexto(nombre, "El nombre");

        if (!Double.isFinite(tarifaBase) || tarifaBase <= 0) {
            throw new IllegalArgumentException("La tarifa base debe ser un número mayor que cero.");
        }
        if (cupoTotal <= 0) {
            throw new IllegalArgumentException("El cupo total debe ser un número entero mayor que cero.");
        }
        if (inscritos < 0 || inscritos > cupoTotal) {
            throw new IllegalArgumentException("Los inscritos deben estar entre cero y el cupo total.");
        }

        this.tarifaBase = tarifaBase;
        this.cupoTotal = cupoTotal;
        this.inscritos = inscritos;
    }


     // Quita espacios exteriores, rechaza el texto vacio y  lo que rompa

    private static String validarTexto(String valor, String nombreCampo) {
        if (valor == null) {
            throw new IllegalArgumentException(nombreCampo + " es obligatorio");
        }
        String limpio = valor.trim();
        if (limpio.isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacio");
        }
        if (limpio.contains(";")) {
            throw new IllegalArgumentException(nombreCampo + " no puede contener punto y coma");
        }
        if (limpio.contains("\n") ) {
            throw new IllegalArgumentException(nombreCampo + " no puede contener saltos de línea");
        }
        return limpio;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public int getCupoTotal() {
        return cupoTotal;
    }

    public int getInscritos() {
        return inscritos;
    }

    // Cupos que quedan
    public int getCuposDisponibles() {
        return cupoTotal - inscritos;
    }

    //Inscribe una persona si queda
    public void inscribir() {
        if (inscritos >= cupoTotal) {
            throw new IllegalStateException("No hay cupo disponible: la actividad está llena.");
        }
        inscritos = inscritos + 1;
    }
    public abstract double calcularTarifaFinal();
    public abstract TipoActividad getTipo();
}
