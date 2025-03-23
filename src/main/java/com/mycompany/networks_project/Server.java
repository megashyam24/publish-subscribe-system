package com.mycompany.networks_project;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int SERVER_PORT = 12345;
    private static final Map<String, List<PrintWriter>> topicSubscribers = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Server is running...");
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void publishToTopic(String topic1, String hello_to_Topic_1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.startsWith("subscribe:")) {
                        String topic = inputLine.split(":")[1];
                        subscribeToTopic(topic, out);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void subscribeToTopic(String topic, PrintWriter clientOut) {
            topicSubscribers.putIfAbsent(topic, new ArrayList<>());
            topicSubscribers.get(topic).add(clientOut);
            System.out.println("Client subscribed to topic: " + topic);
            clientOut.println("Subscribed to topic: " + topic);
        }

        // Publish messages to a specific topic
        public static void publishToTopic(String topic, String message) {
            List<PrintWriter> subscribers = topicSubscribers.get(topic);
            if (subscribers != null) {
                for (PrintWriter subscriber : subscribers) {
                    subscriber.println("Message for " + topic + ": " + message);
                }
            }
        }
    }

    // Example method to publish a message to a specific topic
    public static void examplePublish() {
        // Simulating publishing messages to topics
        publishToTopic("topic1", "Hello to Topic 1!");
        publishToTopic("topic2", "Hello to Topic 2!");
        publishToTopic("topic3", "Hello to Topic 3!");
    }
}
