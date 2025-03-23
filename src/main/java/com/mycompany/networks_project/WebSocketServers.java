/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networks_project;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.InetSocketAddress;

public class WebSocketServers extends WebSocketServer {
    private static final int PORT = 8888;

    public WebSocketServers() {
        super(new InetSocketAddress(PORT));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
        conn.send("Welcome to the WebSocket server!");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received message: " + message);
        conn.send("Echo: " + message); // Echo the message back to the client
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("An error occurred: " + ex);
    }

   
    public void onStart() {
        System.out.println("WebSocket server started on port: " + PORT);
    }

    public static void main(String[] args) {
        WebSocketServers server = new WebSocketServers();
        server.start();
        System.out.println("Server started on port: " + server.getPort());
    }
}
