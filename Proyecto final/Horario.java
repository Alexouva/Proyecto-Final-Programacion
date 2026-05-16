/**
 * Representa un horario semanal como una matriz booleana [7][24].
 * true = hora ocupada, false = hora libre.
 */
public class Horario {

    private boolean[][] horario;

    // Nombres de los días para mostrar en pantalla
    private String[] diasNombre = {
        "Lunes", "Martes", "Miércoles", "Jueves",
        "Viernes", "Sábado", "Domingo"
    };

    /** Crea un horario inicialmente libre. */
    public Horario() {
        this.horario = new boolean[7][24];
    }

    /** Reserva una hora si está libre. */
    public void reservar(int dia, int hora) {
        if (!validar(dia, hora)) return;
        if (horario[dia][hora]) {
            System.out.println("No se puede reservar este horario porque ya se encuentra ocupado.");
        } else {
            horario[dia][hora] = true;
            System.out.println("Horario reservado: " + diasNombre[dia] + " a las " + hora + ":00 hs");
        }
    }

    /** Libera una hora previamente reservada. */
    public void liberar(int dia, int hora) {
        if (!validar(dia, hora)) return;
        if (!horario[dia][hora]) {
            System.out.println("Ese horario ya se encuentra libre.");
        } else {
            horario[dia][hora] = false;
            System.out.println("Horario liberado: " + diasNombre[dia] + " a las " + hora + ":00 hs");
        }
    }

    /** Consulta si una hora está ocupada o libre. */
    public void consultar(int dia, int hora) {
        if (!validar(dia, hora)) return;
        String estado = horario[dia][hora] ? "OCUPADO" : "LIBRE";
        System.out.println(diasNombre[dia] + " " + hora + ":00 -> " + estado);
    }

    /** Muestra el horario semanal (horas laborales: 6 a 22). */
    public void mostrarHorario() {
        System.out.println("\n============= HORARIO SEMANAL =============");
        System.out.printf("%-6s", "Hora");
        for (int d = 0; d < 7; d++)
            System.out.printf("%-11s", diasNombre[d]);
        System.out.println();

        for (int h = 6; h <= 22; h++) {
            System.out.printf("%-6s", h + ":00");
            for (int d = 0; d < 7; d++) {
                System.out.printf("%-11s", horario[d][h] ? "[OCUPADO]" : "[LIBRE]  ");
            }
            System.out.println();
        }
        System.out.println("===========================================");
    }

    /** Valida rango de día (0-6) y hora (0-23). */
    private boolean validar(int dia, int hora) {
        if (dia < 0 || dia > 6) {
            System.out.println("Día inválido. Use 0 (Lunes) a 6 (Domingo).");
            return false;
        }
        if (hora < 0 || hora > 23) {
            System.out.println("Hora inválida. Use 0 a 23.");
            return false;
        }
        return true;
    }
}
