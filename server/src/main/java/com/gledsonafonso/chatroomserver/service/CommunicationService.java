package com.gledsonafonso.chatroomserver.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicationService {
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port);

    System.out.println("Waiting for client connection...");
    clientSocket = serverSocket.accept();

    System.out.println(
      String.format("""
        Connection stabilished.
          Host Address: %s
          Host name: %s 
      """,
      clientSocket.getInetAddress().getHostAddress(),
      clientSocket.getInetAddress().getHostName()
      )
    );

    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    var clientMessage = in.readLine(); // receives message sent by the client
    System.out.println("Client say: " + clientMessage);
    
    // sending response
    if ("hello server".equals(clientMessage)) {
      out.println("hello client");
    } else {
      out.println("unrecognised greeting");
    }
  }

  public void stop() {
    try {
      in.close();
      out.close();
      clientSocket.close();
      serverSocket.close();
    } catch (Exception exception) {
      System.out.println("Error while stopping the communication with the client: " + exception.getMessage());
    }
  }
}