package com.gledsonafonso.chatroomserver.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageService {
  private BufferedReader reader;
  private PrintWriter writer;

  public MessageService(Socket socket) throws IOException {
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.writer = new PrintWriter(socket.getOutputStream(), true);
  }

  public void processMessage() {
    try {
      // receives message sent by the client
      String clientMessage;
      while((clientMessage = this.reader.readLine()) != null) {
        if ("exit".equals(clientMessage)) {
          this.writer.println("goodbye");
          break;
        }

        this.writer.println(clientMessage);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void close() throws IOException {
    this.reader.close();
    this.writer.close();
  }
}
