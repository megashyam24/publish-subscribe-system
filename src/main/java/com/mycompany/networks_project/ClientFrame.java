/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.networks_project;

import java.awt.Desktop;
import java.io.*;
import java.util.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author megas
 */
public class ClientFrame extends javax.swing.JFrame {
 private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9090;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private DefaultListModel model;
    private String serverAddr;
    private int port;
    public SocketClient client; // Declare client
private Thread clientThread; 
private boolean openFileButtonClicked = false;
 private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Replace with your DB name
    private static final String USERNAME = "system"; // Replace with your DB username
    private static final String PASSWORD = "karmegam"; // Replace with your DB password
// Declare thread
    Connection connect = null;
public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Establish the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException ex) {
         Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
     }
        return connection;
    }
    /**
     * Creates new form ClientFrame
     */
    public ClientFrame() {
        initComponents();
    }
 public String getServerAddr() {
        return serverAddr;
    }

    public int getPort() {
        return port;
    }
    public void updateChatArea(String message) {
        jTextArea1.append(message + "\n");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MessageField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        signupButton = new javax.swing.JButton();
        PasswordField1 = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        ConfirmButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        connectButton = new javax.swing.JButton();
        usernameField = new javax.swing.JTextField();
        NewsButton = new javax.swing.JRadioButton();
        SportsButton = new javax.swing.JRadioButton();
        EntertainmentButton = new javax.swing.JRadioButton();
        NationalButton = new javax.swing.JRadioButton();
        InternationalButton = new javax.swing.JRadioButton();
        OpenFileButton = new javax.swing.JButton();

        MessageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MessageFieldActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("Password :");

        jLabel4.setText("Username :");

        signupButton.setText("SignUp");
        signupButton.setEnabled(false);
        signupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupButtonActionPerformed(evt);
            }
        });

        PasswordField1.setEnabled(false);
        PasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordField1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jList1.setModel((model = new DefaultListModel()));
        jScrollPane2.setViewportView(jList1);

        jLabel5.setText("Subscribe To :");

        ConfirmButton.setText("Confirm");
        ConfirmButton.setEnabled(false);
        ConfirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmButtonActionPerformed(evt);
            }
        });

        loginButton.setText("Login");
        loginButton.setEnabled(false);
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Host Address : ");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Host Port : ");

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        usernameField.setEnabled(false);
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        NewsButton.setText("News");
        NewsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewsButtonActionPerformed(evt);
            }
        });

        SportsButton.setText("Sports");

        EntertainmentButton.setText("Entertainment");

        NationalButton.setText("National");

        InternationalButton.setText("International");

        OpenFileButton.setText(" Exit");
        OpenFileButton.setEnabled(false);
        OpenFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenFileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ConfirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(OpenFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(11, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(NewsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SportsButton)
                        .addGap(18, 18, 18)
                        .addComponent(EntertainmentButton)
                        .addGap(18, 18, 18)
                        .addComponent(NationalButton)
                        .addGap(18, 18, 18)
                        .addComponent(InternationalButton)
                        .addGap(36, 36, 36))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator2)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(usernameField)
                                .addComponent(jTextField1))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField2)
                                .addComponent(PasswordField1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(connectButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(signupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewsButton)
                    .addComponent(SportsButton)
                    .addComponent(EntertainmentButton)
                    .addComponent(NationalButton)
                    .addComponent(InternationalButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ConfirmButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(OpenFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(connectButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(signupButton)
                        .addComponent(PasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(loginButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(323, 323, 323)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupButtonActionPerformed
        SignupFrame signupFrame = new SignupFrame();
    signupFrame.setVisible(true);
    }//GEN-LAST:event_signupButtonActionPerformed

    private void ConfirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmButtonActionPerformed
        String username = usernameField.getText().trim();
    String subscriptionType = null;

    // Determine which subscription type was selected based on button clicks
    if (EntertainmentButton.isSelected()) {
        subscriptionType = "Entertainment";
    } else if (NationalButton.isSelected()) {
        subscriptionType = "National";
    } else if (NewsButton.isSelected()) {
        subscriptionType = "News";
    } else if (SportsButton.isSelected()) {
        subscriptionType = "Sports";
    } else if (InternationalButton.isSelected()) {
        subscriptionType = "International";
    }

    // Proceed only if a subscription type is selected and username is not empty
    if (subscriptionType != null && !username.isEmpty()) {
        client.sendMsg("subscribe:" + subscriptionType);
        String userIdQuery = "SELECT id FROM userss WHERE username = ?";
        String insertQuery = "INSERT INTO subscriptions (user_id, subscription) VALUES (?, ?)";
        
        // Get the database connection
        try (Connection conn = getConnection();  // Use your existing method to get the connection
             PreparedStatement userIdStmt = conn.prepareStatement(userIdQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Get user ID based on the username
            userIdStmt.setString(1, username);
            ResultSet rs = userIdStmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id"); // Retrieve user ID

                // Insert subscription into the subscriptions table
                insertStmt.setInt(1, userId); // Set user ID
                insertStmt.setString(2, subscriptionType); // Set subscription type
                insertStmt.executeUpdate(); // Execute the insert
                 
                // Optionally show a confirmation message
                JOptionPane.showMessageDialog(this, "Subscription added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "User not found. Please check the username.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
            JOptionPane.showMessageDialog(this, "Error adding subscription: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a subscription type and enter a username.");
    }
    }//GEN-LAST:event_ConfirmButtonActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
  String username = usernameField.getText();
    String password = new String(PasswordField1.getPassword());
    Connection connection = getConnection();
    
    // Check if the connection is established
    if (connection == null) {
        updateChatArea("[Application > Me] : Database connection is not established.\n");
        return; // Exit the method if the connection is not established
    }

    // Validate login against the database
    try {
        String sql = "SELECT * FROM userss WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            JOptionPane.showMessageDialog(null, "Login Successful");

            // Send the username to the server after successful login
            if (out != null) {
                out.println("USER:" + username); // Send the username to the server
                out.flush();
            }

            // Continue with chat functionality
            ConfirmButton.setEnabled(true);
            signupButton.setEnabled(false);
            loginButton.setEnabled(false);
            usernameField.setEnabled(false);
            PasswordField1.setEnabled(false);
            
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password, please sign up.");
            usernameField.setText("");
            PasswordField1.setText("");
        }
    } catch (SQLException e) {
        updateChatArea("[Application > Me] : Login failed. " + e.getMessage() + "\n");
    }
        
    }//GEN-LAST:event_loginButtonActionPerformed

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
       serverAddr = jTextField1.getText();
        String portString = jTextField2.getText();

        // Validate the port number
        try {
            port = Integer.parseInt(portString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid port number. Please enter a valid number.");
            return;
        }

        try {
            // Establish the socket connection
            socket = new Socket(serverAddr, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start the client thread to listen for messages from the server
            client = new SocketClient();
            clientThread = new Thread(client);
            clientThread.start();

            JOptionPane.showMessageDialog(this, "Connected to the server!");

            // Enable the message sending functionality
            ConfirmButton.setEnabled(true);
            OpenFileButton.setEnabled(true);
            signupButton.setEnabled(true);
            loginButton.setEnabled(true);
            usernameField.setEnabled(true);
            PasswordField1.setEnabled(true);
            jTextField1.setEnabled(false);
            jTextField2.setEnabled(false);
            connectButton.setEnabled(false);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to connect to the server: " + e.getMessage());
        }
    }//GEN-LAST:event_connectButtonActionPerformed
 private class SocketClient implements Runnable {
        public void run() {
            String response;
            try {
                while ((response = in.readLine()) != null) {
                    updateChatArea(response); // Update chat area with received messages
                }
            } catch (IOException e) {
                updateChatArea("[Server > Me] : Connection lost.\n");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    updateChatArea("[Application > Me] : Error closing the socket: " + e.getMessage() + "\n");
                }
            }
        }

        private void sendMessage(String string) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private void sendMsg(String message) {
              if (out != null) {
        out.println(message);
        out.flush(); // Make sure to flush to send the message immediately
        System.out.println("Sent: " + message);
    } else {
        System.err.println("Output stream is not initialized.");
    }

        }
    }
 
    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void PasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordField1ActionPerformed

    private void MessageFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MessageFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MessageFieldActionPerformed

    private void NewsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NewsButtonActionPerformed

    private void OpenFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenFileButtonActionPerformed
  String tex = jTextArea1.getText();

        // Split the text into individual lines
        String[] lines = tex.split("\\r?\\n");

        String filePath = null;

        // Loop through the lines to find the one that contains the file path
        for (String line : lines) {
            if (line.startsWith("File_path:")) {
                // Extract the file path after "File_path:"
                filePath = line.substring("File_path:".length()).trim();
                break; // Exit the loop once we find the file path
            }
        }

        // Check if the file path was found and is not null
        if (filePath != null) {
            // Create a File object with the extracted file path
            File file = new File(filePath);

            // Check if the file exists before trying to open it
            if (file.exists()) {
                // Call the openFile() method from SocketClient to open the file
                openFile(file);
                System.out.println("Opening file: " + filePath);
            } else {
                System.err.println("File not found: " + filePath);
                JOptionPane.showMessageDialog(this, "File not found: " + filePath, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.err.println("No valid file path found in the text.");
            JOptionPane.showMessageDialog(this, "No valid file path found in the text.", "Error", JOptionPane.ERROR_MESSAGE);
        }        // Get the text from the text area

    }//GEN-LAST:event_OpenFileButtonActionPerformed
 public boolean isOpenFileButtonClicked() {
        return openFileButtonClicked;
 } private void openFile(File file) {
    if (Desktop.isDesktopSupported()) {
        try {
            Desktop.getDesktop().open(file);
            System.out.println("Opened file: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
        } catch (UnsupportedOperationException e) {
            System.err.println("The file type is not supported or there's no associated application: " + e.getMessage());
        }
    } else {
        System.err.println("Desktop is not supported, cannot open file.");
    }
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton ConfirmButton;
    private javax.swing.JRadioButton EntertainmentButton;
    private javax.swing.JRadioButton InternationalButton;
    public javax.swing.JTextField MessageField;
    private javax.swing.JRadioButton NationalButton;
    private javax.swing.JRadioButton NewsButton;
    private javax.swing.JButton OpenFileButton;
    public javax.swing.JPasswordField PasswordField1;
    private javax.swing.JRadioButton SportsButton;
    public javax.swing.JButton connectButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    public javax.swing.JTextArea jTextArea1;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField2;
    public javax.swing.JButton loginButton;
    public javax.swing.JButton signupButton;
    public javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
