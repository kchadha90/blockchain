/*
 * Copyright (c) 2018, kchadha
 */

import io.vertx.core.Vertx;
import network.VertxHttpClient;
import network.VertxHttpServer;

public class Blockchain {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // deploy the server and client to make the node running
        // rest of the operations are controlled by REST requests
        vertx.deployVerticle(VertxHttpServer.class.getName());
        vertx.deployVerticle(VertxHttpClient.class.getName());
    }
}
