package cr.una.rutas_aereas.rutas_aereas;

public class Aeropuerto {

    private String codigo;
    private double x;
    private double y;
    private String ciudad;
    private String pais;

    public Aeropuerto(String codigo, double x, double y, String ciudad, String pais) {
        this.codigo = codigo;
        this.x = x;
        this.y = y;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

}
