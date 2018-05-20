/*
 * Copyright (c) 2018, kchadha
 */

package gui;import blockchain.Ledger;
import com.google.gson.GsonBuilder;
import io.vertx.core.Vertx;
import user.UserData;

import javax.swing.*;
import java.awt.*;

public class BMA {

    public JPanel mainPanel;
    private JButton viewButton;
    private JButton addButton;
    private JTextField domainField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea ledgerViewArea;
    private JTextArea logViewArea;

    public BMA(Vertx vertx) {

        mainPanel.setPreferredSize(new Dimension(600,600));
        ledgerViewArea.setEditable(false);
        logViewArea.setEditable(false);

        viewButton.addActionListener(e -> {
            ledgerViewArea.setText(new GsonBuilder().setPrettyPrinting().create().toJson(Ledger.getInstance()));
            logViewArea.append("Blockchain Size: " + Ledger.getInstance().getSize() + "\n");
        });

        addButton.addActionListener(e -> {
            String domain = domainField.getText();
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            if (domain.isEmpty()){
                JOptionPane.showMessageDialog(mainPanel, "Domain cannot be empty", "Bad Data",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.isEmpty()){
                JOptionPane.showMessageDialog(mainPanel, "Username cannot be empty", "Bad Data",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length == 0){
                JOptionPane.showMessageDialog(mainPanel, "Password cannot be empty", "Bad Data",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Boolean status = Ledger.getInstance().createTransaction(new UserData(
                    domain, username, password.toString()));
            logViewArea.append("Add Transaction: " + status + "\n");
        });
    }
}
