package cr.una.rutas_aereas.rutas_aereas;

public class Ruta {
    
    private String origen;
    private String destino;
    private int minutos;
    private double costo;

    public Ruta(String origen, String destino, int minutos, double costo) {
        this.origen = origen;
        this.destino = destino;
        this.minutos = minutos;
        this.costo = costo;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public int getMinutos() {
        return minutos;
    }

    public double getCosto() {
        return costo;
    }
}
