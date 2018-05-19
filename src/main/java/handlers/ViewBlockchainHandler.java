/*
 * Copyright (c) 2018, kchadha
 */

package handlers;

import blockchain.Ledger;
import com.google.gson.GsonBuilder;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;


public class ViewBlockchainHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        Ledger blockChain = Ledger.getInstance();
        System.out.println("ViewBlockchain: Got request to view ledger");
        HttpServerResponse response = routingContext.response();

        String data = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain.toString());

        response.putHeader("content-type", "text/plain")
                .end(data);
    }
}
