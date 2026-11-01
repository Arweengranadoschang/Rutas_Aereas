package cr.una.rutas_aereas.rutas_aereas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Administrador_Archivos {

    public static void cargarAeropuertos(Grafo grafo) throws IOException {

        String rutaArchivo = "src/main/java/cr/una/rutas_aereas/rutas_aereas/Datos_TXT/aeropuertos.txt";

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {

            int cantidad = Integer.parseInt(lector.readLine().trim());

            for (int i = 0; i < cantidad; i++) {

                String[] datos = lector.readLine().trim().split("\\s+");

                String codigo = datos[0];
                double x = Double.parseDouble(datos[1]);
                double y = Double.parseDouble(datos[2]);
                String ciudad = datos[3].replace("_", " ");
                String pais = datos[4].replace("_", " ");

                Aeropuerto aeropuerto = new Aeropuerto(
                        codigo, x, y, ciudad, pais
                );

                grafo.agregarAeropuerto(aeropuerto);
            }
        }
    }

    public static void cargarRutas(Grafo grafo) throws IOException {

        String rutaArchivo = "src/main/java/cr/una/rutas_aereas/rutas_aereas/Datos_TXT/rutas.txt";

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {

            int cantidad = Integer.parseInt(lector.readLine().trim());

            for (int i = 0; i < cantidad; i++) {

                String[] datos = lector.readLine().trim().split("\\s+");

                String origen = datos[0];
                String destino = datos[1];
                int minutos = Integer.parseInt(datos[2]);
                double costo = Double.parseDouble(datos[3]);

                Ruta ruta = new Ruta(
                        origen, destino, minutos, costo
                );

                grafo.agregarRuta(ruta);
            }
        }
    }
}
