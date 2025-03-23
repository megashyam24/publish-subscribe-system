package com.mycompany.networks_project;

public class Message {
    private String content;
    private String sender;
    private String receiver;
    private String type;

    public Message(String content, String sender, String receiver, String type) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
    }

    @Override
    public String toString() {
        return type + " " + sender + " " + receiver + " " + content;
    }

    // Optional: Getters and Setters if needed
}
