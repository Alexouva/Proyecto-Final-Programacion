import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Punto de entrada de la aplicación (contiene {@code main}).
 * Coordina la gestión de estudiantes, materias, horarios y rutas del campus.
 */
public class Ejecutar {

    // Estructuras de datos globales usadas por el menú
    static HashMap<String, Estudiante> estudiantes = new HashMap<>();
    static ArrayList<Materia>  materias  = new ArrayList<>();
    static ArrayList<Profesor> profesores = new ArrayList<>();
    static Horario     horario = new Horario();
    static GrafoCampus campus  = new GrafoCampus();
    static Stack<String> pilaDeshacer = new Stack<>();
    static Stack<String> pilaRehacer  = new Stack<>();
    static Scanner sc = new Scanner(System.in);

    /** Método principal que muestra el menú y gestiona las opciones. */
    public static void main(String[] args) {
        cargarDatosDePrueba();   // Cargar datos iniciales para la demo

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero();
            System.out.println();

            switch (opcion) {
                case 1:  registrarEstudiante();    break;
                case 2:  buscarEstudiante();        break;
                case 3:  listarEstudiantes();       break;
                case 4:  eliminarEstudiante();      break;
                case 5:  crearMateria();            break;
                case 6:  inscribirEstudiante();     break;
                case 7:  verColaEspera();           break;
                case 8:  gestionarHorario();        break;
                case 9:  calcularRutaCampus();      break;
                case 10: verReporteAcademico();     break;
                case 11: deshacerAccion();          break;
                case 12: rehacerAccion();           break;
                case 13: System.out.println("¡Hasta luego!"); break;
                default: System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 13);
    }

    static void mostrarMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║    SISTEMA DE GESTIÓN UNIVERSITARIA  ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1.  Registrar estudiante            ║");
        System.out.println("║  2.  Buscar estudiante               ║");
        System.out.println("║  3.  Listar estudiantes              ║");
        System.out.println("║  4.  Eliminar estudiante             ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  5.  Crear materia                   ║");
        System.out.println("║  6.  Inscribir estudiante en materia ║");
        System.out.println("║  7.  Ver cola de espera              ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  8.  Gestionar horario               ║");
        System.out.println("║  9.  Calcular ruta más corta         ║");
        System.out.println("║  10. Ver reporte académico           ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  11. Deshacer última inscripción     ║");
        System.out.println("║  12. Rehacer inscripción             ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  13. Salir                           ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }

    // Gestión de estudiantes

    /** Registra un nuevo estudiante interactuando por consola. */
    static void registrarEstudiante() {
        System.out.println("--- Registrar Estudiante ---");
        System.out.print("Nombre  : ");
        String nombre = sc.nextLine();
        System.out.print("ID      : ");
        String id = sc.nextLine();

        // Verificar que el ID no exista ya en el HashMap
        if (estudiantes.containsKey(id)) {
            System.out.println("Ya existe un estudiante con ese ID.");
            return;
        }

        System.out.print("Email   : ");
        String email = sc.nextLine();
        System.out.print("Semestre (1-10): ");
        int semestre = leerEntero();

        Estudiante nuevo = new Estudiante(nombre, id, email, semestre);
        estudiantes.put(id, nuevo);   // Guardar en el HashMap con ID como clave
        System.out.println("Estudiante registrado correctamente.");
    }

    /** Busca y muestra un estudiante por ID. */
    static void buscarEstudiante() {
        System.out.print("Ingrese el ID del estudiante: ");
        String id = sc.nextLine();
        try {
            Estudiante e = obtenerEstudiante(id);  // Lanza excepción si no existe
            e.mostrarInformacion();
        } catch (EstudianteNoEncontradoException ex) {
            System.out.println(ex.getMessage());   // Mensaje amigable
        }
    }

    /** Lista por consola los estudiantes registrados. */
    static void listarEstudiantes() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.println("--- Lista de Estudiantes ---");
        for (Estudiante e : estudiantes.values()) {
            System.out.println("  ID: " + e.getId()
                    + " | " + e.getNombre()
                    + " | Semestre: " + e.getSemestre());
        }
    }

    /** Elimina un estudiante por ID. */
    static void eliminarEstudiante() {
        System.out.print("Ingrese el ID del estudiante a eliminar: ");
        String id = sc.nextLine();
        if (estudiantes.remove(id) != null) {
            System.out.println("Estudiante eliminado correctamente.");
        } else {
            System.out.println("No existe un estudiante con ese ID.");
        }
    }

    // Gestión de materias

