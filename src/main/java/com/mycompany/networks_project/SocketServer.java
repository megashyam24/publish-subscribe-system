package com.mycompany.networks_project;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketServer {
    private static final int SERVER_PORT = 9090;
    public final ServerFrame serverFrame;
    private static final Map<String, List<Socket>> topicSubscribers = new HashMap<>();
    private static final Set<String> loggedInUsers = new HashSet<>();
    private final List<Socket> clientSockets = new ArrayList<>();

    public SocketServer(ServerFrame serverFrame) {
        this.serverFrame = serverFrame;
        startServer();
    }

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                serverFrame.jTextArea1.append("Server is running on port " + SERVER_PORT + "...\n");
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new ClientHandler(clientSocket).start();
                }
            } catch (IOException e) {
                serverFrame.jTextArea1.append("Error starting server: " + e.getMessage() + "\n");
            }
        }).start();
    }

    private void subscribeToTopic(String topic, Socket clientSocket) {
        topicSubscribers.putIfAbsent(topic, new ArrayList<>());
        topicSubscribers.get(topic).add(clientSocket);
        serverFrame.jTextArea1.append("Client subscribed to topic: " + topic + "\n");
    }

    private void publishToTopic(String topic, String message) {
        List<Socket> subscribers = topicSubscribers.get(topic);
        if (subscribers != null) {
            for (Socket subscriberSocket : subscribers) {
                try {
                    PrintWriter writer = new PrintWriter(subscriberSocket.getOutputStream(), true);
                    writer.println("Message on " + topic + ": " + message);
                    writer.flush();
                } catch (IOException e) {
                    serverFrame.jTextArea1.append("Error sending message: " + e.getMessage() + "\n");
                }
            }
            serverFrame.jTextArea1.append("Published message to topic " + topic + ": " + message + "\n");
        } else {
            serverFrame.jTextArea1.append("No subscribers for topic: " + topic + "\n");
        }
    }

    private void updateUserList() {
        serverFrame.jTextArea1.setText("Logged in users:\n");
        synchronized (loggedInUsers) {
            for (String user : loggedInUsers) {
                serverFrame.jTextArea1.append(user + "\n");
            }
        }
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Register the client socket for messages and file transfer
                synchronized (clientSockets) {
                    clientSockets.add(socket);
                }

                // Get the username from the client
                username = in.readLine();
                if (username != null && !username.trim().isEmpty()) {
                    synchronized (loggedInUsers) {
                        loggedInUsers.add(username);
                    }
                    updateUserList();

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        serverFrame.jTextArea1.append("Received: " + inputLine + "\n");
                        handleClientMessage(inputLine, socket);
                    }
                }
            } catch (IOException e) {
                serverFrame.jTextArea1.append("Error handling client: " + e.getMessage() + "\n");
            } finally {
                try {
                    if (username != null) {
                        synchronized (loggedInUsers) {
                            loggedInUsers.remove(username);
                        }
                        updateUserList();
                    }
                    synchronized (clientSockets) {
                        clientSockets.remove(socket);
                    }
                    socket.close();
                } catch (IOException e) {
                    serverFrame.jTextArea1.append("Error closing socket: " + e.getMessage() + "\n");
                }
            }
        }

        private void handleClientMessage(String message, Socket clientSocket) {
            if (message.startsWith("subscribe:")) {
                String topic = message.split(":", 2)[1].trim();
                subscribeToTopic(topic, clientSocket);
            } else if (message.startsWith("publish:")) {
                String[] parts = message.split(":", 3);
                if (parts.length == 3) {
                    String topic = parts[1].trim();
                    String msg = parts[2].trim();
                    publishToTopic(topic, msg);
                } else {
                    serverFrame.jTextArea1.append("Invalid publish message format: " + message + "\n");
                }
            } else {
                serverFrame.jTextArea1.append("Unknown command: " + message + "\n");
            }
        }
    }

    public void sendMessage(String topic, String message) {
        synchronized (topicSubscribers) {
            List<Socket> subscribers = topicSubscribers.get(topic);
            if (subscribers != null && !subscribers.isEmpty()) {
                for (Socket subscriberSocket : subscribers) {
                    try {
                        PrintWriter writer = new PrintWriter(subscriberSocket.getOutputStream(), true);
                        String filePath = serverFrame.getJTextField3().getText();
                        writer.println("Message on " + topic + ": " + message);
                        writer.flush();

                        if (filePath != null && !filePath.trim().isEmpty()) {
                            sendFileToSubscriber(subscriberSocket, filePath);
                        }
                    } catch (IOException e) {
                        serverFrame.jTextArea1.append("Error sending message: " + e.getMessage() + "\n");
                    }
                }
                serverFrame.jTextArea1.append("Message sent to topic " + topic + ": " + message + "\n");
            } else {
                serverFrame.jTextArea1.append("No subscribers for topic: " + topic + "\n");
            }
        }
    }

    private void sendFileToSubscriber(Socket clientSocket, String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try {
                OutputStream out = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(out, true);
                
                // Send initial file metadata
                writer.println("File: " + file.getName());
                writer.println("File size: " + file.length());
                writer.flush();

                // Transfer file content
                try (FileInputStream fis = new FileInputStream(file);
                     BufferedOutputStream bos = new BufferedOutputStream(out)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                    bos.flush();
                }
                serverFrame.jTextArea1.append("File sent: " + filePath + "\n");
            } catch (IOException e) {
                serverFrame.jTextArea1.append("Error sending file: " + e.getMessage() + "\n");
            }
        } else {
            serverFrame.jTextArea1.append("File not found: " + filePath + "\n");
        }
    }
}
