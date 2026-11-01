package cr.una.rutas_aereas.rutas_aereas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Grafo {

    private HashMap<String, Aeropuerto> aeropuertos;
    private HashMap<String, ArrayList<Ruta>> adyacencia;
    private ArrayList<Ruta> rutas_Canceladas;

    public Grafo() {
        aeropuertos = new HashMap<>();
        adyacencia = new HashMap<>();
        rutas_Canceladas = new ArrayList<>();
    }

    public void agregarAeropuerto(Aeropuerto aeropuerto) {

        String codigo = aeropuerto.getCodigo();

        aeropuertos.put(codigo, aeropuerto);

        adyacencia.putIfAbsent(codigo, new ArrayList<>());
    }

    public void agregarRuta(Ruta ruta) {

        String origen = ruta.getOrigen();
        String destino = ruta.getDestino();

        if (aeropuertos.containsKey(origen)
                && aeropuertos.containsKey(destino)) {

            adyacencia.get(origen).add(ruta);
        }
    }

    public boolean cancelarRuta(String origen, String destino) {

        if (!adyacencia.containsKey(origen)) {
            return false;
        }

        for (Ruta ruta : adyacencia.get(origen)) {

            if (ruta.getDestino().equals(destino)) {

                adyacencia.get(origen).remove(ruta);

                rutas_Canceladas.add(ruta);

                return true;
            }
        }

        return false;
    }
    
    public boolean restaurarRuta(String origen, String destino) {

    for (int i = 0; i < rutas_Canceladas.size(); i++) {

        Ruta ruta = rutas_Canceladas.get(i);

        if (ruta.getOrigen().equals(origen)
                && ruta.getDestino().equals(destino)) {

            adyacencia.get(origen).add(ruta);

            rutas_Canceladas.remove(i);

            return true;
        }
    }

    return false;
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
                    ruta.getOrigen() + " -> " + ruta.getDestino() + " | Minutos: " + ruta.getMinutos() + " | Costo: $" + ruta.getCosto()
            );
        }
    }

    public int cantidadAeropuertos() {
        return aeropuertos.size();
    }

    public int cantidadRutas() {

        int total = 0;

        for (String codigo : adyacencia.keySet()) {
            total += adyacencia.get(codigo).size();
        }

        return total;
    }

    public ArrayList<String> buscarRutaBFS(String origen, String destino) {

        if (!aeropuertos.containsKey(origen)
                || !aeropuertos.containsKey(destino)) {
            return null;
        }

        Queue<String> cola = new LinkedList<>();

        HashSet<String> visitados = new HashSet<>();

        HashMap<String, String> anterior = new HashMap<>();

        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {

            String actual = cola.poll();

            if (actual.equals(destino)) {
                break;
            }

            for (Ruta ruta : adyacencia.get(actual)) {

                String vecino = ruta.getDestino();

                if (!visitados.contains(vecino)) {

                    visitados.add(vecino);
                    anterior.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        if (!visitados.contains(destino)) {
            return null;
        }

        ArrayList<String> camino = new ArrayList<>();

        String actual = destino;

        while (actual != null) {
            camino.add(0, actual);
            actual = anterior.get(actual);
        }

        return camino;
    }

    public ArrayList<String> buscarRutaDijkstra(
            String origen, String destino, boolean usarTiempo) {

        if (!aeropuertos.containsKey(origen)
                || !aeropuertos.containsKey(destino)) {
            return null;
        }

        HashMap<String, Double> distancias = new HashMap<>();
        HashMap<String, String> anterior = new HashMap<>();
        HashSet<String> visitados = new HashSet<>();

        for (String codigo : aeropuertos.keySet()) {
            distancias.put(codigo, Double.POSITIVE_INFINITY);
        }

        distancias.put(origen, 0.0);

        while (visitados.size() < aeropuertos.size()) {

            String actual = null;
            double menorDistancia = Double.POSITIVE_INFINITY;

            for (String codigo : aeropuertos.keySet()) {

                if (!visitados.contains(codigo)
                        && distancias.get(codigo) < menorDistancia) {

                    menorDistancia = distancias.get(codigo);
                    actual = codigo;
                }
            }

            if (actual == null) {
                break;
            }

            if (actual.equals(destino)) {
                break;
            }

            visitados.add(actual);

            for (Ruta ruta : adyacencia.get(actual)) {

                String vecino = ruta.getDestino();

                if (!visitados.contains(vecino)) {

                    double peso;

                    if (usarTiempo) {
                        peso = ruta.getMinutos();
                    } else {
                        peso = ruta.getCosto();
                    }

                    double nuevaDistancia = distancias.get(actual) + peso;

                    if (nuevaDistancia < distancias.get(vecino)) {

                        distancias.put(vecino, nuevaDistancia);
                        anterior.put(vecino, actual);
                    }
                }
            }
        }

        if (distancias.get(destino) == Double.POSITIVE_INFINITY) {
            return null;
        }

        ArrayList<String> camino = new ArrayList<>();

        String actual = destino;

        while (actual != null) {
            camino.add(0, actual);
            actual = anterior.get(actual);
        }

        return camino;
    }

    public double calcularPesoRuta(ArrayList<String> camino, boolean usarTiempo) {

        if (camino == null || camino.size() < 2) {
            return 0;
        }

        double total = 0;

        for (int i = 0; i < camino.size() - 1; i++) {

            String origen = camino.get(i);
            String destino = camino.get(i + 1);

            for (Ruta ruta : adyacencia.get(origen)) {

                if (ruta.getDestino().equals(destino)) {

                    if (usarTiempo) {
                        total += ruta.getMinutos();
                    } else {
                        total += ruta.getCosto();
                    }

                    break;
                }
            }
        }

        return total;
    }

    public boolean esAlcanzableDFS(String origen, String destino) {

        if (!aeropuertos.containsKey(origen)
                || !aeropuertos.containsKey(destino)) {
            return false;
        }

        HashSet<String> visitados = new HashSet<>();

        return dfs(origen, destino, visitados);
    }

    private boolean dfs(String actual, String destino,
            HashSet<String> visitados) {

        if (actual.equals(destino)) {
            return true;
        }

        visitados.add(actual);

        for (Ruta ruta : adyacencia.get(actual)) {

            String vecino = ruta.getDestino();

            if (!visitados.contains(vecino)) {

                if (dfs(vecino, destino, visitados)) {
                    return true;
                }
            }
        }

        return false;
    }
}
