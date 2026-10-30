module cr.una.rutas_aereas.rutas_aereas {
    requires javafx.controls;
    requires javafx.fxml;

    opens cr.una.rutas_aereas.rutas_aereas to javafx.fxml;
    exports cr.una.rutas_aereas.rutas_aereas;
}
