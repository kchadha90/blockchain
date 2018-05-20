/*
 * Copyright (c) 2018, kchadha
 */

package handlers;

import blockchain.Ledger;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import user.UserData;

import static network.VertxHttpClient.SYNC_ADDRESS;

/**
 *
 */
public class TestTransactionHandler implements Handler<RoutingContext> {

    private final Vertx vertx;
    public TestTransactionHandler(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void handle(RoutingContext routingContext) {

        Ledger ledger = Ledger.getInstance();
        Boolean success = ledger.createTransaction(
                new UserData("exajhghjgfjgfjhfgmple", "chadha", "test123"));
        HttpServerResponse response = routingContext.response();

        String status;
        if (success) {
            vertx.eventBus().send(SYNC_ADDRESS, Boolean.TRUE);
            response.setStatusCode(200);
            status = "Successful";
        } else {
            response.setStatusCode(500);
            status = "Failed";
        }

        System.out.println("TestTransaction: " + status);
        response.putHeader("content-type", "text/plain")
                .end(status);
    }
}
