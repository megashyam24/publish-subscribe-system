package com.mycompany.networks_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client() {
        // Setting up the GUI
        JFrame frame = new JFrame("Topic Subscriber");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // Panel to hold radio buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        // Define topics
        String[] topics = {"topic1", "topic2", "topic3"};
        ButtonGroup group = new ButtonGroup();

        // Create radio buttons for each topic
        for (String topic : topics) {
            JRadioButton radioButton = new JRadioButton(topic);
            radioButton.setActionCommand(topic); // Set action command to topic name
            group.add(radioButton);
            panel.add(radioButton);
        }

        // Button to subscribe
        JButton subscribeButton = new JButton("Subscribe");
        subscribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTopic = group.getSelection() != null ? group.getSelection().getActionCommand() : null;
                if (selectedTopic != null) {
                    out.println("subscribe:" + selectedTopic);
                    JOptionPane.showMessageDialog(frame, "Subscribed to " + selectedTopic);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a topic to subscribe.");
                }
            }
        });

        // Text area to display server messages
        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        
        // Adding components to frame
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(subscribeButton, BorderLayout.CENTER);
        frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);
        frame.setVisible(true);

        // Setup socket and I/O
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Create a thread to listen for messages from the server
            new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        messageArea.append("Server: " + serverMessage + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }
}