    /** Crea una materia nueva solicitando datos por consola. */
    static void crearMateria() {
        System.out.println("--- Crear Materia ---");
        System.out.print("Código    : ");
        String codigo = sc.nextLine();
        System.out.print("Nombre    : ");
        String nombre = sc.nextLine();
        System.out.print("Créditos  : ");
        int creditos = leerEntero();
        System.out.print("Cupos máx : ");
        int cupos = leerEntero();

        Materia m = new Materia(codigo, nombre, creditos, cupos);

        System.out.print("¿Agregar prerequisito? (s/n): ");
        String resp = sc.nextLine().trim().toLowerCase();
        if (resp.equals("s")) {
            System.out.print("Nombre del prerequisito: ");
            m.agregarPrerequisito(sc.nextLine());
        }

        // ¿Asignar profesor?
        if (!profesores.isEmpty()) {
            System.out.print("¿Asignar profesor? (s/n): ");
            resp = sc.nextLine().trim().toLowerCase();
            if (resp.equals("s")) {
                System.out.println("Profesores disponibles:");
                for (int i = 0; i < profesores.size(); i++)
                    System.out.println("  " + i + ". " + profesores.get(i).getNombre());
                System.out.print("Seleccione: ");
                int idx = leerEntero();
                if (idx >= 0 && idx < profesores.size())
                    m.asignarProfesor(profesores.get(idx));
            }
        }

        materias.add(m);
        System.out.println("Materia creada correctamente.");
    }

