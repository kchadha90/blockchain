/*
 * Copyright (c) 2018, kchadha
 */

package network;
import blockchain.Ledger;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.json.JsonArray;

/**
 *
 */
public class VertxHttpClient extends AbstractVerticle {
    public static final String SYNC_ADDRESS = "sync_address";
    private static final int TO_NODE = 8081;
    public static final int NODE = 8080;
    private HttpClient httpClient;

    @Override
    public void start() {

        HttpClientOptions options = new HttpClientOptions()
                .setTcpKeepAlive(true)
                .setReceiveBufferSize(1024 * 1000 * 32)
                .setKeepAlive(true)
                .setMaxChunkSize(1024 * 1000 * 32);

        this.httpClient = vertx.createHttpClient(options);
        vertx.eventBus().consumer(SYNC_ADDRESS, msg -> {
            pushLedgerToNodes();
        });
    }

    /**
     *
     */
    private void pushLedgerToNodes(){
        HttpClientRequest request = this.httpClient.post(TO_NODE, "localhost", "/sync");
        request.handler(new Handler<HttpClientResponse>() {
            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                    System.out.println("HttpClient: pushed ledger across available nodes, " + httpClientResponse.statusCode());
            }
        });
        String body = Ledger.getInstance().toString();
        JsonArray data = new JsonArray(body);
        request.putHeader("content-length", Integer.toString(body.getBytes().length));
        request.putHeader("content-type", "application/json");
        request.write(data.encode());
        request.end();
    }
}
