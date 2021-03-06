/*
 * Copyright (c) 2018, kchadha
 */

package handlers;

import blockchain.Utils;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;


/**
 *
 */
public class SyncBlockchainHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        HttpServerRequest request = routingContext.request();
        request.handler(buffer -> {
            System.out.println("SyncBlockchain: got request to sync ledger, BufferSize: " + buffer.length());
            HttpServerResponse response = routingContext.response();
            boolean syncLedgerStatus = false;
            try {
                syncLedgerStatus = Utils.syncLedger(buffer.toJsonArray());
            } catch (Exception ex) {
                System.err.println("SyncBlockchain: failed to sync ledger !!!!");
            }
            String status;
            if (syncLedgerStatus) {
                status = "successful";
                response.setStatusCode(200);
            } else {
                status = "failed";
                response.setStatusCode(500);
            }
            System.out.println("SyncBlockchain: "  + status);
            response.putHeader("content-type", "text/plain");
            response.end(status);
        });
    }
}
