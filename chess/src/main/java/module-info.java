module chess {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires gson;
    requires java.sql;
    requires shared;
    requires client;
    requires javax.websocket.client.api;

    opens sample;
    exports sample;
}