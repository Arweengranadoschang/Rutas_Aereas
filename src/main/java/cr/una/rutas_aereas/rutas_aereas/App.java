package cr.una.rutas_aereas.rutas_aereas;

import java.io.IOException;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) {

        Grafo grafo = new Grafo();

        try {

            Administrador_Archivos.cargarAeropuertos(grafo);

            Administrador_Archivos.cargarRutas(grafo);

            System.out.println("===== RESULTADO DE LA CARGA =====");

            System.out.println(
                    "Cantidad de aeropuertos: "
                    + grafo.cantidadAeropuertos()
            );

            System.out.println(
                    "Cantidad de rutas: "
                    + grafo.cantidadRutas()
            );

            System.out.println("================================");

            grafo.mostrarAeropuerto("SJO");
            System.out.println("\n===== PRUEBA DE BFS =====");

            ArrayList<String> camino = grafo.buscarRutaBFS("SJO", "YYZ");

            if (camino != null) {

                System.out.println("Ruta encontrada: " + camino);

                System.out.println("Cantidad de vuelos: " + (camino.size() - 1));

            } else {

                System.out.println("No existe una ruta entre esos aeropuertos.");
            }

            System.out.println("\n===== PRUEBA DE DIJKSTRA =====");

            ArrayList<String> rutaRapida
                    = grafo.buscarRutaDijkstra("SJO", "YYZ", true);

            if (rutaRapida != null) {

                double tiempoTotal
                        = grafo.calcularPesoRuta(rutaRapida, true);

                System.out.println("Ruta más rápida: " + rutaRapida);
                System.out.println("Tiempo total: " + tiempoTotal + " minutos");

            } else {
                System.out.println("No existe una ruta disponible.");
            }

            ArrayList<String> rutaEconomica
                    = grafo.buscarRutaDijkstra("SJO", "YYZ", false);

            if (rutaEconomica != null) {

                double costoTotal
                        = grafo.calcularPesoRuta(rutaEconomica, false);

                System.out.println("Ruta más económica: " + rutaEconomica);
                System.out.println("Costo total: $" + costoTotal);

            } else {
                System.out.println("No existe una ruta disponible.");
            }

            System.out.println("==============================");

            System.out.println("\n===== PRUEBA DE DFS =====");

            boolean existeCamino = grafo.esAlcanzableDFS("SJO", "YYZ");

            if (existeCamino) {
                System.out.println("Existe un camino entre SJO y YYZ.");
            } else {
                System.out.println("No existe un camino entre SJO y YYZ.");
            }

            System.out.println("=========================");

            System.out.println("\n===== PRUEBA DE CANCELACION =====");

            boolean cancelada = grafo.cancelarRuta("SJO", "LAX");

            System.out.println("¿Se canceló SJO -> LAX? " + cancelada);

            System.out.println("Rutas disponibles desde SJO:");
            grafo.mostrarAeropuerto("SJO");

            boolean restaurada = grafo.restaurarRuta("SJO", "LAX");

            System.out.println("¿Se restauró SJO -> LAX? " + restaurada);

            System.out.println("=================================");

            System.out.println("\n===== PRUEBA DE RUTAS CANCELADAS =====");

            grafo.cancelarRuta("SJO", "LAX");

            boolean existeAntes = grafo.esAlcanzableDFS("SJO", "LAX");

            System.out.println("¿Existe camino SJO -> LAX después de cancelar el vuelo directo? "
                    + existeAntes);

            ArrayList<String> rutaAlternativa
                    = grafo.buscarRutaBFS("SJO", "LAX");

            System.out.println("Ruta alternativa: " + rutaAlternativa);

            grafo.restaurarRuta("SJO", "LAX");

            System.out.println("Vuelo SJO -> LAX restaurado.");

            System.out.println("======================================");

        } catch (IOException e) {

            System.out.println("Error al cargar los archivos:");
            System.out.println(e.getMessage());

        }
    }
}
