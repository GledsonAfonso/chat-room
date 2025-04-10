package com.gledsonafonso.chatroomserver.service.connection;

import java.io.IOException;
import java.net.ServerSocket;

import com.gledsonafonso.chatroomserver.service.message.MessagePublisher;

public class CommunicationService implements AutoCloseable {
  private ServerSocket serverSocket;

  public void start(int port) throws IOException {
    this.serverSocket = new ServerSocket(port);

    System.out.println("Waiting for client connections...");

    while(true) {
      var connection = new ClientConnection(this.serverSocket.accept());
      MessagePublisher.addSubscriber(connection);

      connection.start();
    }
  }

  @Override
  public void close() throws Exception {
    this.serverSocket.close();

    System.out.println("Communication closed.");
  }
}