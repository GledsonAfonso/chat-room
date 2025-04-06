package com.gledsonafonso.chatroomserver.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicationService implements AutoCloseable {
  private ServerSocket serverSocket;
  private Socket clientSocket;

  public void start(int port) throws IOException {
    this.serverSocket = new ServerSocket(port);

    System.out.println("Waiting for client connections...");

    while(true) {
      new MessageService(this.serverSocket.accept()).start();
    }
  }

  @Override
  public void close() throws Exception {
    this.clientSocket.close();
    this.serverSocket.close();

    System.out.println("Communication closed.");
  }
}