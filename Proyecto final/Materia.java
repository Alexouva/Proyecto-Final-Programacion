import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Representa una materia/asignatura del catálogo.
 * Mantiene inscritos, cola de espera, prerequisitos y profesor asignado.
 */
public class Materia {

    private String codigo;
    private String nombre;
    private int creditos;
    private int cuposMaximos;

    // LinkedList de prerequisitos (nombres de materias requeridas)
    private LinkedList<String> prerequisitos;

    // Lista de estudiantes actualmente inscritos
    private ArrayList<Estudiante> inscritos;

    // Cola de espera: el primero en llegar es el primero en entrar (FIFO)
    private Queue<Estudiante> colaEspera;

    // Profesor asignado (puede ser null si no tiene)
    private Profesor profesor;

    public Materia(String codigo, String nombre, int creditos, int cuposMaximos) {
        this.codigo       = codigo;
        this.nombre       = nombre;
        this.creditos     = creditos;
        this.cuposMaximos = cuposMaximos;
        this.prerequisitos = new LinkedList<>();
        this.inscritos     = new ArrayList<>();
        this.colaEspera    = new LinkedList<>();   // Queue implementada con LinkedList
        this.profesor      = null;
    }

    /** Inscribe a un estudiante: si hay cupo ingresa, si no se añade a la cola. */
    public void inscribirEstudiante(Estudiante e) {
        // Verificar si ya está inscrito
        for (Estudiante est : inscritos) {
            if (est.getId().equals(e.getId())) {
                System.out.println("El estudiante ya está inscrito en esta materia.");
                return;
            }
        }
        
        // Verificar si ya está en la cola de espera
        for (Estudiante est : colaEspera) {
            if (est.getId().equals(e.getId())) {
                System.out.println("El estudiante ya está en la cola de espera.");
                return;
            }
        }

        if (inscritos.size() < cuposMaximos) {
            // Hay cupo disponible
            inscritos.add(e);
            System.out.println("Inscripción realizada correctamente.");
        } else {
            // No hay cupo: agregar a la cola de espera
            colaEspera.add(e);
            System.out.println("No hay cupos disponibles en este momento.");
            System.out.println("El estudiante fue agregado a la cola de espera.");
        }
    }

    /** Cancela la inscripción del estudiante e intenta promover al siguiente de la cola. */
    public boolean cancelarInscripcion(String idEstudiante) {
        for (Estudiante est : inscritos) {
            if (est.getId().equals(idEstudiante)) {
                inscritos.remove(est);
                // Si hay alguien esperando, promoverlo automáticamente
                if (!colaEspera.isEmpty()) {
                    Estudiante promovido = colaEspera.poll();  // Saca el primero de la cola
                    inscritos.add(promovido);
                    System.out.println("  El estudiante " + promovido.getNombre()
                            + " fue promovido desde la cola de espera.");
                }
                return true;
            }
        }
        return false;  // No encontró al estudiante
    }

    /** Añade un prerequisito (nombre de materia). */
    public void agregarPrerequisito(String prereq) {
        prerequisitos.add(prereq);
    }

    /** Asigna un profesor a la materia. */
    public void asignarProfesor(Profesor p) {
        this.profesor = p;
        System.out.println("  Profesor " + p.getNombre() + " asignado a: " + nombre);
    }

    /** Muestra por consola la cola de espera de la materia. */
    public void mostrarColaEspera() {
        if (colaEspera.isEmpty()) {
            System.out.println("No hay estudiantes en cola de espera para: " + nombre);
        } else {
            System.out.println("Cola de espera — " + nombre + ":");
            int posicion = 1;
            for (Estudiante e : colaEspera) {
                System.out.println("  " + posicion + ". " + e.getNombre()
                        + " (ID: " + e.getId() + ")");
                posicion++;
            }
        }
    }

    /** Muestra información resumida de la materia. */
    public void mostrarInformacion() {
        System.out.println("  Código   : " + codigo);
        System.out.println("  Nombre   : " + nombre);
        System.out.println("  Créditos : " + creditos);
        System.out.println("  Cupos    : " + inscritos.size() + " / " + cuposMaximos);
        System.out.println("  En espera: " + colaEspera.size());
        if (profesor != null)
            System.out.println("  Profesor : " + profesor.getNombre());
        if (!prerequisitos.isEmpty())
            System.out.println("  Prereqs  : " + prerequisitos);
    }

    // Getters
    public String getCodigo()              { return codigo; }
    public String getNombre()              { return nombre; }
    public int getCuposMaximos()           { return cuposMaximos; }
    public int getCuposDisponibles()       { return cuposMaximos - inscritos.size(); }
    public ArrayList<Estudiante> getInscritos()   { return inscritos; }
    public Queue<Estudiante> getColaEspera()      { return colaEspera; }
}
