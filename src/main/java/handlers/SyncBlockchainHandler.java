/*
 * Copyright (c) 2018, kchadha
 */

package handlers;

import blockchain.Ledger;
import blockchain.Utils;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;


public class SyncBlockchainHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        HttpServerRequest request = routingContext.request();
        request.handler(buffer -> {
            System.out.println("SyncBlockchain: Got request to sync ledger");
            System.out.println(buffer.toString());
            Boolean status = Utils.syncLedger(new JsonArray(buffer.toString()));
            if(status){
                System.out.println("SyncBlockchain: Successful");
            }else{
                System.out.println("SyncBlockchain: Failed");
            }
        });
        HttpServerResponse response = routingContext.response();
        response.setStatusCode(204);
        response.end();
    }
}
