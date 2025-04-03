package com.gledsonafonso.chatroomclient.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionService {
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  public void start(String ip, int port) throws IOException {
    clientSocket = new Socket(ip, port);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  public String sendMessage(String message) throws IOException {
    out.println(message); // sending message
    String resp = in.readLine(); // receiving response
    return resp;
  }

  public void stop() {
    try {
      in.close();
      out.close();
      clientSocket.close();
    } catch (Exception exception) {
      System.out.println("Error while stopping the client connection: " + exception.getMessage());
    }
  }
}