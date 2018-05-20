/*
 * Copyright (c) 2018, kchadha
 */

package handlers;

import blockchain.Utils;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import static network.VertxHttpClient.SYNC_ADDRESS;

/**
 *
 */
public class AddTransactionHandler implements Handler<RoutingContext> {
    private final Vertx vertx;

    public AddTransactionHandler(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        HttpServerRequest request = routingContext.request();
        request.handler(buffer -> {
            System.out.println("AddTransaction: got request to add block, BufferSize: " + buffer.length());
            HttpServerResponse response = routingContext.response();
            Boolean addStatus = Utils.addTransaction(buffer.toJsonObject());
            String status;
            if (addStatus) {
                status = "successful";
                vertx.eventBus().send(SYNC_ADDRESS, Boolean.TRUE);
                response.setStatusCode(200);
            } else {
                status = "failed";
                response.setStatusCode(500);
            }
            System.out.println("AddTransaction: "  + status);
            response.putHeader("content-type", "text/plain");
            response.end(status);
        });
    }
}
