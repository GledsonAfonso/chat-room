package com.gledsonafonso.chatroomclient.service.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.gledsonafonso.chatroomclient.utils.MessageUtils;

public class ServerConnection implements AutoCloseable {
  private Socket socket;
  private BufferedReader reader;
  private PrintWriter writer;
  private String clientId;

  public ServerConnection(String ip, int port) throws IOException {
    this.socket = new Socket(ip, port);
    this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    this.writer = new PrintWriter(this.socket.getOutputStream(), true);
    this.clientId = this.getRemoteId();
  }

  public BufferedReader getReader() {
    return reader;
  }

  public PrintWriter getWriter() {
    return writer;
  }

  public String getClientId() {
    return clientId;
  }
  
  @Override
  public void close() throws Exception {
    this.writer.println(MessageUtils.EXIT_MESSAGE);

    this.reader.close();
    this.writer.close();
    this.socket.close();

    System.out.println("Connection closed.");
  }

  private String getRemoteId() throws IOException {
    this.writer.println(MessageUtils.GET_REMOTE_ID_COMMAND);

    var serverResponse = this.reader.readLine();
    if(serverResponse != null && serverResponse.contains(MessageUtils.REMOTE_ID_PREFIX)) {
      var remoteId = serverResponse.split(MessageUtils.REMOTE_ID_PREFIX)[1];
      System.out.println("You're logged as: " + remoteId);
      System.out.println("To exit, press ctrl + c \n\n");
      
      return remoteId;
    }

    throw new RuntimeException("Couldn't set remote id.");
  }
}