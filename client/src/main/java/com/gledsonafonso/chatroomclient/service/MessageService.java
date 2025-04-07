package com.gledsonafonso.chatroomclient.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageService {
  private String id;
  private BufferedReader reader;
  private PrintWriter writer;

  public MessageService(Socket socket) throws IOException {
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.writer = new PrintWriter(socket.getOutputStream(), true);
    this.id = this.getRemoteId();
  }

  public String getId() {
    return this.id;
  }

  public String sendMessage(String message) throws IOException {
    // sending message to server
    this.writer.println(message);

    // receiving response from server
    var serverResponse = this.reader.readLine();

    if (serverResponse != null && serverResponse.contains(this.id)) {
      return "";
    }

    return serverResponse;
  }

  public void close() throws IOException {
    this.reader.close();
    this.writer.close();
  }

  private String getRemoteId() throws IOException {
    this.writer.println("get--remote-id");

    var serverResponse = this.reader.readLine();
    if(serverResponse != null && serverResponse.contains("remote-id--")) {
      var remoteId = serverResponse.split("remote-id--")[1];
      System.out.println("You're logged as: " + remoteId + "\n\n");
      
      return remoteId;
    }

    throw new RuntimeException("Couldn't set remote id.");
  }
}
