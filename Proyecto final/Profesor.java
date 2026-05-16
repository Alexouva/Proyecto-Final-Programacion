/**
 * Representa a un profesor del campus.
 * Extiende {@code Persona} y añade departamento y salario.
 */
public class Profesor extends Persona {

    private String departamento;
    private double salario;

    public Profesor(String nombre, String id, String email,
                    String departamento, double salario) {
        super(nombre, id, email);
        this.departamento = departamento;
        this.salario      = salario;
    }

    /** Devuelve el departamento. */
    public String getDepartamento()          { return departamento; }
    /** Devuelve el salario. */
    public double getSalario()               { return salario; }
    /** Establece el departamento. */
    public void setDepartamento(String dep)  { this.departamento = dep; }
    /** Establece el salario. */
    public void setSalario(double salario)   { this.salario = salario; }

    /**
     * Muestra la información del profesor en consola.
     */
    @Override
    public void mostrarInformacion() {
        System.out.println("  [Profesor]");
        System.out.println("  Nombre       : " + getNombre());
        System.out.println("  ID           : " + getId());
        System.out.println("  Email        : " + getEmail());
        System.out.println("  Departamento : " + departamento);
        System.out.printf( "  Salario      : $%.2f%n", salario);
    }
}
