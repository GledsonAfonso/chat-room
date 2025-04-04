package com.gledsonafonso.chatroomclient.service;

import java.io.IOException;
import java.net.Socket;

public class ConnectionService implements AutoCloseable {
  private Socket socket;
  private MessageService messageService;

  public void start(String ip, int port) throws IOException {
    this.socket = new Socket(ip, port);
    this.messageService = new MessageService(this.socket);
  }

  public String sendMessage(String message) throws IOException {
    return this.messageService.sendMessage(message);
  }
  
  @Override
  public void close() throws Exception {
    this.messageService.close();
    this.socket.close();

    System.out.println("Connection closed.");
  }
}