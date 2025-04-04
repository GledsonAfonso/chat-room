package com.gledsonafonso.chatroomclient.service;

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

  public String sendMessage(String message) throws IOException {
    // sending message to server
    this.writer.println(message);

    // receiving response from server
    return this.reader.readLine();
  }

  public void close() throws IOException {
    this.reader.close();
    this.writer.close();
  }
}
