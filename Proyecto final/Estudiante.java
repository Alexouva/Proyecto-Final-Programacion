    /**
 * Representa a un estudiante.
 * Mantiene semestre y una matriz de notas (10x20) donde -1.0 indica nota no registrada.
 */
public class Estudiante extends Persona {

    private int semestre;

    /** Matriz de notas: filas = semestres (0-9), columnas = materias (0-19).
     * Valor -1.0 indica nota no registrada.
     */
    private Double[][] notas;

    /**
     * Crea un estudiante e inicializa la matriz de notas en -1.0.
     */
    public Estudiante(String nombre, String id, String email, int semestre) {
        super(nombre, id, email);
        this.semestre = semestre;
        this.notas = new Double[10][20];

        // Inicializar toda la matriz con -1 (sin nota)
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 20; j++)
                notas[i][j] = -1.0;
    }

    /** Devuelve el semestre actual. */
    public int getSemestre()           { return semestre; }
    /** Establece el semestre. */
    public void setSemestre(int s)     { this.semestre = s; }
    /** Devuelve la matriz de notas. */
    public Double[][] getNotas()       { return notas; }

    /** Asigna una nota en la matriz (semestre 0-9, materia 0-19). */
    public void setNota(int sem, int mat, double nota) {
        if (sem >= 0 && sem < 10 && mat >= 0 && mat < 20) {
            notas[sem][mat] = nota;
        } else {
            System.out.println("Posición de nota inválida.");
        }
    }

    // ---- MÉTODOS DE REPORTE ACADÉMICO ----

    /** Calcula el promedio de un semestre específico. */
    public double calcularPromedioPorSemestre(int sem) {
        double suma = 0;
        int cantidad = 0;
        for (int j = 0; j < 20; j++) {
            if (notas[sem][j] >= 0) {   // Solo notas registradas
                suma += notas[sem][j];
                cantidad++;
            }
        }
        return cantidad > 0 ? suma / cantidad : 0.0;
    }

    /** Calcula el promedio acumulado de toda la carrera. */
    public double calcularPromedioAcumulado() {
        double suma = 0;
        int cantidad = 0;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 20; j++)
                if (notas[i][j] >= 0) {
                    suma += notas[i][j];
                    cantidad++;
                }
        return cantidad > 0 ? suma / cantidad : 0.0;
    }

    /** Muestra en consola las materias reprobadas (nota < 3.0). */
    public void mostrarMateriasReprobadas() {
        System.out.println("  Materias reprobadas (nota < 3.0):");
        boolean hayReprobadas = false;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 20; j++)
                if (notas[i][j] >= 0 && notas[i][j] < 3.0) {
                    System.out.printf("    Semestre %d, Materia %d: %.1f%n", i + 1, j + 1, notas[i][j]);
                    hayReprobadas = true;
                }
        if (!hayReprobadas)
            System.out.println("    Ninguna. ¡Buen desempeño!");
    }

    /** Muestra por consola un reporte académico completo del estudiante. */
    public void mostrarReporteCompleto() {
        System.out.println("\n========== REPORTE ACADÉMICO ==========");
        mostrarInformacion();
        System.out.println();
        System.out.println("  Promedios por semestre:");
        for (int i = 0; i < 10; i++) {
            double prom = calcularPromedioPorSemestre(i);
            if (prom > 0)
                System.out.printf("    Semestre %d: %.2f%n", i + 1, prom);
        }
        System.out.printf("  Promedio acumulado: %.2f%n", calcularPromedioAcumulado());
        System.out.println();
        mostrarMateriasReprobadas();
        System.out.println("========================================");
    }

    /** Muestra información básica del estudiante. */
    @Override
    public void mostrarInformacion() {
        System.out.println("  [Estudiante]");
        System.out.println("  Nombre  : " + getNombre());
        System.out.println("  ID      : " + getId());
        System.out.println("  Email   : " + getEmail());
        System.out.println("  Semestre: " + semestre);
    }
}
