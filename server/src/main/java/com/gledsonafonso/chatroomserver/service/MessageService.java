package com.gledsonafonso.chatroomserver.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageService extends Thread {
  private String id;
  private BufferedReader reader;
  private PrintWriter writer;

  public MessageService(Socket socket) throws IOException {
    this.id = "user-" + socket.getRemoteSocketAddress().toString();
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.writer = new PrintWriter(socket.getOutputStream(), true);

    System.out.println(this.id + " logged in.");
  }

  @Override
  public void run() {
    try {
      this.processMessage();
      this.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void processMessage() {
    // receives message sent by the client
    String clientMessage;
    while((clientMessage = this.readLine()) != null) {
      if(clientMessage.contains("get--remote-id")) {
        this.writer.println("remote-id--" + this.id);
        continue;
      } else if (clientMessage.equals("exit")) {
        this.writer.println("goodbye");
        break;
      }

      this.writer.println(this.id + ": " + clientMessage);
    }
  }

  private String readLine() {
    try {
      return this.reader.readLine();
    } catch (IOException e) {
      if(e.getMessage().contains("Stream closed")) {
        return null;
      }

      throw new RuntimeException(e);
    }
  }

  private void close() throws IOException {
    this.reader.close();
    this.writer.close();

    System.out.println("Client " + this.id + " disconnected.");
  }
}
