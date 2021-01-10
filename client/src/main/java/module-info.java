module client {
    requires gson;
    // Do not require javax.websocket.api.
    // Only a specification, needs an implementation like javax.websocket.client.api
    // requires javax.websocket.api;
    requires shared;
    requires javax.websocket.client.api;
    requires java.sql;

    exports client;
}