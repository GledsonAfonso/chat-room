package com.gledsonafonso.chatroomserver.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicationService implements AutoCloseable {
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private MessageService messageService;

  public void start(int port) throws IOException {
    this.serverSocket = new ServerSocket(port);

    System.out.println("Waiting for client connection...");
    this.clientSocket = this.serverSocket.accept();

    System.out.println(
        String.format("""
              Connection stabilished.
                Host Address: %s
                Host name: %s
            """,
            this.clientSocket.getInetAddress().getHostAddress(),
            this.clientSocket.getInetAddress().getHostName()));

    this.messageService = new MessageService(this.clientSocket);
    this.messageService.processMessage();
  }

  @Override
  public void close() throws Exception {
    this.messageService.close();
    this.clientSocket.close();
    this.serverSocket.close();

    System.out.println("Communication closed.");
  }
}