/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networks_project;

/**
 *
 * @author megas
 */
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClientHandler {
    private String username;
    private PrintWriter out;
    
    // A Set to hold subscribed users
    private static Set<String> subscribedUsers = new HashSet<>();
    
    // A Map to hold username to PrintWriter mapping
    private static Map<String, PrintWriter> userWriters = new HashMap<>();

    public ClientHandler(String username, PrintWriter out) {
        this.username = username;
        this.out = out;
        // Register this user in the mapping
        userWriters.put(username, out);
    }

    // Method to add the client to the subscriber list
    public void subscribe() {
        subscribedUsers.add(username);
        System.out.println(username + " subscribed.");
        // Optionally, save this subscription to the database here
    }

    // Method to send messages to subscribed users
    public static void broadcastMessage(String message) {
        System.out.println("Broadcasting message: " + message);
        for (String subscriber : subscribedUsers) {
            PrintWriter subscriberOut = userWriters.get(subscriber);
            if (subscriberOut != null) {
                subscriberOut.println(message);
                System.out.println("Sent to " + subscriber);
            }
        }
    }

    // Method to remove a client from the subscriber list
    public void unsubscribe() {
        subscribedUsers.remove(username);
        userWriters.remove(username);
        System.out.println(username + " unsubscribed.");
    }

    // Utility method to get PrintWriter for a specific user
    public static PrintWriter getPrintWriterForUser(String username) {
        return userWriters.get(username);
    }
}
