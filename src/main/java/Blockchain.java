/*
 * Copyright (c) 2018, kchadha
 */

import gui.BMA;
import io.vertx.core.Vertx;
import network.VertxHttpClient;
import network.VertxHttpServer;

import javax.swing.*;

public class Blockchain {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // deploy the server and client to make the node running
        // rest of the operations are controlled by REST requests
        vertx.deployVerticle(VertxHttpServer.class.getName());
        vertx.deployVerticle(VertxHttpClient.class.getName());
        SwingUtilities.invokeLater(() -> Blockchain.setup(vertx));
    }

    private static void setup(Vertx vertx) {

        // setting the style
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception ignored){}

        // initializing the main frame
        JFrame frame = new JFrame("BMA");
        frame.setContentPane(new BMA(vertx).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
