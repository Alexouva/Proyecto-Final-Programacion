/**
 * GrafoCampus: representa edificios del campus y permite calcular la ruta más corta
 * entre dos nodos mediante el algoritmo de Dijkstra usando una matriz de adyacencia.
 */
public class GrafoCampus {

    // Nombres de los 5 edificios del campus
    private String[] edificios = {
        "Ingeniería",   // 0
        "Cafetería",    // 1
        "Biblioteca",   // 2
        "Rectoría",     // 3
        "Laboratorios"  // 4
    };

    private int n = 5;   // Número de edificios

    // Matriz de adyacencia: mapa[i][j] = distancia en metros
    // 0 significa que NO hay conexión directa entre esos dos edificios
    private int[][] mapa = {
        //   Ing   Caf   Bib   Rec   Lab
        {      0,  150,  200,    0,  100 },   // Ingeniería
        {    150,    0,  100,  180,    0 },   // Cafetería
        {    200,  100,    0,  130,  150 },   // Biblioteca
        {      0,  180,  130,    0,  200 },   // Rectoría
        {    100,    0,  150,  200,    0 }    // Laboratorios
    };

    /**
     * Calcula e imprime la ruta más corta entre dos edificios.
     * @param origen índice del edificio origen
     * @param destino índice del edificio destino
     */
    public void calcularRuta(int origen, int destino) {
        if (origen < 0 || origen >= n || destino < 0 || destino >= n) {
            System.out.println("Edificio inválido.");
            return;
        }

        int[] distancia = new int[n];    // Distancia mínima desde el origen
        int[] anterior  = new int[n];    // Para reconstruir la ruta
        boolean[] visitado = new boolean[n];

        // Paso 1: inicializar distancias en "infinito" y anterior en -1
        for (int i = 0; i < n; i++) {
            distancia[i] = Integer.MAX_VALUE;
            anterior[i]  = -1;
        }
        distancia[origen] = 0;  // La distancia al nodo de inicio es 0

        // Paso 2: iterar n veces (una por nodo)
        for (int iteracion = 0; iteracion < n; iteracion++) {

            // Buscar el nodo NO visitado con la distancia más pequeña
            int u = -1;
            for (int v = 0; v < n; v++) {
                if (!visitado[v] && (u == -1 || distancia[v] < distancia[u]))
                    u = v;
            }

            // Si la distancia es infinita, los nodos restantes son inalcanzables
            if (distancia[u] == Integer.MAX_VALUE) break;

            visitado[u] = true;  // Marcar como visitado

            // Paso 3: revisar vecinos de u y actualizar distancias
            for (int v = 0; v < n; v++) {
                if (mapa[u][v] > 0 && !visitado[v]) {
                    int nuevaDistancia = distancia[u] + mapa[u][v];
                    if (nuevaDistancia < distancia[v]) {
                        distancia[v] = nuevaDistancia;
                        anterior[v]  = u;  // Guardamos por dónde llegamos
                    }
                }
            }
        }

        // Mostrar resultado
        if (distancia[destino] == Integer.MAX_VALUE) {
            System.out.println("No existe una ruta entre "
                    + edificios[origen] + " y " + edificios[destino]);
            return;
        }

        System.out.print("Ruta más corta: ");
        imprimirRuta(anterior, destino);
        System.out.println();
        System.out.println("Distancia total: " + distancia[destino] + " metros");
    }

    /** Imprime la ruta reconstruida usando el arreglo "anterior". */
    private void imprimirRuta(int[] anterior, int actual) {
        if (anterior[actual] == -1) {
            // Llegamos al origen: imprimir y retornar
            System.out.print(edificios[actual]);
            return;
        }
        imprimirRuta(anterior, anterior[actual]);  // Primero imprimir el anterior
        System.out.print(" -> " + edificios[actual]);
    }

    /** Muestra la lista de edificios disponibles con sus índices. */
    public void mostrarEdificios() {
        System.out.println("  Edificios del campus:");
        for (int i = 0; i < n; i++)
            System.out.println("    " + i + ". " + edificios[i]);
    }

    /** Muestra la matriz de distancias entre edificios. */
    public void mostrarMapa() {
        System.out.println("\n  Mapa de distancias (metros):");
        System.out.printf("%-16s", "");
        for (String e : edificios) System.out.printf("%-14s", e);
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.printf("%-16s", edificios[i]);
            for (int j = 0; j < n; j++) {
                String val = mapa[i][j] == 0 ? "---" : mapa[i][j] + "m";
                System.out.printf("%-14s", val);
            }
            System.out.println();
        }
    }
}
