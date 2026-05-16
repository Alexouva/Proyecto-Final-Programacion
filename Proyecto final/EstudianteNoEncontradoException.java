/**
 * Excepción lanzada cuando no se encuentra un estudiante por su ID.
 */
public class EstudianteNoEncontradoException extends Exception {

    public EstudianteNoEncontradoException(String id) {
        super("No existe un estudiante con el ID: " + id);
    }
}
