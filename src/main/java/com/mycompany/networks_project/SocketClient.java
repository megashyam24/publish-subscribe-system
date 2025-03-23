package com.mycompany.networks_project;

import java.awt.Desktop;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class SocketClient {
    private static final String SERVER_ADDRESS = "localhost"; // Server address
    private static final int SERVER_PORT = 9090; // Server port
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
     public static String lastReceivedFilePath;
     ClientFrame c = new ClientFrame();

    // Constructor to establish a connection to the server
    public SocketClient(String address, int port) {
        try {
            socket = new Socket(address, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to server at " + address + ":" + port);
        } catch (IOException e) {
            System.err.println("Unable to connect to the server: " + e.getMessage());
        }
    }

    // Method to send a message to the server
    public void sendMsg(String message) {
        if (out != null) {
            out.println(message);
            out.flush(); // Make sure to flush to send the message immediately
            System.out.println("Sent: " + message);
        } else {
            System.err.println("Output stream is not initialized.");
        }
    }

    // Method to send a file to the server
    public void sendFile(File file) {
        try {
            out.println("sendfile:" + file.getName()); // Notify the server about the file
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 OutputStream os = socket.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = bis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
                System.out.println("File sent: " + file.getName());
            }
        } catch (IOException e) {
            System.err.println("Error sending file: " + e.getMessage());
        }
    }

    // Method to receive a message from the server
    // Method to receive a message from the server
public String receiveMessage() {
    String message = null;
    try {
        message = in.readLine();
        if (message != null) {
            if (message.startsWith("file:")) {
                // Handle file reception
                String[] parts = message.split(":", 4); // Change the split to 4 parts
                if (parts.length == 4) { // Adjust to check for 4 parts
                    String filePath = parts[1].trim(); // Get the file path
                    String fileName = parts[2].trim(); // Get the file name
                    long fileSize = Long.parseLong(parts[3].trim()); // Get the file size
                    receiveFile(filePath, fileName, fileSize); // Pass file path and name to the receiveFile method
                }
            } else {
                System.out.println("Received: " + message);
            }
        }
    } catch (IOException e) {
        System.err.println("Error receiving message: " + e.getMessage());
    }
    return message;
}

// Method to receive a file from the server
 private void receiveFile(String filePath, String fileName, long fileSize) {
    try {
        // Create a file with the full path
        File file = new File(filePath, fileName); // Combine the file path and name
        this.lastReceivedFilePath = file.getAbsolutePath();
        try (FileOutputStream fos = new FileOutputStream(file);
             InputStream is = socket.getInputStream()) {
            byte[] buffer = new byte[4096];
            long totalBytesRead = 0;
            int bytesRead;
            while (totalBytesRead < fileSize && (bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }
            System.out.println("File received: " + file.getAbsolutePath() + " (Size: " + fileSize + " bytes)");

            // Check if the OpenFileButton has been clicked
            if (c.isOpenFileButtonClicked()) {
                openFile(file); // Open the file if the button was clicked
            }
        }
    } catch (IOException e) {
        System.err.println("Error receiving file: " + e.getMessage());
    }
}

public static String getLastReceivedFilePath() {
        return lastReceivedFilePath;
    }
// Method to open the file using the default application
private void openFile(File file) {
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


    // Method to close the connection and resources
    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }

    // Main method to run the client
    public static void main(String[] args) {
        SocketClient client = new SocketClient(SERVER_ADDRESS, SERVER_PORT);

        // Handle incoming messages in a separate thread
        new Thread(() -> {
            while (true) {
                String response = client.receiveMessage();
                if (response == null) {
                    break; // Exit loop if response is null (e.g., server closed connection)
                }
            }
        }).start();

        // Allow the user to subscribe or publish messages
        String username = JOptionPane.showInputDialog("Enter your username:");
        if (username != null) {
            client.sendMsg(username);
        }

        String command;
        while (true) {
            command = JOptionPane.showInputDialog("Enter command (subscribe:<topic>, publish:<topic>:<message>, sendfile:<filename>, exit):");
            if (command != null && command.equalsIgnoreCase("exit")) {
                break; // Exit command
            } else if (command.startsWith("sendfile:")) {
                String fileName = command.split(":", 2)[1].trim();
                File file = new File(fileName);
                if (file.exists()) {
                    client.sendFile(file); // Call method to send file
                } else {
                    System.err.println("File not found: " + fileName);
                }
            } else {
                client.sendMsg(command); // Send other commands
            }
        }

        client.close(); // Close the client connection
    }
}