    /** Inscribe a un estudiante en una materia y registra la acción para deshacer. */
    static void inscribirEstudiante() {
        if (materias.isEmpty()) {
            System.out.println("No hay materias creadas.");
            return;
        }
        System.out.print("ID del estudiante: ");
        String id = sc.nextLine();

        try {
            Estudiante e = obtenerEstudiante(id);

            // Mostrar materias con cupos disponibles
            System.out.println("Materias disponibles:");
            for (int i = 0; i < materias.size(); i++) {
                Materia m = materias.get(i);
                System.out.println("  " + i + ". " + m.getNombre()
                        + " [cupos: " + m.getCuposDisponibles()
                        + "/" + m.getCuposMaximos() + "]");
            }
            System.out.print("Seleccione materia: ");
            int idx = leerEntero();

            if (idx < 0 || idx >= materias.size()) {
                System.out.println("Opción inválida.");
                return;
            }

            Materia m = materias.get(idx);
            m.inscribirEstudiante(e);

            // Guardar la acción en la pila para poder deshacerla
            String accion = "INSCRIPCION:" + e.getId() + ":" + m.getCodigo();
            pilaDeshacer.push(accion);
            pilaRehacer.clear();   // Al hacer una nueva acción, se borra el historial de rehacer

        } catch (EstudianteNoEncontradoException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /** Muestra la cola de espera de una materia seleccionada. */
    static void verColaEspera() {
        if (materias.isEmpty()) {
            System.out.println("No hay materias creadas.");
            return;
        }
        System.out.println("Seleccione una materia:");
        for (int i = 0; i < materias.size(); i++)
            System.out.println("  " + i + ". " + materias.get(i).getNombre());
        System.out.print("Opción: ");
        int idx = leerEntero();
        if (idx >= 0 && idx < materias.size())
            materias.get(idx).mostrarColaEspera();
        else
            System.out.println("Opción inválida.");
    }

    // Gestión de horario

    /** Opciones para reservar, liberar o consultar el horario. */
    static void gestionarHorario() {
        System.out.println("--- Gestión de Horario ---");
        System.out.println("  1. Reservar horario");
        System.out.println("  2. Liberar horario");
        System.out.println("  3. Consultar disponibilidad");
        System.out.println("  4. Ver horario semanal completo");
        System.out.print("Opción: ");
        int op = leerEntero();

        if (op == 4) {
            horario.mostrarHorario();
            return;
        }

        System.out.println("  Día: 0=Lunes, 1=Martes, 2=Miércoles, 3=Jueves,");
        System.out.println("       4=Viernes, 5=Sábado, 6=Domingo");
        System.out.print("  Día: ");
        int dia = leerEntero();
        System.out.print("  Hora (0-23): ");
        int hora = leerEntero();

        switch (op) {
            case 1: horario.reservar(dia, hora);  break;
            case 2: horario.liberar(dia, hora);   break;
            case 3: horario.consultar(dia, hora); break;
            default: System.out.println("Opción inválida.");
        }
    }

    // Dijkstra — ruta más corta en el campus

    /** Interfaz para calcular la ruta más corta entre dos edificios. */
    static void calcularRutaCampus() {
        System.out.println("--- Ruta más corta en el Campus ---");
        campus.mostrarEdificios();
        System.out.println();
        campus.mostrarMapa();
        System.out.println();
        System.out.print("Edificio origen  : ");
        int origen = leerEntero();
        System.out.print("Edificio destino : ");
        int destino = leerEntero();
        System.out.println();
        campus.calcularRuta(origen, destino);
    }

    // Reporte académico

    /** Muestra el reporte académico de un estudiante (genera notas de ejemplo si no hay). */
    static void verReporteAcademico() {
        System.out.print("ID del estudiante: ");
        String id = sc.nextLine();
        try {
            Estudiante e = obtenerEstudiante(id);

            // Verificar si el estudiante tiene notas; si no, cargar notas de ejemplo
            boolean tieneNotas = false;
            for (Double[] fila : e.getNotas())
                for (Double n : fila)
                    if (n != null && n >= 0) { tieneNotas = true; break; }

            if (!tieneNotas) {
                System.out.println("(Sin notas registradas. Se cargan notas de ejemplo para la demo.)");
                // Usar el semestre registrado del estudiante (1-10) como índice base (0-9)
                int semIndex = e.getSemestre() - 1;
                if (semIndex < 0) semIndex = 0;
                if (semIndex > 9) semIndex = 9;
                // Cargar algunas notas de ejemplo en el semestre actual del estudiante
                e.setNota(semIndex, 0, 4.5);
                e.setNota(semIndex, 1, 3.2);
                e.setNota(semIndex, 2, 2.7);   // Reprobada
            }

            e.mostrarReporteCompleto();

        } catch (EstudianteNoEncontradoException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Deshacer / Rehacer

    /** Deshace la última acción registrada (actualmente: inscripciones). */
    static void deshacerAccion() {
        if (pilaDeshacer.isEmpty()) {
            System.out.println("No hay operaciones para deshacer.");
            return;
        }

        // Sacar la última acción de la pila de deshacer
        String accion = pilaDeshacer.pop();
        pilaRehacer.push(accion);   // Moverla a la pila de rehacer

        // Interpretar la acción y revertirla
        String[] partes = accion.split(":");
        if (partes[0].equals("INSCRIPCION")) {
            String idEst  = partes[1];
            String codMat = partes[2];
            for (Materia m : materias) {
                if (m.getCodigo().equals(codMat)) {
                    boolean ok = m.cancelarInscripcion(idEst);
                    if (ok)
                        System.out.println("Acción deshecha: estudiante "
                                + idEst + " removido de " + m.getNombre());
                    else
                        System.out.println("No se pudo deshacer (el estudiante estaba en cola, no inscrito).");
                }
            }
        }
    }

    /** Rehace la última acción deshecha. */
    static void rehacerAccion() {
        if (pilaRehacer.isEmpty()) {
            System.out.println("No hay operaciones para rehacer.");
            return;
        }

        // Sacar la acción de rehacer y volver a ejecutarla
        String accion = pilaRehacer.pop();
        pilaDeshacer.push(accion);

        String[] partes = accion.split(":");
        if (partes[0].equals("INSCRIPCION")) {
            String idEst  = partes[1];
            String codMat = partes[2];
            if (estudiantes.containsKey(idEst)) {
                for (Materia m : materias) {
                    if (m.getCodigo().equals(codMat)) {
                        m.inscribirEstudiante(estudiantes.get(idEst));
                        System.out.println("Acción rehecha: inscripción restaurada.");
                    }
                }
            } else {
                System.out.println("No se puede rehacer: el estudiante ya no existe.");
            }
        }
    }

    // Métodos auxiliares
    static int leerEntero() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { return -1; }
    }

    static Estudiante obtenerEstudiante(String id) throws EstudianteNoEncontradoException {
        Estudiante e = estudiantes.get(id);
        if (e == null) throw new EstudianteNoEncontradoException(id);
        return e;
    }

    static void cargarDatosDePrueba() {
        // Agregar un profesor y un estudiante de ejemplo para la demo
        Profesor p = new Profesor("Ana López", "P001", "ana@uni.edu", "Matemáticas", 3500.0);
        profesores.add(p);
        Estudiante e = new Estudiante("Juan Pérez", "E001", "juan@uni.edu", 2);
        estudiantes.put(e.getId(), e);
        Materia m = new Materia("MAT101", "Cálculo I", 4, 2);
        m.asignarProfesor(p);
        materias.add(m);
    }
}
