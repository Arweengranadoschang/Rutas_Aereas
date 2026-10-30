package cr.una.rutas_aereas.rutas_aereas;

import java.util.ArrayList;
import java.util.HashMap;

public class Grafo {

    private HashMap<String, Aeropuerto> aeropuertos;

    private HashMap<String, ArrayList<Ruta>> adyacencia;

    public Grafo() {
        aeropuertos = new HashMap<>();
        adyacencia = new HashMap<>();
    }

    public void agregarAeropuerto(Aeropuerto aeropuerto) {

        String codigo = aeropuerto.getCodigo();

        aeropuertos.put(codigo, aeropuerto);

        adyacencia.put(codigo, new ArrayList<>());
    }

    public void agregarRuta(Ruta ruta) {

        String origen = ruta.getOrigen();

        if (aeropuertos.containsKey(origen)
                && aeropuertos.containsKey(ruta.getDestino())) {

            adyacencia.get(origen).add(ruta);
        }
    }

    public boolean existeAeropuerto(String codigo) {
        return aeropuertos.containsKey(codigo);
    }
    public Aeropuerto buscarAeropuerto(String codigo) {
        return aeropuertos.get(codigo);
    }

    public void mostrarAeropuerto(String codigo) {

        Aeropuerto aeropuerto = buscarAeropuerto(codigo);

        if (aeropuerto == null) {
            System.out.println("El aeropuerto no existe.");
            return;
        }

        System.out.println("Codigo: " + aeropuerto.getCodigo());
        System.out.println("Ciudad: " + aeropuerto.getCiudad());
        System.out.println("Pais: " + aeropuerto.getPais());
        System.out.println("Coordenada X: " + aeropuerto.getX());
        System.out.println("Coordenada Y: " + aeropuerto.getY());

        System.out.println("Rutas salientes:");

        for (Ruta ruta : adyacencia.get(codigo)) {

            System.out.println(
                    ruta.getOrigen() + " -> "
                    + ruta.getDestino()
                    + " | Minutos: " + ruta.getMinutos()
                    + " | Costo: $" + ruta.getCosto()
            );
        }
    }

}
