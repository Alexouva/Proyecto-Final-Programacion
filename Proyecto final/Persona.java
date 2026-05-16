/**
 * Clase abstracta Persona.
 * Base para las clases {@code Estudiante} y {@code Profesor}.
 * Mantiene los datos básicos de una persona: nombre, identificador y email.
 */
public abstract class Persona {

    private String nombre;
    private String id;
    private String email;

    /**
     * Crea una nueva Persona con datos básicos.
     * @param nombre nombre completo
     * @param id identificador único
     * @param email correo electrónico
     */
    public Persona(String nombre, String id, String email) {
        this.nombre = nombre;
        this.id     = id;
        this.email  = email;
    }

    /** Devuelve el nombre. */
    public String getNombre() { return nombre; }
    /** Devuelve el identificador. */
    public String getId()     { return id; }
    /** Devuelve el email. */
    public String getEmail()  { return email; }

    /** Establece el nombre. */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /** Establece el email. */
    public void setEmail(String email)   { this.email  = email; }

    /**
     * Muestra por consola la información específica de la persona.
     * Implementado por las subclases.
     */
    public abstract void mostrarInformacion();
}
