/*
 * Copyright (c) 2018, kchadha
 */

package network;
import handlers.SyncBlockchainHandler;
import handlers.TestTransactionHandler;
import handlers.ViewBlockchainHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class VertxHttpServer extends AbstractVerticle {

    private static final String ABOUT_URI = "/about";
    private static final String TEST_URI = "/test";
    private static final String VIEW_URI = "/view";
    private static final String SYNC_URI = "/sync";

    @Override
    public void start(Future<Void> fut) {

        // Create a router object.
        Router router = Router.router(vertx);

        // create the REST endpoints with their handler
        router.route(ABOUT_URI).handler(new aboutHandler());
        router.route(TEST_URI).blockingHandler(new TestTransactionHandler(vertx));
        router.route(VIEW_URI).handler(new ViewBlockchainHandler());
        router.route(SYNC_URI).handler(new SyncBlockchainHandler());

        // TODO set options
        HttpServerOptions options = new HttpServerOptions();

        vertx.createHttpServer(options)
                .requestHandler(router::accept)
                .listen(
                        // Retrieve the port from the configuration,
                        // default to 8080.
                        config().getInteger("http.port", 8080
                        ),
                        result -> {
                            if (result.succeeded()) {
                                fut.complete();
                            } else {
                                fut.fail(result.cause());
                            }
                        }
                );
    }

    private class aboutHandler implements Handler<RoutingContext> {
        @Override
        public void handle(RoutingContext routingContext) {
            HttpServerResponse response = routingContext.response();
            response.setStatusCode(200);
            response.putHeader("content-type", "text/plain")
                    .end("Ledger Demo !!!");
        }
    }
}